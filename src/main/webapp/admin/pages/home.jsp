<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.language.LanguageManager"%>
<%@page import="com.centria.models.Centre"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
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
OVERVIEW CHART DATA
=================================================
*/

Map<String, Integer> monthlyPaymentStatus =
        (Map<String, Integer>) request.getAttribute(
                "monthlyPaymentStatus"
        );


Map<String, Integer> centreStatusOverview =
        (Map<String, Integer>) request.getAttribute(
                "centreStatusOverview"
        );


/*
=================================================
OVERVIEW CALCULATED VALUES
=================================================
*/

int paidCount =
        monthlyPaymentStatus != null
        &&
        monthlyPaymentStatus.get("PAID") != null
        ?
        monthlyPaymentStatus.get("PAID")
        :
        0;


int unpaidCount =
        monthlyPaymentStatus != null
        &&
        monthlyPaymentStatus.get("UNPAID") != null
        ?
        monthlyPaymentStatus.get("UNPAID")
        :
        0;


int paymentTotal =
        paidCount
        +
        unpaidCount;


int paidPercent =
        paymentTotal > 0
        ?
        (int) Math.round(
                ((double) paidCount / paymentTotal) * 100
        )
        :
        0;


int unpaidPercent =
        100
        -
        paidPercent;


int activeCount =
        centreStatusOverview != null
        &&
        centreStatusOverview.get("ACTIVE") != null
        ?
        centreStatusOverview.get("ACTIVE")
        :
        0;


int followUpCount =
        centreStatusOverview != null
        &&
        centreStatusOverview.get("FOLLOW_UP") != null
        ?
        centreStatusOverview.get("FOLLOW_UP")
        :
        0;


int inactiveCount =
        centreStatusOverview != null
        &&
        centreStatusOverview.get("INACTIVE") != null
        ?
        centreStatusOverview.get("INACTIVE")
        :
        0;


int archivedCount =
        centreStatusOverview != null
        &&
        centreStatusOverview.get("ARCHIVED") != null
        ?
        centreStatusOverview.get("ARCHIVED")
        :
        0;


int deletedCount =
        centreStatusOverview != null
        &&
        centreStatusOverview.get("DELETED") != null
        ?
        centreStatusOverview.get("DELETED")
        :
        0;


