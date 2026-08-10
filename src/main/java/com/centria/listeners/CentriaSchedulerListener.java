/*
 * File        : CentriaSchedulerListener.java
 * Project     : CENTRIA
 *
 * Module      : Scheduler
 *
 * Description :
 * Starts the Centria background scheduler when
 * the web application starts.
 */

package com.centria.listeners;

import com.centria.services.AccountStatusMonitor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Centria Scheduler Listener
 *
 * Responsible for starting and stopping
 * the background account status monitor.
 */
@WebListener
public class CentriaSchedulerListener
        implements ServletContextListener {


    /*
    ======================================================
    SCHEDULER
    ======================================================
    */

    private ScheduledExecutorService scheduler;


    /*
    ======================================================
    APPLICATION START
    ======================================================
    */

    @Override
    public void contextInitialized(
            ServletContextEvent event
    ) {


        System.out.println(
                "[CENTRIA SCHEDULER] "
                + "Starting..."
        );


        /*
        --------------------------------------------------
        Create scheduler
        --------------------------------------------------
        */

        scheduler =
                Executors.newSingleThreadScheduledExecutor();


        /*
        --------------------------------------------------
        Create monitor
        --------------------------------------------------
        */

        AccountStatusMonitor monitor =
                new AccountStatusMonitor();


        /*
        --------------------------------------------------
        Schedule monitor
        --------------------------------------------------

        First execution:
        immediately

        Next executions:
        every 1 minute
        --------------------------------------------------
        */

        scheduler.scheduleAtFixedRate(

                () -> {


                    /*
                    ======================================
                    CHECK INACTIVE / ACTIVE
                    ======================================
                    */

                    monitor.checkInactiveCentres();


                    /*
                    ======================================
                    CHECK EXPIRED ACTIVE CENTRES
                    ======================================
                    */

                    monitor.checkExpiredActiveCentres();


                    /*
                    ======================================
                    CHECK PENDING CENTRES
                    ======================================
                    */

                    monitor.checkPendingCentres();


                    /*
                    ======================================
                    CHECK SUSPENDED CENTRES
                    ======================================
                    */

                    monitor.checkSuspendedCentres();


                },

                0,

                1,

                TimeUnit.MINUTES

        );


        System.out.println(
                "[CENTRIA SCHEDULER] "
                + "Account status monitor started."
        );

    }


    /*
    ======================================================
    APPLICATION STOP
    ======================================================
    */

    @Override
    public void contextDestroyed(
            ServletContextEvent event
    ) {


        System.out.println(
                "[CENTRIA SCHEDULER] "
                + "Stopping..."
        );


        /*
        --------------------------------------------------
        Stop scheduler
        --------------------------------------------------
        */

        if (scheduler != null) {

            scheduler.shutdownNow();

        }


        System.out.println(
                "[CENTRIA SCHEDULER] "
                + "Stopped."
        );

    }

}