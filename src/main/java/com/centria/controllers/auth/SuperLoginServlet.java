/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 */


/*
 * Centria
 * Account Authentication Controller
 *
 * Module : Login
 * Author : Chentouf Bilal
 *
 * Description:
 * Handles authentication for all accounts stored
 * in the super_admins table.
 *
 * Supported account types:
 * - SUPER_ADMIN
 * - MANAGER
 * - OPERATOR
 *
 * Authentication:
 * - Username
 * - BCrypt password
 * - ACTIVE status
 *
 * Session:
 * - adminId
 * - adminUsername
 * - adminType
 */


package com.centria.controllers.auth;


import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.centria.utils.DBConnection;

import org.mindrot.jbcrypt.BCrypt;

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
         =====================================================
         UTF-8
         =====================================================
         */

        request.setCharacterEncoding("UTF-8");


        /*
         =====================================================
         GET LOGIN DATA
         =====================================================
         */

        String username =
                request.getParameter("username");


        String password =
                request.getParameter("password");


        Connection con = null;

        PreparedStatement ps = null;

        PreparedStatement lastLoginPs = null;

        ResultSet rs = null;


        try {


            /*
             =====================================================
             BASIC VALIDATION
             =====================================================
             */

            if (
                    username == null
                    || username.trim().isEmpty()
                    || password == null
                    || password.isEmpty()
            ) {


                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/superlogin.jsp?error=invalid"
                );

                return;
            }


            username =
                    username.trim();


            /*
             =====================================================
             DATABASE CONNECTION
             =====================================================
             */

            con =
                    DBConnection.getConnection();


            /*
             =====================================================
             FIND ACCOUNT
             
             IMPORTANT:
             We search ONLY by username.
             
             We DO NOT check type here.
             
             Therefore:
             SUPER_ADMIN
             MANAGER
             OPERATOR
             
             are all allowed.
             =====================================================
             */

            String sql =
                    "SELECT id, username, type, status, password "
                    + "FROM super_admins "
                    + "WHERE username=?";


            ps =
                    con.prepareStatement(sql);


            ps.setString(
                    1,
                    username
            );


            rs =
                    ps.executeQuery();


            /*
             =====================================================
             ACCOUNT NOT FOUND
             =====================================================
             */

            if (!rs.next()) {


                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/superlogin.jsp?error=invalid"
                );

                return;
            }


            /*
             =====================================================
             GET ACCOUNT DATA
             =====================================================
             */

            int adminId =
                    rs.getInt("id");


            String dbUsername =
                    rs.getString("username");


            String adminType =
                    rs.getString("type");


            String status =
                    rs.getString("status");


            String passwordHash =
                    rs.getString("password");


            /*
             =====================================================
             CHECK PASSWORD
             =====================================================
             */

            boolean passwordCorrect = false;


            if (
                    passwordHash != null
                    && !passwordHash.trim().isEmpty()
            ) {


                passwordCorrect =
                        BCrypt.checkpw(
                                password,
                                passwordHash
                        );
            }


            /*
             =====================================================
             WRONG PASSWORD
             =====================================================
             */

            if (!passwordCorrect) {


                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/superlogin.jsp?error=invalid"
                );

                return;
            }


            /*
             =====================================================
             CHECK ACCOUNT STATUS
             
             Only ACTIVE accounts can login.
             
             Type does NOT matter here.
             =====================================================
             */

            if (
                    status == null
                    || !"ACTIVE".equalsIgnoreCase(status)
            ) {


                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/superlogin.jsp?error=inactive"
                );

                return;
            }


            /*
             =====================================================
             CREATE SESSION
             =====================================================
             */

            HttpSession session =
                    request.getSession(true);


            /*
             =====================================================
             STORE ACCOUNT ID
             =====================================================
             */

            session.setAttribute(
                    "adminId",
                    adminId
            );


            /*
             =====================================================
             STORE USERNAME
             =====================================================
             */

            session.setAttribute(
                    "adminUsername",
                    dbUsername
            );


            /*
             =====================================================
             STORE REAL ACCOUNT TYPE
             
             SUPER_ADMIN
             MANAGER
             OPERATOR
             =====================================================
             */

            session.setAttribute(
                    "adminType",
                    adminType
            );


            /*
             =====================================================
             COMPATIBILITY FLAG
             
             TRUE ONLY FOR SUPER_ADMIN.
             
             IMPORTANT:
             This flag is NOT used to block
             MANAGER or OPERATOR authentication.
             =====================================================
             */

            boolean isSuperAdmin =
                    "SUPER_ADMIN".equalsIgnoreCase(
                            adminType
                    );


            session.setAttribute(
                    "isSuperAdmin",
                    isSuperAdmin
            );


            /*
             =====================================================
             UPDATE LAST LOGIN
             =====================================================
             */

            String lastLoginSql =
                    "UPDATE super_admins "
                    + "SET last_login = NOW() "
                    + "WHERE id=?";


            lastLoginPs =
                    con.prepareStatement(
                            lastLoginSql
                    );


            lastLoginPs.setInt(
                    1,
                    adminId
            );


            lastLoginPs.executeUpdate();


            /*
             =====================================================
             KEEP SELECTED LANGUAGE
             =====================================================
             */

            Object lang =
                    session.getAttribute("lang");


            if (lang == null) {


                session.setAttribute(
                        "lang",
                        "ar"
                );
            }


            /*
             =====================================================
             LOGIN SUCCESS
             
             ALL ACCOUNT TYPES GO TO THE DASHBOARD.
             =====================================================
             */

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/dashboard.jsp?section=home"
            );


        }


        catch (Exception e) {


            e.printStackTrace();


            /*
             =====================================================
             SYSTEM ERROR
             =====================================================
             */

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/superlogin.jsp?error=system_error"
            );


        }


        finally {


            /*
             =====================================================
             CLOSE DATABASE RESOURCES
             =====================================================
             */

            try {


                if (lastLoginPs != null) {
                    lastLoginPs.close();
                }


                if (rs != null) {
                    rs.close();
                }


                if (ps != null) {
                    ps.close();
                }


                if (con != null) {
                    con.close();
                }


            }


            catch (Exception e) {

                e.printStackTrace();

            }
        }
    }
}