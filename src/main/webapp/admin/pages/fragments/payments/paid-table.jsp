<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.centria.models.Payment"%>
<%@page import="com.centria.language.LanguageManager"%>


<%

List<Payment> payments =

        (List<Payment>) request.getAttribute(
                "payments"
        );


java.text.SimpleDateFormat sdf =

        new java.text.SimpleDateFormat(
                "dd/MM/yyyy"
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
        "payments.end.date",
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
        "payments.operation",
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


<span class="status paid">


🟢

<%=LanguageManager.get(
        "payments.status.paid",
        session
)%>


</span>


</td>
<!-- =========================
     START DATE
========================= -->

<td>


<%

if(payment.getSubscriptionStart()!=null){

%>

<%=sdf.format(
        payment.getSubscriptionStart()
)%>


<%

}
else{

%>

-

<%

}

%>


</td>





<!-- =========================
     END DATE
========================= -->

<td>


<%

if(payment.getSubscriptionEnd()!=null){

%>

<%=sdf.format(
        payment.getSubscriptionEnd()
)%>


<%

}
else{

%>

-

<%

}

%>


</td>







<!-- =========================
     SUBSCRIPTION PLAN
========================= -->

<td>


<select

class="subscription-plan"

data-centre="<%=payment.getCentreCode()%>"

>


<option value="1">

1 Month

</option>



<option value="3">

3 Months

</option>



<option value="6">

6 Months

</option>



<option value="12">

12 Months

</option>


</select>


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



<option value="EXTENDED">

<%=LanguageManager.get(
        "payments.extended",
        session
)%>

</option>



</select>


</td>







<!-- =========================
     SAVE BUTTON
========================= -->


<td>


<button

type="button"

class="btn-primary"

onclick="openSubscriptionConfirm(
        '<%=payment.getCentreCode()%>',
        '<%=payment.getCodeFacture()%>'
)"

title="<%=LanguageManager.get(
        "payments.save",
        session
)%>"

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


<td colspan="9">


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