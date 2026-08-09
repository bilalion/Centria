<%--
    Document   : archive
    Project    : CENTRIA
    Module     : Archive
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


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

                    0

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

                    0

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

                    0

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

                    0

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
                  <jsp:include page="fragments/archive/archive-table.jsp" />
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
    تم استرجاع المركز CTR001
</p>
    </div>


</section>

</div>