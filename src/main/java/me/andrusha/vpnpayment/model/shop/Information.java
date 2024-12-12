package me.andrusha.vpnpayment.model.shop;

import javax.persistence.Embeddable;

@Embeddable
public class Information {
    private String name;
    private String description;

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
