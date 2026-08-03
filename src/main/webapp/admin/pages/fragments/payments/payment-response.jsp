<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div id="payment-fragment">

    <jsp:include page="${fragment}" />

</div>



<script>

window.paymentCounters = {

    UNPAID:
    <%=request.getAttribute("unpaidCount") != null
        ? request.getAttribute("unpaidCount")
        : 0 %>,


    PAID:
    <%=request.getAttribute("paidCount") != null
        ? request.getAttribute("paidCount")
        : 0 %>,


    HISTORY:
    <%=request.getAttribute("historyCount") != null
        ? request.getAttribute("historyCount")
        : 0 %>

};

</script>