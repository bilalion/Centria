/*
 * File        : ArchiveServlet.java
 * Project     : CENTRIA
 *
 * Module      : Archive
 *
 * Description :
 * Handles archive centre requests.
 */

package com.centria.controllers.admin;

import com.centria.dao.ArchiveDAO;
import com.centria.models.Archive;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;


/**
 * Archive Servlet
 *
 * Responsible for:
 *
 * - Loading archived centres.
 * - Searching archived centres.
 * - Filtering archived centres.
 * - Pagination.
 * - Displaying archived centres.
 * - Handling bulk archive operations.
 */
@WebServlet("/ArchiveServlet")
public class ArchiveServlet extends HttpServlet {


    /*
    ==================================================
    DAO
    ==================================================
    */

    private ArchiveDAO archiveDAO;


    /*
    ==================================================
    01 - INIT
    ==================================================
    */

    @Override
    public void init()
            throws ServletException {


        archiveDAO =
                new ArchiveDAO();

    }


    /*
    ==================================================
    02 - GET ARCHIVED CENTRES
    ==================================================
    */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {


        
        /*
==================================================
VIEW ARCHIVE DETAILS
==================================================
*/

String action =
        request.getParameter("action");


if (
        "view".equalsIgnoreCase(action)
) {

    String centreCode =
            request.getParameter("centreCode");


    if (
            centreCode == null
            ||
            centreCode.trim().isEmpty()
    ) {

        response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
        );

        response.getWriter().write(
                "CENTRE_CODE_REQUIRED"
        );

        return;
    }


    Archive archive =
            archiveDAO.getArchiveForView(
                    centreCode.trim()
            );


    if (archive == null) {

        response.setStatus(
                HttpServletResponse.SC_NOT_FOUND
        );

        request.setAttribute(
                "archive",
                null
        );

    }
    else {

        request.setAttribute(
                "archive",
                archive
        );

    }


    request.getRequestDispatcher(
            "/admin/pages/fragments/archive/archive-view.jsp"
    ).forward(
            request,
            response
    );

    return;
}
        
        /*
        ==================================================
        LOAD / SEARCH / FILTER / PAGINATION
        ==================================================
        */


        /*
        --------------------------------------------------
        SEARCH
        --------------------------------------------------
        */

        String search =
                request.getParameter(
                        "search"
                );


        /*
        --------------------------------------------------
        STATUS
        --------------------------------------------------
        */

        String status =
                request.getParameter(
                        "status"
                );


        /*
        --------------------------------------------------
        DEFAULT STATUS
        --------------------------------------------------

        If no status is provided:

        ALL
        =
        ARCHIVED + PENDING_DELETE
        --------------------------------------------------
        */

        if (
                status == null
                ||
                status.trim().isEmpty()
        ) {

            status = "ALL";

        }


        /*
        ==================================================
        PAGE
        ==================================================
        */

        int page = 1;


        try {

            if (
                    request.getParameter(
                            "page"
                    ) != null
            ) {

                page =
                        Integer.parseInt(
                                request.getParameter(
                                        "page"
                                )
                        );

            }

        }
        catch (Exception e) {

            page = 1;

        }


        /*
        --------------------------------------------------
        PAGE MUST NEVER BE LESS THAN 1
        --------------------------------------------------
        */

        if (page < 1) {

            page = 1;

        }


        /*
        ==================================================
        PAGE SIZE
        ==================================================

        Same approach used by Centres.

        Current value:
        4 records per page.
        ==================================================
        */

        int pageSize = 4;


        /*
        ==================================================
        LOAD ARCHIVE
        ==================================================
        */

        List<Archive> archivedCentres =
                archiveDAO.getArchivedCentres(
                        search,
                        status,
                        page,
                        pageSize
                );


        /*
        ==================================================
        COUNT FILTERED RESULTS
        ==================================================

        IMPORTANT:

        Count must use the SAME:

        - search
        - status

        Otherwise pagination would be incorrect.
        ==================================================
        */

        int totalArchives =
                archiveDAO.countArchivedCentres(
                        search,
                        status
                );


        /*
        ==================================================
        CALCULATE TOTAL PAGES
        ==================================================
        */

        int totalPages =
                (int) Math.ceil(
                        (double) totalArchives
                        /
                        pageSize
                );


        /*
        --------------------------------------------------
        NO RESULTS
        --------------------------------------------------

        Keep one logical page for the JSP.
        --------------------------------------------------
        */

        if (totalPages < 1) {

            totalPages = 1;

        }


        /*
        ==================================================
        CORRECT PAGE IF OUT OF RANGE
        ==================================================

        Example:

        User is on page 3.

        Then search/filter changes the result
        to only one page.

        We automatically return to page 1.
        ==================================================
        */

        if (page > totalPages) {

            page = totalPages;


            archivedCentres =
                    archiveDAO.getArchivedCentres(
                            search,
                            status,
                            page,
                            pageSize
                    );

        }


        /*
        ==================================================
        SEND ARCHIVE DATA TO JSP
        ==================================================
        */

        request.setAttribute(
                "archivedCentres",
                archivedCentres
        );


        /*
        ==================================================
        SEND PAGINATION DATA
        ==================================================
        */

        request.setAttribute(
                "currentPage",
                page
        );


        request.setAttribute(
                "totalPages",
                totalPages
        );


        request.setAttribute(
                "totalArchives",
                totalArchives
        );


        /*
        ==================================================
        ARCHIVE STATISTICS
        ==================================================
        */

