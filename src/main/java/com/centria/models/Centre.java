package com.centria.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Centre {

    private int id;

    // Code unique du centre
    private String centreCode;

    private String name;

    private String ownerName;

    private String username;

    private String passwordHash;

    private String phone;

    private Date subscriptionStart;

    private Date subscriptionEnd;

    private String status;

    private Timestamp createdAt;

    private boolean mustChangePassword;

    private Timestamp lastLogin;

    /*
    ==================================================
    SUBSCRIPTION DURATION
    ==================================================
    */

    private int durationMonths;


    /*
    ==================================================
    CONSTRUCTOR
    ==================================================
    */

    public Centre() {

    }


    public Centre(
            int id,
            String centreCode,
            String name,
            String ownerName,
            String username,
            String passwordHash,
            String phone,
            Date subscriptionStart,
            Date subscriptionEnd,
            String status,
            Timestamp createdAt,
            boolean mustChangePassword,
            Timestamp lastLogin
    ) {

        this.id = id;
        this.centreCode = centreCode;
        this.name = name;
        this.ownerName = ownerName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.subscriptionStart = subscriptionStart;
        this.subscriptionEnd = subscriptionEnd;
        this.status = status;
        this.createdAt = createdAt;
        this.mustChangePassword = mustChangePassword;
        this.lastLogin = lastLogin;

    }


    /*
    ==================================================
    ID
    ==================================================
    */

    public int getId() {

        return id;

    }


    public void setId(int id) {

        this.id = id;

    }


    /*
    ==================================================
    CENTRE CODE
    ==================================================
    */

    public String getCentreCode() {

        return centreCode;

    }


    public void setCentreCode(String centreCode) {

        this.centreCode = centreCode;

    }


    /*
    ==================================================
    NAME
    ==================================================
    */

    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }


    /*
    ==================================================
    OWNER NAME
    ==================================================
    */

    public String getOwnerName() {

        return ownerName;

    }


    public void setOwnerName(String ownerName) {

        this.ownerName = ownerName;

    }


    /*
    ==================================================
    USERNAME
    ==================================================
    */

    public String getUsername() {

        return username;

    }


    public void setUsername(String username) {

        this.username = username;

    }


    /*
    ==================================================
    PASSWORD HASH
    ==================================================
    */

    public String getPasswordHash() {

        return passwordHash;

    }


    public void setPasswordHash(String passwordHash) {

        this.passwordHash = passwordHash;

    }


    /*
    ==================================================
    PHONE
    ==================================================
    */

    public String getPhone() {

        return phone;

    }


    public void setPhone(String phone) {

        this.phone = phone;

    }


    /*
    ==================================================
    SUBSCRIPTION START
    ==================================================
    */

    public Date getSubscriptionStart() {

        return subscriptionStart;

    }


    public void setSubscriptionStart(Date subscriptionStart) {

        this.subscriptionStart = subscriptionStart;

    }


    /*
    ==================================================
    SUBSCRIPTION END
    ==================================================
    */

    public Date getSubscriptionEnd() {

        return subscriptionEnd;

    }


    public void setSubscriptionEnd(Date subscriptionEnd) {

        this.subscriptionEnd = subscriptionEnd;

    }


    /*
    ==================================================
    STATUS
    ==================================================
    */

    public String getStatus() {

        return status;

    }


    public void setStatus(String status) {

        this.status = status;

    }


    /*
    ==================================================
    CREATED AT
    ==================================================
    */

    public Timestamp getCreatedAt() {

        return createdAt;

    }


    public void setCreatedAt(Timestamp createdAt) {

        this.createdAt = createdAt;

    }


    /*
    ==================================================
    MUST CHANGE PASSWORD
    ==================================================
    */

    public boolean isMustChangePassword() {

        return mustChangePassword;

    }


    public void setMustChangePassword(
            boolean mustChangePassword
    ) {

        this.mustChangePassword = mustChangePassword;

    }


    /*
    ==================================================
    LAST LOGIN
    ==================================================
    */

    public Timestamp getLastLogin() {

        return lastLogin;

    }


    public void setLastLogin(Timestamp lastLogin) {

        this.lastLogin = lastLogin;

    }


    /*
    ==================================================
    DURATION MONTHS
    ==================================================
    */

    public int getDurationMonths() {

        return durationMonths;

    }


    public void setDurationMonths(
            int durationMonths
    ) {

        this.durationMonths = durationMonths;

    }

}