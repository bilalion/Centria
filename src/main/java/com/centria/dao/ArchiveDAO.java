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
01 - GET / SEARCH / FILTER ARCHIVED CENTRES
==========================================================

Search by:
- centre_code
- centre name
- phone

Filter by:
- ALL
- ARCHIVED
- PENDING_DELETE

Rules:
- Empty search = all matching statuses
- ALL = ARCHIVED + PENDING_DELETE
- ARCHIVED = ARCHIVED only
- PENDING_DELETE = PENDING_DELETE only
==========================================================
*/

public List<Archive> getArchivedCentres(
        String search,
        String status,
        int page,
        int pageSize
) {


    /*
    ==================================================
    01 - BUILD SQL
    ==================================================
    */

    StringBuilder sql =
            new StringBuilder();


    sql.append(
            "SELECT "
            + "ca.id, "
            + "ca.centre_code, "
            + "c.name AS centre_name, "
            + "c.phone, "
            + "ca.archive_status, "
            + "ca.archived_at, "
            + "ca.retention_until, "
            + "ca.restored_at, "
            + "ca.deleted_at "
            + "FROM centres_archive ca "
            + "INNER JOIN centres c "
            + "ON c.centre_code = ca.centre_code "
            + "WHERE ca.archive_status IN "
            + "('ARCHIVED', 'PENDING_DELETE') "
    );


    /*
    ==================================================
    02 - STATUS FILTER
    ==================================================
    */

    if (
            status != null
            &&
            !status.trim().isEmpty()
            &&
            !"ALL".equalsIgnoreCase(
                    status.trim()
            )
    ) {

        sql.append(
                "AND ca.archive_status = ? "
        );

    }


    /*
    ==================================================
    03 - SEARCH FILTER
    ==================================================

    Search by:

    - centre_code
    - centre name
    - phone
    ==================================================
    */

    if (
            search != null
            &&
            !search.trim().isEmpty()
    ) {

        sql.append(
                "AND ( "
                + "ca.centre_code LIKE ? "
                + "OR c.name LIKE ? "
                + "OR c.phone LIKE ? "
                + ") "
        );

    }


    /*
    ==================================================
    04 - ORDER
    ==================================================
    */

    sql.append(
            "ORDER BY ca.archived_at DESC "
    );


    /*
    ==================================================
    05 - PAGINATION
    ==================================================
    */

    int offset =
            (page - 1) * pageSize;


    sql.append(
            "LIMIT ? OFFSET ? "
    );


    /*
    ==================================================
    06 - RESULT LIST
    ==================================================
    */

    List<Archive> archives =
            new ArrayList<>();


    /*
    ==================================================
    07 - DATABASE
    ==================================================
    */

    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            sql.toString()
                    )
    ) {


        int parameterIndex = 1;


        /*
        ==================================================
        08 - STATUS PARAMETER
        ==================================================
        */

        if (
                status != null
                &&
                !status.trim().isEmpty()
                &&
                !"ALL".equalsIgnoreCase(
                        status.trim()
                )
        ) {

            ps.setString(
                    parameterIndex++,
                    status.trim()
            );

        }


        /*
        ==================================================
        09 - SEARCH PARAMETERS
        ==================================================
        */

        if (
                search != null
                &&
                !search.trim().isEmpty()
        ) {

            String keyword =
                    "%"
                    +
                    search.trim()
                    +
                    "%";


            /*
            centre_code
            */

            ps.setString(
                    parameterIndex++,
                    keyword
            );


            /*
            centre name
            */

            ps.setString(
                    parameterIndex++,
                    keyword
            );


            /*
            phone
            */

            ps.setString(
                    parameterIndex++,
                    keyword
            );

        }


        /*
        ==================================================
        10 - PAGINATION PARAMETERS
        ==================================================
        */

        ps.setInt(
                parameterIndex++,
                pageSize
        );


        ps.setInt(
                parameterIndex++,
                offset
        );


        /*
        ==================================================
        11 - EXECUTE QUERY
        ==================================================
        */

        try (
                ResultSet rs =
                        ps.executeQuery()
        ) {


            /*
            ==================================================
            12 - READ RESULTS
            ==================================================
            */

            while (rs.next()) {


                Archive archive =
                        new Archive();


                archive.setId(
                        rs.getInt(
                                "id"
                        )
                );


                archive.setCentreCode(
                        rs.getString(
                                "centre_code"
                        )
                );


                archive.setCentreName(
                        rs.getString(
                                "centre_name"
                        )
                );


                archive.setArchiveStatus(
                        rs.getString(
                                "archive_status"
                        )
                );


                archive.setArchivedAt(
                        rs.getTimestamp(
                                "archived_at"
                        )
                );


                archive.setRetentionUntil(
                        rs.getTimestamp(
                                "retention_until"
                        )
                );


                archive.setRestoredAt(
                        rs.getTimestamp(
                                "restored_at"
                        )
                );


                archive.setDeletedAt(
                        rs.getTimestamp(
                                "deleted_at"
                        )
                );


                archives.add(
                        archive
                );

            }

        }


    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] "
                +
                "Error while loading/searching "
                +
                "archived centres."
        );


        e.printStackTrace();

    }


    /*
    ==================================================
    13 - RETURN
    ==================================================
    */

    return archives;
}



