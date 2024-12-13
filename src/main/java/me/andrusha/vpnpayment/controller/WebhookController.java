package me.andrusha.vpnpayment.controller;

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
                    var paymentId = payment.getId();
                    var type = payment.getMetadata().getPaymentType();
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    ProductGiftRequest request = new ProductGiftRequest(username, product, paymentId, type);
                    HttpEntity<ProductGiftRequest> requestEntity = new HttpEntity<>(request, headers);

                    restTemplate.postForObject(tgUrl, requestEntity, String.class);
                }
            }
        }
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/getPromoNotify")
    @ResponseBody
    public ResponseEntity<String> getPromoNotify(@RequestBody Webhook webhook) throws AuthenticationException, IOException {
        Payment payment =  webhook.getObject();
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
        return ResponseEntity.ok("Ok");
    }
}
