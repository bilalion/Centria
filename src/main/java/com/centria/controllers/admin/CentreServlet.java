/*
 * File        : CentreServlet.java
 * Project     : CENTRIA
 *
 * Description :
 * Gestion des centres.
 */

package com.centria.controllers.admin;


/*
======================================================
IMPORTS
======================================================
*/

import com.centria.config.DatabaseConfig;
import com.centria.dao.CentreDAO;
import com.centria.dao.PaymentDAO;

import com.centria.models.Centre;

import com.centria.language.LanguageManager;

import com.centria.utils.PasswordGenerator;
import com.centria.utils.PasswordUtil;


import java.io.IOException;
import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.time.LocalDate;

import java.util.List;


import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;




/*
======================================================
CENTRE SERVLET
Gestion des centres + création paiement initial
======================================================
*/


@WebServlet(
    name="CentreServlet",
    urlPatterns={"/CentreServlet"}
)
public class CentreServlet extends HttpServlet {



    /*
    ==================================================
    DAO OBJECTS
    ==================================================
    */


    private CentreDAO centreDAO;

    private PaymentDAO paymentDAO;





    /*
    ==================================================
    INITIALISATION SERVLET
    ==================================================
    */


    @Override
    public void init() throws ServletException {


        centreDAO = new CentreDAO();


        /*
        DAO paiement
        utilisé après création centre
        */


        paymentDAO = new PaymentDAO();


    }





    /*
    ==================================================
    GET REQUEST
    ==================================================
    */


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {



        request.setCharacterEncoding(
                "UTF-8"
        );



        String action =
                request.getParameter(
                        "action"
                );



        /*
        Action par défaut
        */


        if(action == null || action.isEmpty()){


            action="list";


        }





        switch(action){



            case "list":


                listCentres(
                        request,
                        response
                );


                break;




            case "status":


                updateStatus(
                        request,
                        response
                );


                break;





            case "view":


                viewCentre(
                        request,
                        response
                );


                break;





            case "edit":


                editCentre(
                        request,
                        response
                );


                break;





            case "resetPassword":


                resetPassword(
                        request,
                        response
                );


                break;





            default:


                listCentres(
                        request,
                        response
                );


                break;



        }


    }







    /*
    ==================================================
    POST REQUEST
    إضافة وتعديل بيانات المركز
    ==================================================
    */


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {



        request.setCharacterEncoding(
                "UTF-8"
        );



        String action =
                request.getParameter(
                        "action"
                );




        /*
        إذا لم يصل action
        */


        if(action == null || action.trim().isEmpty()){


            response.setContentType(
                    "application/json;charset=UTF-8"
            );



            response.getWriter().write(
                    "{\"success\":false,\"error\":\"action missing\"}"
            );


            return;

        }







        switch(action){



            /*
            ==========================================
            CREATE CENTRE
            ==========================================
            */


            case "add":


                addCentre(
                        request,
                        response
                );


                break;







            /*
            ==========================================
            UPDATE PROFILE
            ==========================================
            */


            case "updateProfile":


                updateCentreProfile(
                        request,
                        response
                );


                break;








            /*
            ==========================================
            RESET PASSWORD
            ==========================================
            */


            case "resetPassword":


                resetPassword(
                        request,
                        response
                );


                break;







            default:


                response.setContentType(
                        "application/json;charset=UTF-8"
                );



                response.getWriter().write(
                        "{\"success\":false,\"error\":\"unknown action\"}"
                );


                break;



        }



    }








    /*
    ==================================================
    LIST CENTRES
    ==================================================
    */


