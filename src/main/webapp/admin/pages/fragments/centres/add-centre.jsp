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
<%=LanguageManager.get("centers.add.title",session)%>
</title>

<style>

body{
    font-family:'Segoe UI',Tahoma,sans-serif;
    background:#f1f5f9;
    margin:0;
    padding:30px;
}

.container{
    max-width:700px;
    margin:auto;
    background:white;
    padding:30px;
    border-radius:18px;
    box-shadow:0 8px 20px rgba(0,0,0,.08);
}

h1{
    text-align:center;
    color:#1e293b;
    margin-bottom:30px;
}

.form-group{
    margin-bottom:20px;
}

label{
    display:block;
    margin-bottom:8px;
    font-weight:bold;
    color:#334155;
}

input,
select{
    width:100%;
    padding:12px;
    border-radius:10px;
    border:1px solid #cbd5e1;
    font-size:15px;
}

input:focus,
select:focus{
    outline:none;
    border-color:#2563eb;
}

.buttons{
    display:flex;
    justify-content:space-between;
    margin-top:30px;
}

button,
.btn-back{
    padding:12px 25px;
    border-radius:12px;
    border:none;
    cursor:pointer;
    font-weight:bold;
    text-decoration:none;
}

button{
    background:#2563eb;
    color:white;
}

.btn-back{
    background:#64748b;
    color:white;
}

.info-box{
    background:#eff6ff;
    padding:15px;
    border-radius:12px;
    margin-bottom:20px;
    color:#1e40af;
}

</style>

</head>

<body>

<div class="container">

<h1>
➕ <%=LanguageManager.get("centers.add.title",session)%>
</h1>

<div class="info-box">
<%=LanguageManager.get(
        "centers.password.generated.info",
        session
)%>
</div>

<form method="post"
      action="<%=request.getContextPath()%>/CentreServlet">

<input type="hidden"
       name="action"
       value="add">

<!-- ==========================
     CENTRE NAME
     ========================== -->

<div class="form-group">

<label>
<%=LanguageManager.get("centers.name",session)%>
</label>

<input type="text"
       name="name"
       required>

</div>

<!-- ==========================
     OWNER
     ========================== -->

<div class="form-group">

<label>
<%=LanguageManager.get("centers.owner.name",session)%>
</label>

<input type="text"
       name="owner_name"
       required>

</div>

<!-- ==========================
     USERNAME
     ========================== -->

<div class="form-group">

<label>
<%=LanguageManager.get("centers.username",session)%>
</label>

<input type="text"
       name="username"
       required>

</div>

<!-- ==========================
     PHONE
     ========================== -->

<div class="form-group">

<label>
<%=LanguageManager.get("centers.phone",session)%>
</label>

<input type="text"
       name="phone">

</div>

<!-- ==========================
     SUBSCRIPTION START
     ========================== -->

<div class="form-group">

<label>
<%=LanguageManager.get(
        "centers.subscription.start",
        session
)%>
</label>

<input type="date"
       name="subscription_start"
       required>

</div>

<!-- ==========================
     SUBSCRIPTION DURATION
     ========================== -->

<div class="form-group">

<label>
<%=LanguageManager.get(
        "centers.subscription.duration",
        session
)%>
</label>

<select name="duration">

<option value="1">
<%=LanguageManager.get(
        "centers.duration.1",
        session
)%>
</option>

<option value="3">
<%=LanguageManager.get(
        "centers.duration.3",
        session
)%>
</option>

<option value="6">
<%=LanguageManager.get(
        "centers.duration.6",
        session
)%>
</option>

<option value="12">
<%=LanguageManager.get(
        "centers.duration.12",
        session
)%>
</option>

</select>

</div>

<div class="buttons">

<a class="btn-back"
   href="<%=request.getContextPath()%>/admin/dashboard.jsp?section=centres">

<%=LanguageManager.get(
        "centers.back",
        session
)%>

</a>

<button type="submit">

<%=LanguageManager.get(
        "centers.create",
        session
)%>

</button>

</div>

</form>

</div>

</body>

</html>