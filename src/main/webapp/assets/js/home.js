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
                        "PaymentServlet?action=list&tab=UNPAID",
                        null
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

    donut.style.background =
        "conic-gradient(" +

        "#22c55e 0% " +
        paid +
        "%, " +

        "#f43f5e " +
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
    FIND MAXIMUM
    ==========================================
    */

    const maxValue =
        Math.max.apply(
            null,
            values
        );


    /*
    ==========================================
    NO DATA
    ==========================================
    */

    if (maxValue <= 0) {

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


            const width =
                (
                    value
                    /
                    maxValue
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