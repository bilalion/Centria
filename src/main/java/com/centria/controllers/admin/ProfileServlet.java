package com.centria.controllers.admin;

import com.centria.config.UploadConfig;
import com.centria.dao.ProfileDAO;
import com.centria.models.Profile;

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
 * 1. Get profile
 * 2. Update username / email / phone
 * 3. Upload avatar
 * 4. Get avatar
 * 5. Delete old avatar
 *
 * Password is handled separately.
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


    /* ==========================================================
       PROFILE DAO
    ========================================================== */

    private ProfileDAO profileDAO;


    /* ==========================================================
       INIT
    ========================================================== */

    @Override
    public void init() throws ServletException {

        profileDAO =
                new ProfileDAO();
    }


    /* ==========================================================
       GET
    ========================================================== */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String action =
                request.getParameter("action");


        /*
         * GET PROFILE
         */

        if ("getProfile".equalsIgnoreCase(action)) {

            getProfile(
                    request,
                    response
            );

            return;
        }


        /*
         * GET AVATAR
         */

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


    /* ==========================================================
       POST
    ========================================================== */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");


        String action =
                request.getParameter("action");


        /*
         * UPDATE PROFILE
         */

        if ("updateProfile".equalsIgnoreCase(action)) {

            updateProfile(
                    request,
                    response
            );

            return;
        }

        /*
 * ======================================================
 * CHANGE PASSWORD
 * ======================================================
 */

if ("changePassword".equalsIgnoreCase(action)) {

    changePassword(
            request,
            response
    );

    return;
}
        

        /*
         * UPLOAD AVATAR
         */

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


    /* ==========================================================
       GET PROFILE
    ========================================================== */

    private void getProfile(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        /*
         * SESSION
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
         * ADMIN ID FROM SESSION
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
         * GET PROFILE
         */

        Profile profile =
                profileDAO.getProfile(
                        adminId
                );


        if (profile == null) {

            sendJson(
                    response,
                    false,
                    "Profile not found."
            );

            return;
        }


        /*
         * RETURN PROFILE
         */

        sendProfileJson(
                response,
                profile
        );
    }


    /* ==========================================================
       UPDATE PROFILE
    ========================================================== */

    private void updateProfile(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


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
         * 03 - FORM DATA
         * ======================================================
         */

        String username =
                request.getParameter("username");


        String email =
                request.getParameter("email");


        String phone =
                request.getParameter("phone");


        /*
         * ======================================================
         * 04 - CLEAN DATA
         * ======================================================
         */

        username =
                username == null
                ? ""
                : username.trim();


        email =
                email == null
                ? ""
                : email.trim();


        phone =
                phone == null
                ? ""
                : phone.trim();


        /*
         * ======================================================
         * 05 - USERNAME REQUIRED
         * ======================================================
         */

        if (username.isEmpty()) {

            sendJson(
                    response,
                    false,
                    "Username is required."
            );

            return;
        }


        /*
         * ======================================================
         * 06 - EMAIL REQUIRED
         * ======================================================
         */

        if (email.isEmpty()) {

            sendJson(
                    response,
                    false,
                    "Email is required."
            );

            return;
        }


        /*
         * ======================================================
         * 07 - USERNAME DUPLICATE
         * ======================================================
         */

        if (profileDAO.usernameExists(
                username,
                adminId
        )) {

            sendJson(
                    response,
                    false,
                    "Username already exists."
            );

            return;
        }


        /*
         * ======================================================
         * 08 - EMAIL DUPLICATE
         * ======================================================
         */

        if (profileDAO.emailExists(
                email,
                adminId
        )) {

            sendJson(
                    response,
                    false,
                    "Email already exists."
            );

            return;
        }


        /*
         * ======================================================
         * 09 - UPDATE DATABASE
         * ======================================================
         */

        boolean updated =
                profileDAO.updateProfile(
                        adminId,
                        username,
                        email,
                        phone
                );


        if (!updated) {

            sendJson(
                    response,
                    false,
                    "Unable to update profile."
            );

            return;
        }


        /*
         * ======================================================
         * 10 - UPDATE SESSION USERNAME
         * ======================================================
         *
         * Important:
         *
         * If username changed, the current session must
         * also contain the new username.
         *
         * ======================================================
         */

        session.setAttribute(
                "adminUsername",
                username
        );


        /*
         * ======================================================
         * 11 - SUCCESS
         * ======================================================
         */

        sendJson(
                response,
                true,
                "Profile updated successfully."
        );
    }


    /* ==========================================================
       GET AVATAR
    ========================================================== */

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


    
    
    /* ==========================================================
   CHANGE PASSWORD
   ========================================================== */

