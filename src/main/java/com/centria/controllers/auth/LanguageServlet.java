/*
 * File        : LanguageServlet.java
 * Project     : CENTRIA
 *
 * Module      : Authentication / Language
 *
 * Description :
 * Manage system language selection.
 *
 * Responsibilities:
 * - Receive selected language
 * - Validate language using SupportedLanguage
 * - Store language in session
 * - Redirect user back
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




@WebServlet("/LanguageServlet")
public class LanguageServlet extends HttpServlet {




    /*
     ======================================================
     01 - CHANGE SYSTEM LANGUAGE
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
         02 - GET USER SESSION
         ==================================================
         */


        HttpSession session =
                request.getSession();







        /*
         ==================================================
         03 - RECEIVE LANGUAGE CODE
         ==================================================
         */


        String lang =
                request.getParameter("lang");








        /*
         ==================================================
         04 - VALIDATE LANGUAGE
         
         SupportedLanguage handles:
         - null value
         - empty value
         - unsupported language
         ==================================================
         */


        lang =
        SupportedLanguage.normalize(lang);








        /*
         ==================================================
         05 - SAVE LANGUAGE IN SESSION
         ==================================================
         */


        session.setAttribute(
                "lang",
                lang
        );








        /*
         ==================================================
         06 - RETURN TO PREVIOUS PAGE
         ==================================================
         */


        String referer =
                request.getHeader("Referer");




        if(referer != null){


            response.sendRedirect(referer);


        }
        else{


            response.sendRedirect(

                    request.getContextPath()
                    + "/admin/dashboard.jsp?section=home"

            );


        }




    }



}