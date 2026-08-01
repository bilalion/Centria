<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<!-- =================================================
     HEADER
================================================= -->

<div class="dashboard-header">

    <h1>
        <%= LanguageManager.get("payments.title", session) %>
    </h1>

    <p>
        <%= LanguageManager.get("payments.description", session) %>
    </p>

</div>





<!-- =================================================
     TOOLBAR
================================================= -->

<div class="card payments-toolbar">


<form id="paymentsFilterForm"
      class="payments-filter-form">


<!-- SEARCH -->

<div class="search-box">

<input
        type="text"
        id="paymentSearch"
        placeholder="<%=LanguageManager.get(
                "payments.search.placeholder",
                session
        )%>">

</div>





<!-- STATUS -->

<select id="paymentStatus"
        class="centre-select">

<option value="ALL">

<%=LanguageManager.get(
        "payments.all",
        session
)%>

</option>

<option value="UNPAID">

<%=LanguageManager.get(
        "payments.unpaid",
        session
)%>

</option>

<option value="PAID">

<%=LanguageManager.get(
        "payments.paid",
        session
)%>

</option>

</select>





<!-- ORDER -->

<select id="paymentOrder"
        class="centre-select">

<option value="NEW">

<%=LanguageManager.get(
        "payments.newest",
        session
)%>

</option>

<option value="OLD">

<%=LanguageManager.get(
        "payments.oldest",
        session
)%>

</option>

</select>





<button
        type="button"
        class="btn-primary"
        onclick="loadPayments()">

🔎

<%=LanguageManager.get(
        "payments.search",
        session
)%>

</button>

</form>

<input
        type="hidden"
        id="currentPaymentTab"
        value="UNPAID">
</div>






<!-- =================================================
     PAYMENT TABS
================================================= -->

<div class="payment-tabs">


<button
        id="tab-unpaid"
        class="payment-tab active"
        onclick="changePaymentTab('UNPAID')">

<%=LanguageManager.get(
        "payments.unpaid",
        session
)%>

</button>





<button
        id="tab-paid"
        class="payment-tab"
        onclick="changePaymentTab('PAID')">

<%=LanguageManager.get(
        "payments.paid",
        session
)%>

</button>





<button
        id="tab-history"
        class="payment-tab"
        onclick="changePaymentTab('HISTORY')">

<%=LanguageManager.get(
        "payments.history",
        session
)%>

</button>

</div>




<!-- ==============================================
     AJAX CONTENT

     يتم تحميل أحد الملفات التالية:

     fragments/payments/unpaid-table.jsp
     fragments/payments/paid-table.jsp
     fragments/payments/history-table.jsp

============================================== -->

<div id="payments-table-container">

</div>
<!-- =================================================
     CONFIRM PAYMENT MODAL
     ================================================= -->

<div id="payment-confirm-modal"
     class="centre-modal">

    <div class="centre-modal-content reset-confirm-box">

        <button type="button"
                class="modal-close"
                onclick="closePaymentConfirm()">

            ✖

        </button>

        <div class="reset-confirm-content">

            <div class="confirm-header">

                <div class="confirm-icon">

                    💳

                </div>

                <h4 class="confirm-title">

                    <%=LanguageManager.get(
                        "payments.confirm",
                        session
                    )%>

                </h4>

            </div>

          <p>

<%=LanguageManager.get(
        "payments.confirm.message",
        session
)%>

</p>

            <div class="reset-confirm-actions">

                <button
                        type="button"
                        class="btn-secondary"
                        onclick="closePaymentConfirm()">

                    <%=LanguageManager.get(
                        "centers.cancel",
                        session
                    )%>

                </button>

                <button
                        type="button"
                        class="btn-primary"
                        onclick="confirmPayment()">

                    <%=LanguageManager.get(
                        "centers.confirm",
                        session
                    )%>

                </button>

            </div>

        </div>

    </div>

</div>




<!-- =================================================
     SUBSCRIPTION UPDATE MODAL (TAB2)
     UPGRADE / EXTENSION
================================================= -->

<div id="subscription-confirm-modal"
     class="centre-modal">


    <div class="centre-modal-content reset-confirm-box">


        <button type="button"
                class="modal-close"
                onclick="closeSubscriptionConfirm()">

            ✖

        </button>



        <div class="reset-confirm-content">



            <div class="confirm-header">


                <div class="confirm-icon">

                    🔄

                </div>



                <h4 class="confirm-title">

                    <%=LanguageManager.get(
                        "payments.subscription.update",
                        session
                    )%>

                </h4>


            </div>




            <p>

            <%=LanguageManager.get(
                    "payments.subscription.update.message",
                    session
            )%>


            </p>





            <div class="reset-confirm-actions">



                <button
                        type="button"
                        class="btn-secondary"
                        onclick="closeSubscriptionConfirm()">


                    <%=LanguageManager.get(
                            "centers.cancel",
                            session
                    )%>


                </button>





                <button
                        type="button"
                        class="btn-primary"
                        onclick="updateSubscription()">


                    <%=LanguageManager.get(
                            "centers.confirm",
                            session
                    )%>


                </button>



            </div>



        </div>



    </div>


</div>
<!-- =================================================
     PAYMENT DETAILS MODAL
     ================================================= -->

<div id="payment-modal"
     class="centre-modal">

    <div class="centre-modal-content">

        <button
                type="button"
                class="modal-close"
                onclick="closePaymentModal()">

            ✖

        </button>

        <div id="payment-modal-body">

        </div>

    </div>

</div>





<!-- =================================================
     CONTEXT PATH
     ================================================= -->
<!-- =================================================
     JAVASCRIPT
================================================= -->

<script>

window.contextPath =
"<%=request.getContextPath()%>";

</script>


