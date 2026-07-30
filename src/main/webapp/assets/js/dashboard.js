/*
==========================================================
 CENTRIA
 Dashboard JavaScript
 Clean Professional Version
==========================================================
*/


/* ======================================================
   01 - LOAD DASHBOARD CONTENT
   ====================================================== */


function loadContent(
    page,
    element
){



    /* ==================================================
       ACTIVE MENU HANDLING
       ================================================== */


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


    }







    /* ==================================================
       BUILD REQUEST URL
       ================================================== */


    let url;



    /*
       Servlet pages
       Example:
       CentreServlet?action=list
    */


    if(
        page.startsWith(
            "CentreServlet"
        )
    ){


        url =
        window.contextPath
        +
        "/"
        +
        page;


    }




    /*
       Normal JSP pages
    */


    else {


        url =
        window.contextPath
        +
        "/admin/pages/"
        +
        page;


    }








    /* ==================================================
       AJAX LOAD CONTENT
       ================================================== */


    fetch(url)



    .then(
        response => {



            if(!response.ok){


                throw new Error(
                    "HTTP ERROR : "
                    +
                    response.status
                );


            }



            return response.text();



        }
    )





    .then(
        data => {



            let container =
            document.getElementById(
                "content-area"
            );



            if(container){



                container.innerHTML =
                data;



            }







            /*
             * CENTRES MODULE INITIALIZATION
             */


            if(
                page.includes(
                    "CentreServlet"
                )
                &&
                typeof initCentresPage === "function"
            ){


                initCentresPage();


            }



        }
    )






    .catch(
        error => {



            let container =
            document.getElementById(
                "content-area"
            );



            if(container){



                container.innerHTML =


                `
                <div class="card">

                    Error loading content

                </div>
                `;


            }



        }
    );



}