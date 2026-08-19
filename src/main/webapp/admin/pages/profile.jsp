<%--
==========================================================
File        : profile.jsp
Project     : CENTRIA
Layer       : Page
Component   : Profile

Description :
Logged-in administrator profile page.

Editable information :
- username
- email
- phone

Readonly information :
- type
- status
- created_at
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
     PROFILE DATA
     Loaded by ProfileServlet / Controller
    ======================================================
    */

    Object profileUsernameObj =
            request.getAttribute("profileUsername");

    Object profileEmailObj =
            request.getAttribute("profileEmail");

    Object profilePhoneObj =
            request.getAttribute("profilePhone");

    Object profileTypeObj =
            request.getAttribute("profileType");

    Object profileStatusObj =
            request.getAttribute("profileStatus");

    Object profileCreatedAtObj =
            request.getAttribute("profileCreatedAt");


    /*
    ======================================================
     USERNAME
    ======================================================
    */

    String profileUsername =
            profileUsernameObj != null
                    ? profileUsernameObj.toString()
                    : adminUsername;


    if (profileUsername == null ||
        profileUsername.trim().isEmpty()) {

        profileUsername = "admin";
    }


    /*
    ======================================================
     EMAIL
    ======================================================
    */

    String profileEmail =
            profileEmailObj != null
                    ? profileEmailObj.toString()
                    : "";


    /*
    ======================================================
     PHONE
    ======================================================
    */

    String profilePhone =
            profilePhoneObj != null
                    ? profilePhoneObj.toString()
                    : "";


    /*
    ======================================================
     TYPE
    ======================================================
    */

    String profileType =
            profileTypeObj != null
                    ? profileTypeObj.toString()
                    : adminType;


    if (profileType == null ||
        profileType.trim().isEmpty()) {

        profileType = "SUPER_ADMIN";
    }


    /*
    ======================================================
     ROLE DISPLAY
    ======================================================
    */

    String roleDisplay;


    if ("SUPER_ADMIN".equalsIgnoreCase(profileType)) {

        roleDisplay =
                LanguageManager.get(
                        "header.role.super_admin",
                        session
                );

    }

    else if ("MANAGER".equalsIgnoreCase(profileType)) {

        roleDisplay =
                LanguageManager.get(
                        "header.role.manager",
                        session
                );

    }

    else if ("OPERATOR".equalsIgnoreCase(profileType)) {

        roleDisplay =
                LanguageManager.get(
                        "header.role.operator",
                        session
                );

    }

    else {

        roleDisplay = profileType;

    }


    /*
    ======================================================
     STATUS
    ======================================================
    */

    String profileStatus =
            profileStatusObj != null
                    ? profileStatusObj.toString()
                    : "";


    if (profileStatus == null ||
        profileStatus.trim().isEmpty()) {

        profileStatus = "—";
    }


    /*
    ======================================================
     STATUS DISPLAY
    ======================================================
    */

    String statusDisplay;


    if ("ACTIVE".equalsIgnoreCase(profileStatus)) {

        statusDisplay =
                LanguageManager.get(
                        "profile.active",
                        session
                );

    }

    else {

        statusDisplay = profileStatus;

    }


    /*
    ======================================================
     CREATED AT
    ======================================================
    */

    String profileCreatedAt =
            profileCreatedAtObj != null
                    ? profileCreatedAtObj.toString()
                    : "—";


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

        }

        catch (Exception e) {

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

        }

        else {

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
         PROFILE GRID
    ================================================== -->

    <div class="profile-grid">


        <!-- =============================================
             PROFILE SUMMARY
        ============================================== -->

        <div class="profile-card profile-main-card">


            <!-- =========================================
                 AVATAR
            ========================================== -->

            <div class="profile-avatar-wrapper">


                <img
                    id="profileAvatar"
                    class="profile-avatar-large"
                    src="<%= avatarUrl %>"
                    alt="Profile Avatar">


                <input
                    type="file"
                    id="avatarInput"
                    name="avatar"
                    accept="image/*"
                    style="display:none;">


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

                <%= profileUsername %>

            </h2>


            <!-- ROLE -->

            <span class="profile-role">

                <%= roleDisplay %>

            </span>


            <!-- STATUS -->

            <div class="profile-status">


                <span class="profile-status-dot"></span>


                <span>

                    <%= statusDisplay %>

                </span>


            </div>


        </div>


        <!-- =============================================
             ACCOUNT INFORMATION
        ============================================== -->

        <div class="profile-card profile-account-card">


            <!-- =========================================
                 HEADER
            ========================================== -->

            <div class="profile-card-header">


                <!-- TITLE -->

                <div class="profile-card-title">


                    <i class="fa-solid fa-id-card"></i>


                    <span>

                        <%= LanguageManager.get(
                                "profile.account.information",
                                session
                            ) %>

                    </span>


                </div>


                <!-- =====================================
                     EDIT CONTROLS
                ====================================== -->

                <div class="profile-edit-controls">


                    <!-- CHECKBOX -->

                    <label class="profile-edit-checkbox">


                        <input
                            type="checkbox"
                            id="profileEditCheckbox">


                        <span>

                            <%= LanguageManager.get(
                                    "profile.edit",
                                    session
                                ) %>

                        </span>


                    </label>


                    <!-- EDIT BUTTON -->

                    <button
                        type="button"
                        id="profileEditButton"
                        class="profile-edit-button"
                        disabled>


                        <i class="fa-solid fa-pen"></i>


                        <span>

                            <%= LanguageManager.get(
                                    "profile.edit",
                                    session
                                ) %>

                        </span>


                    </button>


                </div>


            </div>


            <!-- =========================================
                 EDITABLE INFORMATION
                 
                 ONLY:
                 username
                 email
                 phone
            ========================================== -->

            <div class="profile-info-list">


                <!-- =====================================
                     USERNAME
                ====================================== -->

                <div class="profile-info-row">


                    <div class="profile-info-icon">

                        <i class="fa-solid fa-user"></i>

                    </div>


                    <div class="profile-info-content">


                        <label
                            class="profile-info-label"
                            for="profileUsername">

                            <%= LanguageManager.get(
                                    "profile.username",
                                    session
                                ) %>

                        </label>


                        <input
                            type="text"
                            id="profileUsername"
                            name="username"
                            class="profile-info-input"
                            value="<%= profileUsername %>"
                            readonly>


                    </div>


                </div>


                <!-- =====================================
                     EMAIL
                ====================================== -->

                <div class="profile-info-row">


                    <div class="profile-info-icon">

                        <i class="fa-solid fa-envelope"></i>

                    </div>


                    <div class="profile-info-content">


                        <label
                            class="profile-info-label"
                            for="profileEmail">

                            <%= LanguageManager.get(
                                    "profile.email",
                                    session
                                ) %>

                        </label>


                        <input
                            type="email"
                            id="profileEmail"
                            name="email"
                            class="profile-info-input"
                            value="<%= profileEmail %>"
                            readonly>


                    </div>


                </div>


                <!-- =====================================
                     PHONE
                ====================================== -->

                <div class="profile-info-row">


                    <div class="profile-info-icon">

                        <i class="fa-solid fa-phone"></i>

                    </div>


                    <div class="profile-info-content">


                        <label
                            class="profile-info-label"
                            for="profilePhone">

                            <%= LanguageManager.get(
                                    "profile.phone",
                                    session
                                ) %>

                        </label>


                        <input
                            type="tel"
                            id="profilePhone"
                            name="phone"
                            class="profile-info-input"
                            value="<%= profilePhone %>"
                            readonly>


                    </div>


                </div>


            </div>


            <!-- =========================================
                 READONLY INFORMATION
                 
                 NO INPUTS
            ========================================== -->

            <div class="profile-readonly-list">


                <!-- =====================================
                     TYPE
                ====================================== -->

                <div class="profile-readonly-row">


                    <div class="profile-readonly-icon">

                        <i class="fa-solid fa-shield-halved"></i>

                    </div>


                    <div class="profile-readonly-content">


                        <span class="profile-readonly-label">

                            <%= LanguageManager.get(
                                    "profile.role",
                                    session
                                ) %>

                        </span>


                        <strong>

                            <%= roleDisplay %>

                        </strong>


                    </div>


                </div>


                <!-- =====================================
                     STATUS
                ====================================== -->

                <div class="profile-readonly-row">


                    <div class="profile-readonly-icon">

                        <i class="fa-solid fa-circle-check"></i>

                    </div>


                    <div class="profile-readonly-content">


                        <span class="profile-readonly-label">

                            Status

                        </span>


                        <strong>

                            <%= statusDisplay %>

                        </strong>


                    </div>


                </div>


                <!-- =====================================
                     CREATED AT
                ====================================== -->

                <div class="profile-readonly-row">


                    <div class="profile-readonly-icon">

                        <i class="fa-solid fa-calendar-days"></i>

                    </div>


                    <div class="profile-readonly-content">


                        <span class="profile-readonly-label">

                            Created at

                        </span>


                        <strong>

                            <%= profileCreatedAt %>

                        </strong>


                    </div>


                </div>


            </div>


        </div>


        <!-- =============================================
             SECURITY
        ============================================== -->

        <div class="profile-card profile-security-card">


            <!-- HEADER -->

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