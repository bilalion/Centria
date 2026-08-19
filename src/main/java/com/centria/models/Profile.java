package com.centria.models;

import java.sql.Timestamp;


/**
 * ==========================================================
 * CENTRIA
 * Profile Model
 * ==========================================================
 *
 * Represents the profile information of a Super Admin.
 *
 * Database table:
 * super_admins
 *
 * Editable:
 * - username
 * - email
 * - phone
 *
 * Read-only:
 * - id
 * - type
 * - status
 * - avatar
 * - created_at
 * - last_login
 *
 * Password is intentionally NOT included here.
 *
 * ==========================================================
 */
public class Profile {


    /* ==========================================================
       01 - FIELDS
    ========================================================== */

    private int id;

    private String username;

    private String type;

    private String status;

    private String email;

    private String phone;

    private String avatar;

    private Timestamp createdAt;

    private Timestamp lastLogin;


    /* ==========================================================
       02 - EMPTY CONSTRUCTOR
    ========================================================== */

    public Profile() {
    }


    /* ==========================================================
       03 - FULL CONSTRUCTOR
    ========================================================== */

    public Profile(
            int id,
            String username,
            String type,
            String status,
            String email,
            String phone,
            String avatar,
            Timestamp createdAt,
            Timestamp lastLogin
    ) {

        this.id = id;

        this.username = username;

        this.type = type;

        this.status = status;

        this.email = email;

        this.phone = phone;

        this.avatar = avatar;

        this.createdAt = createdAt;

        this.lastLogin = lastLogin;
    }


    /* ==========================================================
       04 - ID
    ========================================================== */

    public int getId() {

        return id;
    }


    public void setId(int id) {

        this.id = id;
    }


    /* ==========================================================
       05 - USERNAME
    ========================================================== */

    public String getUsername() {

        return username;
    }


    public void setUsername(String username) {

        this.username = username;
    }


    /* ==========================================================
       06 - TYPE
    ========================================================== */

    public String getType() {

        return type;
    }


    public void setType(String type) {

        this.type = type;
    }


    /* ==========================================================
       07 - STATUS
    ========================================================== */

    public String getStatus() {

        return status;
    }


    public void setStatus(String status) {

        this.status = status;
    }


    /* ==========================================================
       08 - EMAIL
    ========================================================== */

    public String getEmail() {

        return email;
    }


    public void setEmail(String email) {

        this.email = email;
    }


    /* ==========================================================
       09 - PHONE
    ========================================================== */

    public String getPhone() {

        return phone;
    }


    public void setPhone(String phone) {

        this.phone = phone;
    }


    /* ==========================================================
       10 - AVATAR
    ========================================================== */

    public String getAvatar() {

        return avatar;
    }


    public void setAvatar(String avatar) {

        this.avatar = avatar;
    }


    /* ==========================================================
       11 - CREATED AT
    ========================================================== */

    public Timestamp getCreatedAt() {

        return createdAt;
    }


    public void setCreatedAt(Timestamp createdAt) {

        this.createdAt = createdAt;
    }


    /* ==========================================================
       12 - LAST LOGIN
    ========================================================== */

    public Timestamp getLastLogin() {

        return lastLogin;
    }


    public void setLastLogin(Timestamp lastLogin) {

        this.lastLogin = lastLogin;
    }


    /* ==========================================================
       13 - TO STRING
    ========================================================== */

    @Override
    public String toString() {

        return "Profile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}