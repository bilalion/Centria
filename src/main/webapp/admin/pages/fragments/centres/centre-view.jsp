<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.models.Centre"%>
<%@page import="com.centria.language.LanguageManager"%>
<%@page import="java.text.SimpleDateFormat"%>


<%

Centre centre =
(Centre) request.getAttribute("centre");


if(centre == null){

%>

<div class="empty-state">

<p>

<%=LanguageManager.get(
"centers.notfound",
session
)%>

</p>

</div>


<%

return;

}


SimpleDateFormat sdf =
new SimpleDateFormat("dd/MM/yyyy");

SimpleDateFormat sdfDateTime =
new SimpleDateFormat("dd/MM/yyyy HH:mm");

String status = centre.getStatus();

if(status == null){

    status = "PENDING";

}


String statusClass =
"status-" + status.toLowerCase();

String statusLabel =
LanguageManager.get(
        "centers." + status.toLowerCase(),
        session
);
%>





<div class="centre-view-container">

<!-- =================================================
     DIALOG STRIPE
================================================= -->

<div class="dialog-stripe dialog-view">

    <i class="fa-solid fa-building"></i>

    <span class="dialog-divider">|</span>

    <span class="dialog-title">

        <%= LanguageManager.get(
            "centers.details.title",
            session
        ) %>

    </span>

</div>











<!-- =================================================
     INFORMATION GRID
================================================= -->


<div class="centre-card">


<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.code",
session
)%>

</span>


<strong>

<%=centre.getCentreCode()!=null
?
centre.getCentreCode()
:
"-"
%>

</strong>


</div>



<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.name",
session
)%>

</span>


<strong>

<%=centre.getName()!=null
?
centre.getName()
:
"-"
%>

</strong>


</div>




<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.owner",
session
)%>

</span>


<strong>

<%=centre.getOwnerName()!=null
?
centre.getOwnerName()
:
"-"
%>

</strong>


</div>





<!-- USERNAME -->

<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.username",
session
)%>

</span>


<strong>

<%=centre.getUsername()!=null
?
centre.getUsername()
:
"-"
%>

</strong>


</div>








<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.phone",
session
)%>

</span>


<strong>

<%=centre.getPhone()!=null
?
centre.getPhone()
:
"-"
%>

</strong>


</div>


<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.status",
session
)%>

</span>


<strong class="status-badge <%=statusClass%>">

<%=statusLabel%>

</strong>


</div>








<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.subscription.start",
session
)%>

</span>


<strong>

<%=centre.getSubscriptionStart()!=null
?
sdf.format(
centre.getSubscriptionStart()
)
:
"-"
%>

</strong>


</div>








<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.subscription.end",
session
)%>

</span>


<strong>

<%=centre.getSubscriptionEnd()!=null
?
sdf.format(
centre.getSubscriptionEnd()
)
:
"-"
%>

</strong>


</div>




<!-- CREATED AT -->

<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.created",
session
)%>

</span>

<strong>

<%=centre.getCreatedAt()!=null
?
sdf.format(
centre.getCreatedAt()
)
:
"-"
%>

</strong>

</div>

<!-- PASSWORD CHANGE -->

<div class="info-row">

<span>

<%=LanguageManager.get(
"centers.details.change.password",
session
)%>

</span>


<strong>

<%=centre.isMustChangePassword()
?
LanguageManager.get(
"centers.details.change.password.no",
session
)
:
LanguageManager.get(
"centers.details.change.password.yes",
session
)
%>
</strong>


</div>

</div> 




</div>