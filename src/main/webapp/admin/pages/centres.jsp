<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<div class="page-section centres-page">

    <!-- CENTRES BANNER -->

    <section class="centre-banner"
             aria-labelledby="centres-page-title">

        <span class="centre-banner-icon"
              aria-hidden="true">

            <i class="fa-solid fa-building"></i>

        </span>

        <h1 id="centres-page-title"
            class="centre-banner-title">

            <%= LanguageManager.get(
                    "centers.title",
                    session
            ) %>

        </h1>

        <span class="centre-banner-separator"
              aria-hidden="true">

            |

        </span>

        <p class="centre-banner-description">

            <%= LanguageManager.get(
                    "centers.description",
                    session
            ) %>

        </p>

    </section>


    <!-- CENTRES WORKSPACE -->

    <section class="centres-workspace">

        <!-- CONTROL DECK -->

        <div class="centres-control-deck">

            <form id="centresFilterForm"
                  method="get"
                  action="<%=request.getContextPath()%>/CentreServlet"
                  class="centres-filter-form">

                <input type="hidden"
                       name="action"
                       value="list">


                <!-- SEARCH -->

                <div class="centres-search-field">

                    <i class="fa-solid fa-magnifying-glass"
                       aria-hidden="true"></i>

                    <input type="text"
                           id="centreSearch"
                           name="search"
                           value="<%= request.getAttribute("search") != null
                                    ? request.getAttribute("search")
                                    : "" %>"
                           placeholder="<%= LanguageManager.get(
                                   "centers.search.placeholder",
                                   session
                           ) %>">

                </div>


                <!-- STATUS -->

                <div class="centres-select-field">

                    <i class="fa-solid fa-filter"
                       aria-hidden="true"></i>

                    <select id="centreStatus"
                            name="status"
                            class="centre-select">

                        <option value="ALL">
                            <%= LanguageManager.get(
                                    "centers.all",
                                    session
                            ) %>
                        </option>

                        <option value="PENDING">
                            <%= LanguageManager.get(
                                    "centers.pending",
                                    session
                            ) %>
                        </option>

                        <option value="ACTIVE">
                            <%= LanguageManager.get(
                                    "centers.active",
                                    session
                            ) %>
                        </option>

                        <option value="SUSPENDED">
                            <%= LanguageManager.get(
                                    "centers.suspended",
                                    session
                            ) %>
                        </option>

                        <option value="ARCHIVED">
                            <%= LanguageManager.get(
                                    "centers.archived",
                                    session
                            ) %>
                        </option>
                        <option value="INACTIVE">
                            <%= LanguageManager.get(
                                    "centers.inactive",
                                    session) %>
                        </option>

                    </select>

                </div>


                <!-- ORDER -->

                <div class="centres-select-field">

                    <i class="fa-solid fa-arrow-down-wide-short"
                       aria-hidden="true"></i>

                    <select id="centreOrder"
                            name="order"
                            class="centre-select">

                        <option value="NEW">
                            <%= LanguageManager.get(
                                    "centers.newest",
                                    session
                            ) %>
                        </option>

                        <option value="OLD">
                            <%= LanguageManager.get(
                                    "centers.oldest",
                                    session
                            ) %>
                        </option>

                        <option value="NAME">
                            <%= LanguageManager.get(
                                    "centers.name.asc",
                                    session
                            ) %>
                        </option>

                    </select>

                </div>


                <!-- SEARCH ACTION -->

                <button type="button"
                        onclick="loadCentres(1)"
                        class="btn-primary centres-search-button">

                    <i class="fa-solid fa-magnifying-glass"
                       aria-hidden="true"></i>

                    <span>
                        <%= LanguageManager.get(
                                "centers.search",
                                session
                        ) %>
                    </span>

                </button>

            </form>


          
<!-- ADD CENTRE -->

<button type="button"
        class="btn-primary centres-add-action"
        onclick="openAddCentre()">

    <i class="fa-solid fa-plus"
       aria-hidden="true"></i>

    <span>
        <%= LanguageManager.get(
                "centers.add",
                session
        ) %>
    </span>

