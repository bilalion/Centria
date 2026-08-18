package com.centria.config;

import java.io.File;

/**
 * ==========================================================
 * CENTRIA - UPLOAD CONFIGURATION
 * ==========================================================
 *
 * Persistent uploaded files:
 *
 * Centria/
 *     uploads/
 *         avatars/
 *
 * Database stores only the WEB path:
 *
 * /uploads/avatars/filename.jpg
 *
 * ==========================================================
 */
public final class UploadConfig {


    /*
     * ==========================================================
     * PRIVATE CONSTRUCTOR
     * ==========================================================
     */

    private UploadConfig() {
    }


    /*
     * ==========================================================
     * UPLOAD ROOT
     * ==========================================================
     */

    private static final String UPLOAD_ROOT =
            "uploads";


    /*
     * ==========================================================
     * AVATAR DIRECTORY
     * ==========================================================
     */

    private static final String AVATAR_DIRECTORY =
            "avatars";


    /*
     * ==========================================================
     * AVATAR WEB PATH
     * ==========================================================
     *
     * This path is stored in database.
     *
     * ==========================================================
     */

    private static final String AVATAR_WEB_PATH =
            "/uploads/avatars";


    /*
     * ==========================================================
     * GET UPLOAD ROOT
     * ==========================================================
     */

    public static File getUploadRoot(
            File projectRoot) {

        return new File(
                projectRoot,
                UPLOAD_ROOT
        );
    }


    /*
     * ==========================================================
     * GET AVATAR DIRECTORY
     * ==========================================================
     */

    public static File getAvatarDirectory(
            File projectRoot) {

        return new File(
                getUploadRoot(projectRoot),
                AVATAR_DIRECTORY
        );
    }


    /*
     * ==========================================================
     * GET AVATAR WEB PATH
     * ==========================================================
     */

    public static String getAvatarWebPath() {

        return AVATAR_WEB_PATH;
    }


    /*
     * ==========================================================
     * ENSURE AVATAR DIRECTORY
     * ==========================================================
     */

    public static boolean ensureAvatarDirectory(
            File projectRoot) {

        File avatarDirectory =
                getAvatarDirectory(
                        projectRoot
                );

        if (avatarDirectory.exists()) {
            return avatarDirectory.isDirectory();
        }
        return avatarDirectory.mkdirs();
    }
}