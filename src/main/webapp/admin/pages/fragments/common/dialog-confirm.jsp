<%--
    Document   : dialog-confirm
    Project    : CENTRIA
    Component  : Global Confirm Dialog
--%>

<%@ page import="com.centria.language.LanguageManager" %>


<div id="global-confirm-dialog"
     class="global-confirm-dialog"
     role="dialog"
     aria-modal="true"
     aria-hidden="true">


    <!-- ==================================================
         OVERLAY
    ================================================== -->

    <div class="global-confirm-dialog-overlay"
         data-confirm-dialog-close></div>


    <!-- ==================================================
         DIALOG BOX
    ================================================== -->

    <div class="global-confirm-dialog-box">


        <!-- ==================================================
             HEADER
        ================================================== -->

        <div class="global-confirm-dialog-header">

            <div id="global-confirm-dialog-icon"
                 class="global-confirm-dialog-icon">

                <i class="fas fa-circle-question"></i>

            </div>


            <h3 id="global-confirm-dialog-title">

                <%= LanguageManager.get(
                        "common.confirm.title",
                        session
                    ) %>

            </h3>


            <button type="button"
                    class="global-confirm-dialog-close"
                    data-confirm-dialog-close
                    aria-label="Close">

                <i class="fas fa-xmark"></i>

            </button>

        </div>


        <!-- ==================================================
             MESSAGE
        ================================================== -->

        <div class="global-confirm-dialog-body">

            <p id="global-confirm-dialog-message"></p>

        </div>


        <!-- ==================================================
             ACTIONS
        ================================================== -->

        <div class="global-confirm-dialog-actions">


            <!-- CANCEL -->

            <button type="button"
                    id="global-confirm-dialog-cancel"
                    class="global-confirm-dialog-button global-confirm-dialog-button-secondary"
                    data-confirm-dialog-close>

                <%= LanguageManager.get(
                        "common.cancel",
                        session
                    ) %>

            </button>


            <!-- CONFIRM -->

            <button type="button"
                    id="global-confirm-dialog-confirm"
                    class="global-confirm-dialog-button global-confirm-dialog-button-primary">

                <%= LanguageManager.get(
                        "common.confirm",
                        session
                    ) %>

            </button>


        </div>


    </div>

</div>


<!-- ======================================================
     GLOBAL CONFIRM DIALOG TRANSLATIONS
====================================================== -->

<script>

window.centriaConfirmDialogMessages = {


/* ==================================================
   COMMON
================================================== */

"common.confirm.title":
    "<%= LanguageManager.get(
            "common.confirm.title",
            session
        ) %>",


"common.confirm":
    "<%= LanguageManager.get(
            "common.confirm",
            session
        ) %>",


"common.cancel":
    "<%= LanguageManager.get(
            "common.cancel",
            session
        ) %>",


    /* ==================================================
       PROFILE
    ================================================== */

    "profile.confirm.password.change":
        "<%= LanguageManager.get(
                "profile.confirm.password.change",
                session
            ) %>",


    "profile.confirm.profile.update":
        "<%= LanguageManager.get(
                "profile.confirm.profile.update",
                session
            ) %>",


    /* ==================================================
       ARCHIVE
    ================================================== */

    "archive.delete.confirm":
        "<%= LanguageManager.get(
                "archive.delete.confirm",
                session
            ) %>",


    "archive.restore.confirm":
        "<%= LanguageManager.get(
                "archive.restore.confirm",
                session
            ) %>",


    /* ==================================================
       PAYMENTS
    ================================================== */

    "payments.confirm.message":
        "<%= LanguageManager.get(
                "payments.confirm.message",
                session
            ) %>"

};

</script>