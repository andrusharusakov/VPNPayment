package me.andrusha.vpnpayment.model.shop;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Duration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int duration;
    private  String description;
    @OneToMany(mappedBy = "duration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Duration(Long id, int duration, String description) {
        this.id = id;
        this.duration = duration;
        this.description = description;
    }

    public Duration() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
