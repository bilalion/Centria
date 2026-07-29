<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.utils.LanguageManager"%>


<!-- =================================================
     HEADER
     ================================================= -->

<div class="dashboard-header">


    <h1>
        <%= LanguageManager.get("centers.title", session) %>
    </h1>


    <p>
        <%= LanguageManager.get("centers.description", session) %>
    </p>


</div>







<!-- =================================================
     TOOLBAR
     ================================================= -->

<div class="card centres-toolbar">


<form id="centresFilterForm"
      method="get"
      action="<%=request.getContextPath()%>/CentreServlet"
      class="centres-filter-form">


<input type="hidden"
       name="action"
       value="list">







<!-- SEARCH -->

<div class="search-box">

<input type="text"
       id="centreSearch"
       name="search"
       value="<%= request.getAttribute("search") != null 
                ? request.getAttribute("search") 
                : "" %>"
       placeholder="<%=LanguageManager.get(
                    "centers.search.placeholder",
                    session
       )%>">

</div>







<!-- STATUS -->

<select id="centreStatus"
        name="status"
        class="centre-select">


<option value="ALL">

<%=LanguageManager.get(
        "centers.all",
        session
)%>

</option>



<option value="PENDING">

<%=LanguageManager.get(
        "centers.pending",
        session
)%>

</option>



<option value="ACTIVE">

<%=LanguageManager.get(
        "centers.active",
        session
)%>

</option>



<option value="SUSPENDED">

<%=LanguageManager.get(
        "centers.suspended",
        session
)%>

</option>



<option value="ARCHIVED">

<%=LanguageManager.get(
        "centers.archived",
        session
)%>

</option>


</select>







<!-- ORDER -->

<select id="centreOrder"
        name="order"
        class="centre-select">


<option value="NEW">

<%=LanguageManager.get(
        "centers.newest",
        session
)%>

</option>



<option value="OLD">

<%=LanguageManager.get(
        "centers.oldest",
        session
)%>

</option>



<option value="NAME">

<%=LanguageManager.get(
        "centers.name.asc",
        session
)%>

</option>


</select>








<button type="button"
        onclick="loadCentres(1)"
        class="btn-primary">

🔎

<%=LanguageManager.get(
        "centers.search",
        session
)%>

</button>



</form>







<!-- ADD CENTRE -->

<a href="<%=request.getContextPath()%>/admin/pages/fragments/centres/add-centre.jsp"
   class="btn-primary btn-no-underline">


➕

<%=LanguageManager.get(
        "centers.add",
        session
)%>


</a>



</div>









<!-- =================================================
     TABLE
     AJAX LOAD
     ================================================= -->


<div id="centres-table-container">


</div>









<!-- =================================================
     CENTRE VIEW MODAL
     JOptionPane STYLE
     ================================================= -->


<div id="centre-modal"
     class="centre-modal">


    <div class="centre-modal-content">


        <button type="button"
                class="modal-close"
                onclick="closeCentreModal()">

            ✖

        </button>



        <div id="centre-modal-body">


        </div>



    </div>


</div>









<!-- =================================================
     JAVASCRIPT
     ================================================= -->


<script>


window.contextPath =
"<%=request.getContextPath()%>";


</script>


<script src="<%=request.getContextPath()%>/assets/js/centres.js"></script>