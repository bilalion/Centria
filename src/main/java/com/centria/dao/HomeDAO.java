/*
 * File        : HomeDAO.java
 * Project     : CENTRIA
 *
 * Description :
 * Dashboard / Home database operations.
 */

package com.centria.dao;

import com.centria.config.DatabaseConfig;
import com.centria.models.Centre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HomeDAO {


    /*
    ======================================================
    TEMPORARY MONTHLY PRICE
    ======================================================

    Temporary price:
    1 month = 100 DH

    Later this value will come from Settings.
    ======================================================
    */

    private static final double PRICE_PER_MONTH = 100.00;


    /*
    ======================================================
    TOTAL CENTRES
    ======================================================

    Exclude:

    ARCHIVED
    DELETED

    ======================================================
    */

    public int getTotalCentres() {

        int total = 0;

        String sql =
                "SELECT COUNT(*) " +
                "FROM centres " +
                "WHERE status NOT IN ('ARCHIVED', 'DELETED')";

        try (
                Connection con =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                total =
                        rs.getInt(1);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return total;
    }


    /*
    ======================================================
    ACTIVE CENTRES
    ======================================================
    */

    public int getActiveCentres() {

        int total = 0;

        String sql =
                "SELECT COUNT(*) " +
                "FROM centres " +
                "WHERE status = 'ACTIVE'";

        try (
                Connection con =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                total =
                        rs.getInt(1);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return total;
    }


    /*
    ======================================================
    CENTRES REQUIRING ATTENTION
    ======================================================

    Currently:

    PENDING
    +
    SUSPENDED

    ======================================================
    */

    public int getCentresRequiringAttention() {

        int total = 0;

        String sql =
                "SELECT COUNT(*) " +
                "FROM centres " +
                "WHERE status IN ('PENDING', 'SUSPENDED')";

        try (
                Connection con =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                total =
                        rs.getInt(1);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return total;
    }


    /*
    ======================================================
    MONTHLY REVENUE
    ======================================================

    Source:

    history_payment.date_paiement
    history_payment.duration_months

    Calculation:

    duration_months × PRICE_PER_MONTH

    Example:

    1  × 100 = 100 DH
    3  × 100 = 300 DH
    6  × 100 = 600 DH
    12 × 100 = 1200 DH

    Only payments made during the current month
    are included.

    ======================================================
    */

    public double getMonthlyRevenue() {

        double revenue = 0.00;

        String sql =
                "SELECT COALESCE(" +
                "SUM(duration_months * ?), " +
                "0" +
                ") " +
                "FROM history_payment " +
                "WHERE YEAR(date_paiement) = YEAR(CURRENT_DATE) " +
                "AND MONTH(date_paiement) = MONTH(CURRENT_DATE)";

        try (
                Connection con =
                        DatabaseConfig.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setDouble(
                    1,
                    PRICE_PER_MONTH
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    revenue =
                            rs.getDouble(1);

                }

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return revenue;
    }

    
    /*
======================================================
MONTHLY PAYMENT STATUS
======================================================

*/

public Map<String, Integer> getMonthlyPaymentStatus() {

    Map<String, Integer> statusCounts =
            new LinkedHashMap<>();


    /*
    ==================================================
    DEFAULT VALUES
    ==================================================
    */

    statusCounts.put(
            "PAID",
            0
    );


    statusCounts.put(
            "UNPAID",
            0
    );


    String sql =
            "SELECT " +

            "COALESCE(" +
                "SUM(" +
                    "CASE " +
                    "WHEN p.status_payment = 'PAID' " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END" +
                "), " +
                "0" +
            ") AS paid_count, " +

            "COALESCE(" +
                "SUM(" +
                    "CASE " +
                    "WHEN p.status_payment = 'UNPAID' " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END" +
                "), " +
                "0" +
            ") AS unpaid_count " +

            "FROM centres c " +

            "LEFT JOIN payments p " +
            "ON p.centre_code = c.centre_code " +

            "WHERE c.status NOT IN " +
            "('ARCHIVED', 'DELETED')";


    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
    ) {


        if (rs.next()) {

            statusCounts.put(
                    "PAID",
                    rs.getInt(
                            "paid_count"
                    )
            );


            statusCounts.put(
                    "UNPAID",
                    rs.getInt(
                            "unpaid_count"
                    )
            );

        }

    }
    catch (Exception e) {

        System.err.println(
                "ERROR - HomeDAO.getMonthlyPaymentStatus()"
        );

        e.printStackTrace();

    }


    return statusCounts;
}

/*
======================================================
CENTRE STATUS OVERVIEW
======================================================

*/

public Map<String, Integer> getCentreStatusOverview() {

    Map<String, Integer> statusCounts =
            new LinkedHashMap<>();


    /*
    ==================================================
    DEFAULT VALUES
    ==================================================
    */

    statusCounts.put(
            "ACTIVE",
            0
    );


    statusCounts.put(
            "FOLLOW_UP",
            0
    );


    statusCounts.put(
            "INACTIVE",
            0
    );


    statusCounts.put(
            "ARCHIVED",
            0
    );


    statusCounts.put(
            "DELETED",
            0
    );


    String sql =
            "SELECT " +

            "COALESCE(" +
                "SUM(" +
                    "CASE " +
                    "WHEN status = 'ACTIVE' " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END" +
                "), " +
                "0" +
            ") AS active_count, " +

            "COALESCE(" +
                "SUM(" +
                    "CASE " +
                    "WHEN status IN ('PENDING', 'SUSPENDED') " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END" +
                "), " +
                "0" +
            ") AS follow_up_count, " +

            "COALESCE(" +
                "SUM(" +
                    "CASE " +
                    "WHEN status = 'INACTIVE' " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END" +
                "), " +
                "0" +
            ") AS inactive_count, " +

            "COALESCE(" +
                "SUM(" +
                    "CASE " +
                    "WHEN status = 'ARCHIVED' " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END" +
                "), " +
                "0" +
            ") AS archived_count, " +

            "COALESCE(" +
                "SUM(" +
                    "CASE " +
                    "WHEN status = 'DELETED' " +
                    "THEN 1 " +
                    "ELSE 0 " +
                    "END" +
                "), " +
                "0" +
            ") AS deleted_count " +

            "FROM centres";


    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
    ) {


        if (rs.next()) {


            statusCounts.put(
                    "ACTIVE",
                    rs.getInt(
                            "active_count"
                    )
            );


            statusCounts.put(
                    "FOLLOW_UP",
                    rs.getInt(
                            "follow_up_count"
                    )
            );


            statusCounts.put(
                    "INACTIVE",
                    rs.getInt(
                            "inactive_count"
                    )
            );


            statusCounts.put(
                    "ARCHIVED",
                    rs.getInt(
                            "archived_count"
                    )
            );


            statusCounts.put(
                    "DELETED",
                    rs.getInt(
                            "deleted_count"
                    )
            );

        }

    }
    catch (Exception e) {

        System.err.println(
                "ERROR - HomeDAO.getCentreStatusOverview()"
        );

        e.printStackTrace();

    }


    return statusCounts;
}

    /*
    ======================================================
    RECENT CENTRES
    ======================================================

    Returns the latest 5 centres.

    Order:

    created_at DESC

    Exclude:

    ARCHIVED
    DELETED

    ======================================================
    */

public List<Centre> getRecentCentres() {

    List<Centre> centres =
            new ArrayList<>();


    String sql =
            "SELECT " +

            "c.name, " +
            "c.created_at, " +

            "p.code_facture, " +
            "p.status_payment, " +

            "h.duration_months " +

            "FROM centres c " +

            "LEFT JOIN payments p " +
            "ON p.centre_code = c.centre_code " +

            "LEFT JOIN history_payment h " +
            "ON h.code_facture = p.code_facture " +

            "WHERE c.status NOT IN ('ARCHIVED', 'DELETED') " +

            "ORDER BY c.created_at DESC " +

            "LIMIT 5";


    try (
            Connection con =
                    DatabaseConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
    ) {


        while (rs.next()) {


            Centre centre =
                    new Centre();


            /*
            ==========================================
            CENTRE NAME
            ==========================================
            */

            centre.setName(
                    rs.getString("name")
            );


            /*
            ==========================================
            CREATED DATE
            ==========================================
            */

            centre.setCreatedAt(
                    rs.getTimestamp("created_at")
            );


            /*
            ==========================================
            LAST SUBSCRIPTION DURATION
            ==========================================
            */

            int duration =
                    rs.getInt("duration_months");


            if (rs.wasNull()) {

                duration = 0;

            }


            centre.setDurationMonths(
                    duration
            );


            /*
            ==========================================
            ADD CENTRE
            ==========================================
            */

            centres.add(
                    centre
            );

        }

    }
    catch (Exception e) {

        System.err.println(
                "ERROR - HomeDAO.getRecentCentres()"
        );

        e.printStackTrace();

    }


    return centres;
}

}