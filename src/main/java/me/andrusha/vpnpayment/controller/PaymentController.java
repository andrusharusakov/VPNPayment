package me.andrusha.vpnpayment.controller;

import me.andrusha.vpnpayment.model.payment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import me.andrusha.vpnpayment.model.request.UserProductRequest;
import me.andrusha.vpnpayment.model.shop.Product;
import me.andrusha.vpnpayment.service.PaymentService;
import me.andrusha.vpnpayment.service.PromocodeService;
import me.andrusha.vpnpayment.service.ShopService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@CrossOrigin(origins = {"http://localhost:3000" , "https://leafcity.vercel.app", "https://leafcity.ru", "http://91.233.43.231"})
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private PromocodeService promocodeService;


    @PostMapping("/getRedirectPayment")
    @ResponseBody
    public ResponseEntity<?> createPaymentRedirect(@RequestBody UserProductRequest userProductRequest) {
        Payment newPay = new Payment();
        Product product = shopService.getProductById(userProductRequest.getProductId());
        float promocode = promocodeService.getDiscountByCode(userProductRequest.getPromocode());
        if(product != null) {
            Long shortId = paymentService.getNextShortId();
            newPay.setShortId(shortId);
            Amount amount = new Amount(Float.toString(product.getRealPrice() * (1 - promocode)), "RUB");
            ArrayList<Item> items = new ArrayList<Item>();
            items.add(new Item(product.getName(), amount, 2, 1, "another", "commodity","full_payment" ));
            newPay.setReceipt(new Receipt(items, new Customer(userProductRequest.getEmail())));
            newPay.setAmount(amount);
            newPay.setDescription("Платеж #" + shortId + " в магазине leafcity.ru/shop за заказ товара " + product.getName() + " пользователю " + userProductRequest.getUsername());
            newPay.setCapture(true);
            newPay.setMetadata(new PaymentMeta(userProductRequest.getUsername(), product.getId(), product.getName()));
            newPay.setConfirmation(new Confirmation("redirect", "", userProductRequest.getRedirectUrl()));
            newPay = paymentService.createPayment(newPay);

            Map<String, String> response = new HashMap<>();
            response.put("confirmation_url", newPay.getConfirmation().getConfirmation_url());
            return ResponseEntity.ok(response);
        }
        return  ResponseEntity.badRequest().body("продукт не найден!");
    }
    @GetMapping("/getPayments")
    @ResponseBody
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/getPayment")
    @ResponseBody
    public Payment getPayment(@RequestParam  String id) {
        return paymentService.getPayment(id);
    }

    @GetMapping("/getLastPayments")
    @ResponseBody
    public List<Map<String, Object>> getLastPayments() throws ParseException {
        return paymentService.getLastPayments();
    }
}
