<%@page import="com.centria.utils.LanguageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- =================================================
     CENTRE CREATED SUCCESS CARD
     AJAX FRAGMENT
     ================================================= -->


<%

String username =
        request.getAttribute("username") != null
        ? request.getAttribute("username").toString()
        : "-";


String password =
        request.getAttribute("password") != null
        ? request.getAttribute("password").toString()
        : "-";

%>





<div class="centre-success-card">



    <!-- SUCCESS ICON -->

    <div class="success-icon">

        ✅

    </div>





    <!-- TITLE -->

    <h2>

        <%=LanguageManager.get(
                "centers.created.success",
                session
        )%>

    </h2>







    <!-- GENERATED CREDENTIALS -->

    <div class="credentials-box">





        <div class="credential-item">


            <span class="label">

                <%=LanguageManager.get(
                        "centers.generated.username",
                        session
                )%>

            </span>



            <span class="value">

                <%=username%>

            </span>


        </div>








        <div class="credential-item">


            <span class="label">

                <%=LanguageManager.get(
                        "centers.generated.password",
                        session
                )%>

            </span>



            <span class="value">

                <%=password%>

            </span>


        </div>






    </div>








    <!-- WARNING MESSAGE -->


    <div class="warning-box">


        <%=LanguageManager.get(
                "centers.send.credentials",
                session
        )%>


    </div>









    <!-- RETURN BUTTON -->


    <button type="button"

            class="btn-primary"

            onclick="hideCreatedCentre()">



        ⬅

        <%=LanguageManager.get(
                "centers.back",
                session
        )%>


    </button>





</div>