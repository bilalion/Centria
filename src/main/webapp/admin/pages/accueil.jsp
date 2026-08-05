<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>


<div class="page-section home-page">



<!-- =================================================
     SECTION 01 - HOME BANNER
================================================= -->


<section class="home-banner">



    <div class="home-banner-icon">


        <i class="fa-solid fa-house"></i>


    </div>





    <h1 class="home-banner-title">


        <%= LanguageManager.get(
                "dashboard.main.title",
                session
        ) %>


    </h1>





    <span class="home-banner-separator">


        |


    </span>





    <p class="home-banner-description">


        <%= LanguageManager.get(
                "dashboard.card.text",
                session
        ) %>


    </p>



</section>









<!-- =================================================
     SECTION 02 - STATISTICS CARDS
================================================= -->


<section class="stats-grid">





    <!-- TOTAL CENTERS -->


    <div class="stat-card stat-blue">


        <div class="stat-icon">


            <i class="fa-solid fa-building"></i>


        </div>



        <div class="stat-content">


            <span class="stat-title">


                <%= LanguageManager.get(
                        "dashboard.total.centers",
                        session
                ) %>


            </span>



            <strong class="stat-value">

                0

            </strong>



            <span class="stat-description">

                Registered centers

            </span>


        </div>


    </div>









    <!-- ACTIVE CENTERS -->


    <div class="stat-card stat-green">


        <div class="stat-icon">


            <i class="fa-solid fa-circle-check"></i>


        </div>



        <div class="stat-content">


            <span class="stat-title">


                <%= LanguageManager.get(
                        "dashboard.active.centers",
                        session
                ) %>


            </span>



            <strong class="stat-value">

                0

            </strong>



            <span class="stat-description">

                Active subscriptions

            </span>


        </div>


    </div>









    <!-- PENDING CENTERS -->


    <div class="stat-card stat-orange">


        <div class="stat-icon">


            <i class="fa-solid fa-hourglass-half"></i>


        </div>



        <div class="stat-content">


            <span class="stat-title">


                <%= LanguageManager.get(
                        "dashboard.pending.centers",
                        session
                ) %>


            </span>



            <strong class="stat-value">

                0

            </strong>



            <span class="stat-description">

                Waiting approval

            </span>


        </div>


    </div>









    <!-- UNPAID CENTERS -->


    <div class="stat-card stat-red">


        <div class="stat-icon">


            <i class="fa-solid fa-money-bill-wave"></i>


        </div>



        <div class="stat-content">


            <span class="stat-title">


                <%= LanguageManager.get(
                        "dashboard.unpaid.centers",
                        session
                ) %>


            </span>



            <strong class="stat-value">

                0

            </strong>



            <span class="stat-description">

                Payment required

            </span>


        </div>


    </div>






</section>
                
                <!-- =================================================
     SECTION 03 - MAIN DASHBOARD GRID
================================================= -->


<section class="dashboard-grid">






    <!-- =========================
         OVERVIEW CHART
    ========================== -->


    <div class="dashboard-widget overview-widget">


        <div class="widget-header">


            <h2>

                Centres Overview

            </h2>


            <button class="widget-filter">


                This Year


                <i class="fa-solid fa-chevron-down"></i>


            </button>


        </div>





        <div class="widget-body">


            <div class="chart-placeholder">


                Chart Area


            </div>


        </div>



    </div>









    <!-- =========================
         RECENT CENTERS
    ========================== -->


    <div class="dashboard-widget recent-widget">


        <div class="widget-header">


            <h2>

                Recent Centres

            </h2>


            <button class="widget-link">


                View All


            </button>


        </div>





        <div class="widget-body">



            <div class="recent-empty">


                No recent centres


            </div>




        </div>



    </div>






</section>









<!-- =================================================
     SECTION 04 - SYSTEM NOTIFICATION
================================================= -->


<section class="system-notification">



    <div class="notification-icon">


        <i class="fa-solid fa-bell"></i>


    </div>





    <div class="notification-content">


        <h3>

            System Notifications

        </h3>



        <p>

            3 centres have pending payments

        </p>


    </div>





    <a class="notification-action">


        View payments


    </a>






</section>






</div>