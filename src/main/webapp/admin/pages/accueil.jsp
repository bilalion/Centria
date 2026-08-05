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
                "pages.home.title",
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

    <%= LanguageManager.get(
            "dashboard.registered.centres",
            session
    ) %>

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

    <%= LanguageManager.get(
            "dashboard.active.subscriptions",
            session
    ) %>

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

    <%= LanguageManager.get(
            "dashboard.waiting.approval",
            session
    ) %>

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

    <%= LanguageManager.get(
            "dashboard.payment.required",
            session
    ) %>

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

            <%= LanguageManager.get(
                    "dashboard.centres.overview",
                    session
            ) %>

        </h2>




        <button class="widget-filter">


            <%= LanguageManager.get(
                    "dashboard.this.year",
                    session
            ) %>



            <i class="fa-solid fa-chevron-down"></i>


        </button>


    </div>





    <div class="widget-body">


        <div class="chart-placeholder">


            <%= LanguageManager.get(
                    "dashboard.chart.area",
                    session
            ) %>


        </div>


    </div>



</div>









    <!-- =========================
         RECENT CENTERS
    ========================== -->


    <div class="dashboard-widget recent-widget">


        <div class="widget-header">


           <h2>

    <%= LanguageManager.get(
            "dashboard.recent.centres",
            session
    ) %>

</h2>


<button class="widget-link">

    <%= LanguageManager.get(
            "dashboard.view.all",
            session
    ) %>

</button>

        </div>





        <div class="widget-body">



           <div class="recent-empty">

    <%= LanguageManager.get(
            "dashboard.no.recent.centres",
            session
    ) %>

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

    <%= LanguageManager.get(
            "dashboard.system.notifications",
            session
    ) %>

</h3>



       <p>

    <%= LanguageManager.get(
            "dashboard.pending.payments.message",
            session
    ) %>

</p>


    </div>





  <a class="notification-action">

    <%= LanguageManager.get(
            "dashboard.view.payments",
            session
    ) %>

</a>






</section>






</div>