int maxStatusCount =
        Math.max(
                activeCount,
                Math.max(
                        followUpCount,
                        Math.max(
                                inactiveCount,
                                Math.max(
                                        archivedCount,
                                        deletedCount
                                )
                        )
                )
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


if (monthlyPaymentStatus == null) {

    monthlyPaymentStatus =
            new java.util.LinkedHashMap<String, Integer>();

}


if (centreStatusOverview == null) {

    centreStatusOverview =
            new java.util.LinkedHashMap<String, Integer>();

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
     OVERVIEW CHARTS
================================================= -->

<div class="dashboard-widget overview-widget">


    <!-- =================================================
         OVERVIEW HEADER
    ================================================= -->

    <div class="widget-header">


        <div class="overview-header-title">

            <h2>

                <%= LanguageManager.get(
                        "dashboard.centres.overview",
                        session
                ) %>

            </h2>

        </div>


        <button
                type="button"
                class="widget-filter"
                id="overviewYearFilter">

            <%= LanguageManager.get(
                    "dashboard.this.year",
                    session
            ) %>

            <i class="fa-solid fa-chevron-down"></i>

        </button>


    </div>


    <!-- =================================================
         OVERVIEW BODY
    ================================================= -->

    <div class="widget-body">


        <div class="overview-charts-grid">


            <!-- =================================================
                 CHART 01
                 MONTHLY PAYMENT STATUS
            ================================================= -->

            <div
                    class="overview-chart-card"
                    id="monthlyPaymentChart"
            >


                <div class="overview-chart-card-header">


                    <div class="overview-chart-card-title">

                        <i
                                class="fa-solid fa-money-check-dollar"
                                aria-hidden="true">
                        </i>

                        <span>

                            <%= LanguageManager.get(
                                    "dashboard.payment.status",
                                    session
                            ) %>

                        </span>

                    </div>


                    <span class="overview-chart-card-subtitle">

                        <%= LanguageManager.get(
                                "dashboard.this.month",
                                session
                        ) %>

                    </span>


                </div>


                <div class="overview-chart-content">


                    <!-- DONUT PLACEHOLDER -->

                    <div
                            class="monthly-payment-donut"
                            id="monthlyPaymentDonut"
                    >

                        <div class="monthly-payment-donut-center">

                            <strong id="monthlyPaymentPercent">
                                <%= paidPercent %>%
                            </strong>

                            <span>

                                <%= LanguageManager.get(
                                        "dashboard.paid",
                                        session
                                ) %>

                            </span>

                        </div>

                    </div>


                    <!-- LEGEND -->

                    <div
                            class="monthly-payment-legend"
                            id="monthlyPaymentLegend"
                    >


                        <div
                                class="payment-legend-item"
                                data-status="PAID"
                        >

                            <span
                                    class="payment-legend-marker paid">
                            </span>


                            <span class="payment-legend-label">

                                <%= LanguageManager.get(
                                        "dashboard.paid",
                                        session
                                ) %>

                            </span>


                            <strong class="payment-legend-value">

                                <%= paidPercent %>%

                            </strong>

                        </div>


                        <div
                                class="payment-legend-item"
                                data-status="UNPAID"
                        >

                            <span
                                    class="payment-legend-marker unpaid">
                            </span>


                            <span class="payment-legend-label">

                                <%= LanguageManager.get(
                                        "dashboard.unpaid",
                                        session
                                ) %>

                            </span>


                            <strong class="payment-legend-value">

                                <%= unpaidPercent %>%

                            </strong>

                        </div>


                    </div>


                </div>


            </div>


            <!-- =================================================
                 CHART 02
                 CENTRE STATUS
            ================================================= -->

            <div
                    class="overview-chart-card"
                    id="centreStatusChart"
            >


                <div class="overview-chart-card-header">


                    <div class="overview-chart-card-title">

                        <i
                                class="fa-solid fa-chart-column"
                                aria-hidden="true">
                        </i>

                        <span>

                            <%= LanguageManager.get(
                                    "dashboard.centre.status",
                                    session
                            ) %>

                        </span>

                    </div>


                    <span class="overview-chart-card-subtitle">

                        <%= LanguageManager.get(
                                "dashboard.all.centres",
                                session
                        ) %>

                    </span>


                </div>


                <div class="overview-chart-content status-chart-content">


                    <!-- BAR CHART AREA -->

                    <div
                            class="centre-status-bars"
                            id="centreStatusBars"
                    >


                        <!--
                        ==========================================
                        STATIC STRUCTURE ONLY
                        ==========================================

                        Data will be connected later
                        through HomeDAO + HomeServlet + JS.
                        -->


                        <div
                                class="centre-status-bar-row"
                                data-status="ACTIVE"
                        >

                            <div class="centre-status-bar-header">

                                <span class="centre-status-name">

                                    <%= LanguageManager.get(
                                            "dashboard.status.active",
                                            session
                                    ) %>

                                </span>


                                <strong class="centre-status-value">

                                    <%= activeCount %>

                                </strong>

                            </div>


                            <div class="centre-status-track">

                                <div
                                        class="centre-status-bar active"
                                        style="width: <%= maxStatusCount > 0 ? ((activeCount * 100) / maxStatusCount) : 0 %>%;">
                                </div>

                            </div>

                        </div>


                        <div
                                class="centre-status-bar-row"
                                data-status="FOLLOW_UP"
                        >

                            <div class="centre-status-bar-header">

                                <span class="centre-status-name">

                                    <%= LanguageManager.get(
                                            "dashboard.status.follow.up",
                                            session
                                    ) %>

                                </span>


                                <strong class="centre-status-value">

                                    <%= followUpCount %>

                                </strong>

                            </div>


                            <div class="centre-status-track">

                                <div
                                        class="centre-status-bar follow-up"
                                        style="width: <%= maxStatusCount > 0 ? ((followUpCount * 100) / maxStatusCount) : 0 %>%;">
                                </div>

                            </div>

                        </div>


                        <div
                                class="centre-status-bar-row"
                                data-status="INACTIVE"
                        >

                            <div class="centre-status-bar-header">

                                <span class="centre-status-name">

                                    <%= LanguageManager.get(
                                            "dashboard.status.inactive",
                                            session
                                    ) %>

                                </span>


                                <strong class="centre-status-value">

                                    <%= inactiveCount %>

                                </strong>

                            </div>


                            <div class="centre-status-track">

                                <div
                                        class="centre-status-bar inactive"
                                        style="width: <%= maxStatusCount > 0 ? ((inactiveCount * 100) / maxStatusCount) : 0 %>%;">
                                </div>

                            </div>

                        </div>


                        <div
                                class="centre-status-bar-row"
                                data-status="ARCHIVED"
                        >

                            <div class="centre-status-bar-header">

                                <span class="centre-status-name">

                                    <%= LanguageManager.get(
                                            "dashboard.status.archived",
                                            session
                                    ) %>

                                </span>


                                <strong class="centre-status-value">

                                    <%= archivedCount %>

                                </strong>

                            </div>


                            <div class="centre-status-track">

                                <div
                                        class="centre-status-bar archived"
                                        style="width: <%= maxStatusCount > 0 ? ((archivedCount * 100) / maxStatusCount) : 0 %>%;">
                                </div>

                            </div>

                        </div>


                        <div
                                class="centre-status-bar-row"
                                data-status="DELETED"
                        >

                            <div class="centre-status-bar-header">

                                <span class="centre-status-name">

                                    <%= LanguageManager.get(
                                            "dashboard.status.deleted",
                                            session
                                    ) %>

                                </span>


                                <strong class="centre-status-value">

                                    <%= deletedCount %>

                                </strong>

                            </div>


                            <div class="centre-status-track">

                                <div
                                        class="centre-status-bar deleted"
                                        style="width: <%= maxStatusCount > 0 ? ((deletedCount * 100) / maxStatusCount) : 0 %>%;">
                                </div>

                            </div>

                        </div>


                    </div>


                </div>


            </div>


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


 <a
    href="javascript:void(0);"
    class="recent-widget-link"
   onclick="document.getElementById('sidebar-centres').click();">

    <span>
        <%= LanguageManager.get(
                "dashboard.view.all",
                session
        ) %>
    </span>

</a>

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


<%
int pendingPaymentsCount =
        request.getAttribute("pendingPaymentsCount") != null
        ?
        (Integer) request.getAttribute("pendingPaymentsCount")
        :
        0;


String pendingPaymentsMessage =
        LanguageManager.get(
                "dashboard.pending.payments.message",
                session
        ).replace(
                "{0}",
                String.valueOf(pendingPaymentsCount)
        );
%>


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

            <%= pendingPaymentsMessage %>

        </p>


    </div>


    <a
        class="notification-action"
        href="javascript:void(0);"
        onclick="document.getElementById('sidebar-payments').click();">

        <%= LanguageManager.get(
                "dashboard.view.payments",
                session
        ) %>

    </a>


</section>


</div>