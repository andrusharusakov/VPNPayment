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
    private String dicsordUrl;
    public WebhookController(@Value("${url.discordBot}") String discordUrl) {
        this.dicsordUrl = discordUrl;
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

                    restTemplate.postForObject(dicsordUrl, requestEntity, String.class);
                }
            }
        }
        return ResponseEntity.ok("Ok");
    }
}
