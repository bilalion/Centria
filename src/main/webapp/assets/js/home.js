/*
==========================================================
01 - CENTRIA
    Accueil / Home JavaScript
==========================================================
*/


/*
==========================================================
01.01 - INITIALIZE HOME PAGE
==========================================================
*/

function initAccueilPage() {

    /*
    ======================================================
    01.01.01 - CHECK HOME PAGE
    ======================================================
    */

    const homePage =
        document.querySelector(
            ".home-page"
        );


    if (!homePage) {

        return;

    }


    /*
    ======================================================
    01.01.02 - INITIALIZE HOME COMPONENTS
    ======================================================
    */

    initRecentCentres();

    initHomeActions();

    initHomeChart();

    initRevenuePeriod();

}


/*
==========================================================
01.02 - RECENT CENTRES
==========================================================
*/

function initRecentCentres() {

    /*
    ======================================================
    01.02.01 - GET RECENT CENTRES CONTAINER
    ======================================================
    */

    const recentCentresList =
        document.getElementById(
            "recentCentresList"
        );


    if (!recentCentresList) {

        return;

    }


    /*
    ======================================================
    01.02.02 - RECENT CENTRES ARE SERVER RENDERED
    ======================================================

    The recent centres are already rendered
    by home.jsp.

    JavaScript only handles the visual
    initialization state.
    ======================================================
    */

    const items =
        recentCentresList.querySelectorAll(
            ".recent-centre-item"
        );


    /*
    ======================================================
    01.02.03 - MARK ITEMS AS LOADED
    ======================================================
    */

    items.forEach(
        function (item) {

            item.classList.add(
                "is-loaded"
            );

        }
    );

}


/*
==========================================================
01.03 - HOME ACTIONS
==========================================================
*/

function initHomeActions() {

    /*
    ======================================================
    01.03.01 - VIEW ALL CENTRES
    ======================================================
    */

    const viewAllButton =
        document.querySelector(
            ".recent-widget .widget-link"
        );


    if (
        viewAllButton
        &&
        !viewAllButton.dataset.initialized
    ) {

        viewAllButton.dataset.initialized =
            "true";


        viewAllButton.addEventListener(
            "click",
            function () {

                /*
                ==================================================
                01.03.01.01 - LOAD CENTRES
                ==================================================
                */

                if (
                    typeof loadContent
                    ===
                    "function"
                ) {

                    loadContent(
                        "CentreServlet?action=list",
                        this
                    );

                }

            }
        );

    }


    /*
    ======================================================
    01.03.02 - VIEW PAYMENTS
    ======================================================
    */

    const paymentsAction =
        document.querySelector(
            ".notification-action"
        );


    if (
        paymentsAction
        &&
        !paymentsAction.dataset.initialized
    ) {

        paymentsAction.dataset.initialized =
            "true";


        paymentsAction.addEventListener(
            "click",
            function (event) {

                /*
                ==================================================
                01.03.02.01 - PREVENT DEFAULT LINK
                ==================================================
                */

                event.preventDefault();


                /*
                ==================================================
                01.03.02.02 - LOAD PAYMENTS
                ==================================================
                */

                if (
                    typeof loadContent
                    ===
                    "function"
                ) {

                    loadContent(
                        "payments.jsp",
                        document.getElementById(
                            "sidebar-payments"
                        )
                    );

                }

            }
        );

    }

}


/*
==========================================================
01.04 - HOME CHARTS
==========================================================
*/

function initHomeChart() {

    /*
    ======================================================
    01.04.01 - MONTHLY PAYMENT DONUT
    ======================================================
    */

    initMonthlyPaymentDonut();


    /*
    ======================================================
    01.04.02 - CENTRE STATUS BARS
    ======================================================
    */

    initCentreStatusBars();

}


/*
==========================================================
01.05 - MONTHLY PAYMENT DONUT
==========================================================
*/

