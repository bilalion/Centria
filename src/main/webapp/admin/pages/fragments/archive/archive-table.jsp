<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.centria.models.Archive"%>
<%@page import="com.centria.language.LanguageManager"%>


<%

List<Archive> archivedCentres =
        (List<Archive>) request.getAttribute(
                "archivedCentres"
        );

%>


<!-- =====================================================
     ARCHIVE TABLE
     ===================================================== -->

<table class="archive-table">


    <!-- =================================================
         TABLE HEADER
         ================================================= -->

    <thead>

        <tr>


            <!-- SELECT ALL -->

            <th class="archive-select-column">

                <input type="checkbox"
                       id="archiveSelectAll"
                       aria-label="Select all">

            </th>


            <!-- CENTRE CODE -->

            <th>

                <%= LanguageManager.get(
                        "archive.centre.code",
                        session
                ) %>

            </th>


            <!-- CENTRE NAME -->

            <th>

                <%= LanguageManager.get(
                        "archive.centre.name",
                        session
                ) %>

            </th>


            <!-- ARCHIVE STATUS -->

            <th>

                <%= LanguageManager.get(
                        "archive.status",
                        session
                ) %>

            </th>


            <!-- ARCHIVED AT -->

            <th>

                <%= LanguageManager.get(
                        "archive.archived.at",
                        session
                ) %>

            </th>


            <!-- RETENTION UNTIL -->

            <th>

                <%= LanguageManager.get(
                        "archive.retention.until",
                        session
                ) %>

            </th>


            <!-- ACTION -->

            <th>

                <%= LanguageManager.get(
                        "archive.action",
                        session
                ) %>

            </th>


        </tr>

    </thead>


    <!-- =================================================
         TABLE BODY
         ================================================= -->

    <tbody>


        <%
        if (archivedCentres != null &&
            !archivedCentres.isEmpty()) {


            for (Archive archive : archivedCentres) {
        %>


        <tr>


            <!-- CHECKBOX -->

            <td class="archive-select-column">

                <input type="checkbox"
                       class="archive-row-checkbox"
                       value="<%= archive.getCentreCode() %>"
                       data-archive-status="<%= archive.getArchiveStatus() %>">

            </td>


            <!-- CENTRE CODE -->

            <td>

                <%= archive.getCentreCode() %>

            </td>


            <!-- CENTRE NAME -->

            <td>

                <%= archive.getCentreName() %>

            </td>


            <!-- ARCHIVE STATUS -->

            <td>

                <span class="archive-status
                    archive-status-<%= archive.getArchiveStatus().toLowerCase() %>">

                    <%= archive.getArchiveStatus() %>

                </span>

            </td>


            <!-- ARCHIVED AT -->

            <td>

                <%= archive.getArchivedAt() %>

            </td>


            <!-- RETENTION UNTIL -->

            <td>

                <%= archive.getRetentionUntil() %>

            </td>


            <!-- ACTION -->

            <td>

                <button type="button"
                        class="archive-view-button"
                        data-centre-code="<%= archive.getCentreCode() %>"
                        aria-label="View">

                    <i class="fa-solid fa-eye"></i>

                </button>

            </td>


        </tr>


        <%
            }

        } else {
        %>


        <!-- =================================================
             EMPTY STATE
             ================================================= -->

        <tr>

            <td colspan="7"
                class="archive-empty-state">

                <i class="fa-solid fa-box-open"
                   aria-hidden="true"></i>

                <span>

                    <%= LanguageManager.get(
                            "archive.empty",
                            session
                    ) %>

                </span>

            </td>

        </tr>


        <%
        }
        %>


    </tbody>


</table>