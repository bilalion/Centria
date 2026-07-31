/*
 * File        : Payment.java
 * Project     : CENTRIA
 *
 * Description :
 * Payment model.
 */

package com.centria.models;


public class Payment {


    private int id;

    private String centreCode;

    private String codeFacture;

    private String statusPayment;



    public Payment(){

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



    public String getCodeFacture() {

        return codeFacture;

    }


    public void setCodeFacture(String codeFacture) {

        this.codeFacture = codeFacture;

    }



    public String getStatusPayment() {

        return statusPayment;

    }


    public void setStatusPayment(String statusPayment) {

        this.statusPayment = statusPayment;

    }


}