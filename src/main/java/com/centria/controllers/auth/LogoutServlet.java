/*
 * File        : LogoutServlet.java
 * Project     : CENTRIA
 *
 * Description :
 * إنهاء جلسة المدير مع حفظ اللغة.
 */

package com.centria.controllers.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {


    // ====================
    // تسجيل الخروج
    // ====================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException {


        HttpSession oldSession =
                request.getSession(false);


        String language = "ar";


        // ====================
        // حفظ اللغة وإنهاء الجلسة
        // ====================

        if(oldSession != null){

            Object currentLang =
                    oldSession.getAttribute("lang");

            if(currentLang != null){
                language = currentLang.toString();
            }

            oldSession.invalidate();
        }


        // ====================
        // إنشاء جلسة جديدة
        // ====================

        HttpSession newSession =
                request.getSession(true);


        newSession.setAttribute(
                "lang",
                language
        );


        // ====================
        // العودة لتسجيل الدخول
        // ====================

        response.sendRedirect(
                request.getContextPath()
                + "/admin/superlogin.jsp"
        );
    }
}