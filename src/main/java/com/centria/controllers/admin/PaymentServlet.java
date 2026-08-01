package com.centria.controllers.admin;

import com.centria.dao.PaymentDAO;

import java.io.IOException;

import com.centria.models.Payment;
import java.util.List;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    @SuppressWarnings("unused")
    private PaymentDAO paymentDAO;

    public void init() {

        paymentDAO = new PaymentDAO();

    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {

        String action = request.getParameter("action");

        if(action == null){

            action = "list";

        }

        switch(action){

            case "list":

                listPayments(request, response);
                break;

            case "view":

                viewPayment(request, response);
                break;

            default:

                listPayments(request, response);

        }

    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {

        String action = request.getParameter("action");

        if("confirm".equals(action)){

            confirmPayment(request, response);

        }

    }

    /*
    ==============================================
    LIST PAYMENTS
    ==============================================
    */

 /*
======================================================
LIST PAYMENTS

TAB1 : UNPAID
TAB2 : PAID
TAB3 : HISTORY

Currently:
TAB1 implemented

======================================================
*/

private void listPayments(
        HttpServletRequest request,
        HttpServletResponse response
)
throws ServletException, IOException {


    /*
    ==============================================
    GET TAB
    ==============================================
    */


    String tab =

            request.getParameter(
                    "tab"
            );


    if(tab == null || tab.isEmpty()){


        tab = "UNPAID";


    }





    /*
    ==============================================
    SEARCH / ORDER
    ==============================================
    */


    String search =

            request.getParameter(
                    "search"
            );



    String order =

            request.getParameter(
                    "order"
            );



    if(order == null || order.isEmpty()){


        order = "NEW";


    }






    /*
    ==============================================
    PAGINATION
    ==============================================
    */


    int page = 1;


    try{


        if(request.getParameter("page") != null){


            page = Integer.parseInt(
                    request.getParameter("page")
            );


        }


    }
    catch(Exception e){


        page = 1;


    }



    int pageSize = 10;






    /*
    ==============================================
    LOAD PAYMENTS

    TAB1 ONLY FOR NOW

    ==============================================
    */


    List<Payment> payments = null;



    if("UNPAID".equals(tab)){


        payments =

                paymentDAO.getUnpaidPayments(
                        search,
                        order,
                        page,
                        pageSize
                );


    }




    /*
    ==============================================
    SEND DATA TO JSP
    ==============================================
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
======================================================
FORWARD RESPONSE

AJAX:
Return only table fragment

Normal:
Return full payments page

======================================================
*/


if("true".equals(
        request.getParameter("ajax")
)){


    request.getRequestDispatcher(

            "/admin/pages/fragments/payments/unpaid-table.jsp"

    )
    .forward(
            request,
            response
    );


}
else{


    request.getRequestDispatcher(

            "/admin/pages/payments.jsp"

    )
    .forward(
            request,
            response
    );


}


}

    /*
    ==============================================
    VIEW PAYMENT
    ==============================================
    */

    private void viewPayment(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {

    }

    /*
    ==============================================
    CONFIRM PAYMENT
    ==============================================
    */

    private void confirmPayment(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {

    }

}