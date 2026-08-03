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
    else if("updateSubscription".equals(action)){

        updateSubscription(request, response);

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

/*
======================================================
LIST PAYMENTS

CURRENT MODULE:
TAB1 ONLY - UNPAID

======================================================
*/

private void listPayments(
        HttpServletRequest request,
        HttpServletResponse response
)
throws ServletException, IOException {


 /*
==============================================
GET CURRENT TAB
==============================================
*/

String tab = request.getParameter("tab");


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
DATE FILTER
==============================================
*/

java.sql.Date dateFrom = null;

java.sql.Date dateTo = null;


String dateFromParam =
        request.getParameter("dateFrom");


String dateToParam =
        request.getParameter("dateTo");



try{


    if(dateFromParam != null
            && !dateFromParam.isEmpty()){


        dateFrom =
            java.sql.Date.valueOf(
                dateFromParam
            );

    }



    if(dateToParam != null
            && !dateToParam.isEmpty()){


        dateTo =
            java.sql.Date.valueOf(
                dateToParam
            );

    }


}
catch(Exception e){

    e.printStackTrace();

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



    int pageSize = 4;







    /*
    ==============================================
    LOAD TAB1 DATA

    ONLY UNPAID

    ==============================================
    */


 List<Payment> payments;


switch(tab){


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
==============================================
TAB COUNTERS

REAL DATABASE COUNTS

==============================================
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
    
    
int totalRecords;


if("UNPAID".equals(tab)){

    totalRecords = unpaidCount;

}
else if("PAID".equals(tab)){

    totalRecords = paidCount;

}
else{

    totalRecords = historyCount;

}



int totalPages =

        (int)Math.ceil(
            (double)totalRecords / pageSize
        );


request.setAttribute(
        "totalPages",
        totalPages
);




if("true".equals(
        request.getParameter("ajax")
)){


    String fragment;


  if("PAID".equals(tab)){

    fragment =
    "/admin/pages/fragments/payments/paid-table.jsp";

}
else if("HISTORY".equals(tab)){

    fragment =
    "/admin/pages/fragments/payments/history-table.jsp";

}
else{

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

 /*
==============================================
CONFIRM PAYMENT
==============================================
*/

/*
==============================================
CONFIRM PAYMENT

TAB1 : UNPAID

==============================================
*/

    
    
private void confirmPayment(
        HttpServletRequest request,
        HttpServletResponse response
)
throws ServletException, IOException {


    response.setContentType(
            "text/plain;charset=UTF-8"
    );


    try{


        String centreCode =
                request.getParameter("centreCode");


        String startDate =
                request.getParameter("startDate");


        String duration =
                request.getParameter("duration");



  





        if(centreCode == null
                || centreCode.trim().isEmpty()){


            response.getWriter()
                    .print("ERROR: CENTRE_CODE");


            return;

        }




        if(startDate == null
                || startDate.trim().isEmpty()){


            response.getWriter()
                    .print("ERROR: START_DATE");


            return;

        }




        if(duration == null
                || duration.trim().isEmpty()){


            response.getWriter()
                    .print("ERROR: DURATION");


            return;

        }






        java.sql.Date newStartDate =

                java.sql.Date.valueOf(
                        startDate
                );




        int durationMonths =

                Integer.parseInt(
                        duration
                );






        boolean success =

                paymentDAO.confirmPayment(
                        centreCode,
                        newStartDate,
                        durationMonths
                );







        if(success){


            response.getWriter()
                    .print("SUCCESS");


        }
        else{


            response.getWriter()
                    .print("ERROR: DAO_FALSE");


        }




    }
    catch(Exception e){


        e.printStackTrace();


        response.getWriter()
                .print(
                        "ERROR: "
                        + e.getMessage()
                );


    }



}



/*
==============================================
Fin TAB1 : UNPAID
==============================================
*/



/*
==============================================
UPDATE SUBSCRIPTION

TAB2 : PAID

UPGRADE / EXTENDED
==============================================
*/

private void updateSubscription(
        HttpServletRequest request,
        HttpServletResponse response
)
throws ServletException, IOException{

    response.setContentType(
            "text/plain;charset=UTF-8"
    );

    try{

        String centreCode =
                request.getParameter("centreCode");

        String codeFacture =
                request.getParameter("codeFacture");

        String operation =
                request.getParameter("operation");

        String duration =
                request.getParameter("duration");

        if(centreCode == null
                || centreCode.trim().isEmpty()){

            response.getWriter().print("ERROR:CENTRE");

            return;

        }

        if(codeFacture == null
                || codeFacture.trim().isEmpty()){

            response.getWriter().print("ERROR:FACTURE");

            return;

        }

        if(operation == null
                || operation.trim().isEmpty()){

            response.getWriter().print("ERROR:OPERATION");

            return;

        }

        if(duration == null
                || duration.trim().isEmpty()){

            response.getWriter().print("ERROR:DURATION");

            return;

        }

        int durationMonths =
                Integer.parseInt(duration);

        boolean success =
                paymentDAO.updateSubscription(
                        centreCode,
                        codeFacture,
                        operation,
                        durationMonths
                );

        if(success){

            response.getWriter().print("SUCCESS");

        }
        else{

            response.getWriter().print("ERROR");

        }

    }
    catch(Exception e){

        e.printStackTrace();

        response.getWriter().print("ERROR");

    }

}

/*
==============================================
Fin TAB2 : PAID
==============================================
*/

}



