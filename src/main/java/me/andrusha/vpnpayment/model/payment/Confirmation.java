package me.andrusha.vpnpayment.model.payment;

import javax.persistence.Embeddable;

@Embeddable
public class Confirmation {
    private String type;
    private String confirmation_token;
    private String confirmation_url;
    private boolean enforce = false;
    private  String  return_url;


    public Confirmation(String type, String confirmation_token, String confirmation_url, String return_url) {
        this.type = type;
        this.confirmation_token = confirmation_token;
        this.confirmation_url = confirmation_url;
        this.return_url = return_url;
    }

    public Confirmation(String type, String confirmation_url, String return_url) {
        this.type = type;
        this.confirmation_url = confirmation_url;
        this.return_url = return_url;
    }

    public Confirmation() {
    }

    public Confirmation(String type) {
        this.type = type;
    }

    public String getConfirmation_url() {
        return confirmation_url;
    }

    public void setConfirmation_url(String confirmation_url) {
        this.confirmation_url = confirmation_url;
    }

    public boolean getEnforce() {
        return enforce;
    }

    public void setEnforce(boolean enforce) {
        this.enforce = enforce;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getConfirmation_token() {
        return confirmation_token;
    }

    public void setConfirmation_token(String confirmation_token) {
        this.confirmation_token = confirmation_token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
