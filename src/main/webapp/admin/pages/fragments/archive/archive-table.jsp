<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.centria.models.Archive"%>
<%@page import="com.centria.language.LanguageManager"%>


<%

List<Archive> archivedCentres =
        (List<Archive>) request.getAttribute(
                "archivedCentres"
        );


/*
=====================================================
PAGINATION
=====================================================
*/

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
        if (
                archivedCentres != null
                &&
                !archivedCentres.isEmpty()
        ) {


            for (
                    Archive archive :
                    archivedCentres
            ) {
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

        }
        else {
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


<!-- =====================================================
     PAGINATION
     ===================================================== -->

<%
if (totalPages > 1) {
%>


<div id="archive-pagination-container"
     class="archive-pagination"
     dir="ltr">


    <!-- =================================================
         FIRST
         ================================================= -->

    <button type="button"
            class="pagination-button"
            onclick="changeArchivePage(1)"
            <%= currentPage <= 1 ? "disabled" : "" %>>

        <i class="fa-solid fa-angles-left"></i>

    </button>


    <!-- =================================================
         PREVIOUS
         ================================================= -->

    <button type="button"
            class="pagination-button"
            onclick="changeArchivePage(<%= currentPage - 1 %>)"
            <%= currentPage <= 1 ? "disabled" : "" %>>

        <i class="fa-solid fa-angle-left"></i>

    </button>


    <!-- =================================================
         PAGE NUMBERS
         ================================================= -->

    <%
    int startPage =
            Math.max(
                    1,
                    currentPage - 2
            );


    int endPage =
            Math.min(
                    totalPages,
                    currentPage + 2
            );


    for (
            int pageNumber = startPage;
            pageNumber <= endPage;
            pageNumber++
    ) {
    %>


        <button type="button"
                class="pagination-button
                <%= pageNumber == currentPage
                        ? "active"
                        : "" %>"
                onclick="changeArchivePage(<%= pageNumber %>)">

            <%= pageNumber %>

        </button>


    <%
    }
    %>


    <!-- =================================================
         NEXT
         ================================================= -->

    <button type="button"
            class="pagination-button"
            onclick="changeArchivePage(<%= currentPage + 1 %>)"
            <%= currentPage >= totalPages ? "disabled" : "" %>>

        <i class="fa-solid fa-angle-right"></i>

    </button>


    <!-- =================================================
         LAST
         ================================================= -->

    <button type="button"
            class="pagination-button"
            onclick="changeArchivePage(<%= totalPages %>)"
            <%= currentPage >= totalPages ? "disabled" : "" %>>

        <i class="fa-solid fa-angles-right"></i>

    </button>


</div>


<%
}
%>