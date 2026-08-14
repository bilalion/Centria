<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<%
/*
======================================================
LANGUAGE / DIRECTION
======================================================
*/

String lang =

        session.getAttribute("lang") != null

        ?

        session.getAttribute("lang").toString()

        :

        "ar";


String direction =

        lang.equals("fr")

        ||

        lang.equals("en")

        ?

        "ltr"

        :

        "rtl";


/*
======================================================
SECTION RESOLUTION
======================================================

Determine which dashboard section should be displayed.

Supported Home values:

home


Internally CENTRIA uses:

home
======================================================
*/

String section =

        request.getAttribute("section") != null

        ?

        request.getAttribute("section").toString()

        :

        request.getParameter("section");


/*
======================================================
NORMALIZE HOME SECTION
======================================================
*/



/*
======================================================
INITIAL HOME LOAD
======================================================

If Home is requested but HomeDAO data has not yet
been loaded, send the request through HomeServlet.

Flow:

dashboard.jsp
      ↓
HomeServlet
      ↓
HomeDAO
      ↓
request attributes
      ↓
dashboard.jsp
      ↓
home.jsp
======================================================
*/

if (
        section == null
        ||
        section.isEmpty()
        ||
        (
            "home".equals(section)
            &&
            request.getAttribute("totalCentres") == null
        )
) {


    request.getRequestDispatcher(
            "/admin/home"
    ).forward(
            request,
            response
    );


    return;

}


%>


<!DOCTYPE html>

<html lang="<%=lang%>"
      dir="<%=direction%>">


<head>


<meta charset="UTF-8">


<meta name="viewport"
      content="width=device-width, initial-scale=1.0">


<title>

<%= LanguageManager.get(
        "dashboard.title",
        session
) %>

</title>


<!-- =================================================
     CORE CSS
================================================= -->


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/core/core.css?v=1">


<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">


<!-- =================================================
     LAYOUT CSS
================================================= -->


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/layout/app-layout.css?v=1">


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/layout/platform-banner.css?v=1">


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/layout/header.css?v=1">


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/layout/sidebar.css?v=1">





<!-- =================================================
     PAGE CSS
================================================= -->


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/pages/home.css?v=1">


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/pages/centres.css?v=1">


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/pages/payments.css?v=1">


<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/pages/archive.css?v=1">


<script>

window.contextPath =

    "<%=request.getContextPath()%>";

</script>


</head>


<body>


<!-- =================================================
     APPLICATION SHELL
================================================= -->


<div class="app-shell">


<!-- =================================================
     PLATFORM BANNER
================================================= -->


<jsp:include page="components/platform-banner.jsp"/>


<!-- =================================================
     GLOBAL HEADER
================================================= -->


<jsp:include page="components/header.jsp"/>


<!-- =================================================
     APPLICATION BODY
================================================= -->


<div class="app-body">


<!-- =================================================
     SIDEBAR
================================================= -->


<jsp:include page="components/sidebar.jsp"/>


<!-- =================================================
     CONTENT AREA
================================================= -->


<main id="content-area"
      class="content-area">


<%
/*
======================================================
SECTION CONTENT
======================================================
*/


/*
======================================================
HOME
======================================================
*/

if ("home".equals(section)) {

%>


    <jsp:include page="pages/home.jsp"/>


<%

}


/*
======================================================
CENTRES
======================================================
*/

else if ("centres".equals(section)) {

%>


    <jsp:include page="pages/centres.jsp"/>


<%

}


/*
======================================================
CENTRE VIEW
======================================================
*/

else if ("centre-view".equals(section)) {

%>


    <jsp:include
        page="pages/fragments/centres/centre-view.jsp"/>


<%

}


/*
======================================================
PAYMENTS
======================================================
*/

else if ("payments".equals(section)) {

%>


    <jsp:include page="pages/payments.jsp"/>


<%

}


/*
======================================================
ARCHIVE
======================================================
*/

else if ("archive".equals(section)) {

%>


    <jsp:include page="pages/archive.jsp"/>


<%

}


/*
======================================================
SETTINGS
======================================================
*/

else if ("settings".equals(section)) {

%>


    <jsp:include page="pages/settings.jsp"/>


<%

}


/*
======================================================
FALLBACK
======================================================
*/

else {

%>


    <jsp:include page="pages/home.jsp"/>


<%

}

%>


</main>


</div>
<!-- END APP BODY -->



<!-- END APP SHELL -->


<!-- =================================================
     JAVASCRIPT
================================================= -->


<script defer
        src="<%=request.getContextPath()%>/assets/js/dashboard.js?v=3">
</script>


<script defer
        src="<%=request.getContextPath()%>/assets/js/home.js?v=4">
</script>


<script defer
        src="<%=request.getContextPath()%>/assets/js/centres.js?v=3">
</script>


<script defer
        src="<%=request.getContextPath()%>/assets/js/payments.js?v=6">
</script>


<script defer
        src="<%=request.getContextPath()%>/assets/js/archive.js?v=1">
</script>


</body>


</html>