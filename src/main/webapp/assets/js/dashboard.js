/*
==========================================================
 CENTRIA
 Dashboard JavaScript

 AJAX CONTENT LOADER
 MODULE INITIALIZATION
==========================================================
*/



/*
==========================================================
 LOAD DASHBOARD CONTENT
==========================================================
*/


/*
==========================================================
 CENTRIA
 Dashboard JavaScript

 AJAX CONTENT LOADER
 MODULE INITIALIZATION
==========================================================
*/


function loadContent(
    page,
    element
){



    /*
    ==============================================
    ACTIVE MENU
    ==============================================
    */


    if(element){


        /*
        Remove active from all sidebar links
        */

        document
        .querySelectorAll(
            ".sidebar-link"
        )
        .forEach(
            function(link){

                link.classList.remove(
                    "active"
                );

            }
        );



        /*
        Add active to clicked link
        */

        element.classList.add(
            "active"
        );





        /*
        ==============================================
        UPDATE BROWSER URL
        ==============================================
        */


        let sectionName = page;



        /*
        CentreServlet
        */

        if(
            page.startsWith(
                "CentreServlet"
            )
        ){

            sectionName = "centres";

        }




        /*
        PaymentServlet
        */

        if(
            page.startsWith(
                "PaymentServlet"
            )
        ){

            sectionName = "payments";

        }

/*
ArchiveServlet
*/

if(
    page.startsWith(
        "ArchiveServlet"
    )
){

    sectionName = "archive";

}



        /*
        Normal JSP pages
        */

        if(
            page.includes("/")
        ){

            sectionName =

            page
            .replace(".jsp","")
            .split("/")
            .pop();

        }




        history.pushState(
            null,
            "",
            window.contextPath
            +
            "/admin/dashboard.jsp?section="
            +
            sectionName
        );


    }







    /*
    ==============================================
    BUILD URL
    ==============================================
    */


    let url;



 if(
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
){

    url =

    window.contextPath
    +
    "/"
    +
    page;

}

    else{


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







    /*
    ==============================================
    AJAX LOAD
    ==============================================
    */


    fetch(url)


    .then(
        response => {


            if(
                !response.ok
            ){

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



            if(container){


                container.innerHTML =
                html;


            }







            /*
            ==========================================
            MODULE INITIALIZATION
            ==========================================
            */





            /*
            CENTRES
            */

            if(
                typeof initCentresPage
                ===
                "function"
            ){


                if(
                    document.getElementById(
                        "centres-table-container"
                    )
                ){


                    initCentresPage();


                }


            }








            /*
            PAYMENTS
            */

            if(
                typeof initPaymentsPage
                ===
                "function"
            ){


                if(
                    document.getElementById(
                        "payments-table-container"
                    )
                ){


                    initPaymentsPage();


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
==========================================================
 SIDEBAR TOGGLE
==========================================================
*/


function toggleSidebar(){


    let sidebar =

    document.querySelector(
        ".sidebar"
    );



    let appBody =

    document.querySelector(
        ".app-body"
    );



    if(!sidebar){

        return;

    }





    sidebar.classList.toggle(
        "collapsed"
    );





    if(appBody){


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
==========================================================
 RESTORE SIDEBAR STATE
==========================================================
*/


document.addEventListener(
"DOMContentLoaded",
function(){



    let sidebar =

    document.querySelector(
        ".sidebar"
    );



    if(!sidebar){

        return;

    }





    let state =

    localStorage.getItem(
        "centria-sidebar"
    );





    if(
        state === "collapsed"
    ){


        sidebar.classList.add(
            "collapsed"
        );



    }



});

