package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embeddable;

@Embeddable
public class PaymentMethod {
    private String type;
    private String id;
    public PaymentMethod(String type, String id) {
        this.type = type;
        this.id = id;
    }
    public PaymentMethod() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
