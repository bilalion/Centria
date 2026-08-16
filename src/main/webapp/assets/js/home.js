/*
==================================================
 CENTRIA
 Accueil / Home JavaScript
==================================================
*/


/*
==================================================
 INITIALIZE ACCUEIL PAGE
==================================================
*/

function initAccueilPage() {


    /*
    ==============================================
    CHECK HOME PAGE
    ==============================================
    */

    const homePage =
        document.querySelector(
            ".home-page"
        );


    if (!homePage) {

        return;

    }


    /*
    ==============================================
    INITIALIZE HOME COMPONENTS
    ==============================================
    */

    initRecentCentres();

    initHomeActions();
    
    initHomeChart();
    
    initRevenuePeriod();

}


/*
==================================================
 RECENT CENTRES
==================================================
*/

function initRecentCentres() {


    const recentCentresList =
        document.getElementById(
            "recentCentresList"
        );


    if (!recentCentresList) {

        return;

    }


    /*
    Recent centres are already rendered
    by ahome.jsp

    JavaScript is only responsible
    for page initialization here.
    */

    const items =
        recentCentresList.querySelectorAll(
            ".recent-centre-item"
        );


    items.forEach(
        function (item) {

            item.classList.add(
                "is-loaded"
            );

        }
    );

}


/*
==================================================
 HOME ACTIONS
==================================================
*/

function initHomeActions() {


    /*
    ==============================================
    VIEW ALL CENTRES
    ==============================================
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
    ==============================================
    VIEW PAYMENTS
    ==============================================
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

                event.preventDefault();


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
==================================================
HOME CHARTS
==================================================
*/

function initHomeChart() {

    initMonthlyPaymentDonut();

    initCentreStatusBars();

}


/*
==================================================
MONTHLY PAYMENT DONUT
==================================================
*/

function initMonthlyPaymentDonut() {

    const donut =
        document.getElementById(
            "monthlyPaymentDonut"
        );


    if (!donut) {

        return;

    }


    /*
    ==========================================
    READ PAYMENT PERCENTAGE FROM JSP
    ==========================================
    */

    const centerValue =
        document.getElementById(
            "monthlyPaymentPercent"
        );


    if (!centerValue) {

        return;

    }


    const paid =
        parseFloat(
            centerValue.textContent
                .replace("%", "")
                .trim()
        ) || 0;


    const unpaid =
        100 - paid;


    /*
    ==========================================
    DRAW DONUT
    ==========================================
    */

 const primaryColor =
    getComputedStyle(document.documentElement)
        .getPropertyValue("--primary")
        .trim();

const primaryLightColor =
    getComputedStyle(document.documentElement)
        .getPropertyValue("--primary-light")
        .trim();

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
    ==========================================
    CENTER VALUE
    ==========================================
    */

    centerValue.textContent =
        Math.round(paid) + "%";

}


/*
==================================================
CENTRE STATUS BARS
==================================================
*/

function initCentreStatusBars() {

    const container =
        document.getElementById(
            "centreStatusBars"
        );


    if (!container) {

        return;

    }


    const rows =
        container.querySelectorAll(
            ".centre-status-bar-row"
        );


    if (!rows.length) {

        return;

    }


    /*
    ==========================================
    READ VALUES
    ==========================================
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
    ==========================================
    CALCULATE TOTAL
    ==========================================

    The total is the sum of all
    displayed centre statuses.

    Example:

    ACTIVE      = 4
    FOLLOW_UP   = 1
    INACTIVE    = 1
    ARCHIVED    = 1
    DELETED     = 1

    TOTAL       = 8
    */

    const totalValue =
        values.reduce(
            function (sum, value) {

                return sum + value;

            },
            0
        );


    /*
    ==========================================
    NO DATA
    ==========================================
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
    ==========================================
    DRAW BARS
    ==========================================
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
            ==========================================
            CALCULATE PERCENTAGE OF TOTAL
            ==========================================
            */

            const width =
                (
                    value
                    /
                    totalValue
                )
                *
                100;


            bar.style.width =
                width + "%";


        }
    );

}

/*
==================================================
 REVENUE PERIOD
==================================================
*/

/*
==================================================
 REVENUE PERIOD
==================================================
*/

/*
==================================================
 REVENUE PERIOD
==================================================
*/

function initRevenuePeriod() {

    const select =
        document.getElementById("revenuePeriod");

    if (!select) {
        return;
    }

    const card =
        select.closest(".stat-card");

    if (!card) {
        return;
    }


    /* ==================================================
       REVENUE VALUES
    ================================================== */

    const monthlyRevenue =
        parseFloat(
            card.getAttribute("data-monthly-revenue")
        ) || 0;

    const annualRevenue =
        parseFloat(
            card.getAttribute("data-annual-revenue")
        ) || 0;


    /* ==================================================
       TRANSLATED TEXT
    ================================================== */

    const monthlyTitle =
        card.getAttribute("data-monthly-title") || "";

    const annualTitle =
        card.getAttribute("data-annual-title") || "";

    const monthlyDescription =
        card.getAttribute("data-monthly-description") || "";

    const annualDescription =
        card.getAttribute("data-annual-description") || "";


    /* ==================================================
       TARGET ELEMENTS
    ================================================== */

    const title =
        document.getElementById("revenueTitle");

    const value =
        document.getElementById("revenueValue");

    const description =
        document.getElementById("revenueDescription");


    if (!title || !value || !description) {
        return;
    }


    /* ==================================================
       HIDE / SHOW STATE
    ================================================== */

    let revenueHidden = true;


    /* ==================================================
       UPDATE REVENUE
    ================================================== */

    function updateRevenue() {

        let revenue;
        let currentTitle;
        let currentDescription;


        if (select.value === "year") {

            revenue = annualRevenue;
            currentTitle = annualTitle;
            currentDescription = annualDescription;

        } else {

            revenue = monthlyRevenue;
            currentTitle = monthlyTitle;
            currentDescription = monthlyDescription;

        }


        title.textContent =
            currentTitle;

        description.textContent =
            currentDescription;


        if (revenueHidden) {

            value.textContent ="*****";

        } else {

            value.textContent =
                revenue.toFixed(2) + " DH";
        }


        value.style.direction = "ltr";
        value.style.unicodeBidi = "isolate";
    }


    /* ==================================================
       PERIOD CHANGE
    ================================================== */

    select.onchange =
        updateRevenue;


    /* ==================================================
       CARD CLICK - HIDE / SHOW
    ================================================== */

    card.addEventListener(
        "click",
        function(event) {

            /*
             * Don't trigger Hide/Show when
             * using the period selector.
             */

            if (
                event.target === select ||
                select.contains(event.target)
            ) {
                return;
            }


            revenueHidden =
                !revenueHidden;


            updateRevenue();

        }
    );


    /* ==================================================
       CARD INTERACTION
    ================================================== */

    card.style.cursor =
        "pointer";


    /* ==================================================
       INITIAL VALUE
    ================================================== */

    updateRevenue();

}


/*
==================================================
 DOM READY
==================================================
*/

document.addEventListener(
    "DOMContentLoaded",
    function () {


        /*
        Only initialize if Home
        is already displayed.
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

