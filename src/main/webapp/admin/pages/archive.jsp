<%--
    Document   : archive
    Project    : CENTRIA
    Module     : Archive
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<%
/*
======================================================
ARCHIVE DATA SAFETY
======================================================

When Archive is loaded directly from dashboard.jsp
during browser refresh, some request attributes may
not yet exist.

Use safe default values instead of allowing JSP
to fail because of null Integer casts.
======================================================
*/

Integer archivedCount =
        (Integer) request.getAttribute(
                "archivedCount"
        );

Integer pendingDeleteCount =
        (Integer) request.getAttribute(
                "pendingDeleteCount"
        );

Integer deletedCount =
        (Integer) request.getAttribute(
                "deletedCount"
        );


if (archivedCount == null) {

    archivedCount = 0;

}


if (pendingDeleteCount == null) {

    pendingDeleteCount = 0;

}


if (deletedCount == null) {

    deletedCount = 0;

}


Integer archiveTotal =
        archivedCount
        +
        pendingDeleteCount;


/*
======================================================
LAST ARCHIVE OPERATION
======================================================
*/

java.util.Map<String, Object> lastArchiveOperation =
        (java.util.Map<String, Object>)
                request.getAttribute(
                        "lastArchiveOperation"
                );


String lastActionMessage = "";


if (lastArchiveOperation != null) {


    String operator =
            (String)
                    lastArchiveOperation.get(
                            "operator"
                    );


    String operationType =
            (String)
                    lastArchiveOperation.get(
                            "operationType"
                    );


    Integer operationCountValue =
            (Integer)
                    lastArchiveOperation.get(
                            "operationCount"
                    );


    java.sql.Timestamp operationAt =
            (java.sql.Timestamp)
                    lastArchiveOperation.get(
                            "operationAt"
                    );


    /*
    --------------------------------------------------
    Safety
    --------------------------------------------------
    */

    if (operator == null) {

        operator = "";

    }


    int operationCount =
            operationCountValue != null
            ?
            operationCountValue
            :
            0;


    /*
    --------------------------------------------------
    Date / Time
    --------------------------------------------------
    */

    String operationDate = "";

    String operationTime = "";


    if (operationAt != null) {


        operationDate =
                new java.text.SimpleDateFormat(
                        "dd/MM/yyyy"
                ).format(
                        operationAt
                );


        operationTime =
                new java.text.SimpleDateFormat(
                        "HH:mm"
                ).format(
                        operationAt
                );

    }


    /*
    --------------------------------------------------
    Language key
    --------------------------------------------------
    */

    String languageKey =
            "RESTORE".equalsIgnoreCase(
                    operationType
            )
            ?
            "archive.last.action.restore"
            :
            "archive.last.action.delete";


    String messageTemplate =
            LanguageManager.get(
                    languageKey,
                    session
            );


    /*
    --------------------------------------------------
    Build translated message
    --------------------------------------------------
    */

    lastActionMessage =
            java.text.MessageFormat.format(
                    messageTemplate,
                    operator,
                    operationCount,
                    operationDate,
                    operationTime
            );

}

%>