function initMonthlyPaymentDonut() {

    /*
    ======================================================
    01.05.01 - GET DONUT
    ======================================================
    */

    const donut =
        document.getElementById(
            "monthlyPaymentDonut"
        );


    if (!donut) {

        return;

    }


    /*
    ======================================================
    01.05.02 - GET CENTER VALUE
    ======================================================
    */

    const centerValue =
        document.getElementById(
            "monthlyPaymentPercent"
        );


    if (!centerValue) {

        return;

    }


    /*
    ======================================================
    01.05.03 - READ PAYMENT PERCENTAGE
    ======================================================
    */

    const paid =
        parseFloat(
            centerValue.textContent
                .replace("%", "")
                .trim()
        ) || 0;


    /*
    ======================================================
    01.05.04 - CALCULATE UNPAID
    ======================================================
    */

    const unpaid =
        100 - paid;


    /*
    ======================================================
    01.05.05 - READ CENTRIA COLORS
    ======================================================
    */

    const primaryColor =
        getComputedStyle(
            document.documentElement
        )
        .getPropertyValue(
            "--primary"
        )
        .trim();


    const primaryLightColor =
        getComputedStyle(
            document.documentElement
        )
        .getPropertyValue(
            "--primary-light"
        )
        .trim();


    /*
    ======================================================
    01.05.06 - DRAW DONUT
    ======================================================
    */

    donut.style.background =
        "conic-gradient(" +

        primaryColor +

        " 0% " +

        paid +

        "%, " +

        primaryLightColor +

        " " +

        paid +

        "% 100%" +

        ")";


    /*
    ======================================================
    01.05.07 - UPDATE CENTER VALUE
    ======================================================
    */

    centerValue.textContent =
        Math.round(paid) + "%";

}


/*
==========================================================
01.06 - CENTRE STATUS BARS
==========================================================
*/

function initCentreStatusBars() {

    /*
    ======================================================
    01.06.01 - GET STATUS CONTAINER
    ======================================================
    */

    const container =
        document.getElementById(
            "centreStatusBars"
        );


    if (!container) {

        return;

    }


    /*
    ======================================================
    01.06.02 - GET STATUS ROWS
    ======================================================
    */

    const rows =
        container.querySelectorAll(
            ".centre-status-bar-row"
        );


    if (!rows.length) {

        return;

    }


    /*
    ======================================================
    01.06.03 - READ STATUS VALUES
    ======================================================
    */

    const values = [];


    rows.forEach(
        function (row) {

            const valueElement =
                row.querySelector(
                    ".centre-status-value"
                );


            const value =
                valueElement
                ?
                parseInt(
                    valueElement.textContent.trim(),
                    10
                ) || 0
                :
                0;


            values.push(
                value
            );

        }
    );


    /*
    ======================================================
    01.06.04 - CALCULATE TOTAL
    ======================================================

    The total is the sum of all displayed
    centre statuses.

    Example:

    ACTIVE      = 4
    FOLLOW_UP   = 1
    INACTIVE    = 1
    ARCHIVED    = 1
    DELETED     = 1

    TOTAL       = 8
    ======================================================
    */

    const totalValue =
        values.reduce(
            function (
                sum,
                value
            ) {

                return sum + value;

            },
            0
        );


    /*
    ======================================================
    01.06.05 - NO DATA
    ======================================================
    */

    if (totalValue <= 0) {

        rows.forEach(
            function (row) {

                const bar =
                    row.querySelector(
                        ".centre-status-bar"
                    );


                if (bar) {

                    bar.style.width =
                        "0%";

                }

            }
        );


        return;

    }


    /*
    ======================================================
    01.06.06 - DRAW STATUS BARS
    ======================================================
    */

    rows.forEach(
        function (
            row,
            index
        ) {

            const bar =
                row.querySelector(
                    ".centre-status-bar"
                );


            if (!bar) {

                return;

            }


            const value =
                values[index];


            /*
            ==================================================
            01.06.06.01 - CALCULATE PERCENTAGE
            ==================================================
            */

            const width =
                (
                    value
                    /
                    totalValue
                )
                *
                100;


            /*
            ==================================================
            01.06.06.02 - APPLY WIDTH
            ==================================================
            */

            bar.style.width =
                width + "%";

        }
    );

}


/*
==========================================================
01.07 - REVENUE PERIOD
==========================================================
*/

