package me.andrusha.vpnpayment.service;

import me.andrusha.vpnpayment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import me.andrusha.vpnpayment.model.payment.Payment;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ShopService shopService;

    public List<Payment> getPayments(){
        return paymentRepository.findAll();
    }
    public Payment getPayment(String id){
        return paymentRepository.findById(id).orElse(null);
    }
    public void savePayment(Payment payment){
        paymentRepository.save(payment);
    }

    public Payment createPayment(Payment payment) {

        RestTemplate restTemplate = new RestTemplate();
        String shopId = System.getenv("SHOP_ID");
        String secretKey = System.getenv("SECRET_KEY");
        String IdempotenceKey = getRandomString();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = shopId + ":" + secretKey;
        byte[] encodedAuth = Base64Utils.encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.set("Idempotence-Key", IdempotenceKey);

        // Валидация email
        String defaultEmail = "fylhtqhecfrjd@gmail.com";
        String customerEmail = payment.getReceipt().getCustomer().getEmail();
        if (!isValidEmail(customerEmail)) {
            payment.getReceipt().getCustomer().setEmail(defaultEmail);
        }

        // Формируем и отправляем запрос
        HttpEntity<Payment> request = new HttpEntity<>(payment, headers);
        Payment newPayment = restTemplate.postForObject("https://api.yookassa.ru/v3/payments", request, Payment.class);

        // Сохраняем данные о новом платеже, если успешно создан
        if (newPayment != null) {
            newPayment.setShortId(payment.getShortId());
            paymentRepository.save(newPayment);
        }

        return newPayment;
    }

    public List<Map<String, Object>> getLastPayments() throws ParseException {
        List<Payment> allPayments = paymentRepository.findByStatus("succeeded");
        allPayments.sort(Comparator.comparing(Payment::getCreated_at).reversed());
        List<Payment> lastPayments = allPayments.subList(0, Math.min(10, allPayments.size()));
        List<Map<String, Object>> jsonResults = new ArrayList<>();

        for (Payment payment : lastPayments) {
            Map<String, Object> paymentJson = new HashMap<>();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date createdDate = null;

            createdDate = formatter.parse(payment.getCreated_at());


            if(createdDate != null) {
                long diffInMillis = System.currentTimeMillis() - createdDate.getTime();
                String daysAgo = getDaysAgoCount(diffInMillis);
                var existingProduct = shopService.getProductById(payment.getMetadata().getProductId());
                if (existingProduct != null) {
                    paymentJson.put("daysAgo", daysAgo);
                    paymentJson.put("username", payment.getMetadata().getUsername());
                    paymentJson.put("imageUrl", existingProduct.getImageUrl());
                    paymentJson.put("productName", existingProduct.getName());
                    paymentJson.put("key", payment.getCreated_at());
                    jsonResults.add(paymentJson);
                }
            }
        }
        return jsonResults;
    }

    private String getDaysAgoCount(long diffInMillis) {
        Long diffDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        String daysAgo ="";
        if (diffDays==0){
            daysAgo = "сегодня";
        }else if (diffDays.toString().endsWith("1")){
            daysAgo = diffDays + " день назад";
        }
        else if (diffDays.toString().endsWith("2")||diffDays.toString().endsWith("3")||diffDays.toString().endsWith("4")){
            daysAgo = diffDays + " дня назад";
        }else{
            daysAgo = diffDays + " дней назад";
        }
        return daysAgo;
    }

    private String getRandomString(){
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public Long getNextShortId() {
        Long maxShortId = paymentRepository.findMaxShortId();
        return (maxShortId == null) ? 1 : maxShortId + 1;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Регулярное выражение для проверки валидного email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
