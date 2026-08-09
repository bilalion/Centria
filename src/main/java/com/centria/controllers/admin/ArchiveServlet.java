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
 * Responsible for loading and displaying
 * archived centres.
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
    INIT
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
    GET
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

}