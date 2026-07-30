<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.utils.LanguageManager"%>
<%@page import="com.centria.models.Centre"%>


<%
Centre centre =
(Centre) request.getAttribute("centre");
%>




<div class="centre-view-container">



<!-- =================================================
     HEADER
     ================================================= -->


<div class="confirm-header">


    <div class="confirm-icon">

        ✏️

    </div>



    <h4 class="confirm-title">

        <%=LanguageManager.get(
            "centers.edit.title",
            session
        )%>

    </h4>


</div>









<!-- =================================================
     FORM
     ================================================= -->


<form id="editCentreForm">






<input type="hidden"
       name="id"
       value="<%=centre.getId()%>">







<!-- =================================================
     CENTRE INFORMATION GRID
     ================================================= -->


<div class="edit-grid">





<!-- CODE CENTRE -->

<div class="form-group">


<label>

🔑

<%=LanguageManager.get(
        "centers.edit.code",
        session
)%>

</label>



<input type="text"
       value="<%=centre.getCentreCode()%>"
       readonly
       class="readonly-field">


</div>








<!-- CENTRE NAME -->

<div class="form-group">


<label>

🏢

<%=LanguageManager.get(
        "centers.name",
        session
)%>

</label>



<input type="text"
       name="name"
       value="<%=centre.getName()%>"
       required>


</div>









<!-- OWNER -->

<div class="form-group">


<label>

👤

<%=LanguageManager.get(
        "centers.owner.name",
        session
)%>

</label>



<input type="text"
       name="owner_name"
       value="<%=centre.getOwnerName()%>"
       required>


</div>









<!-- PHONE -->

<div class="form-group">


<label>

📞

<%=LanguageManager.get(
        "centers.phone",
        session
)%>

</label>



<input type="text"
       name="phone"
       value="<%=centre.getPhone()%>">


</div>





</div>









<!-- =================================================
     SUBSCRIPTION INFORMATION
     READ ONLY
     ================================================= -->


<div class="subscription-info">



<h5>


🔒


<%=LanguageManager.get(
        "centers.edit.subscription.title",
        session
)%>


</h5>





<div class="subscription-grid">



<p>

<span>

📅

<%=LanguageManager.get(
        "centers.subscription.start",
        session
)%>

</span>


<strong>

<%=centre.getSubscriptionStart()%>

</strong>


</p>







<p>

<span>

📅

<%=LanguageManager.get(
        "centers.subscription.end",
        session
)%>

</span>


<strong>

<%=centre.getSubscriptionEnd()%>

</strong>


</p>








<p>

<span>

⚙️

<%=LanguageManager.get(
        "centers.status",
        session
)%>

</span>


<strong>

<%=centre.getStatus()%>

</strong>


</p>



</div>



</div>









<!-- =================================================
     ACTIONS
     ================================================= -->


<div class="reset-confirm-actions">





<button type="button"
        class="btn-secondary"
        onclick="closeCentreModal()">



<%=LanguageManager.get(
        "centers.cancel",
        session
)%>



</button>









<button type="button"
        class="btn-primary"
        onclick="saveEditCentre()">



💾


<%=LanguageManager.get(
        "centers.save",
        session
)%>



</button>





</div>






</form>





</div>