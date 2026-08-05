<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<!-- =================================================
     SECTION 01 - APPLICATION FOOTER
================================================= -->


<footer class="footer">



    <!-- =============================================
         SECTION 02 - FOOTER INFORMATION
    ============================================== -->


    <div class="footer-left">


        <span>


            <%= LanguageManager.get(
                    "footer.copyright",
                    session
            ) %>


        </span>


    </div>





    <div class="footer-right">


        <span>


            CENTRIA PLATFORM | 


        </span>


        <span>


            Version 1.0


        </span>


    </div>





</footer>