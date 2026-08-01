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
        "payments.subscription.end",
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
        "payments.account.status",
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

<span class="cell-text"
title="<%=payment.getCentreCode()%>">

<%=payment.getCentreCode()%>

</span>

</td>





<td>

<span class="cell-text"
title="<%=payment.getCentreName()%>">

<%=payment.getCentreName()%>

</span>

</td>





<td>

<%

if(payment.getSubscriptionEnd() != null){

    java.text.SimpleDateFormat sdf =
            new java.text.SimpleDateFormat("dd/MM/yyyy");

%>

<%=sdf.format(payment.getSubscriptionEnd())%>

<%

}
else{

%>

-

<%

}

%>

</td>





<td>

<span class="cell-text"
title="<%=payment.getCodeFacture()%>">

<%=payment.getCodeFacture()%>

</span>

</td>






<!-- =========================
     PAYMENT STATUS
========================= -->

<td>

<span class="status unpaid">

🔴

<%=LanguageManager.get(
        "payments.status.unpaid",
        session
)%>

</span>

</td>






<!-- =========================
     ACCOUNT STATUS
========================= -->


<td>

<%

String accountStatus =
        payment.getAccountStatus();



if("ACTIVE".equals(accountStatus)){

%>


<span class="account-status account-active">

🟢

<%=LanguageManager.get(
        "payments.account.active",
        session
)%>

</span>


<%

}
else if("PENDING".equals(accountStatus)){

%>


<span class="account-status account-pending">

🟡

<%=LanguageManager.get(
        "payments.account.pending",
        session
)%>

</span>


<%

}
else if("SUSPENDED".equals(accountStatus)){

%>


<span class="account-status account-suspended">

🔴

<%=LanguageManager.get(
        "payments.account.suspended",
        session
)%>

</span>


<%

}
else{

%>


<span class="account-status">

-

</span>


<%

}

%>


</td>






<!-- =========================
     NEW START DATE
========================= -->


<td>

<input

type="date"

class="payment-start-date"

data-centre="<%=payment.getCentreCode()%>"

>

</td>






<!-- =========================
     DURATION
========================= -->


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






<!-- =========================
     ACTION
========================= -->


<td>


<button

type="button"

class="btn-primary payment-action-btn"

title="<%=LanguageManager.get(
        "payments.register",
        session
)%>"

onclick="openPaymentConfirm('<%=payment.getCentreCode()%>')"

>

💳

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