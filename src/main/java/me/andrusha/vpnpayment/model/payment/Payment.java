package me.andrusha.vpnpayment.model.payment;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    private String id;
    private Long shortId;
    private String status;
    private String description;
    private String created_at;
    private boolean test;
    private boolean paid;
    private boolean refundable;
    private boolean capture;
    private boolean save_payment_method;
    @Embedded
    private Confirmation confirmation;
    @Embedded
    private PaymentMeta metadata;
    @Embedded
    private Recipient recipient;
    @Embedded
    private Amount amount;
    @Embedded
    private Receipt receipt;
    private String paymentMethodId;

    public Payment(String id, String status, String description, String created_at, boolean test, boolean paid, boolean refundable, boolean capture, boolean save_payment_method, Confirmation confirmation, PaymentMeta metadata, Recipient recipient, Amount amount, Receipt receipt, String paymentMethodId) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.created_at = created_at;
        this.test = test;
        this.paid = paid;
        this.refundable = refundable;
        this.capture = capture;
        this.confirmation = confirmation;
        this.metadata = metadata;
        this.recipient = recipient;
        this.amount = amount;
        this.receipt = receipt;
        this.paymentMethodId = paymentMethodId;
        this.save_payment_method = save_payment_method;
    }

    public Payment() {
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Confirmation getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Confirmation confirmation) {
        this.confirmation = confirmation;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getShortId() {
        return shortId;
    }

    public void setShortId(Long shortId) {
        this.shortId = shortId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public PaymentMeta getMetadata() {
        return metadata;
    }

    public void setMetadata(PaymentMeta metadata) {
        this.metadata = metadata;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public boolean isSave_payment_method() {
        return save_payment_method;
    }

    public void setSave_payment_method(boolean save_payment_method) {
        this.save_payment_method = save_payment_method;
    }
}