        int archivedCount =
                archiveDAO.countArchivedCentres();


        int pendingDeleteCount =
                archiveDAO.countPendingDeleteCentres();


        int deletedCount =
                archiveDAO.countDeletedCentres();


        /*
        ==================================================
        SEND STATISTICS TO JSP
        ==================================================
        */

        request.setAttribute(
                "archivedCount",
                archivedCount
        );


        request.setAttribute(
                "pendingDeleteCount",
                pendingDeleteCount
        );


        request.setAttribute(
                "deletedCount",
                deletedCount
        );


        /*
        ==================================================
        LAST ARCHIVE OPERATION
        ==================================================
        */

        java.util.Map<String, Object>
                lastArchiveOperation =
                archiveDAO.getLastArchiveOperation();


        /*
        ==================================================
        SEND LAST OPERATION TO JSP
        ==================================================
        */

        request.setAttribute(
                "lastArchiveOperation",
                lastArchiveOperation
        );


        /*
        ==================================================
        AJAX REQUEST
        ==================================================

        When Archive is loaded through AJAX,
        return only the archive table fragment.

        This follows the same pattern used
        by CentreServlet.
        ==================================================
        */

        if (
                "true".equals(
                        request.getParameter(
                                "ajax"
                        )
                )
        ) {


            request.getRequestDispatcher(
                    "/admin/pages/fragments/archive/archive-table.jsp"
            ).forward(
                    request,
                    response
            );


            return;

        }


        /*
        ==================================================
        OPEN ARCHIVE PAGE
        ==================================================
        */

        request.getRequestDispatcher(
                "/admin/pages/archive.jsp"
        ).forward(
                request,
                response
        );

    }


    /*
    ==================================================
    03 - APPLY BULK OPERATION
    ==================================================
    */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {


        /*
        --------------------------------------------------
        Read action
        --------------------------------------------------
        */

        String action =
                request.getParameter(
                        "action"
                );


        /*
        --------------------------------------------------
        Only APPLY is supported here
        --------------------------------------------------
        */

        if (
                action == null
                ||
                !"apply".equalsIgnoreCase(
                        action
                )
        ) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );


            response.getWriter().write(
                    "INVALID_ACTION"
            );


            return;

        }


        /*
        --------------------------------------------------
        Read selected operation
        --------------------------------------------------
        */

        String operation =
                request.getParameter(
                        "operation"
                );


        /*
        ==================================================
        Validate operation
        ==================================================

        Supported operations:

        - RESTORE
        - DELETE
        ==================================================
        */

        if (
                operation == null
                ||
                (
                        !"RESTORE".equalsIgnoreCase(
                                operation
                        )
                        &&
                        !"DELETE".equalsIgnoreCase(
                                operation
                        )
                )
        ) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );


            response.getWriter().write(
                    "INVALID_OPERATION"
            );


            return;

        }


        /*
        --------------------------------------------------
        Read selected centre codes
        --------------------------------------------------
        */

        String[] centreCodes =
                request.getParameterValues(
                        "centreCodes"
                );


        /*
        --------------------------------------------------
        At least one centre must be selected
        --------------------------------------------------
        */

        if (
                centreCodes == null
                ||
                centreCodes.length == 0
        ) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );


            response.getWriter().write(
                    "NO_CENTRES_SELECTED"
            );


            return;

        }


        /*
        ==================================================
        RESTORE / DELETE
        ==================================================
        */

        int successCount = 0;

        int failedCount = 0;


        for (
                String centreCode :
                centreCodes
        ) {


            /*
            --------------------------------------------------
            Ignore empty values
            --------------------------------------------------
            */

            if (
                    centreCode == null
                    ||
                    centreCode.trim().isEmpty()
            ) {

                failedCount++;

                continue;

            }


            /*
            ==================================================
            APPLY SELECTED OPERATION
            ==================================================
            */

            boolean operationSuccess;


            if (
                    "RESTORE".equalsIgnoreCase(
                            operation
                    )
            ) {


                /*
                --------------------------------------------------
                RESTORE ONE CENTRE
                --------------------------------------------------
                */

                operationSuccess =
                        archiveDAO.restoreCentre(
                                centreCode.trim()
                        );

            }
            else {


                /*
                --------------------------------------------------
                DELETE ONE CENTRE
                --------------------------------------------------
                */

                operationSuccess =
                        archiveDAO.deleteCentre(
                                centreCode.trim()
                        );

            }


            /*
            ==================================================
            RESULT
            ==================================================
            */

            if (operationSuccess) {

                successCount++;

            }
            else {

                failedCount++;

            }

        }


        /*
        ==================================================
        UPDATE ARCHIVE OPERATION
        ==================================================
        */

        if (successCount > 0) {


            HttpSession session =
                    request.getSession(false);


            String adminUsername =
                    session != null
                    ? (String)
                        session.getAttribute(
                                "adminUsername"
                        )
                    : null;


            if (
                    adminUsername != null
                    &&
                    !adminUsername.trim().isEmpty()
            ) {


                archiveDAO.updateArchiveOperation(
                        adminUsername,
                        operation,
                        successCount
                );

            }

        }


        /*
        ==================================================
        SEND OPERATION RESULT
        ==================================================
        */

        response.setContentType(
                "text/plain;charset=UTF-8"
        );


        response.getWriter().write(
                "SUCCESS="
                +
                successCount
                +
                ";FAILED="
                +
                failedCount
        );

    }

}