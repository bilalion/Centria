/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


/*
 * Centria
 * Super Admin Authentication Controller
 *
 * Module : Login
 * Author : Chentouf Bilal
 *
 * Description:
 * Handles Super Admin login process.
 * - Checks username and password
 * - Creates admin session
 * - Keeps selected language
 * - Redirects to dashboard
 */


package com.centria.controllers.auth;


import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.centria.utils.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/SuperLoginServlet")
public class SuperLoginServlet extends HttpServlet {


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        /*
         =====================================
         Encoding UTF-8
         =====================================
         */

        request.setCharacterEncoding("UTF-8");


        /*
         =====================================
         استقبال بيانات الدخول
         =====================================
         */

        String username =
                request.getParameter("username");


        String password =
                request.getParameter("password");


        Connection con = null;

        PreparedStatement ps = null;

        ResultSet rs = null;


        try {


            /*
             =====================================
             الاتصال بقاعدة البيانات
             =====================================
             */

            con =
            DBConnection.getConnection();



            /*
             =====================================
             التحقق من بيانات Super Admin
             =====================================
             */

            String sql =

            "SELECT * FROM super_admins "
            + "WHERE username=? "
            + "AND password=?";



            ps =
            con.prepareStatement(sql);



            ps.setString(
                    1,
                    username
            );


            ps.setString(
                    2,
                    password
            );



            rs =
            ps.executeQuery();



            if(rs.next()){


                /*
                 =====================================
                 إنشاء Session
                 =====================================
                 */

                HttpSession session =
                        request.getSession();



                /*
                 =====================================
                 تسجيل حالة Super Admin
                 =====================================
                 */

                session.setAttribute(
                        "isSuperAdmin",
                        true
                );



                session.setAttribute(
                     "adminUsername",
                     username
                );


/*
 =====================================
 حفظ نوع المستخدم
 =====================================
 */

                  String adminType =
                  rs.getString("type");


                  session.setAttribute(
                          "adminType",
                          adminType
                  );


                /*
                 =====================================
                 تثبيت اللغة المختارة

                 اللغة يتم اختيارها فقط
                 قبل الدخول
                 =====================================
                 */

                Object lang =
                        session.getAttribute("lang");



                if(lang == null){

                    session.setAttribute(
                            "lang",
                            "ar"
                    );

                }



                /*
                 =====================================
                 الدخول إلى لوحة التحكم
                 =====================================
                 */

                response.sendRedirect(

                        request.getContextPath()
                        + "/admin/dashboard.jsp?section=home"

                );


            }

            else{


                /*
                 =====================================
                 بيانات غير صحيحة
                 =====================================
                 */

                response.sendRedirect(

                        request.getContextPath()
                        + "/admin/superlogin.jsp?error=invalid"

                );


            }


        }

    catch(Exception e){


    e.printStackTrace();


    response.sendRedirect(

            request.getContextPath()
            + "/admin/superlogin.jsp?error=system_error"

    );


}

        finally{


            /*
             =====================================
             إغلاق الموارد
             =====================================
             */

            try{


                if(rs != null)
                    rs.close();


                if(ps != null)
                    ps.close();


                if(con != null)
                    con.close();


            }

            catch(Exception e){

                e.printStackTrace();

            }


        }


    }


}