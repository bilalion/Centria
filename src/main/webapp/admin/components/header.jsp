<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>
<%@page import="com.centria.dao.ProfileDAO"%>


<%
    /*
    =================================================
     LOGGED-IN USER
    =================================================
    */

    String adminUsername =
            (String) session.getAttribute("adminUsername");

    String adminType =
            (String) session.getAttribute("adminType");


    /*
    =================================================
     AVATAR
    =================================================
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
    =================================================
     DEFAULT AVATAR
    =================================================
    */

    String headerAvatarUrl =
            request.getContextPath()
            + "/assets/images/default-avatar.png";


    /*
    =================================================
     LOAD SAVED AVATAR
    =================================================
    */

    if (adminId != null) {

        try {

            ProfileDAO profileDAO =
                    new ProfileDAO();

            String savedAvatar =
                    profileDAO.getAvatar(adminId);


            if (savedAvatar != null &&
                !savedAvatar.trim().isEmpty()) {


                if (savedAvatar.startsWith("/")) {

                    headerAvatarUrl =
                            request.getContextPath()
                            + savedAvatar;

                }

                else {

                    headerAvatarUrl =
                            request.getContextPath()
                            + "/"
                            + savedAvatar;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    /*
    =================================================
     FALLBACK
    =================================================
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
    =================================================
     TYPE DISPLAY
    =================================================
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
%>


<!-- =================================================
     SECTION 01 - HEADER
================================================= -->

<header class="header">


    <!-- =============================================
         SECTION 02 - HEADER LEFT
    ============================================== -->

    <div class="header-left">


        <!-- SIDEBAR TOGGLE -->

        <button class="sidebar-toggle"
                type="button"
                onclick="toggleSidebar()">

            <i class="fa-solid fa-bars"></i>

        </button>


        <!-- PLATFORM LOGO -->

        <div class="header-logo">

            <img
                src="<%=request.getContextPath()%>/assets/images/centria-logo.png"
                alt="Centria Logo">

        </div>


        <!-- BRAND -->

        <div class="header-brand">

            <span class="brand-name">
                Centria
            </span>


            <span class="brand-separator">
                |
            </span>


            <span class="brand-panel">

                <%= LanguageManager.get(
                        "header.developer.panel",
                        session
                    ) %>

            </span>

        </div>


    </div>


    <!-- =============================================
         SECTION 03 - HEADER RIGHT
    ============================================== -->

    <div class="header-actions">


        <!-- =========================================
             SETTINGS
        ========================================== -->

        <a href="#"
           class="header-action"
           onclick="return false;">

            <i class="fa-solid fa-gear"></i>

        </a>


        <!-- =========================================
             NOTIFICATION
        ========================================== -->

        <button
            type="button"
            class="header-action notification-button">

            <i class="fa-solid fa-bell"></i>

            <span class="notification-count">
                3
            </span>

        </button>


        <!-- =========================================
             USER PROFILE
        ========================================== -->

        <div
            class="header-profile"
            id="headerProfile">


            <!-- =====================================
                 AVATAR
            ====================================== -->

            <span class="header-avatar">

                <img
                    src="<%= headerAvatarUrl %>"
                    alt="Avatar">

            </span>


            <!-- USER INFORMATION -->

            <div class="header-user-info">

                <span class="header-username">

                    <%= adminUsername %>

                </span>


                <span class="header-role">

                    <%= roleDisplay %>

                </span>

            </div>


            <!-- ARROW -->

            <span class="header-arrow">

                <i class="fa-solid fa-chevron-down"></i>

            </span>


            <!-- =====================================
                 USER DROPDOWN
            ====================================== -->

            <div
                class="user-dropdown"
                id="userDropdown">


                <!-- =================================
                     PROFILE
                ================================== -->

                <a
                    href="<%=request.getContextPath()%>/admin/dashboard.jsp?section=profile"
                    class="user-dropdown-item">

                    <i class="fa-solid fa-user"></i>

                    <span>

                        <%= LanguageManager.get(
                                "header.profile",
                                session
                            ) %>

                    </span>

                </a>


                <!-- =================================
                     ADD ACCOUNT

                     SUPER ADMIN ONLY
                ================================== -->

                <% if ("SUPER_ADMIN".equalsIgnoreCase(adminType)) { %>


                    <a
                        href="<%=request.getContextPath()%>/admin/pages/add-account.jsp"
                        class="user-dropdown-item">

                        <i class="fa-solid fa-users-gear"></i>

                        <span>

                            <%= LanguageManager.get(
                                    "header.account.management",
                                    session
                                ) %>

                        </span>

                    </a>


                <% } %>


                <!-- =================================
                     DIVIDER
                ================================== -->

                <div class="user-dropdown-divider"></div>


                <!-- =================================
                     LOGOUT
                ================================== -->

                <a
                    href="<%=request.getContextPath()%>/LogoutServlet"
                    class="user-dropdown-item logout-item">

                    <i class="fa-solid fa-right-from-bracket"></i>

                    <span>

                        <%= LanguageManager.get(
                                "header.logout",
                                session
                            ) %>

                    </span>

                </a>


            </div>


        </div>


    </div>


</header>


<!-- =================================================
     HEADER JAVASCRIPT
================================================= -->

<script
    src="<%=request.getContextPath()%>/assets/js/header.js">
</script>