<%@page import="com.centria.language.LanguageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- =================================================
     SECTION 01 - PLATFORM BANNER
================================================= -->


<div class="platform-banner">





    <!-- =============================================
         PLATFORM BRAND
    ============================================== -->


    <div class="platform-brand">





        <!-- CENTRIA LOGO -->


        <img src="<%=request.getContextPath()%>/assets/images/centria-logo.png"

             class="platform-logo"

             alt="Centria Logo">







        <!-- PLATFORM TITLE -->


        <span class="platform-title">


            <%= LanguageManager.get(
                    "platform.banner.title",
                    session
            ) %>


        </span>





    </div>









    <!-- =============================================
         PLATFORM SEPARATOR
    ============================================== -->


    <span class="platform-separator">


        |


    </span>









    <!-- =============================================
         PLATFORM INFORMATION
    ============================================== -->


    <div class="platform-information">





        <span class="platform-message">


            <%= LanguageManager.get(
                    "platform.banner.message",
                    session
            ) %>


        </span>






    </div>







</div>