    private void listCentres(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {



        String search =
                request.getParameter(
                        "search"
                );



        String status =
                request.getParameter(
                        "status"
                );



        String order =
                request.getParameter(
                        "order"
                );





        if(search == null)

            search="";





        if(status == null || status.isEmpty())

            status="ALL";





        if(order == null || order.isEmpty())

            order="NEW";





        int page=1;

        int pageSize=4;





        try{


            if(request.getParameter("page")!=null){


                page =
                Integer.parseInt(
                        request.getParameter("page")
                );


            }


        }
        catch(Exception e){


            page=1;


        }





        List<Centre> centres =


                centreDAO.searchCentres(
                        search,
                        status,
                        order,
                        page,
                        pageSize
                );





        int totalCentres =


                centreDAO.countCentres(
                        search,
                        status
                );





        int totalPages =


                (int)Math.ceil(
                        (double)totalCentres/pageSize
                );





        request.setAttribute(
                "centres",
                centres
        );



        request.setAttribute(
                "currentPage",
                page
        );



        request.setAttribute(
                "totalPages",
                totalPages
        );



        request.setAttribute(
                "search",
                search
        );



        request.setAttribute(
                "status",
                status
        );



        request.setAttribute(
                "order",
                order
        );




        if("true".equals(
                request.getParameter("ajax")
        )){


            request.getRequestDispatcher(
                    "/admin/pages/fragments/centres/centres-table.jsp"
            )
            .forward(
                    request,
                    response
            );


            return;

        }





        request.getRequestDispatcher(
                "/admin/pages/centres.jsp"
        )
        .forward(
                request,
                response
        );


    }
    
    /*
    ==================================================
    VIEW CENTRE
    عرض معلومات مركز
    ==================================================
    */


    private void viewCentre(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {



        try{


            int id =
            Integer.parseInt(
                    request.getParameter("id")
            );



            Centre centre =

                    centreDAO.getCentreById(id);




            if(centre == null){


                response.sendRedirect(
                        request.getContextPath()
                        + "/CentreServlet?action=list"
                );


                return;

            }




            request.setAttribute(
                    "centre",
                    centre
            );




            request.getRequestDispatcher(
                    "/admin/pages/fragments/centres/centre-view.jsp"
            )
            .forward(
                    request,
                    response
            );



        }
        catch(Exception e){


            e.printStackTrace();


            response.sendRedirect(
                    request.getContextPath()
                    + "/CentreServlet?action=list"
            );


        }


    }







    /*
    ==================================================
    ADD CENTRE
    إنشاء مركز جديد + إنشاء سجل دفع UNPAID
    ==================================================
    */


   /*
==================================================
ADD CENTRE

Create new centre
+
Create initial payment UNPAID

Subscription dates are saved in centres table.
After payment confirmation,
PaymentDAO will update dates and status.

==================================================
*/

/*
==================================================
ADD CENTRE

Create new centre
+
Create initial payment UNPAID

Centre starts as PENDING.
Subscription activation happens after payment confirmation.

==================================================
*/

/*
==================================================
ADD CENTRE

Create new centre
+
Initial subscription is already PAID

Process:

1- Save centre
2- Status = ACTIVE
3- Calculate subscription_end
4- Create payment record PAID
5- Generate invoice

==================================================
*/

private void addCentre(
        HttpServletRequest request,
        HttpServletResponse response
)
throws ServletException, IOException {



    String name =
            request.getParameter("name");


    String owner =
            request.getParameter("owner_name");


    String phone =
            request.getParameter("phone");


    String start =
            request.getParameter("subscription_start");


    String duration =
            request.getParameter("subscription_duration");







    /*
    ==============================================
    CREATE TEMPORARY PASSWORD
    ==============================================
    */


    String temporaryPassword =
            PasswordGenerator.generatePassword();








    Centre centre =
            new Centre();






    centre.setName(name);


    centre.setOwnerName(owner);


    centre.setPhone(phone);






    centre.setPasswordHash(

            PasswordUtil.hashPassword(
                    temporaryPassword
            )

    );









    /*
    ==============================================
    SUBSCRIPTION START
    ==============================================
    */


    centre.setSubscriptionStart(

            Date.valueOf(start)

    );









    /*
    ==============================================
    CALCULATE SUBSCRIPTION END

    According to duration

    ==============================================
    */


    int months = 1;



    if("3".equals(duration)){


        months = 3;


    }
    else if("6".equals(duration)){


        months = 6;


    }
    else if("12".equals(duration)){


        months = 12;


    }






    LocalDate end =

            LocalDate.parse(start)
            .plusMonths(months);






    centre.setSubscriptionEnd(

            Date.valueOf(end)

    );








    /*
    ==============================================
    FIRST SUBSCRIPTION IS ALREADY PAID

    New centre starts ACTIVE

    ==============================================
    */


    centre.setStatus(
            "ACTIVE"
    );









    /*
    ==============================================
    SAVE CENTRE

    ==============================================
    */


    boolean saved =

            centreDAO.addCentre(centre);









    if(saved && centre.getCentreCode()!=null){







        /*
        ==========================================
        CREATE INITIAL PAYMENT

        Creates:

        payments
        +
        payment_history

        ==========================================
        */


        boolean paymentCreated =

                paymentDAO.createInitialPayment(
                        centre.getCentreCode()
                );







        if(!paymentCreated){


            System.out.println(
                    "WARNING : Initial payment not created"
            );


        }









        /*
        ==========================================
        SAVE LOGIN INFORMATION TEMPORARILY

        Display once after creation

        ==========================================
        */


        request.getSession().setAttribute(
                "centreCode",
                centre.getCentreCode()
        );



        request.getSession().setAttribute(
                "username",
                centre.getUsername()
        );



        request.getSession().setAttribute(
                "password",
                temporaryPassword
        );








        response.sendRedirect(

                request.getContextPath()
                +
                "/admin/pages/fragments/centres/centre-created.jsp"

        );






    }
    else {




        request.setAttribute(
                "error",
                "Erreur création centre"
        );




        request.getRequestDispatcher(
                "/admin/pages/add-centre.jsp"
        )
        .forward(
                request,
                response
        );



    }



}





    /*
    ==================================================
    UPDATE STATUS
    تغيير حالة المركز
    ==================================================
    */


    private void updateStatus(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws IOException {



        response.setContentType(
                "application/json;charset=UTF-8"
        );



        try{


            int id =

            Integer.parseInt(
                    request.getParameter("id")
            );



            String status =

                    request.getParameter("status");




            boolean updated =

                    centreDAO.updateStatus(
                            id,
                            status
                    );




            response.getWriter().print(

                    "{\"success\":"
                    + updated
                    + "}"

            );



        }
        catch(Exception e){


            e.printStackTrace();


            response.getWriter().print(
                    "{\"success\":false}"
            );


        }


    }









    /*
    ==================================================
    RESET PASSWORD
    ==================================================
    */


    private void resetPassword(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws IOException {



        response.setContentType(
                "text/html;charset=UTF-8"
        );



        try{


            int id =

            Integer.parseInt(
                    request.getParameter("id")
            );



            Centre centre =

                    centreDAO.getCentreById(id);





            if(centre == null){


                response.getWriter().println(
                        "<p>Centre introuvable</p>"
                );


                return;

            }






            String newPassword =

                    PasswordGenerator.generatePassword();






            boolean updated =

                    centreDAO.resetPassword(
                            id,
                            newPassword
                    );





            if(updated){



                response.getWriter().println(

                        "<div class='reset-success'>"

                        + "<h4>🔑 Password Reset</h4>"

                        + "<p>Code : "
                        + centre.getCentreCode()
                        + "</p>"

                        + "<p>Username : "
                        + centre.getUsername()
                        + "</p>"

                        + "<p>Password :</p>"

                        + "<div class='temporary-password'>"
                        + newPassword
                        + "</div>"

                        + "</div>"

                );


            }
            else{


                response.getWriter().println(
                        "<p>Error resetting password</p>"
                );


            }



        }
        catch(Exception e){


            e.printStackTrace();


            response.getWriter().println(
                    "<p>Server error</p>"
            );


        }



    }







    /*
    ==================================================
    EDIT CENTRE
    ==================================================
    */


    private void editCentre(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {



        try{


            int id =

            Integer.parseInt(
                    request.getParameter("id")
            );



            Centre centre =

                    centreDAO.getCentreById(id);




            request.setAttribute(
                    "centre",
                    centre
            );




            request.getRequestDispatcher(
                    "/admin/pages/fragments/centres/centre-edit.jsp"
            )
            .forward(
                    request,
                    response
            );



        }
        catch(Exception e){


            e.printStackTrace();


        }


    }








    /*
    ==================================================
    UPDATE PROFILE
    ==================================================
    */


    private void updateCentreProfile(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws IOException {



        response.setContentType(
                "application/json;charset=UTF-8"
        );



        try{


            int id =

            Integer.parseInt(
                    request.getParameter("id")
            );



            Centre centre =

                    new Centre();




            centre.setId(id);



            centre.setName(
                    request.getParameter("name")
            );



            centre.setOwnerName(
                    request.getParameter("owner_name")
            );



            centre.setPhone(
                    request.getParameter("phone")
            );





            boolean updated =

                    centreDAO.updateCentreProfile(
                            centre
                    );





            response.getWriter().print(

                    "{\"success\":"
                    + updated
                    + "}"

            );



        }
        catch(Exception e){


            e.printStackTrace();


            response.getWriter().print(
                    "{\"success\":false}"
            );


        }


    }







    @Override
    public String getServletInfo(){

        return "Centre Management Servlet";

    }

    


}