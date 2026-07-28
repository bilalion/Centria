package com.centria.models;

import java.sql.Date;
import java.sql.Timestamp;


public class Centre {


    private int id;

    // Nouveau : Code unique du centre
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



    public Centre(){

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
    ){

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





    public String getName() {

        return name;

    }



    public void setName(String name) {

        this.name = name;

    }



    public String getOwnerName() {

        return ownerName;

    }



    public void setOwnerName(String ownerName) {

        this.ownerName = ownerName;

    }



    public String getUsername() {

        return username;

    }



    public void setUsername(String username) {

        this.username = username;

    }



    public String getPasswordHash() {

        return passwordHash;

    }



    public void setPasswordHash(String passwordHash) {

        this.passwordHash = passwordHash;

    }



    public String getPhone() {

        return phone;

    }



    public void setPhone(String phone) {

        this.phone = phone;

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



    public String getStatus() {

        return status;

    }



    public void setStatus(String status) {

        this.status = status;

    }



    public Timestamp getCreatedAt() {

        return createdAt;

    }



    public void setCreatedAt(Timestamp createdAt) {

        this.createdAt = createdAt;

    }



    public boolean isMustChangePassword() {

        return mustChangePassword;

    }



    public void setMustChangePassword(boolean mustChangePassword) {

        this.mustChangePassword = mustChangePassword;

    }



    public Timestamp getLastLogin() {

        return lastLogin;

    }



    public void setLastLogin(Timestamp lastLogin) {

        this.lastLogin = lastLogin;

    }


}