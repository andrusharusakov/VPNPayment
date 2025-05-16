package me.andrusha.vpnpayment.controller;

import java.util.Map;
import java.util.HashMap;

import me.andrusha.vpnpayment.model.payment.Payment;
import me.andrusha.vpnpayment.model.payment.PromoPayment;
import me.andrusha.vpnpayment.model.payment.Webhook;
import me.andrusha.vpnpayment.model.request.ProductGiftRequest;
import me.andrusha.vpnpayment.service.PaymentService;
import me.andrusha.vpnpayment.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.io.IOException;

@RestController()
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    ShopService shopService;
    private String tgUrl;

    public WebhookController(@Value("${url.tgBot}") String tgUrl) {
        this.tgUrl = tgUrl;
    }

    @PostMapping("/getNotify")
    @ResponseBody
    public ResponseEntity<String> getNotify(@RequestBody Webhook webhook) throws AuthenticationException, IOException {
        if (webhook.getObject() == null) {
            return ResponseEntity.ok("No payment object");
        }

        Payment payment = webhook.getObject();
        if ("canceled".equals(payment.getStatus())) {
            processCanceledPayment(payment);
            return ResponseEntity.ok("Ok");
        }
        Payment existingPayment = paymentService.getPayment(payment.getId());

        // Если платеж не найден в БД
        if (existingPayment == null) {
            // Проверяем статус и тип платежа
            String status = payment.getStatus();
            String type = payment.getMetadata() != null ? payment.getMetadata().getPaymentType() : null;

            // Если автоплатеж сразу успешен (succeeded + auto), можно создать и обработать
            if ("succeeded".equals(status) && "auto".equals(type)) {
                // Сохраняем платеж в базе, если его там нет
                paymentService.createPayment(payment);
                processSucceededPayment(payment);
                return ResponseEntity.ok("Ok");
            } else {
                // Если нет подходящих условий, логируем или просто отвечаем
                return ResponseEntity.ok("Payment not found or not auto succeeded");
            }
        }

        // Если платеж найден в базе
        String existingStatus = existingPayment.getStatus();
        String newStatus = payment.getStatus();
        String type = payment.getMetadata() != null ? payment.getMetadata().getPaymentType() : null;

        // Если был pending и стал succeeded (редиректный случай)
        if ("pending".equals(existingStatus) && "succeeded".equals(newStatus)) {
            existingPayment.setStatus("succeeded");
            paymentService.savePayment(existingPayment);
            processSucceededPayment(payment);
        }
        // Если у нас автоплатеж (auto) и сразу succeeded, просто обрабатываем
        else if ("succeeded".equals(newStatus) && "auto".equals(type)) {
            // Обновим статус, если он не установлен
            if (!"succeeded".equals(existingStatus)) {
                existingPayment.setStatus("succeeded");
                paymentService.savePayment(existingPayment);
            }
            processSucceededPayment(payment);
        }

        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/getPromoNotify")
    @ResponseBody
    public ResponseEntity<String> getPromoNotify(@RequestBody PromoPayment promoPayment) throws AuthenticationException, IOException {
        if(promoPayment != null){
            var product = shopService.getProductById(promoPayment.getProductId());
            var username = (String.valueOf(promoPayment.getUserId()));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ProductGiftRequest request = new ProductGiftRequest(username, product, null, "standart");
            HttpEntity<ProductGiftRequest> requestEntity = new HttpEntity<>(request, headers);
            restTemplate.postForObject(tgUrl, requestEntity, String.class);
        }
        return ResponseEntity.ok("Ok");
    }

    /**
     * Обработка успешного платежа: отправка запроса к Telegram и выдача товара.
     */
    private void processSucceededPayment(Payment payment) {
        var product = shopService.getProductById(payment.getMetadata().getProductId());
        var username = payment.getMetadata().getUsername();
        var paymentId = payment.getId();
        var type = payment.getMetadata().getPaymentType();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ProductGiftRequest request = new ProductGiftRequest(username, product, paymentId, type);
        HttpEntity<ProductGiftRequest> requestEntity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(tgUrl, requestEntity, String.class);
    }

    private void processCanceledPayment(Payment payment) {
        // Получаем пользователя из метаданных
        String username = payment.getMetadata() != null
            ? payment.getMetadata().getUsername()
            : "unknown";
    
        // Получаем причину отказа
        String reason = payment.getCancellationDetails() != null
            ? payment.getCancellationDetails().getReason()
            : "no reason provided";
    
        // Формируем тело запроса (можно расширить DTO, если надо)
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("reason", reason);
    
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,String>> requestEntity = new HttpEntity<>(payload, headers);
    
        // Отправляем в ваш Telegram-бот
        restTemplate.postForObject(tgUrl, requestEntity, String.class);
    }
}
