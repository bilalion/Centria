<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.language.LanguageManager"%>
<%@page import="com.centria.models.Centre"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>


<%

List<Centre> centres =
(List<Centre>) request.getAttribute("centres");


SimpleDateFormat sdf =
new SimpleDateFormat("dd/MM/yyyy");


int currentPage =
request.getAttribute("currentPage") != null
?
(Integer) request.getAttribute("currentPage")
:
1;


int totalPages =
request.getAttribute("totalPages") != null
?
(Integer) request.getAttribute("totalPages")
:
1;

%>




<%

if(centres == null || centres.isEmpty()){

%>


<div class="empty-state">

<p>

<%=LanguageManager.get(
        "centers.empty",
        session
)%>

</p>

</div>



<%

}else{

%>





<div class="table-container">


<table class="centers-table">


<thead>


<tr>


<th>

<%=LanguageManager.get(
        "centers.code",
        session
)%>

</th>




<th>

<%=LanguageManager.get(
        "centers.name",
        session
)%>

</th>




<th>

<%=LanguageManager.get(
        "centers.owner",
        session
)%>

</th>




<th>

<%=LanguageManager.get(
        "centers.phone",
        session
)%>

</th>




<th>

<%=LanguageManager.get(
        "centers.subscription.start",
        session
)%>

</th>




<th>

<%=LanguageManager.get(
        "centers.subscription.end",
        session
)%>

</th>




<th>

<%=LanguageManager.get(
        "centers.status",
        session
)%>

</th>




<th>

<%=LanguageManager.get(
        "centers.actions",
        session
)%>

</th>


</tr>


</thead>





<tbody>



<%

for(Centre centre : centres){


String status =
centre.getStatus();


if(status == null){

    status="PENDING";

}


String statusClass =
"status-" + status.toLowerCase();


%>





<tr>





<!-- CENTRE CODE -->

<td>

<strong>

<%=centre.getCentreCode()!=null
?
centre.getCentreCode()
:
"-"
%>

</strong>

</td>






<!-- NAME -->

<td>

<strong>

<%=centre.getName()!=null
?
centre.getName()
:
"-"
%>

</strong>

</td>







<!-- OWNER -->

<td>

<%=centre.getOwnerName()!=null
?
centre.getOwnerName()
:
"-"
%>

</td>







<!-- PHONE -->

<td>

<%=centre.getPhone()!=null
?
centre.getPhone()
:
"-"
%>

</td>







<!-- START -->

<td>

<%

if(centre.getSubscriptionStart()!=null){

%>

<%=sdf.format(
        centre.getSubscriptionStart()
)%>


<%

}else{

%>

-

<%

}

%>

</td>







<!-- END -->

<td>

<%

if(centre.getSubscriptionEnd()!=null){

%>

<%=sdf.format(
        centre.getSubscriptionEnd()
)%>


<%

}else{

%>

-

<%

}

%>

</td>







<!-- STATUS -->

<td>


<select

class="status-select <%=statusClass%>"

data-id="<%=centre.getId()%>"

onchange="updateCentreStatus(this)"


>



<option value="PENDING"

<%=
"PENDING".equals(status)
?
"selected"
:
""
%>

>

<%=LanguageManager.get(
        "centers.pending",
        session
)%>


</option>





<option value="ACTIVE"

<%=
"ACTIVE".equals(status)
?
"selected"
:
""
%>

>

<%=LanguageManager.get(
        "centers.active",
        session
)%>


</option>





<option value="SUSPENDED"

<%=
"SUSPENDED".equals(status)
?
"selected"
:
""
%>

>

<%=LanguageManager.get(
        "centers.suspended",
        session
)%>


</option>





<option value="ARCHIVED"

<%=
"ARCHIVED".equals(status)
?
"selected"
:
""
%>

>

<%=LanguageManager.get(
        "centers.archived",
        session
)%>


</option>


</select>


</td>







<!-- ACTIONS -->

<td>


<div class="actions">



<button
type="button"
class="action-btn action-view"
onclick="viewCentre(<%=centre.getId()%>)">
👁
</button>



<button
type="button"
class="action-btn action-edit"
title="<%=LanguageManager.get("centers.edit",session)%>"
onclick="editCentre(<%=centre.getId()%>)">

✏️

</button>




<button
type="button"
class="action-btn action-reset"
title="<%=LanguageManager.get("centers.reset.password",session)%>"
onclick="resetCentrePassword(<%=centre.getId()%>)">

🔑

</button>

</div>


</td>







</tr>



<%

}

%>



</tbody>


</table>


</div>








<div class="pagination-container">



<%

if(totalPages > 1){

%>




<button

class="page-btn"

onclick="changeCentrePage(<%=currentPage - 1%>)"

<%=currentPage <= 1 ? "disabled" : ""%>

>

◀

</button>







<%

for(int i=1;i<=totalPages;i++){

%>




<button

class="page-btn <%=i==currentPage ? "active" : ""%>"

onclick="changeCentrePage(<%=i%>)"

>

<%=i%>

</button>





<%

}

%>






<button

class="page-btn"

onclick="changeCentrePage(<%=currentPage + 1%>)"

<%=currentPage >= totalPages ? "disabled" : ""%>

>

▶

</button>





<%

}

%>



</div>





<%

}

%>