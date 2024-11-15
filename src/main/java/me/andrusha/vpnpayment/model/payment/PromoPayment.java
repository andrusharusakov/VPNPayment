package me.andrusha.vpnpayment.model.payment;

import javax.persistence.*;

@Entity
public class PromoPayment {
    @Id
    private long userId;
    private long productId;

    public PromoPayment(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public PromoPayment() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
