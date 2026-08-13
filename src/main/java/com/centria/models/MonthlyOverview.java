package com.centria.models;


/*
 * File        : MonthlyOverview.java
 * Project     : CENTRIA
 *
 * Description :
 * Monthly dashboard overview data.
 */


public class MonthlyOverview {


    /*
    ======================================================
    MONTH NUMBER
    ======================================================

    1  = January
    2  = February
    ...
    12 = December
    ======================================================
    */

    private int monthNumber;


    /*
    ======================================================
    PAYMENT COUNT
    ======================================================
    */

    private int paymentCount;


    /*
    ======================================================
    MONTHLY REVENUE
    ======================================================
    */

    private double revenue;


    /*
    ======================================================
    CONSTRUCTOR
    ======================================================
    */

    public MonthlyOverview() {

    }


    /*
    ======================================================
    MONTH NUMBER
    ======================================================
    */

    public int getMonthNumber() {

        return monthNumber;
    }


    public void setMonthNumber(
            int monthNumber
    ) {

        this.monthNumber =
                monthNumber;
    }


    /*
    ======================================================
    PAYMENT COUNT
    ======================================================
    */

    public int getPaymentCount() {

        return paymentCount;
    }


    public void setPaymentCount(
            int paymentCount
    ) {

        this.paymentCount =
                paymentCount;
    }


    /*
    ======================================================
    REVENUE
    ======================================================
    */

    public double getRevenue() {

        return revenue;
    }


    public void setRevenue(
            double revenue
    ) {

        this.revenue =
                revenue;
    }

}