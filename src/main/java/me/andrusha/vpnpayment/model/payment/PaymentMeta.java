package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embeddable;

@Embeddable
public class PaymentMeta{
    private String username;
    private Long productId;
    private String metaDescription;
    private String type;

    public PaymentMeta(String username, Long productId, String metaDescription, String type) {
        this.username = username;
        this.productId = productId;
        this.metaDescription = metaDescription;
        this.type = type;
    }

    public PaymentMeta() {
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

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