/*
==========================================================
01.1 - GET ARCHIVE DETAILS FOR VIEW
==========================================================

Purpose:
- Load complete archive information for View dialog only.
- Does NOT modify any data.
- Does NOT affect Restore / Delete / Monitor.
==========================================================
*/

public Archive getArchiveForView(String centreCode) {

    String sql =
            "SELECT " +
            "    ca.id, " +
            "    ca.centre_code, " +
            "    c.name AS centre_name, " +
            "    c.phone, " +
            "    ca.archive_status, " +
            "    ca.archived_at, " +
            "    ca.retention_until, " +
            "    ca.restored_at, " +
            "    ca.deleted_at " +
            "FROM centres_archive ca " +
            "LEFT JOIN centres c " +
            "    ON c.centre_code = ca.centre_code " +
            "WHERE ca.centre_code = ? " +
            "LIMIT 1";


    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        ps.setString(
                1,
                centreCode
        );


        try (
                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

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
                        rs.getTimestamp(
                                "retention_until"
                        )
                );


                archive.setRestoredAt(
                        rs.getTimestamp(
                                "restored_at"
                        )
                );


                archive.setDeletedAt(
                        rs.getTimestamp(
                                "deleted_at"
                        )
                );


                return archive;
            }
        }

    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] " +
                "Error while loading archive details for view: "
                + centreCode
        );

        e.printStackTrace();
    }


    return null;
}



    /*
==========================================================
02- BUTTON RESTORE CENTRE
==========================================================
*/

public boolean restoreCentre(String centreCode) {

 String updateCentreSql =
        "UPDATE centres " +
        "SET status = 'SUSPENDED', " +
        "    subscription_end = DATE_ADD(CURDATE(), INTERVAL 3 DAY) " +
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
03.1 - UPDATE ARCHIVE OPERATION
==========================================================
*/



public boolean updateArchiveOperation(
        String operator,
        String operationType,
        int operationCount) {

    String sql =
            "UPDATE archive_operation " +
            "SET operator = ?, " +
            "operation_type = ?, " +
            "operation_count = ?, " +
            "operation_at = NOW() " +
            "WHERE id = 1";

    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        ps.setString(1, operator);
        ps.setString(2, operationType);
        ps.setInt(3, operationCount);


        System.out.println(
                "[CENTRIA ARCHIVE] "
                + "Updating archive operation: "
                + "operator=" + operator
                + ", operationType=" + operationType
                + ", operationCount=" + operationCount
        );


        int updatedRows =
                ps.executeUpdate();


        System.out.println(
                "[CENTRIA ARCHIVE] "
                + "Archive operation rows updated: "
                + updatedRows
        );


        return updatedRows > 0;

    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] "
                + "Error while updating archive operation."
        );

        e.printStackTrace();

        return false;
    }
}



/*
==========================================================
03.2 - GET LAST ARCHIVE OPERATION
==========================================================
*/

