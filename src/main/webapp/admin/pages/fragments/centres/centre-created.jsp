<%@page import="com.centria.language.LanguageManager"%>
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



// ==============================
// READ GENERATED DATA
// ==============================

String centreCode =
        (String) session.getAttribute("centreCode");


String username =
        (String) session.getAttribute("username");


String password =
        (String) session.getAttribute("password");




// ==============================
// PROTECT AGAINST REFRESH
// ==============================

if(centreCode == null ||
   username == null ||
   password == null){


    response.sendRedirect(
        request.getContextPath()
        + "/admin/dashboard.jsp?section=centres"
    );


    return;

}




// ==============================
// CLEAR AFTER FIRST DISPLAY
// ==============================

session.removeAttribute("centreCode");
session.removeAttribute("username");
session.removeAttribute("password");



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



<div class="success-icon">

✓

</div>






<h1>

<%=LanguageManager.get(
        "centers.created.success",
        session
)%>

</h1>








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









<!-- CENTRE CODE -->

<div class="info-item">


<span class="label">


🏢


<%=LanguageManager.get(
        "centers.code.label",
        session
)%>


:


</span>




<span class="value">


<%=centreCode%>


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


<%=username%>


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


<%=password%>


</span>



</div>





</div>









<button class="copy-btn"
        id="copyCredentialsBtn"
        type="button">


📋


<%=LanguageManager.get(
        "centers.copy.credentials",
        session
)%>


</button>









<div class="warning-box">


⚠️


<%=LanguageManager.get(
        "centers.send.credentials",
        session
)%>


</div>









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

