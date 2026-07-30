<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<div class="sidebar">

    <!-- ==========================
         BRAND
         ========================== -->

    <div class="brand-section">

        <img
            src="<%=request.getContextPath()%>/assets/images/centria-logo.png"
            alt="Centria Logo"
            class="logo">

        <h2 class="brand-name">
            Centria
        </h2>

        <span class="brand-subtitle">
            <%= LanguageManager.get("dashboard.subtitle", session) %>
        </span>

    </div>

    <hr>

    <!-- ==========================
         WELCOME
         ========================== -->

    <p class="welcome-text">
        <%= LanguageManager.get("dashboard.welcome", session) %>
    </p>

    <!-- ==========================
         MENU
         ========================== -->

    <div class="menu-section">

        <!-- HOME -->
        <a href="javascript:void(0)"
           class="menu-link"
           onclick="loadContent('accueil.jsp', this)">

            <span class="menu-icon">🏠</span>

            <span class="menu-text">
                <%= LanguageManager.get("dashboard.home", session) %>
            </span>

        </a>

        <!-- CENTERS -->
        <a href="javascript:void(0)"
           class="menu-link"
           onclick="loadContent('CentreServlet?action=list', this)">

            <span class="menu-icon">🏢</span>

            <span class="menu-text">
                <%= LanguageManager.get("dashboard.centers", session) %>
            </span>

        </a>
                    <!-- PAYMENTS -->
        <a href="javascript:void(0)"
           class="menu-link"
           onclick="loadContent('payments.jsp', this)">

            <span class="menu-icon">💰</span>

            <span class="menu-text">
                <%= LanguageManager.get("dashboard.payments", session) %>
            </span>

        </a>

        <!-- ARCHIVE -->
        <a href="javascript:void(0)"
           class="menu-link"
           onclick="loadContent('archive.jsp', this)">

            <span class="menu-icon">🗄️</span>

            <span class="menu-text">
                <%= LanguageManager.get("dashboard.archive", session) %>
            </span>

        </a>

        <!-- SETTINGS -->
        <a href="javascript:void(0)"
           class="menu-link"
           onclick="loadContent('settings.jsp', this)">

            <span class="menu-icon">⚙️</span>

            <span class="menu-text">
                <%= LanguageManager.get("dashboard.settings", session) %>
            </span>

        </a>

    </div>

    <!-- ==========================
         LOGOUT
         ========================== -->

    <a href="<%=request.getContextPath()%>/LogoutServlet"
       class="logout-btn">

        <span class="menu-icon">🚪</span>

        <span class="menu-text">
            <%= LanguageManager.get("dashboard.logout", session) %>
        </span>

    </a>

</div>