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
<%=LanguageManager.get(
        "payments.phone",
        session
)%>
</th>


<th>
<%=LanguageManager.get(
        "payments.invoice",
        session
)%>
</th>


<th>
<%=LanguageManager.get(
        "payments.status",
        session
)%>
</th>


<th>
<%=LanguageManager.get(
        "payments.start.date",
        session
)%>
</th>


<th>
<%=LanguageManager.get(
        "payments.duration",
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
<%=payment.getPhone()%>
</td>


<td>
<%=payment.getCodeFacture()%>
</td>


<td>

<span class="status unpaid">

🔴

<%=LanguageManager.get(
        "payments.status.unpaid",
        session
)%>

</span>

</td>


<td>

<input
type="date"
class="payment-start-date"
data-centre="<%=payment.getCentreCode()%>"
>

</td>


<td>

<select
class="payment-duration"
data-centre="<%=payment.getCentreCode()%>"
>

<option value="1">
1
</option>

<option value="3">
3
</option>

<option value="6">
6
</option>

<option value="12">
12
</option>

</select>

</td>


<td>

<button
type="button"
class="btn-primary"
onclick="openPaymentConfirm('<%=payment.getCentreCode()%>')"
>

✅

<%=LanguageManager.get(
        "payments.confirm",
        session
)%>

</button>


</td>


</tr>


<%

    }

}
else{

%>


<tr>

<td colspan="8">

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