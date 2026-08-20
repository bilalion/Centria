<%--
    Document   : dialog-error
    Project    : CENTRIA
    Component  : Global Dialog
--%>

<%@ page import="com.centria.language.LanguageManager" %>

<div id="global-dialog"
     class="global-dialog"
     role="dialog"
     aria-modal="true"
     aria-hidden="true">

    <!-- OVERLAY -->
    <div class="global-dialog-overlay"
         data-dialog-close></div>


    <!-- DIALOG -->
    <div class="global-dialog-box">


        <!-- HEADER -->
        <div class="global-dialog-header">

            <div id="global-dialog-icon"
                 class="global-dialog-icon">

                <i class="fas fa-circle-exclamation"></i>

            </div>


            <h3 id="global-dialog-title">
                <%= LanguageManager.get(
                        "common.error",
                        session
                    ) %>
            </h3>


            <button type="button"
                    class="global-dialog-close"
                    data-dialog-close
                    aria-label="Close">

                <i class="fas fa-xmark"></i>

            </button>

        </div>


        <!-- MESSAGE -->
        <div class="global-dialog-body">

            <p id="global-dialog-message"></p>

        </div>


        <!-- ACTIONS -->
        <div class="global-dialog-actions">

            <button type="button"
                    id="global-dialog-confirm"
                    class="global-dialog-button global-dialog-button-primary"
                    data-dialog-close>

                <%= LanguageManager.get(
                        "common.ok",
                        session
                    ) %>

            </button>

        </div>


    </div>

</div>


<!-- ======================================================
     GLOBAL DIALOG TRANSLATIONS
====================================================== -->

<script>

window.centriaDialogMessages = {


    /* ==================================================
       COMMON
    ================================================== */

    "common.error":
        "<%= LanguageManager.get("common.error", session) %>",


    "common.success":
        "<%= LanguageManager.get("common.success", session) %>",


/* ==================================================
   PROFILE - CHANGE PASSWORD
================================================== */

"profile.password.fields.required":
    "<%= LanguageManager.get("profile.password.fields.required", session) %>",


"profile.password.current.required":
    "<%= LanguageManager.get("profile.password.current.required", session) %>",


"profile.password.new.required":
    "<%= LanguageManager.get("profile.password.new.required", session) %>",


"profile.password.confirm.required":
    "<%= LanguageManager.get("profile.password.confirm.required", session) %>",


"profile.password.too.short":
    "<%= LanguageManager.get("profile.password.too.short", session) %>",


"profile.password.mismatch":
    "<%= LanguageManager.get("profile.password.mismatch", session) %>",


"profile.password.current.incorrect":
    "<%= LanguageManager.get("profile.password.current.incorrect", session) %>",


"profile.password.change.error":
    "<%= LanguageManager.get("profile.password.change.error", session) %>",


"profile.password.changed.successfully":
    "<%= LanguageManager.get("profile.password.changed.successfully", session) %>",

    /* ==================================================
       ARCHIVE
    ================================================== */

    "archive.error.operation.required":
        "<%= LanguageManager.get("archive.error.operation.required", session) %>",


    "archive.error.selection.required":
        "<%= LanguageManager.get("archive.error.selection.required", session) %>",


    "archive.error.delete":
        "<%= LanguageManager.get("archive.error.delete", session) %>",


    "archive.error.restore":
        "<%= LanguageManager.get("archive.error.restore", session) %>",


    "archive.error.view":
        "<%= LanguageManager.get("archive.error.view", session) %>",


    "archive.error.load":
        "<%= LanguageManager.get("archive.error.load", session) %>",


    "archive.error.centre.code":
        "<%= LanguageManager.get("archive.error.centre.code", session) %>",


    "archive.error.dialog":
        "<%= LanguageManager.get("archive.error.dialog", session) %>",


    /* ==================================================
       PAYMENTS
    ================================================== */

    "payments.error.start.date.required":
        "<%= LanguageManager.get("payments.error.start.date.required", session) %>",


    "payments.error.confirm":
        "<%= LanguageManager.get("payments.error.confirm", session) %>",


    "payments.error.centre.required":
        "<%= LanguageManager.get("payments.error.centre.required", session) %>",


    "payments.error.subscription.update":
        "<%= LanguageManager.get("payments.error.subscription.update", session) %>"

};

</script>