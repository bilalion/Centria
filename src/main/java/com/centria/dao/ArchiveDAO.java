package com.centria.dao;

import com.centria.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;

public class ArchiveDAO {

    /*
    ==========================================================
    ARCHIVE CENTRE
    ==========================================================
    Creates or updates the archive record for a centre.

    First archive:
        INSERT

    Re-archive:
        UPDATE

    The centre appears only once in centres_archive.
    ==========================================================
    */

    public boolean archiveCentre(String centreCode) {

        String checkSql =
                "SELECT id " +
                "FROM centres_archive " +
                "WHERE centre_code = ?";

        String insertSql =
                "INSERT INTO centres_archive " +
                "(centre_code, archive_status, archived_at, retention_until) " +
                "VALUES (?, 'ARCHIVED', ?, ?)";

        String updateSql =
                "UPDATE centres_archive " +
                "SET archive_status = 'ARCHIVED', " +
                "    archived_at = ?, " +
                "    retention_until = ?, " +
                "    restored_at = NULL, " +
                "    deleted_at = NULL " +
                "WHERE centre_code = ?";

        try (
                Connection con = DatabaseConfig.getConnection();
                PreparedStatement checkPs =
                        con.prepareStatement(checkSql)
        ) {

            checkPs.setString(1, centreCode);

            try (ResultSet rs = checkPs.executeQuery()) {

                LocalDate archivedDate = LocalDate.now();
                LocalDate retentionDate =
                        archivedDate.plusDays(90);

                Date archivedAt =
                        Date.valueOf(archivedDate);

                Date retentionUntil =
                        Date.valueOf(retentionDate);

                if (rs.next()) {

                    try (
                            PreparedStatement updatePs =
                                    con.prepareStatement(updateSql)
                    ) {

                        updatePs.setDate(1, archivedAt);
                        updatePs.setDate(2, retentionUntil);
                        updatePs.setString(3, centreCode);

                        return updatePs.executeUpdate() > 0;
                    }

                } else {

                    try (
                            PreparedStatement insertPs =
                                    con.prepareStatement(insertSql)
                    ) {

                        insertPs.setString(1, centreCode);
                        insertPs.setDate(2, archivedAt);
                        insertPs.setDate(3, retentionUntil);

                        return insertPs.executeUpdate() > 0;
                    }
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "[CENTRIA ARCHIVE] " +
                    "Error while archiving centre: "
                    + centreCode
            );

            e.printStackTrace();

            return false;
        }
    }
    
    
    public boolean archiveCentre(
        Connection con,
        String centreCode) {

    String checkSql =
            "SELECT id " +
            "FROM centres_archive " +
            "WHERE centre_code = ?";

    String insertSql =
            "INSERT INTO centres_archive " +
            "(centre_code, archive_status, archived_at, retention_until) " +
            "VALUES (?, 'ARCHIVED', NOW(), DATE_ADD(NOW(), INTERVAL 90 DAY))";

    String updateSql =
            "UPDATE centres_archive " +
            "SET archive_status = 'ARCHIVED', " +
            "    archived_at = NOW(), " +
            "    retention_until = DATE_ADD(NOW(), INTERVAL 90 DAY), " +
            "    restored_at = NULL, " +
            "    deleted_at = NULL " +
            "WHERE centre_code = ?";

    try (
            PreparedStatement checkPs =
                    con.prepareStatement(checkSql)
    ) {

        checkPs.setString(1, centreCode);

        try (ResultSet rs = checkPs.executeQuery()) {

            if (rs.next()) {

                try (
                        PreparedStatement updatePs =
                                con.prepareStatement(updateSql)
                ) {

                    updatePs.setString(1, centreCode);

                    return updatePs.executeUpdate() > 0;
                }

            }
            else {

                try (
                        PreparedStatement insertPs =
                                con.prepareStatement(insertSql)
                ) {

                    insertPs.setString(1, centreCode);

                    return insertPs.executeUpdate() > 0;
                }
            }
        }

    }
    catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}
}