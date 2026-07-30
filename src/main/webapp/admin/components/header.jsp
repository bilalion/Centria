<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<div class="header">



    <!-- ==========================
         عنوان لوحة التحكم
         ========================== -->


    <div class="header-title">


        <h2>

            <%= LanguageManager.get("dashboard.title", session) %>

        </h2>


    </div>







    <!-- ==========================
         معلومات المستخدم
         ========================== -->


    <div class="header-actions">



        <div class="header-user">


            <span class="user-icon">

                👨‍💻

            </span>




            <span class="user-name">

                CHENTOUF BILAL

            </span>



        </div>





    </div>






</div>