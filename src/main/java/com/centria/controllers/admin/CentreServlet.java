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


// Servlet de gestion des centres
@WebServlet(
    name="CentreServlet",
    urlPatterns={"/CentreServlet"}
)
public class CentreServlet extends HttpServlet {

    private CentreDAO centreDAO;

    // Initialisation du DAO
    @Override
    public void init() throws ServletException {
        centreDAO = new CentreDAO();
    }

    // Traitement des requêtes GET
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if(action == null || action.isEmpty()){
            action="list";
        }

        switch(action){

            case "list":
                listCentres(request,response);
                break;

            case "status":
                updateStatus(request,response);
                break;

            default:
                listCentres(request,response);
                break;
        }
    }

    // Traitement des requêtes POST
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        if("add".equals(action)){
            addCentre(request,response);
            return;
        }
        doGet(request,response);
    }


    // Affichage des centres avec recherche et pagination
    private void listCentres(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String search=request.getParameter("search");
        String status=request.getParameter("status");
        String order=request.getParameter("order");

        if(search==null) search="";
        if(status==null || status.isEmpty()) status="ALL";
        if(order==null || order.isEmpty()) order="NEW";

        int page=1;
        int pageSize=10;

        try{
            if(request.getParameter("page")!=null){
                page=Integer.parseInt(
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
        request.setAttribute("centres",centres);
        request.setAttribute("currentPage",page);
        request.setAttribute("pageSize",pageSize);
        request.setAttribute("totalPages",totalPages);
        request.setAttribute("search",search);
        request.setAttribute("status",status);
        request.setAttribute("order",order);


        if("true".equals(request.getParameter("ajax"))){

            request.getRequestDispatcher(
                "/admin/pages/fragments/centres/centres-table.jsp"
            ).forward(request,response);

            return;
        }


        request.getRequestDispatcher(
                "/admin/pages/centres.jsp"
        ).forward(request,response);
    }


    // Création d'un nouveau centre
    private void addCentre(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String name=request.getParameter("name");
        String owner=request.getParameter("owner_name");
        String username=request.getParameter("username");
        String phone=request.getParameter("phone");
        String start=request.getParameter("subscription_start");
        String duration=request.getParameter("subscription_duration");


        String temporaryPassword =
                PasswordGenerator.generatePassword();


        Centre centre=new Centre();

        centre.setName(name);
        centre.setOwnerName(owner);
        centre.setUsername(username);
        centre.setPhone(phone);


        // Cryptage du mot de passe
        centre.setPasswordHash(
                PasswordUtil.hashPassword(
                        temporaryPassword
                )
        );

        centre.setSubscriptionStart(
                Date.valueOf(start)
        );


        int months=1;

        if("3".equals(duration)) months=3;
        if("6".equals(duration)) months=6;
        if("12".equals(duration)) months=12;


        LocalDate end =
                LocalDate.parse(start)
                .plusMonths(months);


        centre.setSubscriptionEnd(
                Date.valueOf(end)
        );


        boolean saved =
                centreDAO.addCentre(centre);

        if(saved){

            request.setAttribute("created",true);
            request.setAttribute("centreName",name);
            request.setAttribute("username",username);
            request.setAttribute("password",temporaryPassword);

            request.getRequestDispatcher(
                "/admin/pages/fragments/centres/centre-created.jsp"
            ).forward(request,response);
        }else{
            request.setAttribute(
                    "error",
                    "Erreur création centre"
            );


            request.getRequestDispatcher(
                "/admin/pages/add-centre.jsp"
            ).forward(request,response);
        }
    }

    // Mise à jour du statut via AJAX
    private void updateStatus(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        try{

            int id=Integer.parseInt(
                    request.getParameter("id")
            );


            String status=request.getParameter("status");


            boolean updated =
                    centreDAO.updateStatus(
                            id,
                            status
                    );


            if(updated){

                response.getWriter().print(
                    "{\"success\":true,\"status\":\""+status+"\"}"
                );

            }else{

                response.getWriter().print(
                    "{\"success\":false}"
                );
            }

        }catch(Exception e){

            e.printStackTrace();

            response.getWriter().print(
                "{\"success\":false,\"error\":\"server\"}"
            );
        }
    }

    @Override
    public String getServletInfo(){
        return "Centre Management Servlet";
    }
}