public java.util.Map<String, Object> getLastArchiveOperation() {

    String sql =
            "SELECT " +
            "    operator, " +
            "    operation_type, " +
            "    operation_count, " +
            "    operation_at " +
            "FROM archive_operation " +
            "WHERE id = 1";


    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
    ) {

        if (rs.next()) {

            java.util.Map<String, Object> operation =
                    new java.util.HashMap<>();


            operation.put(
                    "operator",
                    rs.getString("operator")
            );


            operation.put(
                    "operationType",
                    rs.getString("operation_type")
            );


            operation.put(
                    "operationCount",
                    rs.getInt("operation_count")
            );


            operation.put(
                    "operationAt",
                    rs.getTimestamp("operation_at")
            );


            return operation;
        }

    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] " +
                "Error while loading last archive operation."
        );

        e.printStackTrace();
    }


    return null;
}

/*
==========================================================
 04-  function for Monitor Only
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
    
   
 
    public int countArchivedCentres(
        String search,
        String status
) {

    StringBuilder sql =
            new StringBuilder(
                    "SELECT COUNT(*) "
                    + "FROM centres_archive ca "
                    + "INNER JOIN centres c "
                    + "ON c.centre_code = ca.centre_code "
                    + "WHERE ca.archive_status IN "
                    + "('ARCHIVED', 'PENDING_DELETE') "
            );


    /*
    --------------------------------------------------
    STATUS FILTER
    --------------------------------------------------
    */

    if (
            status != null
            &&
            !status.trim().isEmpty()
            &&
            !"ALL".equalsIgnoreCase(
                    status.trim()
            )
    ) {

        sql.append(
                "AND ca.archive_status = ? "
        );

    }


    /*
    --------------------------------------------------
    SEARCH FILTER
    --------------------------------------------------
    */

    if (
            search != null
            &&
            !search.trim().isEmpty()
    ) {

        sql.append(
                "AND ( "
                + "ca.centre_code LIKE ? "
                + "OR c.name LIKE ? "
                + "OR c.phone LIKE ? "
                + ") "
        );

    }


    try (
            Connection connection =
                    DatabaseConfig.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(
                            sql.toString()
                    )
    ) {

        int parameterIndex = 1;


        /*
        --------------------------------------------------
        STATUS PARAMETER
        --------------------------------------------------
        */

        if (
                status != null
                &&
                !status.trim().isEmpty()
                &&
                !"ALL".equalsIgnoreCase(
                        status.trim()
                )
        ) {

            statement.setString(
                    parameterIndex++,
                    status.trim()
            );

        }


        /*
        --------------------------------------------------
        SEARCH PARAMETERS
        --------------------------------------------------
        */

        if (
                search != null
                &&
                !search.trim().isEmpty()
        ) {

            String keyword =
                    "%"
                    +
                    search.trim()
                    +
                    "%";


            statement.setString(
                    parameterIndex++,
                    keyword
            );


            statement.setString(
                    parameterIndex++,
                    keyword
            );


            statement.setString(
                    parameterIndex++,
                    keyword
            );

        }


        /*
        --------------------------------------------------
        EXECUTE
        --------------------------------------------------
        */

        try (
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return resultSet.getInt(1);

            }

        }

    }
    catch (Exception e) {

        System.err.println(
                "[CENTRIA ARCHIVE] "
                +
                "Error while counting filtered "
                +
                "archived centres."
        );

        e.printStackTrace();

    }


    return 0;
}
    
    
public int countArchivedCentres() {

    String sql =
            "SELECT COUNT(*) " +
            "FROM centres_archive " +
            "WHERE archive_status = 'ARCHIVED'";

    try (
            Connection connection =
                    DatabaseConfig.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
    ) {

        if (resultSet.next()) {

            return resultSet.getInt(1);

        }

    }
    catch (Exception e) {

        e.printStackTrace();

    }

    return 0;

}
public int countPendingDeleteCentres() {

    String sql =
            "SELECT COUNT(*) " +
            "FROM centres_archive " +
            "WHERE archive_status = 'PENDING_DELETE'";

    try (
            Connection connection =
                    DatabaseConfig.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
    ) {

        if (resultSet.next()) {

            return resultSet.getInt(1);

        }

    }
    catch (Exception e) {

        e.printStackTrace();

    }

    return 0;

}


