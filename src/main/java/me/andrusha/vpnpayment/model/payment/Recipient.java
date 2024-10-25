package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embeddable;

@Embeddable
public class Recipient {
    private String account_id;
    private String gateway_id;

    public Recipient(String account_id, String gateway_id) {
        this.account_id = account_id;
        this.gateway_id = gateway_id;
    }

    public Recipient() {
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_Id(String accountId) {
        this.account_id = accountId;
    }

    public String getGateway_id() {
        return gateway_id;
    }

    public void setGateway_id(String gateway_id) {
        this.gateway_id = gateway_id;
    }
}