private void changePassword(
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {


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
     * 03 - FORM DATA
     * ======================================================
     */

    String currentPassword =
            request.getParameter(
                    "currentPassword"
            );


    String newPassword =
            request.getParameter(
                    "newPassword"
            );


    String confirmPassword =
            request.getParameter(
                    "confirmPassword"
            );


    /*
     * ======================================================
     * 04 - NULL CHECK
     * ======================================================
     */

    currentPassword =
            currentPassword == null
            ? ""
            : currentPassword;


    newPassword =
            newPassword == null
            ? ""
            : newPassword;


    confirmPassword =
            confirmPassword == null
            ? ""
            : confirmPassword;


    /*
     * ======================================================
     * 05 - CURRENT PASSWORD REQUIRED
     * ======================================================
     */

    if (currentPassword.isEmpty()) {

        sendJson(
                response,
                false,
                "Current password is required."
        );

        return;
    }


    /*
     * ======================================================
     * 06 - NEW PASSWORD REQUIRED
     * ======================================================
     */

    if (newPassword.isEmpty()) {

        sendJson(
                response,
                false,
                "New password is required."
        );

        return;
    }


    /*
     * ======================================================
     * 07 - CONFIRM PASSWORD REQUIRED
     * ======================================================
     */

    if (confirmPassword.isEmpty()) {

        sendJson(
                response,
                false,
                "Please confirm the new password."
        );

        return;
    }


    /*
     * ======================================================
     * 08 - MINIMUM PASSWORD LENGTH
     * ======================================================
     */

    if (newPassword.length() < 8) {

        sendJson(
                response,
                false,
                "Password must contain at least 8 characters."
        );

        return;
    }


    /*
     * ======================================================
     * 09 - PASSWORD MATCH
     * ======================================================
     */

    if (!newPassword.equals(
            confirmPassword
    )) {

        sendJson(
                response,
                false,
                "Passwords do not match."
        );

        return;
    }


    /*
     * ======================================================
     * 10 - CHANGE PASSWORD
     * ======================================================
     *
     * DAO will:
     *
     * 1. Get old password hash
     * 2. Verify current password with BCrypt
     * 3. Generate new BCrypt hash
     * 4. Update password_hash
     *
     * ======================================================
     */

    boolean changed =
            profileDAO.changePassword(
                    adminId,
                    currentPassword,
                    newPassword
            );


    /*
     * ======================================================
     * 11 - FAILED
     * ======================================================
     */

    if (!changed) {

        sendJson(
                response,
                false,
                "Current password is incorrect."
        );

        return;
    }


    /*
     * ======================================================
     * 12 - SUCCESS
     * ======================================================
     */

    sendJson(
            response,
            true,
            "Password changed successfully."
    );
}
    
    /* ==========================================================
       UPLOAD AVATAR
    ========================================================== */

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
         */

        File uploadDirectory =
                UploadConfig.getAvatarDirectory(
                        projectRoot
                );


        /*
         * ======================================================
         * 13 - CREATE DIRECTORY
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
         * 20 - DATABASE FAILED
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


    /* ==========================================================
       DELETE OLD AVATAR
    ========================================================== */

    private void deleteOldAvatarFile(
            String oldAvatar,
            File projectRoot) {


        if (oldAvatar == null ||
            oldAvatar.trim().isEmpty()) {

            return;
        }


        String avatarWebPath =
                UploadConfig.getAvatarWebPath();


        if (!oldAvatar.startsWith(
                avatarWebPath + "/")) {

            return;
        }


        String oldFileName =
                oldAvatar.substring(
                        (avatarWebPath + "/").length()
                );


        if (oldFileName.contains("/") ||
            oldFileName.contains("\\") ||
            oldFileName.contains("..")) {

            return;
        }


        File uploadDirectory =
                UploadConfig.getAvatarDirectory(
                        projectRoot
                );


        File oldFile =
                new File(
                        uploadDirectory,
                        oldFileName
                );


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


    /* ==========================================================
       GET ADMIN ID FROM SESSION
    ========================================================== */

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

        } catch (NumberFormatException e) {

            return null;
        }
    }


    /* ==========================================================
       GET EXTENSION
    ========================================================== */

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


    /* ==========================================================
       EXTENSION FROM CONTENT TYPE
    ========================================================== */

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


    /* ==========================================================
       ALLOWED EXTENSIONS
    ========================================================== */

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


    /* ==========================================================
       PROFILE JSON
    ========================================================== */

    private void sendProfileJson(
            HttpServletResponse response,
            Profile profile)
            throws IOException {


        response.setContentType(
                "application/json"
        );


        response.setCharacterEncoding(
                "UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.print("{"
                + "\"success\":true,"
                + "\"data\":{"
                + "\"id\":" + profile.getId() + ","
                + "\"username\":\""
                + escapeJson(profile.getUsername())
                + "\","
                + "\"type\":\""
                + escapeJson(profile.getType())
                + "\","
                + "\"status\":\""
                + escapeJson(profile.getStatus())
                + "\","
                + "\"email\":\""
                + escapeJson(profile.getEmail())
                + "\","
                + "\"phone\":\""
                + escapeJson(profile.getPhone())
                + "\","
                + "\"avatar\":\""
                + escapeJson(profile.getAvatar())
                + "\","
                + "\"createdAt\":\""
                + escapeJson(
                        profile.getCreatedAt() == null
                        ? ""
                        : profile.getCreatedAt().toString()
                )
                + "\","
                + "\"lastLogin\":\""
                + escapeJson(
                        profile.getLastLogin() == null
                        ? ""
                        : profile.getLastLogin().toString()
                )
                + "\""
                + "}"
                + "}");

        out.flush();
    }


    /* ==========================================================
       JSON RESPONSE
    ========================================================== */

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
                escapeJson(data);


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


    /* ==========================================================
       ESCAPE JSON
    ========================================================== */

    private String escapeJson(
            String value) {


        if (value == null) {

            return "";
        }


        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}