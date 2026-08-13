<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<!-- =================================================
     SECTION 01 - SIDEBAR
================================================= -->


<aside class="sidebar">





    <!-- =============================================
         SECTION 02 - NAVIGATION MENU
    ============================================== -->


    <nav class="sidebar-menu">






        <!-- HOME -->


        <a href="javascript:void(0)"

           class="sidebar-link"

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









        <!-- CENTRES -->


        <a href="javascript:void(0)"

           class="sidebar-link"

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









        <!-- PAYMENTS -->


        <a href="javascript:void(0)"

           class="sidebar-link"

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









        <!-- ARCHIVE -->


        <a href="javascript:void(0)"

           class="sidebar-link"

           onclick="loadContent('ArchiveServlet?action=list', this)" >



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






        <!-- LOGOUT -->


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