<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.language.LanguageManager"%>
<%@page import="com.centria.models.Centre"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>

<%

List<Centre> centres =
(List<Centre>) request.getAttribute("centres");


SimpleDateFormat sdf =
new SimpleDateFormat("dd/MM/yyyy");

String lang =
(String) session.getAttribute("lang");

if(lang == null){

    lang = "ar";

}

boolean rtl =
!"fr".equalsIgnoreCase(lang)
&&
!"en".equalsIgnoreCase(lang);


int currentPage =
request.getAttribute("currentPage") != null
?
(Integer) request.getAttribute("currentPage")
:
1;


int totalPages =
request.getAttribute("totalPages") != null
?
(Integer) request.getAttribute("totalPages")
:
1;

%>


<%

if(centres == null || centres.isEmpty()){

%>

<div class="empty-state">

    <i class="fa-solid fa-building-circle-xmark"
       aria-hidden="true"></i>

    <p>

        <%=LanguageManager.get(
                "centers.empty",
                session
        )%>

    </p>

</div>

<%

}else{

%>


<!-- =================================================
     CENTRES REGISTER TABLE
================================================= -->

<div class="table-container">

    <table class="centers-table">

        <thead>

            <tr>

                <!-- CENTRE -->

                <th>

                    <%=LanguageManager.get(
                            "centers.name",
                            session
                    )%>

                </th>


                <!-- OWNER + PHONE -->

                <th>

                    <%=LanguageManager.get(
                            "centers.owner",
                            session
                    )%>

                    /

                    <%=LanguageManager.get(
                            "centers.phone",
                            session
                    )%>

                </th>


                <!-- SUBSCRIPTION PERIOD -->

                <th>

                    <%=LanguageManager.get(
                            "centers.subscription.start",
                            session
                    )%>

                    /

                    <%=LanguageManager.get(
                            "centers.subscription.end",
                            session
                    )%>

                </th>


                <!-- STATUS -->

                <th>

                    <%=LanguageManager.get(
                            "centers.status",
                            session
                    )%>

                </th>


                <!-- ACTIONS -->

                <th>

                    <%=LanguageManager.get(
                            "centers.actions",
                            session
                    )%>

                </th>

            </tr>

        </thead>


        <tbody>

            <%

            for(Centre centre : centres){

                String status =
                centre.getStatus();

                if(status == null || status.trim().isEmpty()){

                    status = "PENDING";

                }

                status = status.toUpperCase();

                String statusClass =
                "status-" + status.toLowerCase();

                String rowStatusClass =
                "is-pending";

                String statusLabel =
                LanguageManager.get(
                        "centers.pending",
                        session
                );

                if("ACTIVE".equals(status)){

                    rowStatusClass = "is-active";

                    statusLabel =
                    LanguageManager.get(
                            "centers.active",
                            session
                    );

                }
                
                else if("INACTIVE".equals(status)){

    rowStatusClass = "is-inactive";

    statusLabel =
    LanguageManager.get(
            "centers.inactive",
            session
    );

}
                else if("SUSPENDED".equals(status)){

                    rowStatusClass = "is-suspended";

                    statusLabel =
                    LanguageManager.get(
                            "centers.suspended",
                            session
                    );

                }
                else if("ARCHIVED".equals(status)){

                    rowStatusClass = "is-archived";

                    statusLabel =
                    LanguageManager.get(
                            "centers.archived",
                            session
                    );

                }

                String startDate =
                centre.getSubscriptionStart() != null
                ?
                sdf.format(
                        centre.getSubscriptionStart()
                )
                :
                "-";

                String endDate =
                centre.getSubscriptionEnd() != null
                ?
                sdf.format(
                        centre.getSubscriptionEnd()
                )
                :
                "-";

            %>

            <tr class="centre-row <%=rowStatusClass%>"
                data-status="<%=status%>">


                <!-- CENTRE IDENTITY -->

                <td>

                    <div class="centre-identity">

                        <span class="centre-avatar"
                              aria-hidden="true">

                            <i class="fa-solid fa-building"></i>

                        </span>

                        <div class="centre-identity-copy">

                            <strong class="centre-name">

                                <%=centre.getName() != null
                                ?
                                centre.getName()
                                :
                                "-"
                                %>

                            </strong>

                            <span class="centre-code">

                                <%=centre.getCentreCode() != null
                                ?
                                centre.getCentreCode()
                                :
                                "-"
                                %>

                            </span>

                        </div>

                    </div>

                </td>


                <!-- OWNER + CONTACT -->

                <td>

                    <div class="centre-contact">

                        <span class="centre-owner">

                            <i class="fa-regular fa-user"
                               aria-hidden="true"></i>

                            <%=centre.getOwnerName() != null
                            ?
                            centre.getOwnerName()
                            :
                            "-"
                            %>

                        </span>

                        <span class="centre-phone">

                            <i class="fa-solid fa-phone"
                               aria-hidden="true"></i>

                            <%=centre.getPhone() != null
                            ?
                            centre.getPhone()
                            :
                            "-"
                            %>

                        </span>

                    </div>

                </td>


        <!-- SUBSCRIPTION TIMELINE -->

<td>

    <div class="centre-subscription-timeline">

        <span class="centre-timeline-line"
              aria-hidden="true"></span>

       <div class="centre-timeline-copy">

    <span class="centre-timeline-date">

        <strong>
            <%=LanguageManager.get(
                    "centers.subscription.start",
                    session
            )%> :
        </strong>

        <%=startDate%>

    </span>

    <span class="centre-timeline-date">

        <strong>
            <%=LanguageManager.get(
                    "centers.subscription.end",
                    session
            )%> :
        </strong>

        <%=endDate%>

    </span>

</div>

    </div>

</td>


                <!-- STATUS -->

                <td>

                    <select class="centre-status-select <%=statusClass%>"
                            data-id="<%=centre.getId()%>"
                            onchange="updateCentreStatus(this)">

                        <option value="PENDING"
                        <%=
                        "PENDING".equals(status)
                        ?
                        "selected"
                        :
                        ""
                        %>>

                            <%=LanguageManager.get(
                                    "centers.pending",
                                    session
                            )%>

                        </option>

                        <option value="ACTIVE"
                        <%=
                        "ACTIVE".equals(status)
                        ?
                        "selected"
                        :
                        ""
                        %>>

                            <%=LanguageManager.get(
                                    "centers.active",
                                    session
                            )%>

                        </option>

                        
                        <option value="INACTIVE"
                                <%=
                                    "INACTIVE".equals(status)
                                            ?
                                            "selected"
                                            :
                                            ""
                                %>>
                            <%=LanguageManager.get(
                                    "centers.inactive",
                                    session)%>
                        </option>
                        
                        <option value="SUSPENDED"
                        <%=
                        "SUSPENDED".equals(status)
                        ?
                        "selected"
                        :
                        ""
                        %>>

                            <%=LanguageManager.get(
                                    "centers.suspended",
                                    session
                            )%>

                        </option>

                        <option value="ARCHIVED"
                        <%=
                        "ARCHIVED".equals(status)
                        ?
                        "selected"
                        :
                        ""
                        %>>

                            <%=LanguageManager.get(
                                    "centers.archived",
                                    session
                            )%>

                        </option>

                    </select>

                </td>


                <!-- ACTIONS -->

                <td>

                    <div class="table-actions">

                        <button type="button"
                                class="action-button action-view"
                                title="<%=LanguageManager.get(
                                        "centers.actions",
                                        session
                                )%>"
                                onclick="viewCentre(<%=centre.getId()%>)">

                            <i class="fa-regular fa-eye"
                               aria-hidden="true"></i>

                        </button>


                        <button type="button"
                                class="action-button action-edit"
                                title="<%=LanguageManager.get(
                                        "centers.edit",
                                        session
                                )%>"
                                onclick="editCentre(<%=centre.getId()%>)">

                            <i class="fa-solid fa-pen"
                               aria-hidden="true"></i>

                        </button>


                        <button type="button"
                                class="action-button action-reset"
                                title="<%=LanguageManager.get(
                                        "centers.reset.password",
                                        session
                                )%>"
                                onclick="resetCentrePassword(<%=centre.getId()%>)">

                            <i class="fa-solid fa-key"
                               aria-hidden="true"></i>

                        </button>

                    </div>

                </td>

            </tr>

            <%

            }

            %>

        </tbody>

    </table>