</button>

        </div>


        <!-- CENTRES REGISTER -->

        <section class="centres-register"
                 aria-labelledby="centres-register-title">

            <header class="centres-register-header">

                <!-- TITLE + TOTAL -->

                <div class="centres-register-title-wrap">

                    <span class="centres-register-icon"
                          aria-hidden="true">

                        <i class="fa-solid fa-table-list"></i>

                    </span>

                    <div>

                        <h2 id="centres-register-title">

                            <%= LanguageManager.get(
                                    "centers.title",
                                    session
                            ) %>

                        </h2>

                        <span class="centres-total"
                              data-centres-total
                              aria-live="polite"></span>

                    </div>

                </div>


                <!-- QUICK STATUS FILTERS -->

                <div class="centres-register-status">

                    <div class="centres-status-filters"
                         aria-label="Centre status filters">

                        <button type="button"
                                class="centre-status-filter is-active"
                                data-centre-status-filter="ALL"
                                aria-pressed="true">

                            <span class="centre-status-dot is-all"></span>

                            <span>
                                <%= LanguageManager.get(
                                        "centers.all",
                                        session
                                ) %>
                            </span>

                            <span class="centre-filter-count"
                                  data-centre-status-count="ALL"></span>

                        </button>

                        <button type="button"
                                class="centre-status-filter"
                                data-centre-status-filter="ACTIVE"
                                aria-pressed="false">

                            <span class="centre-status-dot is-active"></span>

                            <span>
                                <%= LanguageManager.get(
                                        "centers.active",
                                        session
                                ) %>
                            </span>

                            <span class="centre-filter-count"
                                  data-centre-status-count="ACTIVE"></span>

                        </button>
                            <button type="button"
                                    class="centre-status-filter"
                                    data-centre-status-filter="INACTIVE"
                                    aria-pressed="false">
                                <span class="centre-status-dot is-inactive"></span>
                                <span>
                                    <%= LanguageManager.get(
                                            "centers.inactive",
                                            session) %>
                                </span>
                                <span class="centre-filter-count"
                                      data-centre-status-count="INACTIVE"></span>
                            </button>

                        <button type="button"
                                class="centre-status-filter"
                                data-centre-status-filter="PENDING"
                                aria-pressed="false">

                            <span class="centre-status-dot is-pending"></span>

                            <span>
                                <%= LanguageManager.get(
                                        "centers.pending",
                                        session
                                ) %>
                            </span>

                            <span class="centre-filter-count"
                                  data-centre-status-count="PENDING"></span>

                        </button>

                        <button type="button"
                                class="centre-status-filter"
                                data-centre-status-filter="SUSPENDED"
                                aria-pressed="false">

                            <span class="centre-status-dot is-suspended"></span>

                            <span>
                                <%= LanguageManager.get(
                                        "centers.suspended",
                                        session
                                ) %>
                            </span>

                            <span class="centre-filter-count"
                                  data-centre-status-count="SUSPENDED"></span>

                        </button>

                  

                    </div>

                </div>


                <!-- BALANCE FOR CENTER ALIGNMENT -->

                <span class="centres-register-balance"
                      aria-hidden="true"></span>

            </header>


            <!-- TABLE -->

            <div class="centres-table-stage">

                <div id="centres-table-container"
                     class="table-content-area">

                    <div class="centres-table-loading"
                         aria-live="polite">

                        <i class="fa-solid fa-spinner fa-spin"
                           aria-hidden="true"></i>

                    </div>

                </div>

            </div>

        </section>

    </section>


    <!-- CENTRE VIEW MODAL -->

    <div id="centre-modal"
         class="centre-modal"
         role="dialog"
         aria-modal="true"
         aria-hidden="true">

        <div class="centre-modal-content">

            <button type="button"
                    class="modal-close"
                    onclick="closeCentreModal()"
                    aria-label="<%= LanguageManager.get(
                            "centers.cancel",
                            session
                    ) %>">

                <i class="fa-solid fa-xmark"
                   aria-hidden="true"></i>

            </button>

            <div id="centre-modal-body"></div>

        </div>

    </div>


    <!-- RESET PASSWORD CONFIRM MODAL -->

    <div id="reset-confirm-modal"
         class="centre-modal"
         role="dialog"
         aria-modal="true"
         aria-hidden="true">

        <div class="centre-modal-content reset-confirm-box">

            <button type="button"
                    class="modal-close"
                    onclick="closeResetConfirm()"
                    aria-label="<%= LanguageManager.get(
                            "centers.cancel",
                            session
                    ) %>">

                <i class="fa-solid fa-xmark"
                   aria-hidden="true"></i>

            </button>

            <div class="reset-confirm-content">

                <div class="confirm-header">

                    <span class="confirm-icon"
                          aria-hidden="true">

                        <i class="fa-solid fa-key"></i>

                    </span>

                    <h4 class="confirm-title">

                        <%= LanguageManager.get(
                                "centers.reset.title",
                                session
                        ) %>

                    </h4>

                </div>

                <p>

                    <%= LanguageManager.get(
                            "centers.reset.confirm",
                            session
                    ) %>

                </p>

                <div class="reset-confirm-actions">

                    <button type="button"
                            class="btn-secondary btn-cancel"
                            onclick="closeResetConfirm()">

                        <%= LanguageManager.get(
                                "centers.cancel",
                                session
                        ) %>

                    </button>

                    <button type="button"
                            class="btn-primary btn-confirm"
                            onclick="confirmResetPassword()">

                        <%= LanguageManager.get(
                                "centers.confirm",
                                session
                        ) %>

                    </button>

                </div>

            </div>

        </div>

    </div>


    <!-- EDIT CONFIRM MODAL -->

    <div id="edit-confirm-modal"
         class="centre-modal"
         role="dialog"
         aria-modal="true"
         aria-hidden="true">

        <div class="centre-modal-content reset-confirm-box">

            <button type="button"
                    class="modal-close"
                    onclick="closeEditConfirm()"
                    aria-label="<%= LanguageManager.get(
                            "centers.cancel",
                            session
                    ) %>">

                <i class="fa-solid fa-xmark"
                   aria-hidden="true"></i>

            </button>

            <div class="reset-confirm-content">

                <div class="confirm-header">

                    <span class="confirm-icon is-warning"
                          aria-hidden="true">

                        <i class="fa-solid fa-triangle-exclamation"></i>

                    </span>

                    <h4 class="confirm-title">

                        <%= LanguageManager.get(
                                "centers.edit.title",
                                session
                        ) %>

                    </h4>

                </div>

                <p>

                    <%= LanguageManager.get(
                            "centers.edit.confirm",
                            session
                    ) %>

                </p>

                <div class="reset-confirm-actions">

                    <button type="button"
                            class="btn-secondary"
                            onclick="closeEditConfirm()">

                        <%= LanguageManager.get(
                                "centers.cancel",
                                session
                        ) %>

                    </button>

                    <button type="button"
                            class="btn-primary"
                            onclick="confirmEditCentre()">

                        <%= LanguageManager.get(
                                "centers.confirm",
                                session
                        ) %>

                    </button>

                </div>

            </div>

        </div>

    </div>

</div>