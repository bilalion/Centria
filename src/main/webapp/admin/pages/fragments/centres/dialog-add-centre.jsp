<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<div class="add-dialog">

    <!-- =================================================
         TOP BAR
    ================================================= -->

    <div class="add-dialog-top">

    

        <div class="add-dialog-header">

            <div class="add-dialog-icon">

          <i class="fa-solid fa-building"></i>

            </div>

            <div class="add-dialog-divider"></div>

            <h2 class="add-dialog-title">

                <%= LanguageManager.get(
                        "centers.add.title",
                        session
                ) %>

            </h2>

        </div>

    </div>



    <!-- =================================================
         FORM
    ================================================= -->

 <form id="addCentreForm"
      method="post"
      action="<%=request.getContextPath()%>/CentreServlet">

    <input type="hidden"
           name="action"
           value="add">



        <!-- =================================================
             CENTRE INFORMATION
        ================================================= -->

        <div class="add-dialog-section">

    <div class="add-dialog-section-title">

        <i class="fa-solid fa-school"></i>

        <%= LanguageManager.get(
                "centers.information",
                session
        ) %>

    </div>

    <div class="add-dialog-grid">

        <!-- Centre Name -->

        <div class="add-dialog-row add-dialog-row-full">

            <div class="add-dialog-label">

                <i class="fa-solid fa-school"></i>

                <%= LanguageManager.get(
                        "centers.name",
                        session
                ) %>

            </div>

            <div class="add-dialog-input-box">

                <input
                        type="text"
                        name="name"
                        class="add-dialog-input"
                        required>

            </div>

        </div>

        <!-- Owner Name -->

        <div class="add-dialog-row">

            <div class="add-dialog-label">

                <i class="fa-solid fa-user-tie"></i>

                <%= LanguageManager.get(
                        "centers.owner.name",
                        session
                ) %>

            </div>

            <div class="add-dialog-input-box">

                <input
                        type="text"
                        name="owner_name"
                        class="add-dialog-input"
                        required>

            </div>

        </div>

        <!-- Phone -->

        <div class="add-dialog-row">

            <div class="add-dialog-label">

                <i class="fa-solid fa-phone"></i>

                <%= LanguageManager.get(
                        "centers.phone",
                        session
                ) %>

            </div>

            <div class="add-dialog-input-box">

                <input
                        type="text"
                        name="phone"
                        class="add-dialog-input">

            </div>

        </div>

    </div>

</div>



         <!-- =================================================
             SUBSCRIPTION
        ================================================= -->

        <div class="add-dialog-section">

            <div class="add-dialog-section-title">

                <i class="fa-solid fa-calendar-days"></i>

                <%= LanguageManager.get(
                        "centers.subscription",
                        session
                ) %>

            </div>

            <div class="add-dialog-grid">

                <!-- ==========================
                     START DATE
                ========================== -->

                <div class="add-dialog-row">

                    <div class="add-dialog-label">

                        <i class="fa-solid fa-calendar-days"></i>

                        <%= LanguageManager.get(
                                "centers.subscription.start",
                                session
                        ) %>

                    </div>

                    <div class="add-dialog-input-box">

                        <input
                                type="date"
                                name="subscription_start"
                                class="add-dialog-input"
                                required>

                    </div>

                </div>



                <!-- ==========================
                     DURATION
                ========================== -->

                <div class="add-dialog-row">

                    <div class="add-dialog-label">

                        <i class="fa-solid fa-clock"></i>

                        <%= LanguageManager.get(
                                "centers.subscription.duration",
                                session
                        ) %>

                    </div>

                    <div class="add-dialog-input-box">

                        <select
                                name="subscription_duration"
                                class="add-dialog-select"
                                required>

                            <option value="1">

                                <%= LanguageManager.get(
                                        "centers.duration.1",
                                        session
                                ) %>

                            </option>

                            <option value="3">

                                <%= LanguageManager.get(
                                        "centers.duration.3",
                                        session
                                ) %>

                            </option>

                            <option value="6">

                                <%= LanguageManager.get(
                                        "centers.duration.6",
                                        session
                                ) %>

                            </option>

                            <option value="12">

                                <%= LanguageManager.get(
                                        "centers.duration.12",
                                        session
                                ) %>

                            </option>

                        </select>

                    </div>

                </div>

            </div>

        </div>



        <!-- =================================================
             NOTE
        ================================================= -->

        <div class="add-dialog-note">

            <i class="fa-solid fa-shield-halved"></i>

            <span>

                <%= LanguageManager.get(
                        "centers.connection.generated",
                        session
                ) %>

            </span>

        </div>



        <!-- =================================================
             FOOTER
        ================================================= -->

        <div class="add-dialog-footer">

            <button
                    type="button"
                    class="btn-secondary"
                    onclick="closeCentreModal()">

                <i class="fa-solid fa-arrow-left"></i>

                <%= LanguageManager.get(
                        "centers.back",
                        session
                ) %>

            </button>

            <button
                    type="submit"
                    class="btn-primary">

                <i class="fa-solid fa-circle-check"></i>

                <%= LanguageManager.get(
                        "centers.create",
                        session
                ) %>

            </button>

        </div>

    </form>

</div>