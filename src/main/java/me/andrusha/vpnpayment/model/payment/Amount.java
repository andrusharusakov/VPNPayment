package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embeddable;

@Embeddable
public class Amount {
    private String value;
    private String currency;

    public Amount(String value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Amount() {
    }

    public String getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
