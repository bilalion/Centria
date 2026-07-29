/*
 * Centria
 * Centres Management JavaScript
 *
 * AJAX Search / Filter / Sort / Pagination
 */



// =====================================
// LOAD CENTRES
// =====================================


function loadCentres(page){



    if(!page){

        page = 1;

    }



    let search =
    document.getElementById(
        "centreSearch"
    ).value;



    let status =
    document.getElementById(
        "centreStatus"
    ).value;



    let order =
    document.getElementById(
        "centreOrder"
    ).value;





    let url =
        window.contextPath
        +
        "/CentreServlet?action=list"
        +
        "&ajax=true"
        +
        "&page="
        +
        page
        +
        "&search="
        +
        encodeURIComponent(search)
        +
        "&status="
        +
        encodeURIComponent(status)
        +
        "&order="
        +
        encodeURIComponent(order);







    fetch(url)



    .then(
        response => {


            if(!response.ok){


                throw new Error(
                    "HTTP ERROR "
                    +
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
                "centres-table-container"
            );




            if(container){



                container.innerHTML =
                html;



                activateCentreEvents();



            }




        }
    )
    .catch(
        error => {
            console.error(
                "Centres AJAX Error:",
                error
            );
        }
    );
}

// =====================================
// SEARCH BUTTON
// =====================================


function searchCentres(event){
    if(event){
        event.preventDefault();
    }

    loadCentres(1);
    return false;
}
// =====================================
// FILTER CHANGE
// =====================================
function filterCentres(){
    loadCentres(1);
}

// =====================================
// CHANGE PAGE
// =====================================


function changeCentrePage(page){



    loadCentres(page);



}
// =====================================
// UPDATE CENTRE STATUS AJAX
// =====================================


function updateCentreStatus(select){
    let id =
    select.getAttribute(
        "data-id"
    );
    let status =
    select.value;
    let url =
        window.contextPath
        +
        "/CentreServlet?action=status"
        +
        "&id="
        +
        id
        +
        "&status="
        +
        encodeURIComponent(status);
    fetch(url)
    .then(
        response =>
        response.json()
    )

    .then(
        data => {
            if(data.success){
                console.log(
                    "Status updated:",
                    data.status
                );

                // تغيير لون select مباشرة
                select.classList.remove(
                    "status-pending",
                    "status-active",
                    "status-suspended",
                    "status-archived"
                );
                select.classList.add(
                    "status-"
                    +
                    status.toLowerCase()
                );
            }
            else{
                alert(
                    "Erreur modification statut"
                );
            }
        }
    )
    .catch(
        error => {
            console.error(
                "Status AJAX Error:",
                error
            );
            alert(
                "Erreur serveur"
            );
        }
    );
}

// =====================================
// ACTIVATE EVENTS
// =====================================
function activateCentreEvents(){
    let form =
    document.getElementById(
        "centresFilterForm"
    );
    if(form){
        form.onsubmit =
        searchCentres;
    }
    let status =
    document.getElementById(
        "centreStatus"
    );
    if(status){
        status.onchange =
        filterCentres;
    }
    let order =
    document.getElementById(
        "centreOrder"
    );
    if(order){
        order.onchange =
        filterCentres;
    }

}
// =====================================
// LIVE SEARCH
// =====================================
let centreSearchTimer;
function activateSearch(){
    let search =
    document.getElementById(
        "centreSearch"
    );
    if(!search){
        return;
    }
    search.oninput =
    function(){
        clearTimeout(
            centreSearchTimer
        );
        centreSearchTimer =
        setTimeout(
            function(){
                loadCentres(1);
            },
            400
        );
    };
}

// =====================================
// AJAX PAGE INITIALIZATION
// =====================================

function initCentresPage(){
    activateCentreEvents();
    activateSearch();
    loadCentres(1);
}

// =====================================
// INIT
// =====================================

document.addEventListener(
"DOMContentLoaded",
function(){

    activateCentreEvents();
    activateSearch();
    loadCentres(1);

});


// =====================================
// VIEW CENTRE MODAL
// =====================================

function viewCentre(id){


    let url =
        window.contextPath
        +
        "/CentreServlet?action=view&id="
        +
        id;



    fetch(url)


    .then(response => {


        if(!response.ok){

            throw new Error(
                "HTTP ERROR "
                +
                response.status
            );

        }


        return response.text();


    })



    .then(html => {


        let modalBody =
        document.getElementById(
            "centre-modal-body"
        );


        let modal =
        document.getElementById(
            "centre-modal"
        );



        if(modalBody && modal){


            modalBody.innerHTML =
            html;


            modal.classList.add(
                "show"
            );


        }


    })


    .catch(error=>{


        console.error(
            "View Centre Error:",
            error
        );


    });


}

// =====================================
// CLOSE CENTRE MODAL
// =====================================

function closeCentreModal(){


    let modal =
    document.getElementById(
        "centre-modal"
    );


    if(modal){


        modal.classList.remove(
            "show"
        );


        document.getElementById(
            "centre-modal-body"
        ).innerHTML="";


    }


}


// =====================================
// RESET CENTRE PASSWORD
// =====================================

function resetCentrePassword(id){


    if(!confirm("Reset password ?")){

        return;

    }



    let url =
        window.contextPath
        +
        "/CentreServlet?action=resetPassword&id="
        +
        id;




    fetch(url)



    .then(response => {


        if(!response.ok){


            throw new Error(
                "HTTP ERROR "
                +
                response.status
            );


        }


        return response.text();


    })



    .then(data => {



        let modalBody =
        document.getElementById(
            "centre-modal-body"
        );


        let modal =
        document.getElementById(
            "centre-modal"
        );




        if(modalBody && modal){


            modalBody.innerHTML =
            data;



            modal.classList.add(
                "show"
            );


        }



    })



    .catch(error => {


        console.error(
            "Reset Password Error:",
            error
        );


        alert(
            "Error resetting password"
        );


    });



}