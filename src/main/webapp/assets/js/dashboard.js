/*
==================================================
 CENTRIA
 Dashboard JavaScript
==================================================
*/


/*
==================================================
 LOAD DASHBOARD CONTENT
==================================================
*/

/*
==============================================
LOAD DASHBOARD CONTENT
==============================================
*/

function loadContent(
    page,
    element
) {


    /*
    ==============================================
    ACTIVE MENU
    ==============================================
    */

    if (element) {

        document
            .querySelectorAll(
                ".sidebar-link"
            )
            .forEach(
                function (link) {

                    link.classList.remove(
                        "active"
                    );

                }
            );


        /*
        Only activate sidebar links.
        Do not add "active" to buttons
        such as Recent Centres -> View All.
        */

        if (
            element.classList
            &&
            element.classList.contains(
                "sidebar-link"
            )
        ) {

            element.classList.add(
                "active"
            );

        }

    }


    /*
    ==============================================
    DETERMINE SECTION
    ==============================================
    */

    let sectionName = "home";


    /*
    HOME
    */

    if (
        page.startsWith(
            "HomeServlet"
        )
    ) {

        sectionName = "home";

    }


    /*
    CENTRES
    */

    else if (
        page.startsWith(
            "CentreServlet"
        )
    ) {

        sectionName = "centres";

    }


    /*
    PAYMENTS
    */

    else if (
        page.startsWith(
            "PaymentServlet"
        )
    ) {

        sectionName = "payments";

    }


    /*
    ARCHIVE
    */

    else if (
        page.startsWith(
            "ArchiveServlet"
        )
    ) {

        sectionName = "archive";

    }


    /*
    NORMAL JSP PAGE
    */

    else if (
        page.endsWith(
            ".jsp"
        )
    ) {

        sectionName =
            page
                .replace(
                    ".jsp",
                    ""
                )
                .split("/")
                .pop();

    }


    /*
    ==============================================
    UPDATE BROWSER URL
    ==============================================
    */

    history.pushState(
        null,
        "",
        window.contextPath
        +
        "/admin/dashboard.jsp?section="
        +
        sectionName
    );


    /*
    ==============================================
    BUILD URL
    ==============================================
    */

    let url;


    /*
    HOME SERVLET
    */

    if (
        page.startsWith(
            "HomeServlet"
        )
    ) {

        url =
            window.contextPath
            +
            "/admin/home?ajax=true";

    }


    /*
    CENTRE / PAYMENT / ARCHIVE SERVLETS
    */

    else if (
        page.startsWith(
            "CentreServlet"
        )
        ||
        page.startsWith(
            "PaymentServlet"
        )
        ||
        page.startsWith(
            "ArchiveServlet"
        )
    ) {

        url =
            window.contextPath
            +
            "/"
            +
            page;

    }


    /*
    NORMAL JSP PAGES
    */

    else {

        url =
            window.contextPath
            +
            "/admin/pages/"
            +
            page;

    }


    console.log(
        "Loading:",
        url
    );

    console.log(
        "Section:",
        sectionName
    );


    /*
    ==============================================
    AJAX LOAD
    ==============================================
    */

    fetch(url)

        .then(
            response => {

                if (
                    !response.ok
                ) {

                    throw new Error(
                        response.status
                    );

                }


                return response.text();

            }
        )


        .then(
            html => {


                let container =
                    document.getElementById(
                        "content-area"
                    );


                if (container) {

                    container.innerHTML =
                        html;

                }


                /*
                ==========================================
                ACCUEIL
                ==========================================
                */

                if (
                    typeof initAccueilPage
                    ===
                    "function"
                ) {

                    if (
                        document.querySelector(
                            ".home-page"
                        )
                    ) {

                        initAccueilPage();

                    }

                }


                /*
                ==========================================
                CENTRES
                ==========================================
                */

                if (
                    typeof initCentresPage
                    ===
                    "function"
                ) {

                    if (
                        document.getElementById(
                            "centres-table-container"
                        )
                    ) {

                        initCentresPage();

                    }

                }


                /*
                ==========================================
                PAYMENTS
                ==========================================
                */

                if (
                    typeof initPaymentsPage
                    ===
                    "function"
                ) {

                    if (
                        document.getElementById(
                            "payments-table-container"
                        )
                    ) {

                        initPaymentsPage();

                    }

                }


                /*
                ==========================================
                ARCHIVE
                ==========================================
                */

                if (
                    typeof initArchivePage
                    ===
                    "function"
                ) {

                    if (
                        document.getElementById(
                            "archive-table-container"
                        )
                    ) {

                        initArchivePage();

                    }

                }

            }
        )


        .catch(
            error => {

                console.error(
                    "Dashboard loading error:",
                    error
                );

            }
        );

}

/*
==================================================
 SIDEBAR TOGGLE
==================================================
*/

function toggleSidebar() {


    let sidebar =

        document.querySelector(
            ".sidebar"
        );


    let appBody =

        document.querySelector(
            ".app-body"
        );


    if (!sidebar) {

        return;

    }


    sidebar.classList.toggle(
        "collapsed"
    );


    if (appBody) {

        appBody.classList.toggle(
            "sidebar-collapsed"
        );

    }


    /*
    Save user preference
    */

    let state =

        sidebar.classList.contains(
            "collapsed"
        )
        ?
        "collapsed"
        :
        "expanded";


    localStorage.setItem(
        "centria-sidebar",
        state
    );

}


/*
==================================================
 RESTORE SIDEBAR STATE
==================================================
*/

document.addEventListener(
    "DOMContentLoaded",
    function () {


        let sidebar =

            document.querySelector(
                ".sidebar"
            );


        if (!sidebar) {

            return;

        }


        let state =

            localStorage.getItem(
                "centria-sidebar"
            );


        if (
            state === "collapsed"
        ) {

            sidebar.classList.add(
                "collapsed"
            );

        }


        /*
        ==========================================
        INITIAL ACCUEIL
        ==========================================
        */

        if (
            document.querySelector(
                ".home-page"
            )
        ) {

            if (
                typeof initAccueilPage
                ===
                "function"
            ) {

                initAccueilPage();

            }

        }

    }
);


/*
==================================================
 RESTORE ARCHIVE AFTER PAGE REFRESH
==================================================
*/

document.addEventListener(
    "DOMContentLoaded",
    function () {

        /*
        --------------------------------------------------
        Read current section
        --------------------------------------------------
        */

        const params =
            new URLSearchParams(
                window.location.search
            );


        const section =
            params.get("section");


        /*
        --------------------------------------------------
        Archive only
        --------------------------------------------------
        */

        if (
            section === "archive"
        ) {

            console.log(
                "Restoring Archive after refresh"
            );


            loadContent(
                "ArchiveServlet?action=list",
                null
            );

        }

    }
);