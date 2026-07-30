/*
 * File        : LogoutServlet.java
 * Project     : CENTRIA
 *
 * Description :
 * End admin session while preserving language.
 *
 * Responsibilities:
 * - Retrieve current language
 * - Close old session
 * - Create new session
 * - Restore language
 */


package com.centria.controllers.auth;



import java.io.IOException;


import com.centria.language.SupportedLanguage;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {




    /*
     ======================================================
     01 - LOGOUT PROCESS
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
         02 - GET CURRENT SESSION
         ==================================================
         */


        HttpSession oldSession =
                request.getSession(false);





        String language =

                SupportedLanguage.getDefault();








        /*
         ==================================================
         03 - PRESERVE LANGUAGE
         
         Before destroying session
         ==================================================
         */


        if(oldSession != null){



            Object currentLang =

                    oldSession.getAttribute("lang");



            if(currentLang != null){


                language =

                SupportedLanguage.normalize(
                        currentLang.toString()
                );


            }



            /*
             ==============================================
             Destroy old session
             ==============================================
             */


            oldSession.invalidate();


        }








        /*
         ==================================================
         04 - CREATE NEW SESSION
         ==================================================
         */


        HttpSession newSession =

                request.getSession(true);






        newSession.setAttribute(
                "lang",
                language
        );









        /*
         ==================================================
         05 - RETURN TO LOGIN PAGE
         ==================================================
         */


        response.sendRedirect(

                request.getContextPath()
                + "/admin/superlogin.jsp"

        );




    }




}