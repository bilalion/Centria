<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="payment-fragment">

    <jsp:include page="${fragment}" />

</div>


<%

int totalPages =
        request.getAttribute("totalPages") != null
        ? (Integer)request.getAttribute("totalPages")
        : 0;


int currentPage =
        request.getAttribute("currentPage") != null
        ? (Integer)request.getAttribute("currentPage")
        : 1;


%>



<!-- ===============================
     PAGINATION
================================ -->

<div class="payments-pagination">


<%

if(totalPages > 1){


int startPage =
        Math.max(
            1,
            currentPage - 1
        );


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

onclick="changePaymentPage(1)"

<%=currentPage <= 1 ? "disabled" : ""%>

>

&lt;&lt;

</button>





<!-- PREVIOUS -->

<button

class="page-btn"

onclick="changePaymentPage(<%=currentPage - 1%>)"

<%=currentPage <= 1 ? "disabled" : ""%>

>

&lt;

</button>





<!-- THREE NUMBERS -->

<%

for(int i = startPage; i <= endPage; i++){

%>


<button

class="page-btn <%=i == currentPage ? "active" : ""%>"

onclick="changePaymentPage(<%=i%>)"

>

<%=i%>

</button>


<%

}

%>





<!-- NEXT -->

<button

class="page-btn"

onclick="changePaymentPage(<%=currentPage + 1%>)"

<%=currentPage >= totalPages ? "disabled" : ""%>

>

&gt;

</button>





<!-- LAST PAGE -->

<button

class="page-btn"

onclick="changePaymentPage(<%=totalPages%>)"

<%=currentPage >= totalPages ? "disabled" : ""%>

>

&gt;&gt;

</button>





<%

}

%>


</div>





<script>

window.paymentCounters = {

UNPAID:
<%=request.getAttribute("unpaidCount")%>,


PAID:
<%=request.getAttribute("paidCount")%>,


HISTORY:
<%=request.getAttribute("historyCount")%>

};


</script>