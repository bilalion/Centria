package com.centria.dao;

import com.centria.config.DatabaseConfig;
import com.centria.models.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * ==========================================================
 * CENTRIA
 * ProfileDAO
 * ==========================================================
 *
 * Database operations related to the logged-in user profile.
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
 * Password:
 * - handled separately
 *
 * ==========================================================
 */
public class ProfileDAO {


    /* ==========================================================
       01 - GET PROFILE
    ========================================================== */

    public Profile getProfile(int userId) {

        String sql =
                "SELECT " +
                "id, " +
                "username, " +
                "type, " +
                "status, " +
                "email, " +
                "phone, " +
                "avatar, " +
                "created_at, " +
                "last_login " +
                "FROM super_admins " +
                "WHERE id = ?";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    Profile profile =
                            new Profile();


                    profile.setId(
                            resultSet.getInt("id")
                    );


                    profile.setUsername(
                            resultSet.getString("username")
                    );


                    profile.setType(
                            resultSet.getString("type")
                    );


                    profile.setStatus(
                            resultSet.getString("status")
                    );


                    profile.setEmail(
                            resultSet.getString("email")
                    );


                    profile.setPhone(
                            resultSet.getString("phone")
                    );


                    profile.setAvatar(
                            resultSet.getString("avatar")
                    );


                    profile.setCreatedAt(
                            resultSet.getTimestamp("created_at")
                    );


                    profile.setLastLogin(
                            resultSet.getTimestamp("last_login")
                    );


                    return profile;
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

        }


        return null;
    }


    /* ==========================================================
       02 - GET AVATAR
    ========================================================== */

    public String getAvatar(int userId) {

        String sql =
                "SELECT avatar " +
                "FROM super_admins " +
                "WHERE id = ?";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return resultSet.getString("avatar");

                }
            }


        } catch (Exception e) {

            e.printStackTrace();

        }


        return null;
    }


    /* ==========================================================
       03 - UPDATE AVATAR
    ========================================================== */

    public boolean updateAvatar(
            int userId,
            String avatarPath
    ) {

        String sql =
                "UPDATE super_admins " +
                "SET avatar = ? " +
                "WHERE id = ?";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    avatarPath
            );


            statement.setInt(
                    2,
                    userId
            );


            int rows =
                    statement.executeUpdate();


            return rows > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
    }


    /* ==========================================================
       04 - DELETE AVATAR
    ========================================================== */

    public boolean deleteAvatar(int userId) {

        String sql =
                "UPDATE super_admins " +
                "SET avatar = NULL " +
                "WHERE id = ?";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );


            int rows =
                    statement.executeUpdate();


            return rows > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
    }


    /* ==========================================================
       05 - UPDATE PROFILE
    ========================================================== */

    public boolean updateProfile(
            int userId,
            String username,
            String email,
            String phone
    ) {

        String sql =
                "UPDATE super_admins " +
                "SET username = ?, " +
                "email = ?, " +
                "phone = ? " +
                "WHERE id = ?";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username
            );


            statement.setString(
                    2,
                    email
            );


            statement.setString(
                    3,
                    phone
            );


            statement.setInt(
                    4,
                    userId
            );


            int rows =
                    statement.executeUpdate();


            return rows > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
    }


    /* ==========================================================
       06 - CHECK USERNAME
    ========================================================== */

    public boolean usernameExists(
            String username,
            int userId
    ) {

        String sql =
                "SELECT id " +
                "FROM super_admins " +
                "WHERE username = ? " +
                "AND id <> ? " +
                "LIMIT 1";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username
            );


            statement.setInt(
                    2,
                    userId
            );


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                return resultSet.next();

            }


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
    }


    /* ==========================================================
       07 - CHECK EMAIL
    ========================================================== */

    public boolean emailExists(
            String email,
            int userId
    ) {

        String sql =
                "SELECT id " +
                "FROM super_admins " +
                "WHERE email = ? " +
                "AND id <> ? " +
                "LIMIT 1";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    email
            );


            statement.setInt(
                    2,
                    userId
            );


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                return resultSet.next();

            }


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
    }


    /* ==========================================================
       08 - GET PASSWORD HASH
    ========================================================== */

    public String getPasswordHash(int userId) {

        String sql =
                "SELECT password " +
                "FROM super_admins " +
                "WHERE id = ?";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );


            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return resultSet.getString("password");

                }
            }


        } catch (Exception e) {

            e.printStackTrace();

        }


        return null;
    }


    /* ==========================================================
       09 - UPDATE PASSWORD HASH
    ========================================================== */

    public boolean updatePasswordHash(
            int userId,
            String passwordHash
    ) {

        String sql =
                "UPDATE super_admins " +
                "SET password = ? " +
                "WHERE id = ?";


        try (
                Connection connection =
                        DatabaseConfig.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    passwordHash
            );


            statement.setInt(
                    2,
                    userId
            );


            int rows =
                    statement.executeUpdate();


            return rows > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }
    }

}