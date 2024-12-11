package me.andrusha.vpnpayment.model.request;

public class UserProductRequest {
    private String username;
    private String email;
    private String promoCode;
    private Long productId;
    private String redirectUrl;
    private String paymentId;

    public UserProductRequest(String username, String email, Long productId, String redirectUrl, String promoCode, String paymentId) {
        this.username = username;
        this.email = email;
        this.productId = productId;
        this.redirectUrl = redirectUrl;
        this.promoCode = promoCode;
        this.paymentId = paymentId;
    }

    public String getPromocode() {
        return promoCode;
    }

    public void setPromocode(String promoCode) {
        this.promoCode = promoCode;
    }

    public UserProductRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
