<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<!-- ======================================================
     CENTRIA — PAYMENTS
====================================================== -->


<section class="payments-page">


    <!-- ==================================================
         01. PAGE HEADER
    ================================================== -->

    <section class="payments-header">


        <div class="payments-header-content">


            <div class="payments-header-icon">

                💳

            </div>


            <div class="payments-header-text">


                <h1>

                    <%=LanguageManager.get(
                        "payments.title",
                        session
                    )%>

                </h1>


                <span class="payments-header-separator"></span>


                <p>

                    <%=LanguageManager.get(
                        "payments.description",
                        session
                    )%>

                </p>


            </div>


        </div>


    </section>


    <!-- ==================================================
         02. PAYMENT STATISTICS CARDS
    ================================================== -->

    <section class="stats-grid payments-stats-grid">

        <!-- PAID -->

        <div class="stat-card stat-green">

            <div class="stat-icon">

                <i class="fa-solid fa-circle-check"></i>

            </div>

            <div class="stat-content">

                <span class="stat-title">

                    <%=LanguageManager.get(
                        "payments.paid",
                        session
                    )%>

                </span>

   <strong class="stat-value">0</strong>

                <span class="stat-description">

                    <%=LanguageManager.get(
                        "payments.paid.description",
                        session
                    )%>

                </span>

            </div>

        </div>


        <!-- UNPAID -->

        <div class="stat-card stat-red">

            <div class="stat-icon">

                <i class="fa-solid fa-money-bill-wave"></i>

            </div>

            <div class="stat-content">

                <span class="stat-title">

                    <%=LanguageManager.get(
                        "payments.unpaid",
                        session
                    )%>

                </span>

       <strong class="stat-value">0</strong>

                <span class="stat-description">

                    <%=LanguageManager.get(
                        "payments.unpaid.description",
                        session
                    )%>

                </span>

            </div>

        </div>

    </section>


    <!-- ==================================================
         03. FILTER / CONTROL AREA
    ================================================== -->

    <section class="payments-controls">


        <!-- SEARCH -->

        <div class="payments-search">


            <input
                type="text"
                id="paymentSearch"
                placeholder="<%=LanguageManager.get(
                    "payments.search.placeholder",
                    session
                )%>"
            >


        </div>



        <!-- DATE FROM -->

        <div class="payments-date">


            <input
                type="date"
                id="paymentDateFrom"
                aria-label="Date from"
            >


        </div>



        <!-- DATE TO -->

        <div class="payments-date">


            <input
                type="date"
                id="paymentDateTo"
                aria-label="Date to"
            >


        </div>



        <!-- ORDER -->

        <div class="payments-order">


            <select id="paymentOrder">


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


        </div>



        <!-- SEARCH BUTTON -->

        <button
            type="button"
            onclick="loadPayments(1)"
        >


            🔎


            <%=LanguageManager.get(
                "payments.search",
                session
            )%>


        </button>


    </section>



    <!-- ==================================================
         04. PAYMENTS WORKSPACE
    ================================================== -->

    <section class="payments-workspace">


        <!-- ==================================================
             04.1 TABS
        ================================================== -->

        <div class="payments-tabs">


            <!-- ==============================================
                 UNPAID
            =============================================== -->

            <button
                type="button"
                id="tab-unpaid"
                class="payment-tab active"
                onclick="changePaymentTab('UNPAID')"
            >


                <span class="payment-tab-label">

                    <%=LanguageManager.get(
                        "payments.unpaid",
                        session
                    )%>

                </span>


                <span
                    class="tab-notification unpaid-count"
                    id="unpaidCount"
                >

                    <%=request.getAttribute(
                        "unpaidCount"
                    ) != null
                        ? request.getAttribute("unpaidCount")
                        : 0
                    %>

                </span>


            </button>



            <!-- ==============================================
                 PAID
            =============================================== -->

            <button
                type="button"
                id="tab-paid"
                class="payment-tab"
                onclick="changePaymentTab('PAID')"
            >


                <span class="payment-tab-label">

                    <%=LanguageManager.get(
                        "payments.paid",
                        session
                    )%>

                </span>


                <span
                    class="tab-notification paid-count"
                    id="paidCount"
                >

                    <%=request.getAttribute(
                        "paidCount"
                    ) != null
                        ? request.getAttribute("paidCount")
                        : 0
                    %>

                </span>


            </button>



            <!-- ==============================================
                 HISTORY
            =============================================== -->

            <button
                type="button"
                id="tab-history"
                class="payment-tab"
                onclick="changePaymentTab('HISTORY')"
            >


                <span class="payment-tab-label">

                    <%=LanguageManager.get(
                        "payments.history",
                        session
                    )%>

                </span>


                <span
                    class="tab-notification history-count"
                    id="historyCount"
                >

                    <%=request.getAttribute(
                        "historyCount"
                    ) != null
                        ? request.getAttribute("historyCount")
                        : 0
                    %>

                </span>


            </button>


        </div>



        <!-- ==================================================
             04.2 CURRENT TAB
        ================================================== -->

        <input
            type="hidden"
            id="currentPaymentTab"
            value="UNPAID"
        >



        <!-- ==================================================
             04.3 TABLE
        ================================================== -->

        <div
            id="payments-table-container"
            class="payments-table-container"
        >

        </div>



        <!-- ==================================================
             04.4 PAGINATION
        ================================================== -->

        <div
            id="payments-pagination-container"
            class="payments-pagination-container"
        >

        </div>


    </section>


