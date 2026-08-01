/*
 * File        : Payment.java
 * Project     : CENTRIA
 *
 * Description :
 * Payment model.
 */

package com.centria.models;

import java.sql.Date;

public class Payment {

    private int id;

    private String centreCode;

    private String centreName;

    private String phone;

    private String codeFacture;

    private Date subscriptionStart;

    private Date subscriptionEnd;

    private int durationMonths;

    private String statusPayment;
    
    private String accountStatus;
    

    public Payment() {

    }

    public int getId() {

        return id;

    }

    public void setId(int id) {

        this.id = id;

    }

    public String getCentreCode() {

        return centreCode;

    }

    public void setCentreCode(String centreCode) {

        this.centreCode = centreCode;

    }

    public String getCentreName() {

        return centreName;

    }

    public void setCentreName(String centreName) {

        this.centreName = centreName;

    }

    public String getPhone() {

        return phone;

    }

    public void setPhone(String phone) {

        this.phone = phone;

    }

    public String getCodeFacture() {

        return codeFacture;

    }

    public void setCodeFacture(String codeFacture) {

        this.codeFacture = codeFacture;

    }

    public Date getSubscriptionStart() {

        return subscriptionStart;

    }

    public void setSubscriptionStart(Date subscriptionStart) {

        this.subscriptionStart = subscriptionStart;

    }

    public Date getSubscriptionEnd() {

        return subscriptionEnd;

    }

    public void setSubscriptionEnd(Date subscriptionEnd) {

        this.subscriptionEnd = subscriptionEnd;

    }

    public int getDurationMonths() {

        return durationMonths;

    }

    public void setDurationMonths(int durationMonths) {

        this.durationMonths = durationMonths;

    }

    public String getStatusPayment() {

        return statusPayment;

    }

    public void setStatusPayment(String statusPayment) {

        this.statusPayment = statusPayment;

    }
    
    public String getAccountStatus() {

    return accountStatus;

}


public void setAccountStatus(String accountStatus) {

    this.accountStatus = accountStatus;

}

}