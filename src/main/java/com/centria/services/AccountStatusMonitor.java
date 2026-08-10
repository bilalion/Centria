/*
 * File        : AccountStatusMonitor.java
 * Project     : CENTRIA
 *
 * Module      : Account Status Monitor
 *
 * Description :
 * Monitors centre account status according to
 * subscription dates.
 *
 * Current responsibility:
 *
 * ACTIVE
 * ↓
 * subscription_start > today
 * ↓
 * INACTIVE
 *
 * INACTIVE
 * ↓
 * subscription_start <= today
 * AND
 * subscription_end >= today
 * ↓
 * ACTIVE
 *
 * ACTIVE
 * ↓
 * subscription_end < today
 * ↓
 * PENDING
 *
 * Important:
 * This class does NOT handle:
 *
 * PENDING → SUSPENDED
 * SUSPENDED → ARCHIVED
 * ARCHIVED → DELETE
 *
 * Those are handled by their dedicated monitor methods.
 */

package com.centria.services;

import com.centria.dao.CentreDAO;


/**
 * Account Status Monitor
 *
 * Responsible for automatic monitoring of
 * centre account status.
 */
public class AccountStatusMonitor {


    /*
    ======================================================
    DAO
    ======================================================
    */

    private final CentreDAO centreDAO;


    /*
    ======================================================
    CONSTRUCTOR
    ======================================================
    */

    public AccountStatusMonitor() {

        this.centreDAO =
                new CentreDAO();

    }


    /*
    ======================================================
    CHECK INACTIVE CENTRES
    ======================================================
    */

    public void checkInactiveCentres() {

        try {

            int updatedCount =
                    centreDAO.monitorInactiveCentres();


            /*
            ------------------------------------------------
            Logging
            ------------------------------------------------
            */

            if (updatedCount > 0) {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + updatedCount
                        + " centre(s) status updated "
                        + "for subscription period."
                );

            }
            else {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + "No INACTIVE status changes found."
                );

            }


        }
        catch (Exception e) {

            System.err.println(
                    "[CENTRIA MONITOR] "
                    + "Error while checking inactive centres."
            );

            e.printStackTrace();

        }

    }


    /*
    ======================================================
    CHECK EXPIRED ACTIVE CENTRES
    ======================================================
    */

    public void checkExpiredActiveCentres() {

        try {

            int updatedCount =
                    centreDAO.monitorExpiredActiveCentres();


            /*
            ------------------------------------------------
            Logging
            ------------------------------------------------
            */

            if (updatedCount > 0) {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + updatedCount
                        + " centre(s) changed: "
                        + "ACTIVE -> PENDING"
                );

            }
            else {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + "No expired ACTIVE centres found."
                );

            }


        }
        catch (Exception e) {

            System.err.println(
                    "[CENTRIA MONITOR] "
                    + "Error while checking expired centres."
            );

            e.printStackTrace();

        }

    }


    /*
    ======================================================
    CHECK PENDING CENTRES
    ======================================================
    */

    public void checkPendingCentres() {

        try {

            int updatedCount =
                    centreDAO.monitorPendingCentres(3);


            if (updatedCount > 0) {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + updatedCount
                        + " centre(s) changed: "
                        + "PENDING -> SUSPENDED"
                );

            }
            else {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + "No PENDING centres exceeded "
                        + "the grace period."
                );

            }

        }
        catch (Exception e) {

            System.err.println(
                    "[CENTRIA MONITOR] "
                    + "Error while checking pending centres."
            );

            e.printStackTrace();

        }

    }


    /*
    ======================================================
    CHECK SUSPENDED CENTRES
    ======================================================
    */

    public void checkSuspendedCentres() {

        try {

            int updatedCount =
                    centreDAO.monitorSuspendedCentres(30);


            if (updatedCount > 0) {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + updatedCount
                        + " centre(s) changed: "
                        + "SUSPENDED -> ARCHIVED"
                );

            }
            else {

                System.out.println(
                        "[CENTRIA MONITOR] "
                        + "No SUSPENDED centres reached "
                        + "the archive period."
                );

            }

        }
        catch (Exception e) {

            System.err.println(
                    "[CENTRIA MONITOR] "
                    + "Error while checking suspended centres."
            );

            e.printStackTrace();

        }

    }

}