</section>



<!-- ======================================================
     05. PAYMENT CONFIRM MODAL
====================================================== -->

<div
    id="payment-confirm-modal"
    class="centre-modal"
>


    <div class="centre-modal-content reset-confirm-box">


        <!-- CLOSE -->

        <button
            type="button"
            class="modal-close"
            onclick="closePaymentConfirm()"
        >

            ✖

        </button>



        <div class="reset-confirm-content">


            <!-- HEADER -->

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



            <!-- MESSAGE -->

            <p>

                <%=LanguageManager.get(
                    "payments.confirm.message",
                    session
                )%>

            </p>



            <!-- ACTIONS -->

            <div class="reset-confirm-actions">


                <button
                    type="button"
                    class="btn-secondary"
                    onclick="closePaymentConfirm()"
                >

                    <%=LanguageManager.get(
                        "centers.cancel",
                        session
                    )%>

                </button>


                <button
                    type="button"
                    class="btn-primary"
                    onclick="confirmPayment()"
                >

                    <%=LanguageManager.get(
                        "centers.confirm",
                        session
                    )%>

                </button>


            </div>


        </div>


    </div>


</div>



<!-- ======================================================
     06. SUBSCRIPTION CONFIRM MODAL
====================================================== -->

<div
    id="subscription-confirm-modal"
    class="centre-modal"
>


    <div class="centre-modal-content reset-confirm-box">


        <!-- CLOSE -->

        <button
            type="button"
            class="modal-close"
            onclick="closeSubscriptionConfirm()"
        >

            ✖

        </button>



        <div class="reset-confirm-content">


            <!-- HEADER -->

            <div class="confirm-header">


                <div class="confirm-icon">

                    🔄

                </div>


                <h4 class="confirm-title">

                    <%=LanguageManager.get(
                        "payments.save.confirm.title",
                        session
                    )%>

                </h4>


            </div>



            <!-- MESSAGE -->

            <p id="subscription-confirm-message">

                <%=LanguageManager.get(
                    "payments.save.confirm.message",
                    session
                )%>

            </p>



            <!-- ACTIONS -->

            <div class="reset-confirm-actions">


                <button
                    type="button"
                    class="btn-secondary"
                    onclick="closeSubscriptionConfirm()"
                >

                    <%=LanguageManager.get(
                        "centers.cancel",
                        session
                    )%>

                </button>


                <button
                    type="button"
                    class="btn-primary"
                    onclick="updateSubscription()"
                >

                    <%=LanguageManager.get(
                        "centers.confirm",
                        session
                    )%>

                </button>


            </div>


        </div>


    </div>


</div>



<!-- ======================================================
     07. PAYMENT DETAILS MODAL
====================================================== -->

<div
    id="payment-modal"
    class="centre-modal"
>


    <div class="centre-modal-content">


        <button
            type="button"
            class="modal-close"
            onclick="closePaymentModal()"
        >

            ✖

        </button>


        <div id="payment-modal-body">

        </div>


    </div>


</div>