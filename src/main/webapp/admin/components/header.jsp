<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<%
    /*
    =================================================
     LOGGED-IN SUPER ADMIN
    =================================================
    */

    String adminUsername =
            (String) session.getAttribute("adminUsername");


    /*
    -------------------------------------------------
     Fallback
    -------------------------------------------------
    */

    if (adminUsername == null ||
        adminUsername.trim().isEmpty()) {

        adminUsername = "Super Admin";

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


            <img src="<%=request.getContextPath()%>/assets/images/centria-logo.png"

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

                Developer Panel

            </span>



        </div>





    </div>









    <!-- =============================================
         SECTION 03 - HEADER RIGHT
    ============================================== -->


    <div class="header-actions">








        <!-- SETTINGS -->


        <a href="javascript:void(0)"

           class="header-action"

           onclick="loadContent('settings.jsp', this)">



            <i class="fa-solid fa-gear"></i>



        </a>









        <!-- NOTIFICATION -->


        <button class="header-action notification-button">


            <i class="fa-solid fa-bell"></i>



            <span class="notification-count">

                3

            </span>



        </button>









        <!-- USER PROFILE -->


        <div class="header-profile">





            <span class="header-avatar">


                <i class="fa-solid fa-user"></i>


            </span>







            <div class="header-user-info">



                <span class="header-username">


                    <%= adminUsername %>


                </span>





                <span class="header-role">


                    Super Admin


                </span>



            </div>








            <span class="header-arrow">


                <i class="fa-solid fa-chevron-down"></i>


            </span>






        </div>







    </div>








</header>
