package me.andrusha.vpnpayment.model.payment;

public class Webhook {
    private String type;
    private String event;
    private Payment object;

    public Webhook(String type, String event, Payment object) {
        this.type = type;
        this.event = event;
        this.object = object;
    }

    public Webhook() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Payment getObject() {
        return object;
    }

    public void setObject(Payment payment) {
        this.object = payment;
    }
}
