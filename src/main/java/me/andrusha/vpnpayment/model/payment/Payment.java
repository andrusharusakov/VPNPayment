package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
// Игнорируем лишние поля из JSON, чтобы не падать на unexpected properties
@JsonIgnoreProperties(ignoreUnknown = true)
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

    private String payment_method_id;

    @Embedded
    // Вот эта аннотация говорит Jackson: из JSON возьми поле "cancellation_details"
    @JsonProperty("cancellation_details")
    private CancellationDetails cancellationDetails;

    public Payment() {}

    public Payment(String id,
                   String status,
                   String description,
                   String created_at,
                   boolean test,
                   boolean paid,
                   boolean refundable,
                   boolean capture,
                   boolean save_payment_method,
                   Confirmation confirmation,
                   PaymentMeta metadata,
                   Recipient recipient,
                   Amount amount,
                   Receipt receipt,
                   String payment_method_id) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.created_at = created_at;
        this.test = test;
        this.paid = paid;
        this.refundable = refundable;
        this.capture = capture;
        this.save_payment_method = save_payment_method;
        this.confirmation = confirmation;
        this.metadata = metadata;
        this.recipient = recipient;
        this.amount = amount;
        this.receipt = receipt;
        this.payment_method_id = payment_method_id;
    }

    // --- getters & setters ---

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public boolean isSave_payment_method() {
        return save_payment_method;
    }

    public void setSave_payment_method(boolean save_payment_method) {
        this.save_payment_method = save_payment_method;
    }

    public Confirmation getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Confirmation confirmation) {
        this.confirmation = confirmation;
    }

    public PaymentMeta getMetadata() {
        return metadata;
    }

    public void setMetadata(PaymentMeta metadata) {
        this.metadata = metadata;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public CancellationDetails getCancellationDetails() {
        return cancellationDetails;
    }

    public void setCancellationDetails(CancellationDetails cancellationDetails) {
        this.cancellationDetails = cancellationDetails;
    }
}
