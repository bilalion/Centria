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

import java.io.IOException;
import java.util.List;


/**
 * Archive Servlet
 *
 * Responsible for:
 *
 * - Loading archived centres.
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
        --------------------------------------------------
        Load archived centres
        --------------------------------------------------
        */

        List<Archive> archivedCentres =
                archiveDAO.getArchivedCentres();


        /*
        --------------------------------------------------
        Send data to JSP
        --------------------------------------------------
        */

        request.setAttribute(
                "archivedCentres",
                archivedCentres
        );

        
        /*
--------------------------------------------------
ARCHIVE STATISTICS
--------------------------------------------------
*/

int archivedCount =
        archiveDAO.countArchivedCentres();


int pendingDeleteCount =
        archiveDAO.countPendingDeleteCentres();


int deletedCount =
        archiveDAO.countDeletedCentres();


/*
--------------------------------------------------
SEND STATISTICS TO JSP
--------------------------------------------------
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
--------------------------------------------------
AJAX REQUEST
--------------------------------------------------

When Archive is loaded through AJAX,
return only the archive table fragment.

This follows the same pattern used
by CentreServlet.
--------------------------------------------------
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
        --------------------------------------------------
        Open Archive page
        --------------------------------------------------
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
                !"apply".equalsIgnoreCase(action)
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
        --------------------------------------------------
        Only RESTORE is handled for now
        --------------------------------------------------
        */

        if (
                operation == null
                ||
                !"RESTORE".equalsIgnoreCase(operation)
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
        --------------------------------------------------
        Restore selected centres
        --------------------------------------------------
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
            --------------------------------------------------
            Restore one centre
            --------------------------------------------------
            */

            boolean restored =
                    archiveDAO.restoreCentre(
                            centreCode.trim()
                    );


            /*
            --------------------------------------------------
            Result
            --------------------------------------------------
            */

            if (restored) {

                successCount++;

            }
            else {

                failedCount++;

            }

        }


        /*
        --------------------------------------------------
        Send operation result
        --------------------------------------------------
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