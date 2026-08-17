package com.centria.controllers.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * ==========================================================
 * CENTRIA - AVATAR SERVLET
 * ==========================================================
 *
 * Serves persistent avatar files stored outside webapp:
 *
 * uploads/
 *     avatars/
 *
 * Browser URL:
 *
 * /Centria/uploads/avatars/avatar_xxx.jpg
 *
 * Physical location:
 *
 * Tomcat/webapps/uploads/avatars/avatar_xxx.jpg
 *
 * ==========================================================
 */

@WebServlet("/uploads/avatars/*")
public class AvatarServlet extends HttpServlet {


    /*
     * ==========================================================
     * GET AVATAR
     * ==========================================================
     */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        /*
         * ======================================================
         * FILE NAME
         * ======================================================
         */

        String pathInfo =
                request.getPathInfo();


        if (pathInfo == null ||
            pathInfo.trim().isEmpty()) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );

            return;
        }


        /*
         * ======================================================
         * REMOVE FIRST /
         * ======================================================
         */

        String fileName =
                pathInfo.startsWith("/")
                ? pathInfo.substring(1)
                : pathInfo;


        /*
         * ======================================================
         * SECURITY
         * ======================================================
         *
         * Only allow a single file name.
         *
         * No:
         *
         * ../
         * \
         * /
         *
         * ======================================================
         */

        if (fileName.contains("/")
                || fileName.contains("\\")
                || fileName.contains("..")) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            return;
        }


        /*
         * ======================================================
         * APPLICATION ROOT
         * ======================================================
         */

        String applicationRoot =
                getServletContext()
                        .getRealPath("/");


        if (applicationRoot == null ||
            applicationRoot.trim().isEmpty()) {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            return;
        }


        /*
         * ======================================================
         * PROJECT / TOMCAT WEBAPPS ROOT
         * ======================================================
         *
         * Example:
         *
         * Tomcat/webapps/Centria/
         *
         * parent:
         *
         * Tomcat/webapps/
         *
         * uploads/
         *     avatars/
         *
         * ======================================================
         */

        File applicationDirectory =
                new File(
                        applicationRoot
                );


        File projectRoot =
                applicationDirectory.getParentFile();


        if (projectRoot == null) {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            return;
        }


        /*
         * ======================================================
         * AVATAR DIRECTORY
         * ======================================================
         */

        File avatarDirectory =
                new File(
                        projectRoot,
                        "uploads"
                                + File.separator
                                + "avatars"
                );


        /*
         * ======================================================
         * AVATAR FILE
         * ======================================================
         */

        File avatarFile =
                new File(
                        avatarDirectory,
                        fileName
                );


        /*
         * ======================================================
         * FILE EXISTS?
         * ======================================================
         */

        if (!avatarFile.exists()
                || !avatarFile.isFile()) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );

            return;
        }


        /*
         * ======================================================
         * CONTENT TYPE
         * ======================================================
         */

        String contentType =
                getServletContext()
                        .getMimeType(
                                avatarFile.getName()
                        );


        if (contentType == null) {

            contentType =
                    Files.probeContentType(
                            avatarFile.toPath()
                    );
        }


        if (contentType == null) {

            contentType =
                    "application/octet-stream";
        }


        /*
         * ======================================================
         * ALLOWED IMAGE TYPES
         * ======================================================
         */

        if (!contentType.startsWith("image/")) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );

            return;
        }


        /*
         * ======================================================
         * RESPONSE
         * ======================================================
         */

        response.setContentType(
                contentType
        );


        response.setContentLengthLong(
                avatarFile.length()
        );


        /*
         * ======================================================
         * CACHE
         * ======================================================
         */

        response.setHeader(
                "Cache-Control",
                "public, max-age=86400"
        );


        /*
         * ======================================================
         * SEND FILE
         * ======================================================
         */

        try (
                InputStream input =
                        Files.newInputStream(
                                avatarFile.toPath()
                        );

                OutputStream output =
                        response.getOutputStream()
        ) {

            byte[] buffer =
                    new byte[8192];

            int bytesRead;


            while (
                    (bytesRead =
                            input.read(buffer))
                    != -1
            ) {

                output.write(
                        buffer,
                        0,
                        bytesRead
                );
            }


            output.flush();
        }
    }
}