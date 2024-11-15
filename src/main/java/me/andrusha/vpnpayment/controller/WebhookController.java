package me.andrusha.vpnpayment.controller;

import me.andrusha.vpnpayment.model.payment.PromoPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import me.andrusha.vpnpayment.model.payment.Payment;
import me.andrusha.vpnpayment.model.payment.Webhook;
import me.andrusha.vpnpayment.model.request.ProductGiftRequest;
import me.andrusha.vpnpayment.service.PaymentService;
import me.andrusha.vpnpayment.service.ShopService;

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
        if(webhook.getObject() != null){
            Payment payment =  webhook.getObject();
            Payment existingPayment = paymentService.getPayment(webhook.getObject().getId());
            if(existingPayment.getStatus().equals("pending")) {
                existingPayment.setStatus(payment.getStatus());
                paymentService.savePayment(existingPayment);

                if (payment.getStatus().equals("succeeded")) {
                    var product = shopService.getProductById(payment.getMetadata().getProductId());
                    var username = payment.getMetadata().getUsername();
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    ProductGiftRequest request = new ProductGiftRequest(username, product);
                    HttpEntity<ProductGiftRequest> requestEntity = new HttpEntity<>(request, headers);

                    restTemplate.postForObject(tgUrl, requestEntity, String.class);
                }
            }
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
            ProductGiftRequest request = new ProductGiftRequest(username, product);
            HttpEntity<ProductGiftRequest> requestEntity = new HttpEntity<>(request, headers);

            restTemplate.postForObject(tgUrl, requestEntity, String.class);
        }
        return ResponseEntity.ok("Ok");
    }
}
