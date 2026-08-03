<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<%

String lang =
        session.getAttribute("lang") != null
        ? session.getAttribute("lang").toString()
        : "ar";



if(!lang.equals("ar")
        && !lang.equals("fr")
        && !lang.equals("en")){

    lang="ar";

}



String direction =
        lang.equals("ar")
        ? "rtl"
        : "ltr";

%>





<!DOCTYPE html>

<html lang="<%=lang%>" dir="<%=direction%>">



<head>


<meta charset="UTF-8">



<meta http-equiv="Cache-Control"
      content="no-cache, no-store, must-revalidate">


<meta http-equiv="Pragma"
      content="no-cache">


<meta http-equiv="Expires"
      content="0">



<title>

<%= LanguageManager.get(
        "login.title",
        session
) %>

</title>





<link rel="stylesheet"

href="<%=request.getContextPath()%>/assets/css/standalone/login.css?v=5">


</head>







<body>



<!-- ==========================
     LANGUAGE SELECTOR
     ========================== -->


<div class="language-selector">



<form action="<%=request.getContextPath()%>/LanguageServlet"
      method="get">



<select name="lang"
        onchange="this.form.submit()">



<option value="ar"
<%= lang.equals("ar") ? "selected" : "" %>>

🇲🇦 العربية

</option>





<option value="fr"
<%= lang.equals("fr") ? "selected" : "" %>>

🇫🇷 Français

</option>





<option value="en"
<%= lang.equals("en") ? "selected" : "" %>>

🇬🇧 English

</option>



</select>



</form>



</div>









<!-- ==========================
     LOGIN CARD
     ========================== -->


<div class="login-card">






<div class="login-brand">



<img

src="<%=request.getContextPath()%>/assets/images/centria-logo.png"

alt="Centria Logo"

class="login-logo"



>



<h1>

Centria

</h1>




<span>

Super Admin

</span>



</div>









<h2>

<%= LanguageManager.get(
        "login.title",
        session
) %>

</h2>









<%

String error =
        request.getParameter("error");


boolean loginError =
        "invalid".equals(error)
        ||
        "db_error".equals(error);



%>









<%

if(loginError){

%>


<div class="error-message">


<%

if("invalid".equals(error)){


%>


<%= LanguageManager.get(
        "error.login.invalid",
        session
) %>


<%


}else{


%>


<%= LanguageManager.get(
        "error.database",
        session
) %>


<%

}


%>



</div>



<%

}

%>









<!-- DATABASE STATUS -->


<div id="dbStatus"

     class="db-status checking">


<%= LanguageManager.get(
        "database.checking",
        session
) %>


</div>









<form action="<%=request.getContextPath()%>/SuperLoginServlet"

      method="POST">







<div class="input-group">



<span class="input-icon">

👤

</span>





<input type="text"

       name="username"

       placeholder="<%= LanguageManager.get(
            "login.username",
            session
       ) %>"

       required>



</div>









<div class="password-wrapper">



<span class="input-icon">

🔒

</span>





<input type="password"

       id="password"

       name="password"

       placeholder="<%= LanguageManager.get(
            "login.password",
            session
       ) %>"

       required>





<div class="toggle-btn"

     onclick="togglePassword()">

👁

</div>



</div>









<div class="remember-me">



<input type="checkbox"

       name="remember">





<label>


<%= LanguageManager.get(
        "login.remember",
        session
) %>


</label>



</div>









<button id="loginBtn"

        type="submit"

        disabled>


🚀


<%= LanguageManager.get(
        "login.button",
        session
) %>


</button>






</form>






</div>









<script>


const contextPath =

"<%=request.getContextPath()%>";



</script>








<script src="<%=request.getContextPath()%>/assets/js/superlogin.js?v=5">

</script>





</body>


</html>
