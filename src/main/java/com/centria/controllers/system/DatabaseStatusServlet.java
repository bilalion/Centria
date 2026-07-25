/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


/*
 * Centria
 * Database Status Controller
 *
 * Module : System
 * Author : Chentouf Bilal
 *
 * Description:
 * Checks database availability
 * and returns JSON status.
 */


package com.centria.controllers.system;


import com.centria.utils.DBConnection;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DatabaseStatusServlet")
public class DatabaseStatusServlet extends HttpServlet {


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        /*
         =====================================
         JSON Response
         =====================================
         */

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        try {
            /*
             =====================================
             اختبار الاتصال
             =====================================
             */

            try(Connection con =
                    DBConnection.getConnection()) {


                if(con != null && !con.isClosed()){


                    response.getWriter()
                            .print(
                            "{\"status\":\"connected\"}"
                            );


                }

                else{


                    response.getWriter()
                            .print(
                            "{\"status\":\"failed\"}"
                            );
}}}
        catch(Exception e){

            /*
             =====================================
             تسجيل الخطأ في السيرفر فقط
             =====================================
             */
            e.printStackTrace();
            /*
             =====================================
             رسالة عامة للمستخدم
             =====================================
             */

            response.getWriter()
                    .print(
                    "{\"status\":\"error\"}"
                    );
        }
    }
}