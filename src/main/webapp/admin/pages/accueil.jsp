<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<!-- =================================================
     HOME HEADER
     ================================================= -->

<div class="dashboard-header">

    <h1>
        <%= LanguageManager.get("dashboard.main.title", session) %>
    </h1>

    <p>
        <%= LanguageManager.get("dashboard.card.text", session) %>
    </p>

</div>


<!-- =================================================
     STATISTICS
     ================================================= -->

<div class="stats-container">

    <div class="card dashboard-card blue-card">

        <div class="card-icon">🏢</div>

        <div class="card-info">

            <h3>
                <%= LanguageManager.get("dashboard.total.centers", session) %>
            </h3>

            <strong>0</strong>

        </div>

    </div>


    <div class="card dashboard-card green-card">

        <div class="card-icon">✅</div>

        <div class="card-info">

            <h3>
                <%= LanguageManager.get("dashboard.active.centers", session) %>
            </h3>

            <strong>0</strong>

        </div>

    </div>


    <div class="card dashboard-card orange-card">

        <div class="card-icon">⏳</div>

        <div class="card-info">

            <h3>
                <%= LanguageManager.get("dashboard.pending.centers", session) %>
            </h3>

            <strong>0</strong>

        </div>

    </div>


    <div class="card dashboard-card red-card">

        <div class="card-icon">💰</div>

        <div class="card-info">

            <h3>
                <%= LanguageManager.get("dashboard.unpaid.centers", session) %>
            </h3>

            <strong>0</strong>

        </div>

    </div>

</div>


<!-- =================================================
     WELCOME CARD
     ================================================= -->

<div class="card welcome-card">

    <div class="welcome-icon">
        🚀
    </div>

    <div class="welcome-content">

        <h2>
            <%= LanguageManager.get("dashboard.card.title", session) %>
        </h2>

        <p>
            <%= LanguageManager.get("dashboard.card.text", session) %>
        </p>

    </div>

</div>