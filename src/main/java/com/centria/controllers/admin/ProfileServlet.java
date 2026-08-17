package com.centria.controllers.admin;

import com.centria.config.UploadConfig;
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
 * 1. Upload avatar
 * 2. Save avatar physically in:
 *
 *    Centria/uploads/avatars/
 *
 * 3. Save only the WEB path in database
 *
 *    /uploads/avatars/filename.jpg
 *
 * 4. Return saved avatar
 *
 * 5. Delete old avatar after successful database update
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


    /*
     * ==========================================================
     * PROFILE DAO
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

        profileDAO =
                new ProfileDAO();
    }


    /*
     * ==========================================================
     * GET
     * ==========================================================
     *
     * Supported:
     *
     * /ProfileServlet?action=getAvatar
     *
     * ==========================================================
     */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String action =
                request.getParameter("action");


        if ("getAvatar".equalsIgnoreCase(action)) {

            getAvatar(
                    request,
                    response
            );

            return;
        }


        sendJson(
                response,
                false,
                "Invalid action."
        );
    }


    /*
     * ==========================================================
     * POST
     * ==========================================================
     *
     * Supported:
     *
     * action=uploadAvatar
     *
     * ==========================================================
     */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");


        String action =
                request.getParameter("action");


        if ("uploadAvatar".equalsIgnoreCase(action)) {

            uploadAvatar(
                    request,
                    response
            );

            return;
        }


        sendJson(
                response,
                false,
                "Invalid action."
        );
    }


    /*
     * ==========================================================
     * GET AVATAR
     * ==========================================================
     */

    private void getAvatar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


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


        Integer adminId =
                getAdminId(session);


        if (adminId == null) {

            sendJson(
                    response,
                    false,
                    "User session not found."
            );

            return;
        }


        String avatar =
                profileDAO.getAvatar(
                        adminId
                );


        if (avatar == null ||
            avatar.trim().isEmpty()) {

            sendJson(
                    response,
                    true,
                    ""
            );

            return;
        }


        sendJson(
                response,
                true,
                avatar
        );
    }


    /*
     * ==========================================================
     * UPLOAD AVATAR
     * ==========================================================
     */

    private void uploadAvatar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException,
                   ServletException {


        /*
         * ======================================================
         * 01 - SESSION
         * ======================================================
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
         * ======================================================
         * 02 - ADMIN ID
         * ======================================================
         */

        Integer adminId =
                getAdminId(session);


        if (adminId == null) {

            sendJson(
                    response,
                    false,
                    "User session not found."
            );

            return;
        }


        /*
         * ======================================================
         * 03 - FILE
         * ======================================================
         */

        Part filePart =
                request.getPart("avatar");


        if (filePart == null ||
            filePart.getSize() <= 0) {

            sendJson(
                    response,
                    false,
                    "No avatar file was selected."
            );

            return;
        }


        /*
         * ======================================================
         * 04 - FILE SIZE
         * ======================================================
         */

        if (filePart.getSize() >
                5L * 1024L * 1024L) {

            sendJson(
                    response,
                    false,
                    "Image size must not exceed 5 MB."
            );

            return;
        }


        /*
         * ======================================================
         * 05 - CONTENT TYPE
         * ======================================================
         */

        String contentType =
                filePart.getContentType();


        if (contentType == null ||
            !contentType.toLowerCase()
                    .startsWith("image/")) {

            sendJson(
                    response,
                    false,
                    "Please select a valid image."
            );

            return;
        }


        /*
         * ======================================================
         * 06 - ORIGINAL FILE NAME
         * ======================================================
         */

        String originalName =
                filePart.getSubmittedFileName();


        /*
         * ======================================================
         * 07 - EXTENSION
         * ======================================================
         */

        String extension =
                getExtension(
                        originalName
                );


        if (extension.isEmpty()) {

            extension =
                    getExtensionFromContentType(
                            contentType
                    );
        }


        /*
         * ======================================================
         * 08 - ALLOWED EXTENSIONS
         * ======================================================
         */

        if (!isAllowedExtension(extension)) {

            sendJson(
                    response,
                    false,
                    "Unsupported image format."
            );

            return;
        }


        /*
         * ======================================================
         * 09 - UNIQUE FILE NAME
         * ======================================================
         */

        String fileName =
                "avatar_"
                + adminId
                + "_"
                + UUID.randomUUID()
                + extension;


        /*
         * ======================================================
         * 10 - APPLICATION DIRECTORY
         * ======================================================
         *
         * getRealPath("/") gives:
         *
         * Tomcat/webapps/Centria/
         *
         * We use its parent:
         *
         * Tomcat/webapps/
         *
         * Then UploadConfig creates:
         *
         * Tomcat/webapps/uploads/avatars/
         *
         * ======================================================
         */

        String applicationRoot =
                getServletContext()
                        .getRealPath("/");


        if (applicationRoot == null ||
            applicationRoot.trim().isEmpty()) {

            sendJson(
                    response,
                    false,
                    "Unable to resolve application directory."
            );

            return;
        }


        File applicationDirectory =
                new File(
                        applicationRoot
                );


        /*
         * ======================================================
         * 11 - PROJECT ROOT
         * ======================================================
         */

        File projectRoot =
                applicationDirectory.getParentFile();


        if (projectRoot == null) {

            sendJson(
                    response,
                    false,
                    "Unable to resolve upload root directory."
            );

            return;
        }


        /*
         * ======================================================
         * 12 - AVATAR DIRECTORY
         * ======================================================
         *
         * UploadConfig controls the location.
         *
         * ======================================================
         */

        File uploadDirectory =
                UploadConfig.getAvatarDirectory(
                        projectRoot
                );


        /*
         * ======================================================
         * 13 - CREATE AVATAR DIRECTORY
         * ======================================================
         */

        if (!UploadConfig.ensureAvatarDirectory(
                projectRoot
        )) {

            sendJson(
                    response,
                    false,
                    "Unable to create avatar directory."
            );

            return;
        }


        /*
         * ======================================================
         * 14 - FINAL FILE
         * ======================================================
         */

        File avatarFile =
                new File(
                        uploadDirectory,
                        fileName
                );


        /*
         * ======================================================
         * 15 - SAVE FILE
         * ======================================================
         */

        filePart.write(
                avatarFile.getAbsolutePath()
        );


        /*
         * ======================================================
         * 16 - VERIFY FILE
         * ======================================================
         */

        if (!avatarFile.exists() ||
            avatarFile.length() <= 0) {

            sendJson(
                    response,
                    false,
                    "Avatar file could not be saved."
            );

            return;
        }


        /*
         * ======================================================
         * 17 - DATABASE WEB PATH
         * ======================================================
         *
         * Database stores:
         *
         * /uploads/avatars/filename.jpg
         *
         * NOT:
         *
         * /Users/...
         *
         * NOT:
         *
         * Tomcat/webapps/...
         *
         * ======================================================
         */

        String databasePath =
                UploadConfig.getAvatarWebPath()
                + "/"
                + fileName;


        /*
         * ======================================================
         * 18 - OLD AVATAR
         * ======================================================
         */

        String oldAvatar =
                profileDAO.getAvatar(
                        adminId
                );


        /*
         * ======================================================
         * 19 - UPDATE DATABASE
         * ======================================================
         */

        boolean updated =
                profileDAO.updateAvatar(
                        adminId,
                        databasePath
                );


        /*
         * ======================================================
         * 20 - DATABASE UPDATE FAILED
         * ======================================================
         */

        if (!updated) {

            if (avatarFile.exists()) {

                avatarFile.delete();
            }


            sendJson(
                    response,
                    false,
                    "Unable to save avatar."
            );

            return;
        }


        /*
         * ======================================================
         * 21 - DELETE OLD AVATAR
         * ======================================================
         */

        deleteOldAvatarFile(
                oldAvatar,
                projectRoot
        );


        /*
         * ======================================================
         * 22 - SUCCESS
         * ======================================================
         */

        sendJson(
                response,
                true,
                databasePath
        );
    }


    /*
     * ==========================================================
     * DELETE OLD AVATAR FILE
     * ==========================================================
     *
     * This function:
     *
     * 1. Validates the old database path
     * 2. Gets only the filename
     * 3. Uses UploadConfig for physical directory
     * 4. Deletes the old physical file
     *
     * ==========================================================
     */

    private void deleteOldAvatarFile(
            String oldAvatar,
            File projectRoot) {


        /*
         * ======================================================
         * 01 - EMPTY PATH
         * ======================================================
         */

        if (oldAvatar == null ||
            oldAvatar.trim().isEmpty()) {

            return;
        }


        /*
         * ======================================================
         * 02 - EXPECTED WEB PATH
         * ======================================================
         */

        String avatarWebPath =
                UploadConfig.getAvatarWebPath();


        /*
         * ======================================================
         * 03 - SECURITY CHECK
         * ======================================================
         */

        if (!oldAvatar.startsWith(
                avatarWebPath + "/")) {

            return;
        }


        /*
         * ======================================================
         * 04 - GET FILE NAME
         * ======================================================
         */

        String oldFileName =
                oldAvatar.substring(
                        (avatarWebPath + "/").length()
                );


        /*
         * ======================================================
         * 05 - SECURITY CHECK
         * ======================================================
         */

        if (oldFileName.contains("/") ||
            oldFileName.contains("\\") ||
            oldFileName.contains("..")) {

            return;
        }


        /*
         * ======================================================
         * 06 - GET AVATAR DIRECTORY
         * ======================================================
         */

        File uploadDirectory =
                UploadConfig.getAvatarDirectory(
                        projectRoot
                );


        /*
         * ======================================================
         * 07 - OLD FILE
         * ======================================================
         */

        File oldFile =
                new File(
                        uploadDirectory,
                        oldFileName
                );


        /*
         * ======================================================
         * 08 - DELETE
         * ======================================================
         */

        if (oldFile.exists() &&
            oldFile.isFile()) {

            boolean deleted =
                    oldFile.delete();


            if (!deleted) {

                System.err.println(
                        "[CENTRIA PROFILE] "
                        + "Unable to delete old avatar: "
                        + oldFile.getAbsolutePath()
                );
            }
        }
    }


    /*
     * ==========================================================
     * GET ADMIN ID
     * ==========================================================
     */

    private Integer getAdminId(
            HttpSession session) {


        Object value =
                session.getAttribute(
                        "adminId"
                );


        if (value == null) {

            return null;
        }


        if (value instanceof Integer) {

            return (Integer) value;
        }


        try {

            return Integer.parseInt(
                    value.toString()
            );

        }
        catch (NumberFormatException e) {

            return null;
        }
    }


    /*
     * ==========================================================
     * GET EXTENSION
     * ==========================================================
     */

    private String getExtension(
            String fileName) {


        if (fileName == null ||
            fileName.trim().isEmpty()) {

            return "";
        }


        int dot =
                fileName.lastIndexOf(".");


        if (dot < 0 ||
            dot == fileName.length() - 1) {

            return "";
        }


        return fileName
                .substring(dot)
                .toLowerCase();
    }


    /*
     * ==========================================================
     * EXTENSION FROM CONTENT TYPE
     * ==========================================================
     */

    private String getExtensionFromContentType(
            String contentType) {


        if (contentType == null) {

            return "";
        }


        String type =
                contentType.toLowerCase();


        if ("image/jpeg".equals(type)) {

            return ".jpg";
        }


        if ("image/png".equals(type)) {

            return ".png";
        }


        if ("image/gif".equals(type)) {

            return ".gif";
        }


        if ("image/webp".equals(type)) {

            return ".webp";
        }


        return "";
    }


    /*
     * ==========================================================
     * ALLOWED EXTENSIONS
     * ==========================================================
     */

    private boolean isAllowedExtension(
            String extension) {


        if (extension == null) {

            return false;
        }


        return
                ".jpg".equals(extension)
                ||
                ".jpeg".equals(extension)
                ||
                ".png".equals(extension)
                ||
                ".gif".equals(extension)
                ||
                ".webp".equals(extension);
    }


    /*
     * ==========================================================
     * JSON RESPONSE
     * ==========================================================
     */

    private void sendJson(
            HttpServletResponse response,
            boolean success,
            String data)
            throws IOException {


        response.setContentType(
                "application/json"
        );


        response.setCharacterEncoding(
                "UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        String safeData =
                data == null
                ?
                ""
                :
                data
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\r", "\\r")
                        .replace("\n", "\\n");


        out.print(
                "{"
                + "\"success\":"
                + success
                + ","
                + "\"data\":\""
                + safeData
                + "\""
                + "}"
        );


        out.flush();
    }
}