package me.andrusha.vpnpayment.model.payment;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.List;

@Embeddable
public class Receipt {
    @Embedded
    @ElementCollection
    List<Item> items;

    @Embedded
    Customer customer;

    public Receipt() {
    }

    public Receipt(List<Item> items, Customer customer) {
        this.items = items;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}