function initRevenuePeriod() {

    /*
    ======================================================
    01.07.01 - GET PERIOD SELECT
    ======================================================
    */

    const select =
        document.getElementById(
            "revenuePeriod"
        );


    if (!select) {

        return;

    }


    /*
    ======================================================
    01.07.02 - GET REVENUE CARD
    ======================================================
    */

    const card =
        select.closest(
            ".stat-card"
        );


    if (!card) {

        return;

    }


    /*
    ======================================================
    01.07.03 - REVENUE VALUES
    ======================================================
    */

    const monthlyRevenue =
        parseFloat(
            card.getAttribute(
                "data-monthly-revenue"
            )
        ) || 0;


    const annualRevenue =
        parseFloat(
            card.getAttribute(
                "data-annual-revenue"
            )
        ) || 0;


    /*
    ======================================================
    01.07.04 - TRANSLATED TEXT
    ======================================================
    */

    const monthlyTitle =
        card.getAttribute(
            "data-monthly-title"
        ) || "";


    const annualTitle =
        card.getAttribute(
            "data-annual-title"
        ) || "";


    const monthlyDescription =
        card.getAttribute(
            "data-monthly-description"
        ) || "";


    const annualDescription =
        card.getAttribute(
            "data-annual-description"
        ) || "";


    /*
    ======================================================
    01.07.05 - TARGET ELEMENTS
    ======================================================
    */

    const title =
        document.getElementById(
            "revenueTitle"
        );


    const value =
        document.getElementById(
            "revenueValue"
        );


    const description =
        document.getElementById(
            "revenueDescription"
        );


    if (
        !title
        ||
        !value
        ||
        !description
    ) {

        return;

    }


    /*
    ======================================================
    01.07.06 - REVENUE VISIBILITY STATE
    ======================================================
    */

    let revenueHidden =
        true;


    /*
    ======================================================
    01.07.07 - UPDATE REVENUE
    ======================================================
    */

    function updateRevenue() {

        let revenue;

        let currentTitle;

        let currentDescription;


        /*
        ==================================================
        01.07.07.01 - YEAR
        ==================================================
        */

        if (
            select.value ===
            "year"
        ) {

            revenue =
                annualRevenue;

            currentTitle =
                annualTitle;

            currentDescription =
                annualDescription;

        }


        /*
        ==================================================
        01.07.07.02 - MONTH
        ==================================================
        */

        else {

            revenue =
                monthlyRevenue;

            currentTitle =
                monthlyTitle;

            currentDescription =
                monthlyDescription;

        }


        /*
        ==================================================
        01.07.07.03 - UPDATE TEXT
        ==================================================
        */

        title.textContent =
            currentTitle;


        description.textContent =
            currentDescription;


        /*
        ==================================================
        01.07.07.04 - HIDE / SHOW VALUE
        ==================================================
        */

        if (revenueHidden) {

            value.textContent =
                "\u2605\u2605\u2605\u2605\u2605";


            value.classList.add(
                "revenue-hidden"
            );

        }

        else {

            value.textContent =
                revenue.toFixed(2)
                +
                " DH";


            value.classList.remove(
                "revenue-hidden"
            );

        }


        /*
        ==================================================
        01.07.07.05 - FORCE LTR MONEY DISPLAY
        ==================================================
        */

        value.style.direction =
            "ltr";


        value.style.unicodeBidi =
            "isolate";

    }


    /*
    ======================================================
    01.07.08 - PERIOD CHANGE
    ======================================================
    */

    select.onchange =
        updateRevenue;


    /*
    ======================================================
    01.07.09 - CARD CLICK
    ======================================================
    */

    card.addEventListener(
        "click",
        function (event) {

            /*
            ==================================================
            01.07.09.01 - IGNORE SELECT CLICK
            ==================================================
            */

            if (
                event.target === select
                ||
                select.contains(
                    event.target
                )
            ) {

                return;

            }


            /*
            ==================================================
            01.07.09.02 - TOGGLE VISIBILITY
            ==================================================
            */

            revenueHidden =
                !revenueHidden;


            updateRevenue();

        }
    );


    /*
    ======================================================
    01.07.10 - CARD POINTER
    ======================================================
    */

    card.style.cursor =
        "pointer";


    /*
    ======================================================
    01.07.11 - INITIAL VALUE
    ======================================================
    */

    updateRevenue();

}


/*
==========================================================
01.08 - DOM READY
==========================================================
*/

document.addEventListener(
    "DOMContentLoaded",
    function () {

        /*
        ==================================================
        01.08.01 - INITIALIZE ONLY WHEN HOME EXISTS
        ==================================================
        */

        if (
            document.querySelector(
                ".home-page"
            )
        ) {

            initAccueilPage();

        }

    }
);