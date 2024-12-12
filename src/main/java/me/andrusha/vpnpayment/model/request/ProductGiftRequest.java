package me.andrusha.vpnpayment.model.request;

import me.andrusha.vpnpayment.model.shop.Product;

public class ProductGiftRequest {
    private String username;
    private Product product;
    private String paymentId;
    private String type;

    public ProductGiftRequest(String username, Product product, String paymentId, String type) {
        this.username = username;
        this.product = product;
        this.paymentId = paymentId;
        this.type = type;
    }

    public ProductGiftRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
