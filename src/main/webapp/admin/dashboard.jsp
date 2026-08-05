<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<%
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





<!-- ==============================
     CORE CSS
================================ -->


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/core/core.css?v=1">




<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">


<!-- ==============================
     LAYOUT CSS
================================ -->


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/app-layout.css?v=1">


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/platform-banner.css?v=1">


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/header.css?v=1">


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/sidebar.css?v=1">


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/layout/footer.css?v=1">

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/pages/accueil.css?v=1">








<!-- ==============================
     PAGE CSS
================================ -->


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/pages/accueil.css?v=1">


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/pages/centres.css?v=1">


<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/pages/payments.css?v=1">







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







    <!-- =============================================
         SIDEBAR
    ============================================== -->


    <jsp:include page="components/sidebar.jsp"/>









    <!-- =============================================
         CONTENT AREA
    ============================================== -->


    <main id="content-area"
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

        }

        else if("centres".equals(section)){


        %>



            <jsp:include page="pages/centres.jsp"/>






        <%

        }

        else if("centre-view".equals(section)){


        %>



            <jsp:include
            page="pages/fragments/centres/centre-view.jsp"/>






        <%

        }

        else if("payments".equals(section)){


        %>



            <jsp:include page="pages/payments.jsp"/>






        <%

        }

        else if("settings".equals(section)){


        %>



            <jsp:include page="pages/settings.jsp"/>






        <%

        }

        else{


        %>



            <jsp:include page="pages/accueil.jsp"/>






        <%

        }

        %>






    </main>







</div>
<!-- END APP BODY -->









<!-- =================================================
     FOOTER
================================================= -->


<jsp:include page="components/footer.jsp"/>








</div>
<!-- END APP SHELL -->









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







</body>


</html>