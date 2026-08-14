/*
 * File        : HomeServlet.java
 * Project     : CENTRIA
 *
 * Description :
 * Home / Dashboard controller.
 */

package com.centria.controllers.admin;


import com.centria.dao.HomeDAO;
import com.centria.models.Centre;
import com.centria.dao.PaymentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet("/admin/home")
public class HomeServlet extends HttpServlet {


    private HomeDAO homeDAO;
    private PaymentDAO paymentDAO;

    /*
    ======================================================
    INIT
    ======================================================
    */

    @Override
    public void init()
            throws ServletException {

        homeDAO = new HomeDAO();
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


        /*
        ==================================================
        1. TOTAL CENTRES
        ==================================================
        */

        int totalCentres =
                homeDAO.getTotalCentres();


        /*
        ==================================================
        2. ACTIVE CENTRES
        ==================================================
        */

        int activeCentres =
                homeDAO.getActiveCentres();


        /*
        ==================================================
        3. CENTRES REQUIRING ATTENTION
        ==================================================
        */

        int centresRequiringAttention =
                homeDAO.getCentresRequiringAttention();


        /*
        ==================================================
        4.1 MONTHLY REVENUE
        ==================================================
        */

        double monthlyRevenue =
                homeDAO.getMonthlyRevenue();

   /*
        ==================================================
        4.2 Annual REVENUE
        ==================================================
        */

        double annualRevenue =
        homeDAO.getAnnualRevenue();

        /*
        ==================================================
        5. RECENT CENTRES
        ==================================================
        */

        List<Centre> recentCentres =
                homeDAO.getRecentCentres();


        /*
        ==================================================
        6. MONTHLY PAYMENT STATUS
        ==================================================

        Used by:

        Donut Chart

        PAID
        UNPAID
        ==================================================
        */

        Map<String, Integer> monthlyPaymentStatus =
                homeDAO.getMonthlyPaymentStatus();


        /*
        ==================================================
        7. CENTRE STATUS OVERVIEW
        ==================================================

        Used by:

        Horizontal Bar Chart

        ACTIVE
        FOLLOW_UP
        INACTIVE
        ARCHIVED
        DELETED

        FOLLOW_UP =
        PENDING + SUSPENDED
        ==================================================
        */

        Map<String, Integer> centreStatusOverview =
                homeDAO.getCentreStatusOverview();
        
        
        
        int pendingPaymentsCount =
        paymentDAO.countUnpaidPayments();


        /*
        ==================================================
        SEND DATA TO REQUEST
        ==================================================
        */

        request.setAttribute(
                "totalCentres",
                totalCentres
        );


        request.setAttribute(
                "activeCentres",
                activeCentres
        );


        request.setAttribute(
                "centresRequiringAttention",
                centresRequiringAttention
        );


        request.setAttribute(
                "monthlyRevenue",
                monthlyRevenue
        );

        request.setAttribute(
        "annualRevenue",
        annualRevenue
);

        request.setAttribute(
                "recentCentres",
                recentCentres
        );


        request.setAttribute(
                "monthlyPaymentStatus",
                monthlyPaymentStatus
        );


        request.setAttribute(
                "centreStatusOverview",
                centreStatusOverview
        );
        
        request.setAttribute(
        "pendingPaymentsCount",
        pendingPaymentsCount
);

        /*
        ==================================================
        CHECK AJAX REQUEST
        ==================================================
        */

        String ajax =
                request.getParameter("ajax");


        /*
        ==================================================
        AJAX
        ==================================================

       

        This content will be inserted
        inside #content-area.
        ==================================================
        */

        if (
                "true".equalsIgnoreCase(ajax)
        ) {


            request.getRequestDispatcher(
                    "/admin/pages/home.jsp"
            ).forward(
                    request,
                    response
            );


            return;

        }


        /*
        ==================================================
        NORMAL / DIRECT REQUEST
        ==================================================

        Return the complete dashboard.
        ==================================================
        */

        request.setAttribute(
                "section",
                "home"
        );


        request.getRequestDispatcher(
                "/admin/dashboard.jsp"
        ).forward(
                request,
                response
        );

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


        doGet(
                request,
                response
        );

    }

}