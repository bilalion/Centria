<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.utils.LanguageManager"%>


<%

String lang =
        session.getAttribute("lang") != null
        ? session.getAttribute("lang").toString()
        : "ar";


String direction =
        lang.equals("fr") || lang.equals("en")
        ? "ltr"
        : "rtl";


%>




<!DOCTYPE html>

<html lang="<%=lang%>" dir="<%=direction%>">


<head>


<meta charset="UTF-8">


<title>

<%= LanguageManager.get("dashboard.title", session) %>

</title>




<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/dashboard.css?v=1008">

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/centres.css?v=1009">

<script>

window.contextPath =
"<%=request.getContextPath()%>";

</script>



<script src="<%=request.getContextPath()%>/assets/js/centres.js?v=2"></script>
<script src="<%=request.getContextPath()%>/assets/js/dashboard.js"></script>




</head>






<body>




<!-- =================================================
     SIDEBAR COMPONENT
     ================================================= -->


<jsp:include page="components/sidebar.jsp"/>







<!-- =================================================
     MAIN CONTENT
     ================================================= -->


<div class="main-content">







<!-- =================================================
     HEADER COMPONENT
     يحتوي:
     - عنوان النظام
     - اللغة
     - المستخدم
     ================================================= -->


<jsp:include page="components/header.jsp"/>








<!-- =================================================
     DYNAMIC CONTENT AREA
     ================================================= -->

<div id="content-area">


<%

String section =
        request.getParameter("section");


if(section == null 
   || section.isEmpty()
  || "home".equals(section)){


%>


    <jsp:include page="pages/accueil.jsp"/>


<%


}else if("centres".equals(section)){


%>


    <jsp:include page="pages/centres.jsp"/>


<%


}else if("payments".equals(section)){


%>


    <jsp:include page="pages/payments.jsp"/>


<%


}else if("settings".equals(section)){


%>


    <jsp:include page="pages/settings.jsp"/>


<%


}else{


%>


    <jsp:include page="pages/accueil.jsp"/>


<%


}


%>


</div>




</div>







</body>



</html>