package com.centria.controllers.admin;

import com.centria.dao.PaymentDAO;
import com.centria.models.Payment;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
======================================================
PAYMENT SERVLET

RESPONSIBILITY:

    - Payment Cards
    - Payment Tabs
    - Payment Tables
    - Search
    - Date Filter
    - Pagination
    - Confirm Payment
    - Update Subscription
    - Print Invoice

======================================================
*/

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {


    private PaymentDAO paymentDAO;


    @Override
    public void init() {

        paymentDAO = new PaymentDAO();

    }


    /*
    ======================================================
    GET
    ======================================================
    */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException {


        String action =
                request.getParameter("action");


        if (action == null || action.isEmpty()) {

            action = "list";

        }


        switch (action) {


            case "list":

                listPayments(
                        request,
                        response
                );

                break;


            case "stats":

                paymentStats(
                        request,
                        response
                );

                break;


            case "print":

                printInvoice(
                        request,
                        response
                );

                break;


            default:

                listPayments(
                        request,
                        response
                );

                break;

        }

    }


    /*
    ======================================================
    POST
    ======================================================
    */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException {


        String action =
                request.getParameter("action");


        if ("confirm".equals(action)) {


            confirmPayment(
                    request,
                    response
            );


        }

        else if ("updateSubscription".equals(action)) {


            updateSubscription(
                    request,
                    response
            );


        }

    }


    /*
    ======================================================
    PAYMENT CARDS
    ======================================================

    IMPORTANT:

    These counts are completely independent
    from the Payment Tabs.

    They read the TOTAL values directly
    from the payments table.

    No:
        search
        dateFrom
        dateTo
        pagination
        tab filter

    ======================================================
    */

    private void loadPaymentCards(
            HttpServletRequest request
    ) {


        /*
        ==================================================
        TOTAL PAID
        ==================================================
        */

        int paidCardCount =
                paymentDAO.countPaidPayments();


        /*
        ==================================================
        TOTAL UNPAID
        ==================================================
        */

        int unpaidCardCount =
                paymentDAO.countUnpaidPayments();


        /*
        ==================================================
        SEND TO JSP
        ==================================================
        */

        request.setAttribute(
                "paidCardCount",
                paidCardCount
        );


        request.setAttribute(
                "unpaidCardCount",
                unpaidCardCount
        );

    }


    /*
    ======================================================
    LIST PAYMENTS

    TAB1 : UNPAID
    TAB2 : PAID
    TAB3 : HISTORY

    ======================================================
    */

    private void listPayments(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException {


        /*
        ==================================================
        LOAD PAYMENT CARDS
        ==================================================

        This is completely independent
        from the Tab counters below.

        ==================================================
        */

        loadPaymentCards(request);


        /*
        ==================================================
        CURRENT TAB
        ==================================================
        */

        String tab =
                request.getParameter("tab");


        if (tab == null || tab.isEmpty()) {

            tab = "UNPAID";

        }


        /*
        ==================================================
        SEARCH
        ==================================================
        */

        String search =
                request.getParameter("search");


        /*
        ==================================================
        ORDER
        ==================================================
        */

        String order =
                request.getParameter("order");


        if (order == null || order.isEmpty()) {

            order = "NEW";

        }


        /*
        ==================================================
        DATE FILTER
        ==================================================
        */

        java.sql.Date dateFrom = null;

        java.sql.Date dateTo = null;


        String dateFromParam =
                request.getParameter("dateFrom");


        String dateToParam =
                request.getParameter("dateTo");


        try {


            if (
                    dateFromParam != null
                    &&
                    !dateFromParam.isEmpty()
            ) {


                dateFrom =
                        java.sql.Date.valueOf(
                                dateFromParam
                        );

            }


            if (
                    dateToParam != null
                    &&
                    !dateToParam.isEmpty()
            ) {


                dateTo =
                        java.sql.Date.valueOf(
                                dateToParam
                        );

            }


        }

        catch (Exception e) {

            e.printStackTrace();

        }


        /*
        ==================================================
        PAGINATION
        ==================================================
        */

        int page = 1;


        try {


            if (
                    request.getParameter("page")
                    != null
            ) {


                page =
                        Integer.parseInt(
                                request.getParameter("page")
                        );

            }


        }

        catch (Exception e) {

            page = 1;

        }


        int pageSize = 4;


        /*
        ==================================================
        LOAD PAYMENT DATA
        ==================================================
        */

        List<Payment> payments;


        switch (tab) {


            /*
            ==============================================
            PAID
            ==============================================
            */

            case "PAID":


                payments =
                        paymentDAO.getPaidPayments(
                                search,
                                order,
                                dateFrom,
                                dateTo,
                                page,
                                pageSize
                        );


                break;


            /*
            ==============================================
            UNPAID
            ==============================================
            */

            case "UNPAID":

            default:


                payments =
                        paymentDAO.getUnpaidPayments(
                                search,
                                order,
                                dateFrom,
                                dateTo,
                                page,
                                pageSize
                        );


                break;


            /*
            ==============================================
            HISTORY
            ==============================================
            */

            case "HISTORY":


                payments =
                        paymentDAO.getHistoryPayments(
                                search,
                                order,
                                dateFrom,
                                dateTo,
                                page,
                                pageSize
                        );


                break;

        }


        /*
        ==================================================
        TAB COUNTERS

        IMPORTANT:

        These are NOT the Card counters.

        These counters use:

            search
            dateFrom
            dateTo

        ==================================================
        */

        int unpaidCount =
                paymentDAO.countUnpaidPayments(
                        search,
                        dateFrom,
                        dateTo
                );


        int paidCount =
                paymentDAO.countPaidPayments(
                        search,
                        dateFrom,
                        dateTo
                );


        int historyCount =
                paymentDAO.countHistoryPayments(
                        search,
                        dateFrom,
                        dateTo
                );


        /*
        ==================================================
        SEND TAB COUNTERS
        ==================================================
        */

        request.setAttribute(
                "unpaidCount",
                unpaidCount
        );


        request.setAttribute(
                "paidCount",
                paidCount
        );


        request.setAttribute(
                "historyCount",
                historyCount
        );


        /*
        ==================================================
        SEND TABLE DATA
        ==================================================
        */

        request.setAttribute(
                "payments",
                payments
        );


        request.setAttribute(
                "currentTab",
                tab
        );


        request.setAttribute(
                "search",
                search
        );


        request.setAttribute(
                "order",
                order
        );


        request.setAttribute(
                "currentPage",
                page
        );


        /*
        ==================================================
        TOTAL RECORDS
        ==================================================
        */

        int totalRecords;


        if ("UNPAID".equals(tab)) {


            totalRecords =
                    unpaidCount;

        }

        else if ("PAID".equals(tab)) {


            totalRecords =
                    paidCount;

        }

        else {


            totalRecords =
                    historyCount;

        }


        /*
        ==================================================
        TOTAL PAGES
        ==================================================
        */

        int totalPages =
                (int) Math.ceil(
                        (double) totalRecords
                        /
                        pageSize
                );


        request.setAttribute(
                "totalPages",
                totalPages
        );


        /*
        ==================================================
        AJAX RESPONSE

        Used ONLY for payment tables.

        ==================================================
        */

        if (
                "true".equals(
                        request.getParameter("ajax")
                )
        ) {


            String fragment;


            if ("PAID".equals(tab)) {


                fragment =
                        "/admin/pages/fragments/payments/paid-table.jsp";

            }


            else if ("HISTORY".equals(tab)) {


                fragment =
                        "/admin/pages/fragments/payments/history-table.jsp";

            }


            else {


                fragment =
                        "/admin/pages/fragments/payments/unpaid-table.jsp";

            }


            request.setAttribute(
                    "fragment",
                    fragment
            );


            request.getRequestDispatcher(
                    "/admin/pages/fragments/payments/payment-response.jsp"
            )
            .forward(
                    request,
                    response
            );


        }

    }


    /*
    ======================================================
    PAYMENT CARDS STATS

    Used ONLY by payments.js

    These values are completely independent
    from the Payment Tabs.

    Source:
        payments.status_payment

    Returns:
        {
            "paid": TOTAL_PAID,
            "unpaid": TOTAL_UNPAID
        }

    No:
        search
        dateFrom
        dateTo
        pagination
        tab filter

    ======================================================
    */

    private void paymentStats(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws IOException {


        int paid =
                paymentDAO.countPaidPayments();


        int unpaid =
                paymentDAO.countUnpaidPayments();


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );


        response.getWriter().write(
                "{"
                + "\"paid\":" + paid + ","
                + "\"unpaid\":" + unpaid
                + "}"
        );

    }


    /*
    ======================================================
    PRINT INVOICE
    ======================================================
    */

    private void printInvoice(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException {


        String invoice =
                request.getParameter("invoice");


        Payment payment =
                paymentDAO.getInvoiceByCode(
                        invoice
                );


        if (payment == null) {


            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );


            return;

        }


        request.setAttribute(
                "payment",
                payment
        );


        request.getRequestDispatcher(
                "/admin/pages/fragments/payments/invoice.jsp"
        )
        .forward(
                request,
                response
        );

    }


    /*
    ======================================================
    CONFIRM PAYMENT

    TAB1 : UNPAID
    ======================================================
    */

    private void confirmPayment(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException {


        response.setContentType(
                "text/plain;charset=UTF-8"
        );


        try {


            String centreCode =
                    request.getParameter(
                            "centreCode"
                    );


            String startDate =
                    request.getParameter(
                            "startDate"
                    );


            String duration =
                    request.getParameter(
                            "duration"
                    );


            /*
            ==============================================
            VALIDATE CENTRE
            ==============================================
            */

            if (
                    centreCode == null
                    ||
                    centreCode.trim().isEmpty()
            ) {


                response.getWriter()
                        .print(
                                "ERROR: CENTRE_CODE"
                        );


                return;

            }


            /*
            ==============================================
            VALIDATE START DATE
            ==============================================
            */

            if (
                    startDate == null
                    ||
                    startDate.trim().isEmpty()
            ) {


                response.getWriter()
                        .print(
                                "ERROR: START_DATE"
                        );


                return;

            }


            /*
            ==============================================
            VALIDATE DURATION
            ==============================================
            */

            if (
                    duration == null
                    ||
                    duration.trim().isEmpty()
            ) {


                response.getWriter()
                        .print(
                                "ERROR: DURATION"
                        );


                return;

            }


            /*
            ==============================================
            CONVERT DATE
            ==============================================
            */

            java.sql.Date newStartDate =
                    java.sql.Date.valueOf(
                            startDate
                    );


            /*
            ==============================================
            CONVERT DURATION
            ==============================================
            */

            int durationMonths =
                    Integer.parseInt(
                            duration
                    );


            /*
            ==============================================
            DAO
            ==============================================
            */

            boolean success =
                    paymentDAO.confirmPayment(
                            centreCode,
                            newStartDate,
                            durationMonths
                    );


            if (success) {


                response.getWriter()
                        .print(
                                "SUCCESS"
                        );


            }

            else {


                response.getWriter()
                        .print(
                                "ERROR: DAO_FALSE"
                        );

            }


        }

        catch (Exception e) {


            e.printStackTrace();


            response.getWriter()
                    .print(
                            "ERROR: "
                            +
                            e.getMessage()
                    );

        }

    }


    /*
    ======================================================
    UPDATE SUBSCRIPTION

    TAB2 : PAID

    UPGRADE / EXTENDED
    ======================================================
    */

    private void updateSubscription(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException {


        response.setContentType(
                "text/plain;charset=UTF-8"
        );


        try {


            String centreCode =
                    request.getParameter(
                            "centreCode"
                    );


            String codeFacture =
                    request.getParameter(
                            "codeFacture"
                    );


            String operation =
                    request.getParameter(
                            "operation"
                    );


            String duration =
                    request.getParameter(
                            "duration"
                    );


            /*
            ==============================================
            VALIDATE CENTRE
            ==============================================
            */

            if (
                    centreCode == null
                    ||
                    centreCode.trim().isEmpty()
            ) {


                response.getWriter()
                        .print(
                                "ERROR:CENTRE"
                        );


                return;

            }


            /*
            ==============================================
            VALIDATE INVOICE
            ==============================================
            */

            if (
                    codeFacture == null
                    ||
                    codeFacture.trim().isEmpty()
            ) {


                response.getWriter()
                        .print(
                                "ERROR:FACTURE"
                        );


                return;

            }


            /*
            ==============================================
            VALIDATE OPERATION
            ==============================================
            */

            if (
                    operation == null
                    ||
                    operation.trim().isEmpty()
            ) {


                response.getWriter()
                        .print(
                                "ERROR:OPERATION"
                        );


                return;

            }


            /*
            ==============================================
            VALIDATE DURATION
            ==============================================
            */

            if (
                    duration == null
                    ||
                    duration.trim().isEmpty()
            ) {


                response.getWriter()
                        .print(
                                "ERROR:DURATION"
                        );


                return;

            }


            /*
            ==============================================
            CONVERT DURATION
            ==============================================
            */

            int durationMonths =
                    Integer.parseInt(
                            duration
                    );


            /*
            ==============================================
            DAO
            ==============================================
            */

            boolean success =
                    paymentDAO.updateSubscription(
                            centreCode,
                            codeFacture,
                            operation,
                            durationMonths
                    );


            if (success) {


                response.getWriter()
                        .print(
                                "SUCCESS"
                        );

            }

            else {


                response.getWriter()
                        .print(
                                "ERROR"
                        );

            }


        }

        catch (Exception e) {


            e.printStackTrace();


            response.getWriter()
                    .print(
                            "ERROR"
                    );

        }

    }

}