<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.centria.models.Payment"%>
<%@page import="com.centria.language.LanguageManager"%>


<%

List<Payment> payments =

        (List<Payment>) request.getAttribute(
                "payments"
        );

%>





<div class="payments-table-wrapper">



<table class="payments-table">



<thead>


<tr>


<th>
<%=LanguageManager.get(
        "payments.centre.code",
        session
)%>
</th>



<th>
<%=LanguageManager.get(
        "payments.centre.name",
        session
)%>
</th>

<th>
Invoice
</th>

<th>
<%=LanguageManager.get(
        "payments.start.date",
        session
)%>
</th>


<th>
<%=LanguageManager.get(
        "payments.end.date",
        session
)%>
</th>


<th>
<%=LanguageManager.get(
        "payments.operation",
        session
)%>
</th>



<th>
<%=LanguageManager.get(
        "payments.plan",
        session
)%>
</th>



<th>
<%=LanguageManager.get(
        "payments.action",
        session
)%>
</th>


</tr>


</thead>






<tbody>



<%

if(payments != null && !payments.isEmpty()){


    for(Payment payment : payments){


%>



<tr>




<td>

<%=payment.getCentreCode()%>

</td>





<td>

<%=payment.getCentreName()%>

</td>


<td>

<%=payment.getCodeFacture()%>

</td>


<td>

<%=payment.getSubscriptionStart()%>

</td>



<td>

<%=payment.getSubscriptionEnd()%>

</td>







<!-- =========================
     OPERATION
========================= -->


<td>


<select

class="subscription-operation"

data-centre="<%=payment.getCentreCode()%>"

>


<option value="UPGRADE">


<%=LanguageManager.get(
        "payments.upgrade",
        session
)%>


</option>



<option value="EXTENSION">


<%=LanguageManager.get(
        "payments.extension",
        session
)%>


</option>


</select>


</td>








<!-- =========================
     PLAN
========================= -->


<td>


<select

class="subscription-plan"

data-centre="<%=payment.getCentreCode()%>"

>


<option value="1">


<%=LanguageManager.get(
        "payments.plan.1",
        session
)%>


</option>



<option value="3">


<%=LanguageManager.get(
        "payments.plan.3",
        session
)%>


</option>



<option value="6">


<%=LanguageManager.get(
        "payments.plan.6",
        session
)%>


</option>



<option value="12">


<%=LanguageManager.get(
        "payments.plan.12",
        session
)%>


</option>


</select>


</td>








<!-- =========================
     ACTION
========================= -->


<td>


<button

type="button"

class="btn-primary"

title="<%=LanguageManager.get(
        "payments.save",
        session
)%>"

onclick="openSubscriptionConfirm('<%=payment.getCentreCode()%>')"

>


💾


</button>


</td>




</tr>




<%

    }

}
else{


%>



<tr>


<td colspan="6">


<%=LanguageManager.get(
        "payments.empty",
        session
)%>


</td>


</tr>



<%

}

%>




</tbody>


</table>


</div>