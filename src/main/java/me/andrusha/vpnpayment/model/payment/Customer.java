package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embeddable;

@Embeddable
public class Customer {
    String email;

    public Customer(String email) {
        this.email = email;
    }

    public Customer() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
