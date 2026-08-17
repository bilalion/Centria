<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<%

String lang =
        session.getAttribute("lang") != null
        ? session.getAttribute("lang").toString()
        : "ar";


if(!lang.equals("ar")
        &&
        !lang.equals("fr")
        &&
        !lang.equals("en")){

    lang="ar";

}


String direction =
        lang.equals("ar")
        ? "rtl"
        : "ltr";





String error =
        request.getParameter("error");



boolean loginError =
        "invalid".equals(error);

%>


<!DOCTYPE html>

<html lang="<%=lang%>"
      dir="<%=direction%>">

<head>

<meta charset="UTF-8">

<meta name="viewport"
      content="width=device-width,initial-scale=1.0">

<title>CENTRIA</title>

<link rel="stylesheet"

href="<%=request.getContextPath()%>/assets/css/standalone/login.css?v=20">

</head>

<body>

<div class="login-layout">

<!-- ==================================================
     LEFT BRANDING PANEL
================================================== -->


<section class="branding-panel">

<div class="branding-top">

<img

src="<%=request.getContextPath()%>/assets/images/centria-logo.png"

class="platform-logo"

alt="CENTRIA">

<span class="platform-tag">

<%=LanguageManager.get(
        "login.platform",
        session
)%>

</span>

</div>

<div class="branding-main">

<div class="branding-content">

<h1>


<%=LanguageManager.get(
        "login.brand.title",
        session
)%>


</h1>

<p>


<%=LanguageManager.get(
        "login.brand.description",
        session
)%>


</p>

</div>

<div class="branding-features">

<!-- CENTRES -->


<div class="feature-card centres">

<div class="feature-icon">

🏫

</div>

<div class="feature-body">


<h3>


<%=LanguageManager.get(
        "login.feature.centres",
        session
)%>


</h3>

<p>


<%=LanguageManager.get(
        "login.feature.centres.description",
        session
)%>


</p>

</div>

</div>


<!-- PAYMENTS -->


<div class="feature-card payments">

<div class="feature-icon">

💳

</div>

<div class="feature-body">


<h3>


<%=LanguageManager.get(
        "login.feature.payments",
        session
)%>


</h3>

<p>


<%=LanguageManager.get(
        "login.feature.payments.description",
        session
)%>


</p>

</div>

</div>


<!-- ANALYTICS -->


<div class="feature-card analytics">

<div class="feature-icon">

📊

</div>

<div class="feature-body">

<h3>

<%=LanguageManager.get(
        "login.feature.analytics",
        session
)%>


</h3>

<p>


<%=LanguageManager.get(
        "login.feature.analytics.description",
        session
)%>


</p>

</div>

</div>

</div>

</div>

<div class="branding-footer">


<%=LanguageManager.get(
        "login.version",
        session
)%>


</div>

</section>

<!-- ==================================================
     RIGHT AUTH PANEL
================================================== -->


<section class="auth-panel">

<div class="language-selector">

<form action="<%=request.getContextPath()%>/LanguageServlet"

      method="get">



<select name="lang"

        onchange="this.form.submit()">



<option value="en"

<%=lang.equals("en") ? "selected" : ""%>>

🇬🇧 English

</option>

<option value="fr"

<%=lang.equals("fr") ? "selected" : ""%>>

🇫🇷 Français

</option>

<option value="ar"

<%=lang.equals("ar") ? "selected" : ""%>>

🇲🇦 العربية

</option>



</select>
</form>
</div>

<!-- ==========================================
     LOGIN HEADER
=========================================== -->


<div class="login-header">
<h2>

<%=LanguageManager.get(
        "login.welcome",
        session
)%>


</h2>


<p>


<%=LanguageManager.get(
        "login.subtitle",
        session
)%>


</p>



</div>

<!-- ==========================================
     LOGIN FORM
=========================================== -->


<form

action="<%=request.getContextPath()%>/SuperLoginServlet"

method="POST">


<div class="input-group">

<span class="input-icon">

👤

</span>

    
<input


type="text"


name="username"


placeholder="<%=LanguageManager.get(
        "login.username",
        session
)%>"


required>




</div>









<div class="password-wrapper">



<span class="input-icon">

🔒

</span>





<input


id="password"


type="password"


name="password"


placeholder="<%=LanguageManager.get(
        "login.password",
        session
)%>"


required>







<div class="toggle-btn"


id="togglePassword"


onclick="togglePassword()">



👁



</div>


</div>



<div class="remember-me">



<input


type="checkbox"


name="remember">





<label>


<%=LanguageManager.get(
        "login.remember",
        session
)%>


</label>



</div>


<button


id="loginBtn"


type="submit"


class="<%= loginError ? "login-error-state" : "" %>"


<%= loginError ? "" : "disabled" %>>



<%=LanguageManager.get(
        "login.button",
        session
)%>


</button>



<%

if(error != null){

%>



<div class="error-message">



<%

if("invalid".equals(error)){


%>



<%=LanguageManager.get(
        "error.login.invalid",
        session
)%>



<%

}


else if("db_error".equals(error)){


%>



<%=LanguageManager.get(
        "error.database",
        session
)%>



<%

}


else if("system_error".equals(error)){


%>



<%=LanguageManager.get(
        "error.system",
        session
)%>



<%

}


%>



</div>


<%

}

%>


</form>

<!-- ==========================================
     AUTH FOOTER
=========================================== -->


<div class="login-footer">



<span>


<%=LanguageManager.get(
        "login.footer.secure",
        session
)%>


</span>

<small>


<%=LanguageManager.get(
        "login.footer.copyright",
        session
)%>


</small>


<div id="dbStatus"

     class="db-status checking">

</div>
</div>



<!-- ==========================================
     DATABASE STATUS
=========================================== -->

</section>


</div>


<script>


const contextPath =

"<%=request.getContextPath()%>";





const loginLanguage = {


    databaseChecking:

    "<%=LanguageManager.get(
            "database.checking",
            session
    )%>",




    databaseConnected:

    "<%=LanguageManager.get(
            "database.connected",
            session
    )%>",




    databaseUnavailable:

    "<%=LanguageManager.get(
            "database.unavailable",
            session
    )%>",




    databaseConnectionError:

    "<%=LanguageManager.get(
            "database.connection.error",
            session
    )%>",




    signingIn:

    "<%=LanguageManager.get(
            "login.signing",
            session
    )%>"



};


</script>


<script

src="<%=request.getContextPath()%>/assets/js/superlogin.js?v=20">

</script>



</body>


</html>

