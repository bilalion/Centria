/*
 * Centria
 * Account Security Filter
 *
 * Module : Authentication
 * Author : Chentouf Bilal
 *
 * Description:
 * Protects admin pages.
 *
 * Allows access for all authenticated accounts:
 *
 * - SUPER_ADMIN
 * - MANAGER
 * - OPERATOR
 *
 * Authentication is established by SuperLoginServlet.
 *
 * The filter checks the authenticated session only.
 * Account type is NOT used to block authentication.
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


    /*
     ==========================================================
     INIT
     ==========================================================
     */

    @Override
    public void init(
            FilterConfig filterConfig)
            throws ServletException {

    }


    /*
     ==========================================================
     DO FILTER
     ==========================================================
     */

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
         ======================================================
         PUBLIC RESOURCES
         
         These resources can be loaded without login.
         ======================================================
         */

        if (
                uri.contains("/assets/")
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
                uri.endsWith(".gif")
                ||
                uri.endsWith(".svg")
                ||
                uri.endsWith(".ico")
                ||
                uri.endsWith(".woff")
                ||
                uri.endsWith(".woff2")
                ||
                uri.endsWith(".ttf")
        ) {


            chain.doFilter(
                    request,
                    response
            );


            return;
        }


        /*
         ======================================================
         LOGIN / LANGUAGE RESOURCES
         
         These resources must remain accessible before login.
         ======================================================
         */

        if (
                uri.endsWith("superlogin.jsp")
                ||
                uri.contains("SuperLoginServlet")
                ||
                uri.contains("LanguageServlet")
        ) {


            /*
             --------------------------------------------------
             Get existing session.
             Do NOT create a new session here.
             --------------------------------------------------
             */

            HttpSession session =
                    req.getSession(false);


            /*
             --------------------------------------------------
             An authenticated account is identified by
             adminId + adminUsername + adminType.
             
             We do NOT check isSuperAdmin.
             --------------------------------------------------
             */

            boolean logged =
                    session != null
                    &&
                    session.getAttribute("adminId") != null
                    &&
                    session.getAttribute("adminUsername") != null
                    &&
                    session.getAttribute("adminType") != null;


            /*
             --------------------------------------------------
             If already logged in and tries to open login page,
             send him to dashboard.
             --------------------------------------------------
             */

            if (
                    logged
                    &&
                    uri.endsWith("superlogin.jsp")
            ) {


                res.sendRedirect(

                        req.getContextPath()
                        + "/admin/dashboard.jsp?section=home"

                );


                return;
            }


            /*
             --------------------------------------------------
             Login servlet / language servlet remain accessible.
             --------------------------------------------------
             */

            chain.doFilter(
                    request,
                    response
            );


            return;
        }


        /*
         ======================================================
         GET CURRENT SESSION
         ======================================================
         */

        HttpSession session =
                req.getSession(false);


        /*
         ======================================================
         CHECK AUTHENTICATED ACCOUNT
         
         IMPORTANT:
         
         We check the existence of the account session,
         NOT the account type.
         
         Therefore:
         
         SUPER_ADMIN -> allowed
         MANAGER     -> allowed
         OPERATOR    -> allowed
         ======================================================
         */

        boolean authenticated =
                session != null
                &&
                session.getAttribute("adminId") != null
                &&
                session.getAttribute("adminUsername") != null
                &&
                session.getAttribute("adminType") != null;


        /*
         ======================================================
         AUTHENTICATED
         ======================================================
         */

        if (authenticated) {


            chain.doFilter(
                    request,
                    response
            );


            return;
        }


        /*
         ======================================================
         NOT AUTHENTICATED
         ======================================================
         */

        res.sendRedirect(

                req.getContextPath()
                + "/admin/superlogin.jsp"

        );
    }


    /*
     ==========================================================
     DESTROY
     ==========================================================
     */

    @Override
    public void destroy() {

    }
}