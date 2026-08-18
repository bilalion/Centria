<%--
==========================================================
File        : profile.jsp
Project     : CENTRIA
Layer       : Page
Component   : Profile

Description :
Logged-in administrator profile page.

Responsibilities :
- Profile overview
- Account information
- Security information
==========================================================
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.language.LanguageManager"%>
<%@page import="com.centria.dao.ProfileDAO"%>


<%
    /*
    ======================================================
     SESSION USER
    ======================================================
    */

    String adminUsername =
            (String) session.getAttribute("adminUsername");

    String adminType =
            (String) session.getAttribute("adminType");


    /*
    ======================================================
     FALLBACK
    ======================================================
    */

    if (adminUsername == null ||
        adminUsername.trim().isEmpty()) {

        adminUsername = "Super Admin";
    }


    if (adminType == null ||
        adminType.trim().isEmpty()) {

        adminType = "SUPER_ADMIN";
    }


    /*
    ======================================================
     ROLE DISPLAY
    ======================================================
    */

    String roleDisplay;


    if ("SUPER_ADMIN".equalsIgnoreCase(adminType)) {

        roleDisplay =
                LanguageManager.get(
                        "header.role.super_admin",
                        session
                );

    }

    else if ("MANAGER".equalsIgnoreCase(adminType)) {

        roleDisplay =
                LanguageManager.get(
                        "header.role.manager",
                        session
                );

    }

    else if ("OPERATOR".equalsIgnoreCase(adminType)) {

        roleDisplay =
                LanguageManager.get(
                        "header.role.operator",
                        session
                );

    }

    else {

        roleDisplay = adminType;

    }


    /*
    ======================================================
     USER ID
    ======================================================
     */

    Integer adminId = null;

    Object sessionAdminId =
            session.getAttribute("adminId");


    if (sessionAdminId instanceof Integer) {

        adminId =
                (Integer) sessionAdminId;

    }

    else if (sessionAdminId != null) {

        try {

            adminId =
                    Integer.parseInt(
                            sessionAdminId.toString()
                    );

        } catch (NumberFormatException e) {

            adminId = null;

        }
    }


    /*
    ======================================================
     AVATAR
    ======================================================
     */

    String avatarPath = null;


    if (adminId != null) {

        try {

            ProfileDAO profileDAO =
                    new ProfileDAO();

            avatarPath =
                    profileDAO.getAvatar(
                            adminId
                    );

        } catch (Exception e) {

            e.printStackTrace();

            avatarPath = null;
        }
    }


    /*
    ======================================================
     DEFAULT AVATAR
    ======================================================
     */

    String avatarUrl =
            request.getContextPath()
            + "/assets/images/default-avatar.png";


    /*
    ======================================================
     SAVED AVATAR
    ======================================================
     */

  if (avatarPath != null &&
    !avatarPath.trim().isEmpty()) {

    if (avatarPath.startsWith("/")) {

        avatarUrl =
                request.getContextPath()
                + avatarPath;

    } else {

        avatarUrl =
                request.getContextPath()
                + "/"
                + avatarPath;

    }
}

%>


<!-- =====================================================
     PROFILE PAGE
===================================================== -->