</div>


<!-- =================================================
     PAGINATION
================================================= -->

<%

if(totalPages > 1){

    int startPage =
            Math.max(1, currentPage - 1);

    int endPage =
            Math.min(
                totalPages,
                startPage + 2
            );

    if(endPage - startPage < 2){

        startPage =
                Math.max(
                    1,
                    endPage - 2
                );

    }

%>

<nav class="centres-pagination">

    <!-- LAST -->

    <button class="page-btn"
            type="button"
            onclick="changeCentrePage(<%=totalPages%>)"
            <%=currentPage >= totalPages ? "disabled" : ""%>>

        <i class="fa-solid fa-angles-right"
           aria-hidden="true"></i>

    </button>

    <!-- NEXT -->

    <button class="page-btn"
            type="button"
            onclick="changeCentrePage(<%=currentPage + 1%>)"
            <%=currentPage >= totalPages ? "disabled" : ""%>>

        <i class="fa-solid fa-chevron-right"
           aria-hidden="true"></i>

    </button>

    <%

    for(int i = startPage; i <= endPage; i++){

    %>

    <button class="page-btn <%=i == currentPage ? "active" : ""%>"
            type="button"
            onclick="changeCentrePage(<%=i%>)">

        <%=i%>

    </button>

    <%

    }

    %>

    <!-- PREVIOUS -->

    <button class="page-btn"
            type="button"
            onclick="changeCentrePage(<%=currentPage - 1%>)"
            <%=currentPage <= 1 ? "disabled" : ""%>>

        <i class="fa-solid fa-chevron-left"
           aria-hidden="true"></i>

    </button>

    <!-- FIRST -->

    <button class="page-btn"
            type="button"
            onclick="changeCentrePage(1)"
            <%=currentPage <= 1 ? "disabled" : ""%>>

        <i class="fa-solid fa-angles-left"
           aria-hidden="true"></i>

    </button>

</nav>

<%

}

%>

<%

}

%>