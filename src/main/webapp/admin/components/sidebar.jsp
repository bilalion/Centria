<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<%
/*
======================================================
CURRENT SECTION
======================================================

Used to keep the correct Sidebar item active when
the dashboard is opened with:

?section=home
?section=centres
?section=payments
?section=archive
======================================================
*/

String currentSection = request.getParameter("section");

if (currentSection == null || currentSection.isEmpty()) {
    currentSection = "home";
}
%>


<!-- =================================================
     SECTION 01 - SIDEBAR
================================================= -->


<aside class="sidebar">


    <!-- =============================================
         SECTION 02 - NAVIGATION MENU
    ============================================== -->


    <nav class="sidebar-menu">


        <!-- =========================================
             HOME
        ========================================== -->


        <a id="sidebar-home"

           href="javascript:void(0)"

           class="sidebar-link <%= "home".equals(currentSection) ? "active" : "" %>"

           onclick="loadContent('HomeServlet', this)">


            <span class="sidebar-icon"
                  title="Home">


                <i class="fa-solid fa-house"></i>


            </span>


            <span class="sidebar-text">


                <%= LanguageManager.get(
                        "dashboard.home",
                        session
                ) %>


            </span>


        </a>


        <!-- =========================================
             CENTRES
        ========================================== -->


        <a id="sidebar-centres"

           href="javascript:void(0)"

           class="sidebar-link <%= "centres".equals(currentSection) ? "active" : "" %>"

           onclick="loadContent('CentreServlet?action=list', this)">


            <span class="sidebar-icon"
                  title="Centres">


                <i class="fa-solid fa-building"></i>


            </span>


            <span class="sidebar-text">


                <%= LanguageManager.get(
                        "dashboard.centers",
                        session
                ) %>


            </span>


        </a>


        <!-- =========================================
             PAYMENTS
        ========================================== -->


        <a id="sidebar-payments"

           href="javascript:void(0)"

           class="sidebar-link <%= "payments".equals(currentSection) ? "active" : "" %>"

           onclick="loadContent('payments.jsp', this)">


            <span class="sidebar-icon"
                  title="Payments">


                <i class="fa-solid fa-money-bill-wave"></i>


            </span>


            <span class="sidebar-text">


                <%= LanguageManager.get(
                        "dashboard.payments",
                        session
                ) %>


            </span>


        </a>


        <!-- =========================================
             ARCHIVE
        ========================================== -->


        <a id="sidebar-archive"

           href="javascript:void(0)"

           class="sidebar-link <%= "archive".equals(currentSection) ? "active" : "" %>"

           onclick="loadContent('ArchiveServlet?action=list', this)">


            <span class="sidebar-icon"
                  title="Archive">


                <i class="fa-solid fa-box-archive"></i>


            </span>


            <span class="sidebar-text">


                <%= LanguageManager.get(
                        "dashboard.archive",
                        session
                ) %>


            </span>


        </a>


    </nav>


    <!-- =============================================
         SECTION 03 - SIDEBAR FOOTER
    ============================================== -->


    <div class="sidebar-footer">


        <!-- =========================================
             LOGOUT
        ========================================== -->


        <a href="<%=request.getContextPath()%>/LogoutServlet"

           class="sidebar-logout">


            <span class="sidebar-icon"
                  title="Logout">


                <i class="fa-solid fa-right-from-bracket"></i>


            </span>


            <span class="sidebar-text">


                <%= LanguageManager.get(
                        "dashboard.logout",
                        session
                ) %>


            </span>


        </a>


    </div>


</aside>