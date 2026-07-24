package com.centria.controllers.auth;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/LanguageServlet")

public class LanguageServlet extends HttpServlet {



@Override
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)

        throws ServletException, IOException {



    HttpSession session =
            request.getSession();




    /*
     ======================================
     الحصول على اللغة المختارة
     ======================================
     */


    String lang =
            request.getParameter("lang");



    if(lang == null || lang.isEmpty()){

        lang="ar";

    }





    /*
     ======================================
     اللغات المدعومة
     ======================================
     */


    if(!lang.equals("ar")
            &&
       !lang.equals("fr")
            &&
       !lang.equals("en")){


        lang="ar";


    }





    /*
     ======================================
     حفظ اللغة للمستخدم الحالي
     ======================================
     */


    session.setAttribute(
            "lang",
            lang
    );






    /*
     ======================================
     العودة للصفحة الحالية
     ======================================
     */


    String referer =
            request.getHeader("Referer");



    if(referer != null){


        response.sendRedirect(referer);


    }
    else{


        response.sendRedirect(
            request.getContextPath()
            + "/admin/dashboard.jsp"
        );


    }



}



}