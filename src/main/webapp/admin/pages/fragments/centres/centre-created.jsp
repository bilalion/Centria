<%@page import="com.centria.utils.LanguageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
String lang =
        session.getAttribute("lang") != null
        ? session.getAttribute("lang").toString()
        : "ar";

String direction =
        lang.equals("ar")
        ? "rtl"
        : "ltr";
%>


<!DOCTYPE html>

<html lang="<%=lang%>" dir="<%=direction%>">


<head>

<meta charset="UTF-8">


<title>

<%=LanguageManager.get(
        "centers.created.success",
        session
)%>

</title>



<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/centre-created.css">



</head>



<body>



<div class="created-container">



    <!-- SUCCESS ICON -->

    <div class="success-icon">

        ✓

    </div>





    <!-- TITLE -->

    <h1>

        <%=LanguageManager.get(
                "centers.created.success",
                session
        )%>


    </h1>







    <!-- CREDENTIALS CARD -->


    <div class="credentials-card">





        <div class="credentials-title">


            🔐


            <span>


                <%=LanguageManager.get(
                        "centers.credentials.title",
                        session
                )%>


            </span>


        </div>








        <!-- USERNAME -->


        <div class="info-item">



            <span class="label">


                👤


                <%=LanguageManager.get(
                        "centers.username.label",
                        session
                )%>


                :


            </span>





            <span class="value"
                  id="generatedUsername">


                <%=request.getAttribute("username")%>


            </span>



        </div>










        <!-- PASSWORD -->


        <div class="info-item">



            <span class="label">


                🔑


                <%=LanguageManager.get(
                        "centers.password.label",
                        session
                )%>


                :


            </span>





            <span class="value password"
                  id="generatedPassword">


                <%=request.getAttribute("password")%>


            </span>



        </div>





    </div>









    <!-- COPY BUTTON -->


    <button class="copy-btn"
            id="copyCredentialsBtn"
            type="button">


        📋


        <%=LanguageManager.get(
                "centers.copy.credentials",
                session
        )%>


    </button>









    <!-- WARNING -->


    <div class="warning-box">


        ⚠️


        <%=LanguageManager.get(
                "centers.send.credentials",
                session
        )%>


    </div>









    <!-- BACK BUTTON -->


    <a class="btn-back"
       href="<%=request.getContextPath()%>/admin/dashboard.jsp?section=centres">



        ←


        <%=LanguageManager.get(
                "centers.back",
                session
        )%>



    </a>





</div>








<script>


document
.getElementById("copyCredentialsBtn")
.addEventListener("click",function(){



    let username =

        document
        .getElementById("generatedUsername")
        .innerText;




    let password =

        document
        .getElementById("generatedPassword")
        .innerText;





    let usernameLabel =

        "<%=LanguageManager.get(
            "centers.username.label",
            session
        )%>";




    let passwordLabel =

        "<%=LanguageManager.get(
            "centers.password.label",
            session
        )%>";





    let successMessage =

        "<%=LanguageManager.get(
            "centers.copy.success",
            session
        )%>";






    let text =

        usernameLabel
        + " : "
        + username

        + "\n\n"

        + passwordLabel
        + " : "
        + password;







    navigator.clipboard.writeText(text)

    .then(()=>{


        this.innerHTML =
        "✅ "
        + successMessage;




        setTimeout(()=>{


            this.innerHTML =
            "📋 "
            +
            "<%=LanguageManager.get(
                "centers.copy.credentials",
                session
            )%>";



        },2000);



    })

    .catch(()=>{


        alert("Copy failed");


    });



});



</script>







</body>


</html>