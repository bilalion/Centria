package com.centria.controllers.admin;

import com.centria.dao.ProfileDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;


/**
 * ==========================================================
 * CENTRIA - PROFILE SERVLET
 * ==========================================================
 *
 * Responsibilities:
 *
 * 1. Load logged-in user profile
 * 2. Load avatar from database
 * 3. Upload avatar
 * 4. Save avatar physically in:
 *
 *      /uploads/avatars/
 *
 * 5. Save avatar path in:
 *
 *      super_admins.avatar
 *
 * ==========================================================
 */
@WebServlet("/ProfileServlet")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024
)

public class ProfileServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;


    /*
     * ==========================================================
     * DAO
     * ==========================================================
     */

    private ProfileDAO profileDAO;


    /*
     * ==========================================================
     * INIT
     * ==========================================================
     */

    @Override
    public void init() throws ServletException {

        profileDAO = new ProfileDAO();

    }


    /*
     * ==========================================================
     * GET
     * ==========================================================
     *
     * Used to load the logged-in user's avatar.
     *
     * ==========================================================
     */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {


        /*
         * ------------------------------------------------------
         * SESSION
         * ------------------------------------------------------
         */

        HttpSession session = request.getSession(false);


        if (session == null) {

            response.sendRedirect(
                    request.getContextPath() + "/superlogin.jsp"
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * USER ID
         * ------------------------------------------------------
         */

        Integer userId = getLoggedUserId(session);


        if (userId == null) {

            response.sendRedirect(
                    request.getContextPath() + "/superlogin.jsp"
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * GET AVATAR
         * ------------------------------------------------------
         */

        String avatar =
                profileDAO.getAvatar(userId);


        /*
         * ------------------------------------------------------
         * REQUEST ATTRIBUTE
         * ------------------------------------------------------
         */

        request.setAttribute(
                "avatar",
                avatar
        );


        /*
         * ------------------------------------------------------
         * RETURN PROFILE PAGE
         * ------------------------------------------------------
         */

        request.setAttribute(
                "section",
                "profile"
        );


        request.getRequestDispatcher(
                "/admin/super_admin_dashboard.jsp"
        ).forward(
                request,
                response
        );

    }


    /*
     * ==========================================================
     * POST
     * ==========================================================
     *
     * Actions:
     *
     * uploadAvatar
     *
     * ==========================================================
     */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {


        /*
         * ------------------------------------------------------
         * JSON RESPONSE
         * ------------------------------------------------------
         */

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );


        /*
         * ------------------------------------------------------
         * SESSION
         * ------------------------------------------------------
         */

        HttpSession session =
                request.getSession(false);


        if (session == null) {

            sendJson(
                    response,
                    false,
                    "Session expired."
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * USER ID
         * ------------------------------------------------------
         */

        Integer userId =
                getLoggedUserId(session);


        if (userId == null) {

            sendJson(
                    response,
                    false,
                    "User session not found."
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * ACTION
         * ------------------------------------------------------
         */

        String action =
                request.getParameter("action");


        if (
                action == null
                ||
                action.trim().isEmpty()
        ) {

            sendJson(
                    response,
                    false,
                    "Invalid action."
            );

            return;
        }


        /*
         * ======================================================
         * UPLOAD AVATAR
         * ======================================================
         */

        if ("uploadAvatar".equals(action)) {

            uploadAvatar(
                    request,
                    response,
                    userId
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * UNKNOWN ACTION
         * ------------------------------------------------------
         */

        sendJson(
                response,
                false,
                "Unknown action."
        );

    }


    /*
     * ==========================================================
     * GET LOGGED USER ID
     * ==========================================================
     *
     * CENTRIA session normally contains the logged-in user ID.
     *
     * ==========================================================
     */

    private Integer getLoggedUserId(
            HttpSession session
    ) {


        /*
         * ------------------------------------------------------
         * PRIMARY SESSION ATTRIBUTE
         * ------------------------------------------------------
         */

        Object userIdObject =
                session.getAttribute("userId");


        if (userIdObject == null) {

            /*
             * Compatibility with existing session names.
             */

            userIdObject =
                    session.getAttribute("superAdminId");
        }


        if (userIdObject == null) {

            userIdObject =
                    session.getAttribute("adminId");
        }


        if (userIdObject == null) {

            return null;
        }


        /*
         * ------------------------------------------------------
         * INTEGER
         * ------------------------------------------------------
         */

        if (userIdObject instanceof Integer) {

            return (Integer) userIdObject;
        }


        /*
         * ------------------------------------------------------
         * STRING / OTHER
         * ------------------------------------------------------
         */

        try {

            return Integer.parseInt(
                    userIdObject.toString()
            );

        } catch (NumberFormatException e) {

            return null;
        }

    }


    /*
     * ==========================================================
     * UPLOAD AVATAR
     * ==========================================================
     */

    private void uploadAvatar(
            HttpServletRequest request,
            HttpServletResponse response,
            int userId
    ) throws IOException, ServletException {


        /*
         * ------------------------------------------------------
         * GET FILE
         * ------------------------------------------------------
         */

        Part filePart =
                request.getPart("avatar");


        if (
                filePart == null
                ||
                filePart.getSize() <= 0
        ) {

            sendJson(
                    response,
                    false,
                    "Please select an image."
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * FILE SIZE
         * ------------------------------------------------------
         */

        long maxSize =
                5L * 1024L * 1024L;


        if (filePart.getSize() > maxSize) {

            sendJson(
                    response,
                    false,
                    "Image size must not exceed 5 MB."
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * CONTENT TYPE
         * ------------------------------------------------------
         */

        String contentType =
                filePart.getContentType();


        if (
                contentType == null
                ||
                !contentType.toLowerCase()
                        .startsWith("image/")
        ) {

            sendJson(
                    response,
                    false,
                    "Please select a valid image."
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * EXTENSION
         * ------------------------------------------------------
         */

        String extension =
                getExtension(filePart);


        if (extension == null) {

            sendJson(
                    response,
                    false,
                    "Unsupported image format."
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * UPLOAD DIRECTORY
         * ------------------------------------------------------
         *
         * Physical directory:
         *
         *      /uploads/avatars/
         *
         * ------------------------------------------------------
         */

        String uploadDirectory =
                getServletContext().getRealPath(
                        "/uploads/avatars"
                );


        if (uploadDirectory == null) {

            sendJson(
                    response,
                    false,
                    "Upload directory could not be resolved."
            );

            return;
        }


        /*
         * ------------------------------------------------------
         * CREATE DIRECTORY
         * ------------------------------------------------------
         */

        File directory =
                new File(uploadDirectory);


        if (!directory.exists()) {

            boolean created =
                    directory.mkdirs();


            if (!created && !directory.exists()) {

                sendJson(
                        response,
                        false,
                        "Unable to create upload directory."
                );

                return;
            }
        }


        /*
         * ------------------------------------------------------
         * UNIQUE FILE NAME
         * ------------------------------------------------------
         */

        String fileName =
                "avatar_"
                +
                userId
                +
                "_"
                +
                UUID.randomUUID().toString()
                +
                extension;


        /*
         * ------------------------------------------------------
         * PHYSICAL FILE
         * ------------------------------------------------------
         */

        File destination =
                new File(
                        directory,
                        fileName
                );


        /*
         * ------------------------------------------------------
         * SAVE FILE
         * ------------------------------------------------------
 */

        try {

            filePart.write(
                    destination.getAbsolutePath()
            );

        } catch (Exception e) {

            e.printStackTrace();

            sendJson(
                    response,
                    false,
                    "Unable to save avatar."
            );

            return;
        }


        /*
         * ======================================================
         * DATABASE PATH
         * ======================================================
         *
         * IMPORTANT:
         *
         * We save the WEB path in the database.
         *
         * Example:
         *
         * /uploads/avatars/avatar_1_xxxxx.jpg
         *
         * NOT:
         *
         * /Users/.../target/Centria/uploads/...
         *
         * ======================================================
         */

        String avatarPath =
                "/uploads/avatars/"
                +
                fileName;


        /*
         * ------------------------------------------------------
         * OLD AVATAR
         * ------------------------------------------------------
         */

        String oldAvatar =
                profileDAO.getAvatar(userId);


        /*
         * ------------------------------------------------------
         * DATABASE UPDATE
         * ------------------------------------------------------
         */

        boolean updated =
                profileDAO.updateAvatar(
                        userId,
                        avatarPath
                );


        /*
         * ------------------------------------------------------
         * DATABASE FAILED
         * ------------------------------------------------------
         */

        if (!updated) {

            /*
             * Remove newly uploaded file because
             * database update failed.
             */

            if (destination.exists()) {

                destination.delete();
            }


            sendJson(
                    response,
                    false,
                    "Unable to update avatar."
            );

            return;
        }


        /*
         * ======================================================
         * DELETE OLD AVATAR
         * ======================================================
         *
         * Only delete the previous avatar after the database
         * has successfully been updated.
         *
         * ======================================================
         */

        deleteOldAvatar(oldAvatar);


        /*
         * ------------------------------------------------------
         * SUCCESS
         * ------------------------------------------------------
 */

        sendJson(
                response,
                true,
                "Avatar updated successfully."
        );

    }


    /*
     * ==========================================================
     * GET EXTENSION
     * ==========================================================
     */

    private String getExtension(
            Part filePart
    ) {


        String submittedFileName =
                filePart.getSubmittedFileName();


        if (
                submittedFileName == null
                ||
                submittedFileName.trim().isEmpty()
        ) {

            return null;
        }


        String fileName =
                new File(
                        submittedFileName
                ).getName();


        int dot =
                fileName.lastIndexOf(".");


        if (
                dot <= 0
                ||
                dot >= fileName.length() - 1
        ) {

            return null;
        }


        String extension =
                fileName
                        .substring(dot)
                        .toLowerCase();


        /*
         * ------------------------------------------------------
         * ALLOWED EXTENSIONS
         * ------------------------------------------------------
         */

        if (
                ".jpg".equals(extension)
                ||
                ".jpeg".equals(extension)
                ||
                ".png".equals(extension)
                ||
                ".gif".equals(extension)
                ||
                ".webp".equals(extension)
        ) {

            return extension;
        }


        return null;

    }


    /*
     * ==========================================================
     * DELETE OLD AVATAR
     * ==========================================================
     */

    private void deleteOldAvatar(
            String oldAvatar
    ) {


        if (
                oldAvatar == null
                ||
                oldAvatar.trim().isEmpty()
        ) {

            return;
        }


        /*
         * ------------------------------------------------------
         * SECURITY
         * ------------------------------------------------------
         *
         * Only delete files located inside:
         *
         * /uploads/avatars/
         *
         * ------------------------------------------------------
         */

        String normalized =
                oldAvatar.replace(
                        "\\",
                        "/"
                );


        if (
                !normalized.startsWith(
                        "/uploads/avatars/"
                )
        ) {

            return;
        }


        String uploadDirectory =
                getServletContext().getRealPath(
                        "/uploads/avatars"
                );


        if (uploadDirectory == null) {

            return;
        }


        String fileName =
                normalized.substring(
                        "/uploads/avatars/".length()
                );


        /*
         * Prevent path traversal.
         */

        if (
                fileName.contains("/")
                ||
                fileName.contains("\\")
                ||
                fileName.contains("..")
        ) {

            return;
        }


        File oldFile =
                new File(
                        uploadDirectory,
                        fileName
                );


        if (
                oldFile.exists()
                &&
                oldFile.isFile()
        ) {

            try {

                oldFile.delete();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }


    /*
     * ==========================================================
     * SEND JSON
     * ==========================================================
     */

    private void sendJson(
            HttpServletResponse response,
            boolean success,
            String message
    ) throws IOException {


        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );


        PrintWriter writer =
                response.getWriter();


        /*
         * ------------------------------------------------------
         * Escape JSON message
         * ------------------------------------------------------
         */

        String safeMessage =
                message == null
                ?
                ""
                :
                message
                        .replace(
                                "\\",
                                "\\\\"
                        )
                        .replace(
                                "\"",
                                "\\\""
                        )
                        .replace(
                                "\r",
                                "\\r"
                        )
                        .replace(
                                "\n",
                                "\\n"
                        );


        writer.print(
                "{"
                +
                "\"success\":"
                +
                success
                +
                ","
                +
                "\"message\":\""
                +
                safeMessage
                +
                "\""
                +
                "}"
        );


        writer.flush();

    }

}