package me.andrusha.vpnpayment.model.request;

import me.andrusha.vpnpayment.model.shop.Product;

public class ProductGiftRequest {
    private String username;
    private String paymentId;
    private Product product;

    public ProductGiftRequest(String username, String paymentId, Product product) {
        this.username = username;
        this.product = product;
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
}
