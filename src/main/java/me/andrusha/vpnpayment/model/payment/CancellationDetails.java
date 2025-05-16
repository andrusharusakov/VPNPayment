package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embeddable;

@Embeddable
public class CancellationDetails {
    private String party;   // кто отменил: "payer" или "supplier"
    private String reason;  // причина отказа

    public CancellationDetails() {}

    public String getParty() {
        return party;
    }
    public void setParty(String party) {
        this.party = party;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
}
