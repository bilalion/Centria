<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


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

<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>
<%= LanguageManager.get(
        "dashboard.title",
        session
) %>
</title>





<


<!-- =================================================
     CORE
     Foundation (Variables, Reset, Typography...)
================================================= -->

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/core/core.css?v=1">



<!-- =================================================
     LAYOUT
     Application Layout Components
================================================= -->

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/app-layout.css?v=1">

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/sidebar.css?v=1">

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/header.css?v=1">



<!-- =================================================
     PAGE STYLES
================================================= -->

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/pages/accueil.css?v=1">

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/pages/centres.css?v=1">

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/pages/payments.css?v=1">





<!-- =================================================
     GLOBAL JAVASCRIPT CONFIG
     ================================================= -->


<script>

window.contextPath =
"<%=request.getContextPath()%>";

</script>







<!-- =================================================
     JAVASCRIPT
     ================================================= -->


<script defer
src="<%=request.getContextPath()%>/assets/js/dashboard.js?v=1">
</script>



<script defer
src="<%=request.getContextPath()%>/assets/js/centres.js?v=3">
</script>



<script defer
src="<%=request.getContextPath()%>/assets/js/payments.js?v=6">
</script>





</head>






<body>





<div class="app-layout">






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


<div id="content-area"
     class="content-area">





<%

String section =

        request.getAttribute("section") != null

        ?

        request.getAttribute("section").toString()

        :

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


}else if("centre-view".equals(section)){


%>


<jsp:include page="pages/fragments/centres/centre-view.jsp"/>


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





</div>






</body>


</html>