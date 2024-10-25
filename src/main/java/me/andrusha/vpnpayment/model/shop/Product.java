package me.andrusha.vpnpayment.model.shop;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private float price;
    @ElementCollection
    private List<Information> about;
    private float sale;
    private int orderNum;
    private Boolean canApplyPromoCode = false;
    @Transient
    private float realPrice;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "duration_id")
    private Duration duration;
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "category_id")
    private Category category;


    public Product() {
    }
    @PreRemove
    private void clearProduct(){
        this.category = null;
        this.duration = null;
    }
    public Boolean getcanApplyPromoCode() {
        return canApplyPromoCode;
    }
    public void setcanApplyPromoCode(boolean canApplyPromoCode) {
        this.canApplyPromoCode = canApplyPromoCode;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Information> getAbout() {
        return about;
    }

    public void setAbout(List<Information> about) {
        this.about = about;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public float getRealPrice() {
        return this.price - ( this.price * this.sale / 100 );
    }

    public void setRealPrice(float realPrice) {
        this.realPrice = realPrice;
    }
}
