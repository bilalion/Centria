<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<!-- ==================================================
     01. PAGE HEADER
================================================== -->

<section class="payments-header">

    <div class="payments-header-content">


        <!-- ==============================================
             01.1 HEADER ICON
        =============================================== -->

        <div class="payments-header-icon">

            <i class="fa-solid fa-credit-card"></i>

        </div>



        <!-- ==============================================
             01.2 HEADER TEXT
        =============================================== -->

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
         CONTROL AREA
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
            >

        </div>



        <!-- DATE TO -->

        <div class="payments-date">

            <input
                type="date"
                id="paymentDateTo"
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



        <!-- SEARCH ACTION -->

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
         PAYMENT WORKSPACE
    ================================================== -->

    <section class="payments-workspace">


        <!-- ==============================================
             TABS
             
             IMPORTANT:
             Functional classes / IDs preserved.
             No visual redesign here yet.
        =============================================== -->


        <div class="payments-tabs">


            <!-- UNPAID -->

            <button
                type="button"
                id="tab-unpaid"
                class="payment-tab active"
                onclick="changePaymentTab('UNPAID')"
            >

                <span>

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



            <!-- PAID -->

            <button
                type="button"
                id="tab-paid"
                class="payment-tab"
                onclick="changePaymentTab('PAID')"
            >

                <span>

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



            <!-- HISTORY -->

            <button
                type="button"
                id="tab-history"
                class="payment-tab"
                onclick="changePaymentTab('HISTORY')"
            >

                <span>

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



        <!-- ==============================================
             CURRENT TAB
        =============================================== -->

        <input
            type="hidden"
            id="currentPaymentTab"
            value="UNPAID"
        >



        <!-- ==============================================
             DYNAMIC TABLE
             
             DO NOT CHANGE ID
        =============================================== -->

        <div
            id="payments-table-container"
            class="payments-table-container"
        >
        </div>



        <!-- ==============================================
             PAGINATION
             
             DO NOT CHANGE ID
        =============================================== -->

        <div
            id="payments-pagination-container"
            class="payments-pagination-container"
        >
        </div>


    </section>



    <!-- ==================================================
         PAYMENT CONFIRM MODAL
         
         Functional ID preserved
    ================================================== -->

    <div
        id="payment-confirm-modal"
        class="centre-modal"
    >

        <div class="centre-modal-content reset-confirm-box">


            <button
                type="button"
                class="modal-close"
                onclick="closePaymentConfirm()"
            >

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



    <!-- ==================================================
         SUBSCRIPTION CONFIRM MODAL
    ================================================== -->

    <div
        id="subscription-confirm-modal"
        class="centre-modal"
    >

        <div class="centre-modal-content reset-confirm-box">


            <button
                type="button"
                class="modal-close"
                onclick="closeSubscriptionConfirm()"
            >

                ✖

            </button>


            <div class="reset-confirm-content">


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


                <p id="subscription-confirm-message">

                    <%=LanguageManager.get(
                        "payments.save.confirm.message",
                        session
                    )%>

                </p>


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



    <!-- ==================================================
         PAYMENT DETAILS MODAL
    ================================================== -->

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


</div>