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
    by accueil.jsp.

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
 HOME CHART
==================================================
*/

function initHomeChart() {


    /*
    Chart data will be connected later
    when HomeDAO provides the overview
    data.

    Nothing is rendered here yet.
    */

    const chart =
        document.querySelector(
            ".chart-placeholder"
        );


    if (!chart) {

        return;

    }


    /*
    Keep the current placeholder
    until the chart data is implemented.
    */

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