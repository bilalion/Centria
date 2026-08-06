<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.language.LanguageManager"%>
<%@page import="com.centria.models.Centre"%>

<%
Centre centre =
(Centre) request.getAttribute("centre");
%>
<div class="edit-dialog">

    <!-- =================================================
         TOP BAR
    ================================================= -->

    <div class="edit-dialog-top">

      

            <i class="fa-solid fa-xmark"></i>

        </button>

        <div class="edit-dialog-header">

            <div class="edit-dialog-icon">

                <i class="fa-solid fa-pen-to-square"></i>

            </div>

            <div class="edit-dialog-divider"></div>

            <h2 class="edit-dialog-title">

                <%=LanguageManager.get(
                        "centers.edit.title",
                        session
                )%>

            </h2>

        </div>

    </div>



    <!-- =================================================
         FORM
    ================================================= -->

    <form id="editCentreForm">

        <input type="hidden"
               name="id"
               value="<%=centre.getId()%>">



        <!-- =================================================
             GENERAL INFORMATION
        ================================================= -->

        <div class="edit-dialog-section">

            <div class="edit-dialog-section-title">

                🏢

                <%=LanguageManager.get(
                        "centers.dialog.information",
                        session
                )%>

            </div>



            <div class="edit-dialog-grid">


                <!-- Centre Code -->

                <div class="edit-dialog-row">

                    <div class="edit-dialog-label">

                        🔑

                        <%=LanguageManager.get(
                                "centers.edit.code",
                                session
                        )%>

                    </div>

                    <div class="edit-dialog-value">

                        <input
                            type="text"
                            class="edit-dialog-input edit-dialog-readonly"
                            value="<%=centre.getCentreCode()%>"
                            readonly>

                    </div>

                </div>



                <!-- Centre Name -->

                <div class="edit-dialog-row">

                    <div class="edit-dialog-label">

                        🏢

                        <%=LanguageManager.get(
                                "centers.name",
                                session
                        )%>

                    </div>

                    <div class="edit-dialog-value">

                        <input
                            type="text"
                            name="name"
                            class="edit-dialog-input"
                            value="<%=centre.getName()%>"
                            required>

                    </div>

                </div>



                <!-- Owner -->

                <div class="edit-dialog-row">

                    <div class="edit-dialog-label">

                        👤

                        <%=LanguageManager.get(
                                "centers.owner.name",
                                session
                        )%>

                    </div>

                    <div class="edit-dialog-value">

                        <input
                            type="text"
                            name="owner_name"
                            class="edit-dialog-input"
                            value="<%=centre.getOwnerName()%>"
                            required>

                    </div>

                </div>



                <!-- Phone -->

                <div class="edit-dialog-row">

                    <div class="edit-dialog-label">

                        📞

                        <%=LanguageManager.get(
                                "centers.phone",
                                session
                        )%>

                    </div>

                    <div class="edit-dialog-value">

                        <input
                            type="text"
                            name="phone"
                            class="edit-dialog-input"
                            value="<%=centre.getPhone()%>">

                    </div>

                </div>

            </div>

        </div>
                            
                                    <!-- =================================================
             SUBSCRIPTION INFORMATION
        ================================================= -->

        <div class="edit-dialog-section">

            <div class="edit-dialog-section-title">

                🔒

                <%=LanguageManager.get(
                        "centers.edit.subscription.title",
                        session
                )%>

            </div>


            <div class="edit-dialog-grid">


                <!-- Subscription Start -->

                <div class="edit-dialog-row">

                    <div class="edit-dialog-label">

                        📅

                        <%=LanguageManager.get(
                                "centers.subscription.start",
                                session
                        )%>

                    </div>

                    <div class="edit-dialog-value">

                        <strong>

                            <%=centre.getSubscriptionStart()%>

                        </strong>

                    </div>

                </div>



                <!-- Subscription End -->

                <div class="edit-dialog-row">

                    <div class="edit-dialog-label">

                        📅

                        <%=LanguageManager.get(
                                "centers.subscription.end",
                                session
                        )%>

                    </div>

                    <div class="edit-dialog-value">

                        <strong>

                            <%=centre.getSubscriptionEnd()%>

                        </strong>

                    </div>

                </div>



                <!-- Status -->

                <div class="edit-dialog-row">

                    <div class="edit-dialog-label">

                        ⚙️

                        <%=LanguageManager.get(
                                "centers.status",
                                session
                        )%>

                    </div>

                    <div class="edit-dialog-value">

                        <span class="status-badge
                            <%= "ACTIVE".equalsIgnoreCase(centre.getStatus()) ? "status-active"
                              : "PENDING".equalsIgnoreCase(centre.getStatus()) ? "status-pending"
                              : "SUSPENDED".equalsIgnoreCase(centre.getStatus()) ? "status-suspended"
                              : "status-archived" %>">

                            <%=centre.getStatus()%>

                        </span>

                    </div>

                </div>

            </div>

        </div>



        <!-- =================================================
             FOOTER
        ================================================= -->

        <div class="edit-dialog-footer">

            <button type="button"
                    class="btn-secondary"
                    onclick="closeCentreModal()">

                <%=LanguageManager.get(
                        "centers.cancel",
                        session
                )%>

            </button>



            <button type="button"
                    class="btn-primary"
                    onclick="saveEditCentre()">

                💾

                <%=LanguageManager.get(
                        "centers.save",
                        session
                )%>

            </button>

        </div>

    </form>

</div>