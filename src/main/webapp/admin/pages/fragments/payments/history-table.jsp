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



<div class="payments-table-wrapper history-table-wrapper">


<table class="payments-table history-table">


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
        "payments.date.payment",
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



<!-- =========================
     CENTRE CODE
========================= -->

<td>

<%=payment.getCentreCode()%>

</td>





<!-- =========================
     CENTRE NAME
========================= -->

<td>

<%=payment.getCentreName()%>

</td>





<!-- =========================
     INVOICE
========================= -->

<td>

<%=payment.getCodeFacture()%>

</td>





<!-- =========================
     PAYMENT DATE
========================= -->

<td>


<%

if(payment.getDatePaiement()!=null){

%>


<%=sdf.format(
        payment.getDatePaiement()
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
     OPERATION
========================= -->

<td>


<%

if(payment.getOperationType()!=null
        && !payment.getOperationType().isEmpty()){

%>


<%=payment.getOperationType()%>


<%

}
else{

%>


INITIAL


<%

}

%>


</td>





<!-- =========================
     ACTION
========================= -->


<td>


<button

type="button"

class="btn-view"

onclick="openPaymentView(
        '<%=payment.getCodeFacture()%>'
)"


title="<%=LanguageManager.get(
        "payments.view",
        session
)%>"

>

👁

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