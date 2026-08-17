package com.centria.dao;

import com.centria.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ==========================================================
 * ProfileDAO
 * ==========================================================
 *
 * Database operations related to the logged-in user profile.
 *
 * Database table:
 * super_admins
 *
 * Avatar column:
 * avatar
 *
 * Physical avatar location:
 * /uploads/avatars/
 *
 * ==========================================================
 */
public class ProfileDAO {


    /*
     * ==========================================================
     * 01 - GET AVATAR
     * ==========================================================
     *
     * Returns the avatar path stored in the database.
     *
     * Example:
     *
     * uploads/avatars/avatar_1.png
     *
     * If no avatar exists:
     *
     * null
     *
     * ==========================================================
     */
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

            statement.setInt(1, userId);


            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return resultSet.getString("avatar");
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }


    /*
     * ==========================================================
     * 02 - UPDATE AVATAR
     * ==========================================================
     *
     * Saves the avatar path in the database.
     *
     * Example:
     *
     * uploads/avatars/avatar_1_20260817.png
     *
     * The physical file itself is saved by ProfileServlet.
     *
     * ==========================================================
     */
    public boolean updateAvatar(
            int userId,
            String avatarPath) {


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

            statement.setString(1, avatarPath);

            statement.setInt(2, userId);


            int rows =
                    statement.executeUpdate();


            return rows > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    /*
     * ==========================================================
     * 03 - DELETE AVATAR
     * ==========================================================
     *
     * Removes only the avatar reference from database.
     *
     * The physical file can be deleted separately
     * by ProfileServlet.
     *
     * ==========================================================
     */
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

            statement.setInt(1, userId);


            int rows =
                    statement.executeUpdate();


            return rows > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}