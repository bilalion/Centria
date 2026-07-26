/*
 * Centria
 * Super Admin Security Filter
 *
 * Module : Authentication
 * Author : Chentouf Bilal
 *
 * Description:
 * Protects Super Admin pages.
 * Allows access only for authenticated admins.
 */



package com.centria.filters;



import java.io.IOException;


import javax.servlet.Filter;

import javax.servlet.FilterChain;

import javax.servlet.FilterConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;






@WebFilter("/admin/*")

public class SuperAdminFilter implements Filter {






@Override
public void init(FilterConfig filterConfig)

        throws ServletException {



}







@Override
public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain)

        throws IOException, ServletException {






    HttpServletRequest req =
            (HttpServletRequest) request;



    HttpServletResponse res =
            (HttpServletResponse) response;






    String uri =
            req.getRequestURI();






    /*
     =====================================
     الموارد العامة
     
     يمكن الوصول إليها بدون تسجيل دخول
     =====================================
     */


    if(uri.contains("/assets/")
            ||
       uri.endsWith(".css")
            ||
       uri.endsWith(".js")
            ||
       uri.endsWith(".png")
            ||
       uri.endsWith(".jpg")
            ||
       uri.endsWith(".jpeg")
            ||
       uri.endsWith(".ico")) {



        chain.doFilter(
                request,
                response
        );


        return;


    }



    /*
     =====================================
     صفحات الدخول واللغة
     
     قبل تسجيل الدخول فقط
     =====================================
     */


    if(uri.endsWith("superlogin.jsp")
            ||
       uri.contains("SuperLoginServlet")
            ||
       uri.contains("LanguageServlet")) {




        HttpSession session =
                req.getSession(false);




        boolean logged =
                session != null
                &&
                Boolean.TRUE.equals(
                    session.getAttribute("isSuperAdmin")
                );





        /*
         إذا كان مسجلا بالفعل
         لا نسمح له بالرجوع للدخول
        */


        if(logged
           && uri.endsWith("superlogin.jsp")) {



            res.sendRedirect(

                req.getContextPath()
                + "/admin/super_admin_dashboard.jsp"

            );


            return;


        }





        chain.doFilter(
                request,
                response
        );


        return;


    }








    /*
     =====================================
     التحقق من جلسة Super Admin
     =====================================
     */


    HttpSession session =
            req.getSession(false);




    boolean isSuperAdmin =
            session != null
            &&
            Boolean.TRUE.equals(

                session.getAttribute(
                    "isSuperAdmin"
                )

            );








    if(isSuperAdmin) {



        chain.doFilter(
                request,
                response
        );



    }

    else {



        res.sendRedirect(

            req.getContextPath()
            + "/admin/superlogin.jsp"

        );


    }







}





@Override
public void destroy() {



}






}
