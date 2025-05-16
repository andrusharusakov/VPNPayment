package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Entity
// Игнорировать лишние поля из JSON, чтобы не было ошибок
@JsonIgnoreProperties(ignoreUnknown = true)
// Применить стратегию snake_case → camelCase
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Payment {
    @Id
    private String id;
    private Long shortId;
    private String status;
    private String description;
    private String createdAt;
    private boolean test;
    private boolean paid;
    private boolean refundable;
    private boolean capture;
    private boolean savePaymentMethod;

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

    @Embedded
    private CancellationDetails cancellationDetails;

    public Payment() {}

    public Payment(
        String id,
        String status,
        String description,
        String createdAt,
        boolean test,
        boolean paid,
        boolean refundable,
        boolean capture,
        boolean savePaymentMethod,
        Confirmation confirmation,
        PaymentMeta metadata,
        Recipient recipient,
        Amount amount,
        Receipt receipt,
        String paymentMethodId
    ) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.test = test;
        this.paid = paid;
        this.refundable = refundable;
        this.capture = capture;
        this.savePaymentMethod = savePaymentMethod;
        this.confirmation = confirmation;
        this.metadata = metadata;
        this.recipient = recipient;
        this.amount = amount;
        this.receipt = receipt;
        this.paymentMethodId = paymentMethodId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public boolean isSavePaymentMethod() {
        return savePaymentMethod;
    }

    public void setSavePaymentMethod(boolean savePaymentMethod) {
        this.savePaymentMethod = savePaymentMethod;
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

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public CancellationDetails getCancellationDetails() {
        return cancellationDetails;
    }

    public void setCancellationDetails(CancellationDetails cancellationDetails) {
        this.cancellationDetails = cancellationDetails;
    }
}