<section class="profile-page">


    <!-- =================================================
         PAGE BANNER
    ================================================== -->

    <div class="profile-banner">


        <div class="profile-banner-icon">

            <i class="fa-solid fa-user"></i>

        </div>


        <div class="profile-banner-content">

            <strong>

                <%= LanguageManager.get(
                        "profile.title",
                        session
                    ) %>

            </strong>


            <span class="profile-banner-separator">
                |
            </span>


            <span class="profile-banner-description">

                <%= LanguageManager.get(
                        "profile.description",
                        session
                    ) %>

            </span>

        </div>


    </div>


    <!-- =================================================
         PROFILE CONTENT
    ================================================== -->

    <div class="profile-grid">


        <!-- =============================================
             MAIN PROFILE CARD
        ============================================== -->

        <div class="profile-card profile-main-card">


            <!-- =========================================
                 AVATAR
            ========================================== -->

            <div class="profile-avatar-wrapper">


                <!-- AVATAR IMAGE -->

                <img
                    id="profileAvatar"
                    class="profile-avatar-large"
                    src="<%= avatarUrl %>"
                    alt="Profile Avatar">


                <!-- HIDDEN FILE INPUT -->

                <input
                    type="file"
                    id="avatarInput"
                    name="avatar"
                    accept="image/*"
                    style="display:none;">


                <!-- CHANGE AVATAR -->

                <button
                    type="button"
                    id="avatarUploadButton"
                    class="profile-avatar-change"
                    title="<%= LanguageManager.get(
                            "profile.avatar.change",
                            session
                        ) %>">

                    <i class="fa-solid fa-camera"></i>

                </button>


            </div>


            <!-- USERNAME -->

            <h2>

                <%= adminUsername %>

            </h2>


            <!-- ROLE -->

            <span class="profile-role">

                <%= roleDisplay %>

            </span>


            <!-- STATUS -->

            <div class="profile-status">

                <span class="profile-status-dot"></span>

                <span>

                    <%= LanguageManager.get(
                            "profile.active",
                            session
                        ) %>

                </span>

            </div>


        </div>


        <!-- =============================================
             ACCOUNT INFORMATION
        ============================================== -->

        <div class="profile-card profile-account-card">


            <!-- CARD HEADER -->

            <div class="profile-card-header">


                <div class="profile-card-title">

                    <i class="fa-solid fa-id-card"></i>

                    <span>

                        <%= LanguageManager.get(
                                "profile.account.information",
                                session
                            ) %>

                    </span>

                </div>


                <!-- EDIT PROFILE -->

                <button
                    type="button"
                    class="profile-edit-button">

                    <i class="fa-solid fa-pen"></i>

                    <span>

                        <%= LanguageManager.get(
                                "profile.edit",
                                session
                            ) %>

                    </span>

                </button>


            </div>


            <!-- ACCOUNT INFORMATION -->

            <div class="profile-info-list">


                <!-- USERNAME -->

                <div class="profile-info-row">


                    <div class="profile-info-icon">

                        <i class="fa-solid fa-user"></i>

                    </div>


                    <div class="profile-info-content">

                        <span class="profile-info-label">

                            <%= LanguageManager.get(
                                    "profile.username",
                                    session
                                ) %>

                        </span>


                        <span class="profile-info-value">

                            <%= adminUsername %>

                        </span>

                    </div>


                </div>


                <!-- ROLE -->

                <div class="profile-info-row">


                    <div class="profile-info-icon">

                        <i class="fa-solid fa-shield-halved"></i>

                    </div>


                    <div class="profile-info-content">

                        <span class="profile-info-label">

                            <%= LanguageManager.get(
                                    "profile.role",
                                    session
                                ) %>

                        </span>


                        <span class="profile-info-value">

                            <%= roleDisplay %>

                        </span>

                    </div>


                </div>


                <!-- EMAIL -->

                <div class="profile-info-row">


                    <div class="profile-info-icon">

                        <i class="fa-solid fa-envelope"></i>

                    </div>


                    <div class="profile-info-content">

                        <span class="profile-info-label">

                            <%= LanguageManager.get(
                                    "profile.email",
                                    session
                                ) %>

                        </span>


                        <span class="profile-info-value">

                            —

                        </span>

                    </div>


                </div>


                <!-- PHONE -->

                <div class="profile-info-row">


                    <div class="profile-info-icon">

                        <i class="fa-solid fa-phone"></i>

                    </div>


                    <div class="profile-info-content">

                        <span class="profile-info-label">

                            <%= LanguageManager.get(
                                    "profile.phone",
                                    session
                                ) %>

                        </span>


                        <span class="profile-info-value">

                            —

                        </span>

                    </div>


                </div>


            </div>


        </div>


        <!-- =============================================
             SECURITY
        ============================================== -->

        <div class="profile-card profile-security-card">


            <!-- CARD HEADER -->

            <div class="profile-card-header">


                <div class="profile-card-title">

                    <i class="fa-solid fa-lock"></i>

                    <span>

                        <%= LanguageManager.get(
                                "profile.security",
                                session
                            ) %>

                    </span>

                </div>


            </div>


            <!-- SECURITY CONTENT -->

            <div class="profile-security-content">


                <div class="profile-security-item">


                    <!-- ICON -->

                    <div class="profile-security-icon">

                        <i class="fa-solid fa-key"></i>

                    </div>


                    <!-- PASSWORD -->

                    <div class="profile-security-text">

                        <strong>

                            <%= LanguageManager.get(
                                    "profile.password",
                                    session
                                ) %>

                        </strong>


                        <span>

                            ••••••••••••

                        </span>

                    </div>


                    <!-- CHANGE PASSWORD -->

                    <button
                        type="button"
                        class="profile-secondary-button">

                        <i class="fa-solid fa-pen"></i>

                        <span>

                            <%= LanguageManager.get(
                                    "profile.change.password",
                                    session
                                ) %>

                        </span>

                    </button>


                </div>


            </div>


        </div>


    </div>


</section>


