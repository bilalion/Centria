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
        "centers.add.title",
        session
)%>
</title>


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/add-centre.css">


<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">


</head>



<body>


<div class="container">



<h1>

<i class="fa-solid fa-building-circle-plus"></i>

<%=LanguageManager.get(
        "centers.add.title",
        session
)%>


</h1>





<div class="info-box">


<i class="fa-solid fa-shield-halved"></i>


<span>

<%=LanguageManager.get(
        "centers.connection.generated",
        session
)%>


</span>


</div>




<form method="post"
      action="<%=request.getContextPath()%>/CentreServlet">


<input type="hidden"
       name="action"
       value="add">






<div class="form-grid">





<!-- ==========================
     CENTRE NAME
     ========================== -->


<div class="form-group full">


<label>

<i class="fa-solid fa-school"></i>

<%=LanguageManager.get(
        "centers.name",
        session
)%>

</label>


<input type="text"
       name="name"
       required>


</div>






<!-- ==========================
     OWNER NAME
     ========================== -->


<div class="form-group">


<label>

<i class="fa-solid fa-user-tie"></i>

<%=LanguageManager.get(
        "centers.owner.name",
        session
)%>

</label>


<input type="text"
       name="owner_name"
       required>


</div>






<!-- ==========================
     PHONE
     ========================== -->


<div class="form-group">


<label>

<i class="fa-solid fa-phone"></i>

<%=LanguageManager.get(
        "centers.phone",
        session
)%>

</label>


<input type="text"
       name="phone">


</div>






<!-- ==========================
     START DATE
     ========================== -->


<div class="form-group">


<label>

<i class="fa-solid fa-calendar-days"></i>


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
     DURATION
     ========================== -->


<div class="form-group full">


<label>

<i class="fa-solid fa-clock"></i>


<%=LanguageManager.get(
        "centers.subscription.duration",
        session
)%>

</label>



<select name="subscription_duration"
        required>



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




</div>







<div class="buttons">





<a class="btn-back"
href="<%=request.getContextPath()%>/admin/dashboard.jsp?section=centres">


<i class="fa-solid fa-arrow-left"></i>


<%=LanguageManager.get(
        "centers.back",
        session
)%>


</a>







<button type="submit">


<i class="fa-solid fa-circle-check"></i>


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