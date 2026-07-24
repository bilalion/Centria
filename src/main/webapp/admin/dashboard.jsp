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



<!-- =================================================
     GLOBAL STYLE
     ================================================= -->

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/global.css?v=1">



<!-- =================================================
     DASHBOARD LAYOUT
     ================================================= -->

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/dashboard.css?v=1">



<!-- =================================================
     COMPONENTS
     ================================================= -->

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/sidebar.css?v=1">


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/header.css?v=1">



<!-- =================================================
     MODULES
     ================================================= -->

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/centres.css?v=1">





<script>

window.contextPath =
"<%=request.getContextPath()%>";

</script>




<!-- =================================================
     JAVASCRIPT
     ================================================= -->

<script defer
src="<%=request.getContextPath()%>/assets/js/dashboard.js">
</script>


<script defer
src="<%=request.getContextPath()%>/assets/js/centres.js?v=2">
</script>



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