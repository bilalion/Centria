/*
 * File        : CentreServlet.java
 * Project     : CENTRIA
 *
 * Description :
 * Gestion des centres.
 */

package com.centria.controllers.admin;

import com.centria.dao.CentreDAO;
import com.centria.models.Centre;
import com.centria.utils.LanguageManager;
import com.centria.utils.PasswordGenerator;
import com.centria.utils.PasswordUtil;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet(
    name="CentreServlet",
    urlPatterns={"/CentreServlet"}
)
public class CentreServlet extends HttpServlet {


    private CentreDAO centreDAO;



    @Override
    public void init() throws ServletException {

        centreDAO = new CentreDAO();

    }




    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");


        String action =
                request.getParameter("action");



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

       resetPassword(request,response);

        break;



    default:

        listCentres(
                request,
                response
        );

        break;

}

    }





@Override
protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
) throws ServletException, IOException {


    System.out.println("========== ENTER DO POST CENTRE ==========");


    /*
     * مهم جدا
     * يجب أن تكون قبل قراءة parameters
     */
    request.setCharacterEncoding("UTF-8");



    System.out.println(
            "REQUEST URI = "
            + request.getRequestURI()
    );


    System.out.println(
            "CONTENT TYPE = "
            + request.getContentType()
    );



    String action =
            request.getParameter("action");



    System.out.println(
            "ACTION RECEIVED = "
            + action
    );



    System.out.println(
            "ID RECEIVED = "
            + request.getParameter("id")
    );





    /*
    ==================================================
    CHECK ACTION
    ==================================================
    */


    if(action == null || action.trim().isEmpty()){


        System.out.println(
                "ERROR : ACTION EMPTY"
        );


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        response.getWriter().write(
                "{"
                + "\"success\":false,"
                + "\"error\":\"action missing\""
                + "}"
        );


        return;

    }







    /*
    ==================================================
    ACTION ROUTER
    ==================================================
    */


    switch(action){



        case "add":


            System.out.println(
                    "EXECUTE ADD CENTRE"
            );


            addCentre(
                    request,
                    response
            );


            break;






        case "updateProfile":


            System.out.println(
                    "EXECUTE UPDATE PROFILE"
            );



            updateCentreProfile(
                    request,
                    response
            );



            break;







        case "resetPassword":


            System.out.println(
                    "EXECUTE RESET PASSWORD"
            );



            resetPassword(
                    request,
                    response
            );


            break;






        default:


            System.out.println(
                    "UNKNOWN ACTION = "
                    + action
            );



            response.setContentType(
                    "application/json;charset=UTF-8"
            );



            response.getWriter().write(
                    "{"
                    + "\"success\":false,"
                    + "\"error\":\"unknown action\""
                    + "}"
            );



            break;



    }



}



    private void listCentres(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {



        String search =
                request.getParameter("search");


        String status =
                request.getParameter("status");


        String order =
                request.getParameter("order");



        if(search == null)
            search="";



        if(status == null || status.isEmpty())
            status="ALL";



        if(order == null || order.isEmpty())
            order="NEW";




        int page=1;

        int pageSize=10;



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
 * ======================================================
 * VIEW CENTRE
 * ======================================================
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



        /*
         * إذا كان المركز غير موجود
         */

        if(centre == null){


            response.sendRedirect(
                    request.getContextPath()
                    + "/CentreServlet?action=list"
            );


            return;

        }




        /*
         * إرسال بيانات المركز
         */

        request.setAttribute(
                "centre",
                centre
        );



        /*
         * تحديد القسم داخل Dashboard
         */

    



       

        request.getRequestDispatcher(
                 "/admin/pages/fragments/centres/centre-view.jsp"
        )
        .forward(
                request,
                response
        );



    }
    catch(NumberFormatException e){


        response.sendRedirect(
                request.getContextPath()
                + "/CentreServlet?action=list"
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


    /* ====================================
     * ADD CENTRE
     * ====================================
*/

    private void addCentre(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {



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








        centre.setSubscriptionStart(

                Date.valueOf(start)

        );








        int months=1;



        if("3".equals(duration))
            months=3;



        if("6".equals(duration))
            months=6;



        if("12".equals(duration))
            months=12;








        LocalDate end =

                LocalDate.parse(start)
                .plusMonths(months);







        centre.setSubscriptionEnd(

                Date.valueOf(end)

        );








        boolean saved =

                centreDAO.addCentre(centre);







if(saved && centre.getCentreCode()!=null){


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
        + "/admin/pages/fragments/centres/centre-created.jsp"

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







    private void updateStatus(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {



        response.setContentType(
                "application/json"
        );


        response.setCharacterEncoding(
                "UTF-8"
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




            if(updated){


                response.getWriter().print(
                    "{\"success\":true}"
                );


            }
            else{


                response.getWriter().print(
                    "{\"success\":false}"
                );


            }



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



        /*
         * جلب بيانات المركز
         */
        Centre centre =
                centreDAO.getCentreById(id);



        if(centre == null){


            response.getWriter().println(

                "<div class='empty-state'>"
                +
                "<p>Centre introuvable</p>"
                +
                "</div>"

            );


            return;

        }






        /*
         * توليد كلمة مرور جديدة
         */
        String newPassword =
                PasswordGenerator.generatePassword();







        /*
         * تحديث كلمة المرور
         */
        boolean updated =

                centreDAO.resetPassword(
                        id,
                        newPassword
                );







        if(updated){



            String codeLabel =
                    LanguageManager.get(
                            "centers.details.code",
                            request.getSession()
                    );



            String usernameLabel =
                    LanguageManager.get(
                            "centers.details.username",
                            request.getSession()
                    );



            String passwordLabel =
                    LanguageManager.get(
                            "centers.reset.password.label",
                            request.getSession()
                    );



            String warning =
                    LanguageManager.get(
                            "centers.reset.warning",
                            request.getSession()
                    );






            String copyText =

                    codeLabel
                    +
                    " : "
                    +
                    centre.getCentreCode()

                    +
                    "\n"

                    +
                    usernameLabel
                    +
                    " : "
                    +
                    centre.getUsername()

                    +
                    "\n"

                    +
                    passwordLabel
                    +
                    " : "
                    +
                    newPassword

                    +
                    "\n\n⚠ "

                    +
                    warning;







            response.getWriter().println(


                "<div class='reset-success'>"



                + "<h3>🔑</h3>"



                + "<h4>"
                + LanguageManager.get(
                        "centers.reset.success",
                        request.getSession()
                  )
                + "</h4>"






                + "<div class='reset-info' id='resetPasswordMessage'>"





                + "<p>"
                + "<strong>"
                + codeLabel
                + " :</strong> "
                + centre.getCentreCode()
                + "</p>"





                + "<p>"
                + "<strong>"
                + usernameLabel
                + " :</strong> "
                + centre.getUsername()
                + "</p>"





                + "<p>"
                + "<strong>"
                + passwordLabel
                + " :</strong>"
                + "</p>"





                + "<div class='temporary-password'>"
                + newPassword
                + "</div>"





                + "</div>"








                /*
                 * زر النسخ
                 */
                + "<button "
                + "type='button' "
                + "class='copy-password-btn' "
                + "onclick='copyLoginInfo()'>"


                + "📋 "
                + LanguageManager.get(
                        "centers.copy.password",
                        request.getSession()
                  )


                + "</button>"








                /*
                 * النص المخفي للنسخ
                 */
                + "<textarea "
                + "id='loginInfoText' "
                + "hidden>"
                + copyText
                + "</textarea>"







                + "<p class='reset-warning'>⚠ "
                + warning
                + "</p>"





                + "</div>"


            );



        }
        else{


            response.getWriter().println(


                "<div class='empty-state'>"
                +
                "<p>Error resetting password</p>"
                +
                "</div>"


            );


        }




    }
    catch(Exception e){


        e.printStackTrace();



        response.getWriter().println(


            "<div class='empty-state'>"
            +
            "<p>Server error</p>"
            +
            "</div>"


        );


    }

}

/*
 * ======================================================
 * EDIT CENTRE DIALOG
 * ======================================================
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



        if(centre == null){


            response.getWriter().println(

                "<div class='empty-state'>"
                +
                "<p>Centre introuvable</p>"
                +
                "</div>"

            );


            return;

        }





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



        response.getWriter().println(

            "<div class='empty-state'>"
            +
            "<p>Erreur chargement modification centre</p>"
            +
            "</div>"

        );


    }


}


/*
 * ======================================================
 * UPDATE CENTRE PROFILE
 * EDIT DIALOG AJAX
 * ======================================================
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



        String name =
                request.getParameter("name");



        String owner =
                request.getParameter("owner_name");



        String phone =
                request.getParameter("phone");





    





        Centre centre =
                new Centre();



        centre.setId(id);

        centre.setName(name);

        centre.setOwnerName(owner);

        centre.setPhone(phone);






        boolean updated =

                centreDAO.updateCentreProfile(
                        centre
                );


        response.getWriter().print(

            "{\"success\":"
            +
            updated
            +
            "}"

        );



    }
    catch(Exception e){


        e.printStackTrace();



        response.getWriter().print(

            "{\"success\":false}"

        );


    }


}

}