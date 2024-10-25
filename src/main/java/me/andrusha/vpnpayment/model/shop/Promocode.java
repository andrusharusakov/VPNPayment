package me.andrusha.vpnpayment.model.shop;

import javax.persistence.*;

@Entity
@Table(name = "promocodes")
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private float discount;

    @Column(nullable = true)
    private String description;

    // Конструкторы
    public Promocode() {}

    public Promocode(String code, float discount, String description) {
        this.code = code;
        this.discount = discount;
        this.description = description;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

