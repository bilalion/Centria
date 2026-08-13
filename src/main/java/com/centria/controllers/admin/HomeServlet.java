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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/home")
public class HomeServlet extends HttpServlet {


    private HomeDAO homeDAO;


    /*
    ======================================================
    INIT
    ======================================================
    */

    @Override
    public void init()
            throws ServletException {

        homeDAO =
                new HomeDAO();

    }


    /*
    ======================================================
    GET
    ======================================================
    */

 /*
======================================================
GET
======================================================
*/

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
    4. MONTHLY REVENUE
    ==================================================
    */

    double monthlyRevenue =
            homeDAO.getMonthlyRevenue();


    /*
    ==================================================
    5. RECENT CENTRES
    ==================================================
    */

    List<Centre> recentCentres =
            homeDAO.getRecentCentres();


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
            "recentCentres",
            recentCentres
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

    Return ONLY accueil.jsp.

    This content will be inserted
    inside #content-area.
    ==================================================
    */

    if (
            "true".equalsIgnoreCase(ajax)
    ) {

        request.getRequestDispatcher(
                "/admin/pages/accueil.jsp"
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