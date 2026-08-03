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





<!-- =================================================
     PAGINATION TOP
     3 NUMBERS CENTER
================================================= -->


<div class="pagination-container">


<%

if(totalPages > 1){



int startPage =
        Math.max(1, currentPage - 1);



int endPage =
        Math.min(
            totalPages,
            startPage + 2
        );



if(endPage - startPage < 2){

    startPage =
        Math.max(
            1,
            endPage - 2
        );

}

%>

<!-- FIRST PAGE -->

<button

class="page-btn"

onclick="changeCentrePage(1)"

<%=currentPage <= 1 ? "disabled" : ""%>

>

&lt;&lt;

</button>





<!-- PREVIOUS -->

<button

class="page-btn"

onclick="changeCentrePage(<%=currentPage - 1%>)"

<%=currentPage <= 1 ? "disabled" : ""%>

>

&lt;

</button>





<%

for(int i = startPage; i <= endPage; i++){

%>


<button

class="page-btn <%=i == currentPage ? "active" : ""%>"

onclick="changeCentrePage(<%=i%>)"

>

<%=i%>

</button>



<%

}

%>





<!-- NEXT -->


<button

class="page-btn"

onclick="changeCentrePage(<%=currentPage + 1%>)"

<%=currentPage >= totalPages ? "disabled" : ""%>

>

&gt;

</button>





<!-- LAST PAGE -->

<button

class="page-btn"

onclick="changeCentrePage(<%=totalPages%>)"

<%=currentPage >= totalPages ? "disabled" : ""%>

>

&gt;&gt;

</button>





<%

}

%>


</div>









<!-- =================================================
     TABLE CONTAINER
================================================= -->


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






<td>


<%=centre.getOwnerName()!=null
?
centre.getOwnerName()
:
"-"
%>


</td>






<td>


<%=centre.getPhone()!=null
?
centre.getPhone()
:
"-"
%>


</td>






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

onclick="viewCentre(<%=centre.getId()%>)"

>

👁

</button>






<button

type="button"

class="action-btn action-edit"

title="<%=LanguageManager.get(
"centers.edit",
session
)%>"

onclick="editCentre(<%=centre.getId()%>)"

>

✏️

</button>






<button

type="button"

class="action-btn action-reset"

title="<%=LanguageManager.get(
"centers.reset.password",
session
)%>"

onclick="resetCentrePassword(<%=centre.getId()%>)"

>

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





<%

}

%>