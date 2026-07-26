<%@page import="com.centria.utils.LanguageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- =================================================
     CENTRE CREATED SUCCESS CARD
     Fragment loaded inside dashboard
     ================================================= -->


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





    <!-- CREDENTIALS -->

    <div class="credentials-box">



        <div class="credential-item">


            <span class="label">

                <%=LanguageManager.get(
                        "centers.generated.username",
                        session
                )%>

            </span>


            <span class="value">

                <%=request.getAttribute("username")%>

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

                <%=request.getAttribute("password")%>

            </span>


        </div>



    </div>







    <!-- WARNING -->


    <div class="warning-box">


        <%=LanguageManager.get(
                "centers.send.credentials",
                session
        )%>


    </div>





    <!-- ACTION -->


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