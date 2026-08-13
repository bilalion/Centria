<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.language.LanguageManager"%>
<%@page import="com.centria.models.Centre"%>

<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>


<%
/*
=================================================
HOME DATA
=================================================
*/

Integer totalCentres =
        (Integer) request.getAttribute(
                "totalCentres"
        );


Integer activeCentres =
        (Integer) request.getAttribute(
                "activeCentres"
        );


Integer centresRequiringAttention =
        (Integer) request.getAttribute(
                "centresRequiringAttention"
        );


Double monthlyRevenue =
        (Double) request.getAttribute(
                "monthlyRevenue"
        );


List<Centre> recentCentres =
        (List<Centre>) request.getAttribute(
                "recentCentres"
        );


/*
=================================================
SAFE DEFAULTS
=================================================
*/

if (totalCentres == null) {

    totalCentres = 0;

}


if (activeCentres == null) {

    activeCentres = 0;

}


if (centresRequiringAttention == null) {

    centresRequiringAttention = 0;

}


if (monthlyRevenue == null) {

    monthlyRevenue = 0.00;

}


/*
=================================================
RECENT CENTRES DATE FORMAT
=================================================
*/

SimpleDateFormat recentDateFormat =
        new SimpleDateFormat(
                "dd/MM/yyyy"
        );

%>


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


    <!-- =================================================
         TOTAL CENTERS
    ================================================= -->

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


            <strong
                    class="stat-value"
                    id="totalCentres">

                <%= totalCentres %>

            </strong>


            <span class="stat-description">

                <%= LanguageManager.get(
                        "dashboard.registered.centres",
                        session
                ) %>

            </span>


        </div>


    </div>


    <!-- =================================================
         ACTIVE CENTERS
    ================================================= -->

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


            <strong
                    class="stat-value"
                    id="activeCentres">

                <%= activeCentres %>

            </strong>


            <span class="stat-description">

                <%= LanguageManager.get(
                        "dashboard.active.subscriptions",
                        session
                ) %>

            </span>


        </div>


    </div>


    <!-- =================================================
         CENTERS REQUIRING ATTENTION
    ================================================= -->

    <div class="stat-card stat-orange">


        <div class="stat-icon">

            <i class="fa-solid fa-hourglass-half"></i>

        </div>


        <div class="stat-content">


            <span class="stat-title">

                <%= LanguageManager.get(
                        "dashboard.attention.centers",
                        session
                ) %>

            </span>


            <strong
                    class="stat-value"
                    id="centresRequiringAttention">

                <%= centresRequiringAttention %>

            </strong>


            <span class="stat-description">

                <%= LanguageManager.get(
                        "dashboard.attention.centers.description",
                        session
                ) %>

            </span>


        </div>


    </div>


    <!-- =================================================
         MONTHLY REVENUE
    ================================================= -->

    <div class="stat-card stat-red">


        <div class="stat-icon">

            <i class="fa-solid fa-money-bill-wave"></i>

        </div>


        <div class="stat-content">


            <span class="stat-title">

                <%= LanguageManager.get(
                        "dashboard.monthly.revenue",
                        session
                ) %>

            </span>


            <strong
                    class="stat-value"
                    id="monthlyRevenue">

                <%= String.format(
                        "%.2f DH",
                        monthlyRevenue
                ) %>

            </strong>


            <span class="stat-description">

                <%= LanguageManager.get(
                        "dashboard.monthly.revenue.description",
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


    <!-- =================================================
         OVERVIEW CHART
    ================================================= -->

    <div class="dashboard-widget overview-widget">


        <div class="widget-header">


            <h2>

                <%= LanguageManager.get(
                        "dashboard.centres.overview",
                        session
                ) %>

            </h2>


            <button
                    type="button"
                    class="widget-filter">

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


  <!-- =================================================
     SECTION 28 - RECENT CENTRES
================================================= -->

<div class="dashboard-widget recent-widget">


    <!-- =================================================
         SECTION 28.01 - RECENT HEADER
    ================================================= -->

    <div class="recent-widget-header">

        <div class="recent-widget-heading">

            <span class="recent-widget-eyebrow">
                <%= LanguageManager.get(
                        "dashboard.recent.centres",
                        session
                ) %>
            </span>

        </div>


        <button
                type="button"
                class="recent-widget-link">

            <span>
                <%= LanguageManager.get(
                        "dashboard.view.all",
                        session
                ) %>
            </span>

        </button>

    </div>


    <!-- =================================================
         SECTION 28.02 - RECENT BODY
    ================================================= -->

    <div class="recent-widget-body">


        <div
                class="recent-centres-list"
                id="recentCentresList">


            <%

            if (
                    recentCentres != null
                    &&
                    !recentCentres.isEmpty()
            ) {


                for (
                        Centre centre :
                        recentCentres
                ) {


                    /*
                    ==========================================
                    DURATION
                    ==========================================
                    */

                    int duration =
                            centre.getDurationMonths();


                    String durationText = "-";


                    if (duration == 1) {

                        durationText =
                                duration
                                + " "
                                + LanguageManager.get(
                                        "dashboard.month",
                                        session
                                );

                    }

                    else if (duration > 1) {

                        durationText =
                                duration
                                + " "
                                + LanguageManager.get(
                                        "dashboard.months",
                                        session
                                );

                    }


            %>


            <!-- =================================================
                 SECTION 28.03 - RECENT CENTRE ITEM
            ================================================= -->

            <div class="recent-centre-item">


                <!-- =================================================
                     CENTRE INFORMATION
                ================================================= -->

                <div class="recent-centre-info">


                    <!-- =================================================
                         CENTRE NAME
                    ================================================= -->

                    <div class="recent-centre-name">

                        <%= centre.getName() != null
                                &&
                                !centre.getName().trim().isEmpty()
                                ?
                                centre.getName()
                                :
                                "-"
                        %>

                    </div>


                </div>


                <!-- =================================================
                     PAYMENT DATE
                ================================================= -->

                <div class="recent-centre-date">

                    <%

                    if (centre.getCreatedAt() != null) {

                    %>

                        <span class="recent-date-value">

                            <%= recentDateFormat.format(
                                    centre.getCreatedAt()
                            ) %>

                        </span>

                    <%

                    }

                    else {

                    %>

                        <span class="recent-date-value">
                            -
                        </span>

                    <%

                    }

                    %>

                </div>


                <!-- =================================================
                     SUBSCRIPTION DURATION
                ================================================= -->

                <div class="recent-centre-duration">

                    <span class="recent-duration-value">

                        <%= durationText %>

                    </span>

                </div>


            </div>


            <%

                }

            }

            else {

            %>


            <!-- =================================================
                 SECTION 28.04 - EMPTY STATE
            ================================================= -->

            <div class="recent-empty">


                <div class="recent-empty-content">

                    <div class="recent-empty-title">

                        <%= LanguageManager.get(
                                "dashboard.no.recent.centres",
                                session
                        ) %>

                    </div>

                </div>


            </div>


            <%

            }

            %>


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


    <a
            class="notification-action"
            href="javascript:void(0);"
            onclick="loadContent('PaymentServlet?action=list&tab=UNPAID', null)">

        <%= LanguageManager.get(
                "dashboard.view.payments",
                session
        ) %>

    </a>


</section>


</div>