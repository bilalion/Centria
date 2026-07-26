<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.utils.LanguageManager"%>


<!-- =================================================
     ADD CENTRE FORM FRAGMENT
     Loaded by AJAX inside centres.jsp
     ================================================= -->


<div class="container add-centre-container">



<h1>

➕ 

<%=LanguageManager.get(
        "centers.add.title",
        session
)%>

</h1>





<div class="info-box">

<%=LanguageManager.get(
        "centers.password.generated.info",
        session
)%>

</div>







<form id="addCentreForm"

      method="post"

      action="<%=request.getContextPath()%>/CentreServlet"

      class="centre-form">





<input type="hidden"

       name="action"

       value="add">







<!-- CENTRE NAME -->

<div class="form-group">


<label>

<%=LanguageManager.get(
        "centers.name",
        session
)%>

</label>



<input type="text"

       name="name"

       required>


</div>








<!-- OWNER -->

<div class="form-group">


<label>

<%=LanguageManager.get(
        "centers.owner.name",
        session
)%>

</label>



<input type="text"

       name="owner_name"

       required>


</div>








<!-- USERNAME -->

<div class="form-group">


<label>

<%=LanguageManager.get(
        "centers.username",
        session
)%>

</label>



<input type="text"

       name="username"

       required>


</div>








<!-- PHONE -->

<div class="form-group">


<label>

<%=LanguageManager.get(
        "centers.phone",
        session
)%>

</label>



<input type="text"

       name="phone">


</div>








<!-- SUBSCRIPTION START -->

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








<!-- SUBSCRIPTION DURATION -->

<div class="form-group">


<label>

<%=LanguageManager.get(
        "centers.subscription.duration",
        session
)%>

</label>




<select name="duration"

        id="subscriptionDuration">



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








<!-- BUTTONS -->

<div class="buttons">





<button type="button"

        class="btn-back"

        onclick="hideAddCentre()">



<%=LanguageManager.get(
        "centers.back",
        session
)%>


</button>







<button type="submit"

        class="btn-primary">



<%=LanguageManager.get(
        "centers.create",
        session
)%>


</button>






</div>







</form>





</div>