<div class="page-section archive-page">


    <!-- =====================================================
         ARCHIVE BANNER
         ===================================================== -->

    <section class="archive-banner"
             aria-labelledby="archive-page-title">


        <span class="archive-banner-icon"
              aria-hidden="true">

            <i class="fa-solid fa-box-archive"></i>

        </span>


        <h1 id="archive-page-title"
            class="archive-banner-title">

            <%= LanguageManager.get(
                    "archive.title",
                    session
            ) %>

        </h1>


        <span class="archive-banner-separator"
              aria-hidden="true">

            |

        </span>


        <p class="archive-banner-description">

            <%= LanguageManager.get(
                    "archive.description",
                    session
            ) %>

        </p>

    </section>



    <!-- =====================================================
         ARCHIVE STATISTICS
         ===================================================== -->

    <section class="archive-statistics"
             aria-label="<%= LanguageManager.get(
                     "archive.total",
                     session
             ) %>">


        <!-- TOTAL -->

        <article class="archive-stat-card archive-stat-total">


            <div class="archive-stat-icon"
                 aria-hidden="true">

                <i class="fa-solid fa-box-archive"></i>

            </div>


            <div class="archive-stat-content">


                <span class="archive-stat-label">

                    <%= LanguageManager.get(
                            "archive.total",
                            session
                    ) %>

                </span>


                <strong class="archive-stat-value"
                        data-archive-stat="total">

                    <%= archiveTotal %>

                </strong>


            </div>

        </article>



        <!-- RESTORABLE -->

        <article class="archive-stat-card archive-stat-restorable">


            <div class="archive-stat-icon"
                 aria-hidden="true">

                <i class="fa-solid fa-rotate-left"></i>

            </div>


            <div class="archive-stat-content">


                <span class="archive-stat-label">

                    <%= LanguageManager.get(
                            "archive.archived",
                            session
                    ) %>

                </span>


                <strong class="archive-stat-value"
                        data-archive-stat="restorable">

                    <%= archivedCount %>

                </strong>


                <span class="archive-stat-description">

                    <%= LanguageManager.get(
                            "archive.restore",
                            session
                    ) %>

                </span>


            </div>

        </article>



        <!-- PENDING DELETE -->

        <article class="archive-stat-card archive-stat-pending">


            <div class="archive-stat-icon"
                 aria-hidden="true">

                <i class="fa-solid fa-clock"></i>

            </div>


            <div class="archive-stat-content">


                <span class="archive-stat-label">

                    <%= LanguageManager.get(
                            "archive.pending.delete",
                            session
                    ) %>

                </span>


                <strong class="archive-stat-value"
                        data-archive-stat="pending-delete">

                    <%= pendingDeleteCount %>

                </strong>


                <span class="archive-stat-description">

                    <%= LanguageManager.get(
                            "archive.retention.until",
                            session
                    ) %>

                </span>


            </div>

        </article>



        <!-- DELETED -->

        <article class="archive-stat-card archive-stat-deleted">


            <div class="archive-stat-icon"
                 aria-hidden="true">

                <i class="fa-solid fa-trash"></i>

            </div>


            <div class="archive-stat-content">


                <span class="archive-stat-label">

                    <%= LanguageManager.get(
                            "archive.deleted",
                            session
                    ) %>

                </span>


                <strong class="archive-stat-value"
                        data-archive-stat="deleted">

                    <%= deletedCount %>

                </strong>


                <span class="archive-stat-description">

                    <%= LanguageManager.get(
                            "archive.delete",
                            session
                    ) %>

                </span>


            </div>

        </article>

    </section>



    <!-- =====================================================
         ARCHIVE WORKSPACE
         ===================================================== -->

    <section class="archive-workspace">


        <!-- =================================================
             ARCHIVE CONTROL DECK
             ================================================= -->

        <div class="archive-control-deck">


            <!-- SEARCH -->

            <div class="archive-search-field">


                <i class="fa-solid fa-magnifying-glass"
                   aria-hidden="true"></i>


                <input type="text"
                       id="archiveSearch"
                       name="search"
                       autocomplete="off"
                       placeholder="<%= LanguageManager.get(
                               "archive.search.placeholder",
                               session
                       ) %>">


            </div>



            <!-- FILTER -->

            <div class="archive-select-field">


                <i class="fa-solid fa-filter"
                   aria-hidden="true"></i>


                <select id="archiveStatus"
                        name="status"
                        class="archive-select">


                    <option value="ALL">

                        <%= LanguageManager.get(
                                "archive.all",
                                session
                        ) %>

                    </option>


                    <option value="ARCHIVED">

                        <%= LanguageManager.get(
                                "archive.archived",
                                session
                        ) %>

                    </option>


                    <option value="PENDING_DELETE">

                        <%= LanguageManager.get(
                                "archive.pending.delete",
                                session
                        ) %>

                    </option>


                    <option value="DELETED">

                        <%= LanguageManager.get(
                                "archive.deleted",
                                session
                        ) %>

                    </option>


                </select>

            </div>



            <!-- SEARCH ACTION -->

            <button type="button"
                    id="archiveSearchButton"
                    class="btn-primary archive-search-button">


                <i class="fa-solid fa-magnifying-glass"
                   aria-hidden="true"></i>


                <span>

                    <%= LanguageManager.get(
                            "archive.filter",
                            session
                    ) %>

                </span>


            </button>



            <!-- EXPORT -->

            <button type="button"
                    id="archiveExportButton"
                    class="btn-secondary archive-export-button">


                <i class="fa-solid fa-download"
                   aria-hidden="true"></i>


                <span>

                    تصدير

                </span>


            </button>


        </div>



        <!-- =================================================
             ARCHIVE REGISTER
             ================================================= -->

        <section class="archive-register"
                 aria-labelledby="archive-register-title">


            <!-- REGISTER HEADER -->

            <header class="archive-register-header">


                <!-- TITLE + TOTAL -->

                <div class="archive-register-title-wrap">


                    <span class="archive-register-icon"
                          aria-hidden="true">

                        <i class="fa-solid fa-table-list"></i>

                    </span>


                    <div>


                        <h2 id="archive-register-title">

                            <%= LanguageManager.get(
                                    "archive.register.title",
                                    session
                            ) %>

                        </h2>


                        <span class="archive-total"
                              data-archive-total
                              aria-live="polite">

                        </span>


                    </div>

                </div>



                <!-- BULK OPERATIONS -->

                <div class="archive-bulk-actions">


                    <label for="archiveOperation"
                           class="archive-operation-label">

                        <%= LanguageManager.get(
                                "archive.operation",
                                session
                        ) %>

                    </label>


                    <select id="archiveOperation"
                            class="archive-operation-select"
                            disabled>


                        <option value="">

                            <%= LanguageManager.get(
                                    "archive.operation",
                                    session
                            ) %>

                        </option>


                        <option value="RESTORE">

                            <%= LanguageManager.get(
                                    "archive.operation.restore",
                                    session
                            ) %>

                        </option>


                        <option value="DELETE">

                            <%= LanguageManager.get(
                                    "archive.operation.delete",
                                    session
                            ) %>

                        </option>


                    </select>



                    <button type="button"
                            id="archiveApplyButton"
                            class="btn-primary archive-apply-button"
                            disabled>


                        <%= LanguageManager.get(
                                "archive.apply",
                                session
                        ) %>


                    </button>


                </div>


            </header>



            <!-- =================================================
                 TABLE
                 ================================================= -->

            <div class="archive-table-stage">


                <div id="archive-table-container"
                     class="table-content-area">


                    <jsp:include
                            page="fragments/archive/archive-table.jsp"/>


                </div>


            </div>



            <!-- =================================================
                 PAGINATION
                 ================================================= -->

            <div id="archive-pagination"
                 class="archive-pagination"
                 aria-label="Pagination">

            </div>


        </section>



        <!-- =====================================================
             LAST ARCHIVE ACTIVITY
             ===================================================== -->

        <section class="archive-notification"
                 aria-labelledby="archive-last-action-title">


            <div class="archive-notification-icon"
                 aria-hidden="true">

                <i class="fa-solid fa-clock-rotate-left"></i>

            </div>


            <div class="archive-notification-content">


                <h4 id="archive-last-action-title">

                    <%= LanguageManager.get(
                            "archive.last.action",
                            session
                    ) %>

                </h4>


                <p data-archive-last-action>

                    <%= lastActionMessage %>

                </p>


            </div>


        </section>



        <!-- =====================================================
             DELETE CONFIRM MODAL
             ===================================================== -->

        <div id="archive-delete-confirm-modal"
             class="centre-modal"
             role="dialog"
             aria-modal="true"
             aria-hidden="true"

             data-delete-message-template="<%= LanguageManager.get(
                     "archive.delete.message",
                     session
             ) %>">


            <div class="centre-modal-content reset-confirm-box">


                <!-- CLOSE -->

                <button type="button"
                        class="modal-close"
                        onclick="closeArchiveDeleteConfirm()"
                        aria-label="<%= LanguageManager.get(
                                "archive.delete.cancel",
                                session
                        ) %>">


                    <i class="fa-solid fa-xmark"
                       aria-hidden="true"></i>


                </button>



                <!-- CONTENT -->

                <div class="reset-confirm-content">


                    <!-- HEADER -->

                    <div class="confirm-header">


                        <!-- WARNING ICON -->

                        <span class="confirm-icon is-warning"
                              aria-hidden="true">


                            <i class="fa-solid fa-triangle-exclamation"></i>


                        </span>



                        <!-- TITLE -->

                        <h4 class="confirm-title">


                            <%= LanguageManager.get(
                                    "archive.delete.title",
                                    session
                            ) %>


                        </h4>


                    </div>



                    <!-- MESSAGE -->

                    <p id="archive-delete-confirm-message">


                        <%= LanguageManager.get(
                                "archive.delete.message",
                                session
                        ) %>


                    </p>



                    <!-- ACTIONS -->

                    <div class="reset-confirm-actions">


                        <!-- CANCEL -->

                        <button type="button"
                                class="btn-secondary"
                                onclick="closeArchiveDeleteConfirm()">


                            <%= LanguageManager.get(
                                    "archive.delete.cancel",
                                    session
                            ) %>


                        </button>



                        <!-- CONFIRM -->

                        <button type="button"
                                class="btn-primary"
                                onclick="confirmArchiveDelete()">


                            <%= LanguageManager.get(
                                    "archive.delete.confirm",
                                    session
                            ) %>


                        </button>


                    </div>


                </div>


            </div>


        </div>


    </section>


</div>