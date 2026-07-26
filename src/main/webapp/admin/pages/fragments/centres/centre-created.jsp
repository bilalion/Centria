<%@page import="com.centria.utils.LanguageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="ar" dir="rtl">

<head>

<meta charset="UTF-8">

<title>
    <%=LanguageManager.get("centers.created.success",session)%>
</title>


<style>

body{

    font-family:'Segoe UI',Tahoma,sans-serif;

    background:#f1f5f9;

    margin:0;

    padding:40px;

}



.container{

    max-width:600px;

    margin:auto;

    background:white;

    padding:35px;

    border-radius:18px;

    box-shadow:0 8px 25px rgba(0,0,0,.08);

    text-align:center;

}



.success-icon{

    font-size:60px;

    margin-bottom:20px;

}



h1{

    color:#16a34a;

    margin-bottom:25px;

}



.info-box{

    background:#f8fafc;

    border-radius:15px;

    padding:20px;

    margin-top:20px;

    text-align:right;

}



.info-item{

    margin:15px 0;

    font-size:17px;

}



.label{

    font-weight:bold;

    color:#334155;

}



.value{

    color:#2563eb;

    font-weight:bold;

}



.warning{

    margin-top:25px;

    background:#fff7ed;

    color:#c2410c;

    padding:15px;

    border-radius:12px;

}



.btn{

    display:inline-block;

    margin-top:30px;

    padding:12px 30px;

    background:#2563eb;

    color:white;

    text-decoration:none;

    border-radius:12px;

    font-weight:bold;

}



.btn:hover{

    background:#1d4ed8;

}


</style>


</head>



<body>



<div class="container">



<div class="success-icon">

✅

</div>



<h1>

<%=LanguageManager.get("centers.created.success",session)%>

</h1>




<div class="info-box">



<div class="info-item">

<span class="label">

<%=LanguageManager.get("centers.generated.username",session)%> :

</span>


<span class="value">

<%=request.getAttribute("username")%>

</span>


</div>





<div class="info-item">


<span class="label">

<%=LanguageManager.get("centers.generated.password",session)%> :

</span>



<span class="value">

<%=request.getAttribute("password")%>

</span>


</div>



</div>






<div class="warning">

<%=LanguageManager.get("centers.send.credentials",session)%>

</div>






<a class="btn"
   href="<%=request.getContextPath()%>/admin/dashboard.jsp?section=centres">

⬅

<%=LanguageManager.get("centers.back",session)%>

</a>




</div>



</body>


</html>