/*
 * Centria
 * Super Admin Dashboard
 */


/*
 * Load Dashboard Content
 */

function loadContent(page, element) {


    /*
     * Active Menu Handling
     */

    if(element){


        document
        .querySelectorAll(".menu-link")
        .forEach(function(link){

            link.classList.remove("active");

        });



        element.classList.add("active");


    }





    let url;




    /*
     * Servlet loading
     * Example:
     * CentreServlet?action=list
     */

    if(page.startsWith("CentreServlet")) {


        url =
        window.contextPath +
        "/" +
        page;


    }





    /*
     * Normal JSP pages
     */

    else {


        url =
        window.contextPath +
        "/admin/pages/" +
        page;


    }






    console.log("Loading:", url);



    fetch(url)



    .then(response => {



        if(!response.ok) {


            throw new Error(
                "HTTP ERROR : " + response.status
            );


        }



        return response.text();



    })






    .then(data => {



        document.getElementById(
            "content-area"
        ).innerHTML = data;





        /*
         * Initialize Centres Page
         */

        if(
            page.includes("CentreServlet")
            &&
            typeof initCentresPage === "function"
        ){


            initCentresPage();


        }



    })







    .catch(error => {



        console.error(
            "Loading error:",
            error
        );



        document.getElementById(
            "content-area"
        ).innerHTML =

        `
        <div class="card">
            Error loading content
        </div>
        `;



    });



}