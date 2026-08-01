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


        document
        .querySelectorAll(
            ".menu-link"
        )
        .forEach(
            function(link){

                link.classList.remove(
                    "active"
                );

            }
        );


        element.classList.add(
            "active"
        );
/*
==================================================
UPDATE BROWSER URL
==================================================
*/

let sectionName = page;


/*
CentreServlet
*/

if(page.startsWith("CentreServlet")){

    sectionName = "centres";

}


/*
PaymentServlet
*/

if(page.startsWith("PaymentServlet")){

    sectionName = "payments";

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


            if(!response.ok){

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