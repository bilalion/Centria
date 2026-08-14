package com.centria.dao;

import com.centria.config.DatabaseConfig;
import com.centria.models.Archive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDAO {


    
    /*
==========================================================
01- GET ARCHIVED CENTRES
==========================================================

*/


public List<Archive> getArchivedCentres() {

    String sql =
            "SELECT " +
            "    ca.id, " +
            "    ca.centre_code, " +
            "    c.name AS centre_name, " +
            "    ca.archive_status, " +
            "    ca.archived_at, " +
            "    ca.retention_until, " +
            "    ca.restored_at, " +
            "    ca.deleted_at " +
            "FROM centres_archive ca " +
            "INNER JOIN centres c " +
            "    ON c.centre_code = ca.centre_code " +
            "WHERE ca.archive_status IN " +
            "    ('ARCHIVED', 'PENDING_DELETE') " +
            "ORDER BY ca.archived_at DESC";


    List<Archive> archives =
            new ArrayList<>();


    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
    ) {


        while (rs.next()) {

            Archive archive =
                    new Archive();


            archive.setId(
                    rs.getInt("id")
            );


            archive.setCentreCode(
                    rs.getString("centre_code")
            );


            archive.setCentreName(
                    rs.getString("centre_name")
            );


            archive.setArchiveStatus(
                    rs.getString("archive_status")
            );


            archive.setArchivedAt(
                    rs.getTimestamp("archived_at")
            );


            archive.setRetentionUntil(
                    rs.getTimestamp("retention_until")
            );


            archive.setRestoredAt(
                    rs.getTimestamp("restored_at")
            );


            archive.setDeletedAt(
                    rs.getTimestamp("deleted_at")
            );


            archives.add(archive);
        }


    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] " +
                "Error while loading archived centres."
        );

        e.printStackTrace();
    }


    return archives;
}
     
    /*
==========================================================
02- BUTTON RESTORE CENTRE
==========================================================
*/

public boolean restoreCentre(String centreCode) {

    String updateCentreSql =
            "UPDATE centres " +
            "SET status = 'PENDING', " +
            "    subscription_end = DATE_SUB(CURDATE(), INTERVAL 1 DAY) " +
            "WHERE centre_code = ?";

    String updateArchiveSql =
            "UPDATE centres_archive " +
            "SET archive_status = 'RESTORED', " +
            "    restored_at = NOW() " +
            "WHERE centre_code = ? " +
            "AND archive_status IN ('ARCHIVED', 'PENDING_DELETE')";

    try (
            Connection con = DatabaseConfig.getConnection()
    ) {

        /*
        --------------------------------------------------
        Start transaction
        --------------------------------------------------
        */

        con.setAutoCommit(false);


        try {

            /*
            --------------------------------------------------
            Update CENTRES
            --------------------------------------------------
            */

            int centreUpdated;

            try (
                    PreparedStatement ps =
                            con.prepareStatement(updateCentreSql)
            ) {

                ps.setString(1, centreCode);

                centreUpdated =
                        ps.executeUpdate();
            }


            /*
            --------------------------------------------------
            Centre must exist
            --------------------------------------------------
            */

            if (centreUpdated == 0) {

                con.rollback();

                System.err.println(
                        "[CENTRIA ARCHIVE] "
                        + "Restore failed. Centre not found: "
                        + centreCode
                );

                return false;
            }


            /*
            --------------------------------------------------
            Update CENTRES_ARCHIVE
            --------------------------------------------------
            */

            int archiveUpdated;

            try (
                    PreparedStatement ps =
                            con.prepareStatement(updateArchiveSql)
            ) {

                ps.setString(1, centreCode);

                archiveUpdated =
                        ps.executeUpdate();
            }


            /*
            --------------------------------------------------
            Archive record must be ARCHIVED or PENDING_DELETE
            --------------------------------------------------
            */

            if (archiveUpdated == 0) {

                con.rollback();

                System.err.println(
                        "[CENTRIA ARCHIVE] "
                        + "Restore failed. "
                        + "No restorable archive record found: "
                        + centreCode
                );

                return false;
            }


            /*
            --------------------------------------------------
            Commit
            --------------------------------------------------
            */

            con.commit();


            System.out.println(
                    "[CENTRIA ARCHIVE] "
                    + "Centre restored successfully: "
                    + centreCode
            );

            return true;


        }
        catch (Exception e) {

            /*
            --------------------------------------------------
            Rollback
            --------------------------------------------------
            */

            try {
                con.rollback();
            }
            catch (Exception rollbackException) {

                rollbackException.printStackTrace();
            }


            throw e;
        }


    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] "
                + "Error while restoring centre: "
                + centreCode
        );

        e.printStackTrace();

        return false;
    }
}

/*
==========================================================
 03-  function for Monitor Only
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
    
    
    /*
==========================================================
04- MONITOR ARCHIVED CENTRES
==========================================================

ARCHIVED
    ↓ retention_until reached
PENDING_DELETE

IMPORTANT:
- centres.status remains ARCHIVED
- Only centres_archive.archive_status changes
- No DELETE is performed
==========================================================
*/

public int monitorArchivedCentres() {

    String sql =
            "UPDATE centres_archive ca " +
            "INNER JOIN centres c " +
            "    ON c.centre_code = ca.centre_code " +
            "SET ca.archive_status = 'PENDING_DELETE' " +
            "WHERE ca.archive_status = 'ARCHIVED' " +
            "AND c.status = 'ARCHIVED' " +
            "AND ca.retention_until <= CURRENT_DATE";


    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        int updatedCount =
                ps.executeUpdate();


        /*
        --------------------------------------------------
        Logging
        --------------------------------------------------
        */

        System.out.println(
                "[CENTRIA ARCHIVE] "
                + updatedCount
                + " centre(s) changed: "
                + "ARCHIVED -> PENDING_DELETE"
        );


        return updatedCount;

    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] "
                + "Error while monitoring archived centres."
        );

        e.printStackTrace();

        return 0;
    }
}
}