public int countDeletedCentres() {

    String sql =
            "SELECT COUNT(*) " +
            "FROM centres_archive " +
            "WHERE archive_status = 'DELETED'";

    try (
            Connection connection =
                    DatabaseConfig.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
    ) {

        if (resultSet.next()) {

            return resultSet.getInt(1);

        }

    }
    catch (Exception e) {

        e.printStackTrace();

    }

    return 0;

}
    /*
==========================================================
05- MONITOR ARCHIVED CENTRES
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




/*
==========================================================
03- DELETE CENTRE
==========================================================

SUPER ADMIN ONLY

CURRENT SUPER ADMIN TABLES:

KEEP:
- centres
- centres_archive
- history_payment

DELETE:
- payments

UPDATE:
- centres.status = DELETED
- centres_archive.archive_status = DELETED
- centres_archive.deleted_at = NOW()

IMPORTANT:
- history_payment is NEVER deleted.
- Future Manager APP tables will be handled later.
==========================================================
*/

public boolean deleteCentre(String centreCode) {

    String deletePaymentsSql =
            "DELETE FROM payments " +
            "WHERE centre_code = ?";


    String updateCentreSql =
            "UPDATE centres " +
            "SET status = 'DELETED' " +
            "WHERE centre_code = ?";


    String updateArchiveSql =
            "UPDATE centres_archive " +
            "SET archive_status = 'DELETED', " +
            "    deleted_at = NOW() " +
            "WHERE centre_code = ? " +
            "AND archive_status IN ('ARCHIVED', 'PENDING_DELETE')";


    try (
            Connection con =
                    DatabaseConfig.getConnection()
    ) {

        /*
        --------------------------------------------------
        START TRANSACTION
        --------------------------------------------------
        */

        con.setAutoCommit(false);


        try {

            /*
            --------------------------------------------------
            01 - DELETE PAYMENTS
            --------------------------------------------------

            Current operational payment records
            are deleted.

            history_payment is NOT touched.
            --------------------------------------------------
            */

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    deletePaymentsSql
                            )
            ) {

                ps.setString(
                        1,
                        centreCode
                );

                ps.executeUpdate();
            }


            /*
            --------------------------------------------------
            02 - UPDATE CENTRES
            --------------------------------------------------
            */

            int centreUpdated;

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updateCentreSql
                            )
            ) {

                ps.setString(
                        1,
                        centreCode
                );

                centreUpdated =
                        ps.executeUpdate();
            }


            /*
            --------------------------------------------------
            CENTRE MUST EXIST
            --------------------------------------------------
            */

            if (centreUpdated == 0) {

                con.rollback();

                return false;
            }


            /*
            --------------------------------------------------
            03 - UPDATE CENTRES_ARCHIVE
            --------------------------------------------------
            */

            int archiveUpdated;

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updateArchiveSql
                            )
            ) {

                ps.setString(
                        1,
                        centreCode
                );

                archiveUpdated =
                        ps.executeUpdate();
            }


            /*
            --------------------------------------------------
            ARCHIVE RECORD MUST EXIST
            --------------------------------------------------
            */

            if (archiveUpdated == 0) {

                con.rollback();

                return false;
            }


            /*
            --------------------------------------------------
            04 - FUTURE MANAGER APP DATA
            --------------------------------------------------

            IMPORTANT:
            Do NOT add Manager APP tables here yet.

            They will be added later after their
            database structure is finalized.
            --------------------------------------------------
            */


            /*
            --------------------------------------------------
            05 - COMMIT
            --------------------------------------------------
            */

            con.commit();


            return true;

        }
        catch (Exception e) {

            /*
            --------------------------------------------------
            ROLLBACK
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
                "[CENTRIA ARCHIVE] " +
                "Error while deleting centre: "
                + centreCode
        );

        e.printStackTrace();

        return false;
    }
}

}