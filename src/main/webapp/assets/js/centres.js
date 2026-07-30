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



    let searchElement =
    document.getElementById(
        "centreSearch"
    );


    let statusElement =
    document.getElementById(
        "centreStatus"
    );


    let orderElement =
    document.getElementById(
        "centreOrder"
    );



    let search =
    searchElement
    ?
    searchElement.value
    :
    "";



    let status =
    statusElement
    ?
    statusElement.value
    :
    "ALL";



    let order =
    orderElement
    ?
    orderElement.value
    :
    "NEW";





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
// RESET CENTRE PASSWORD - OPEN CONFIRM
// =====================================

let resetCentreId = null;



function resetCentrePassword(id){


    resetCentreId = id;



    let modal =
    document.getElementById(
        "reset-confirm-modal"
    );



    if(modal){


        modal.classList.add(
            "show"
        );


    }


}






// =====================================
// CLOSE RESET CONFIRM
// =====================================

function closeResetConfirm(){


    let modal =
    document.getElementById(
        "reset-confirm-modal"
    );



    if(modal){


        modal.classList.remove(
            "show"
        );


    }


}






// =====================================
// CONFIRM RESET PASSWORD
// =====================================

function confirmResetPassword(){



    closeResetConfirm();



    let url =
        window.contextPath
        +
        "/CentreServlet?action=resetPassword&id="
        +
        resetCentreId;




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




    .catch(error => {



        console.error(
            "Reset Password Error:",
            error
        );




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



            `
            <div class="empty-state">

                <h3>⚠️</h3>

                <p>
                    Error resetting password
                </p>

            </div>
            `;



            modal.classList.add(
                "show"
            );

        }



    });



}



// =====================================
// COPY RESET PASSWORD MESSAGE
// =====================================

function copyLoginInfo(){


    let text =
    document.getElementById(
        "loginInfoText"
    );


    if(!text){
        return;
    }


    navigator.clipboard.writeText(
        text.value
    )
    .then(()=>{


        let btn =
        document.querySelector(
            ".copy-password-btn"
        );


        if(btn){


            let old =
            btn.innerHTML;


            btn.innerHTML =
            "✅ " + old;



            setTimeout(()=>{

                btn.innerHTML = old;

            },1500);


        }


    })
    .catch(error=>{


        console.error(
            "Copy error:",
            error
        );


    });


}


// =====================================
// COPY SUCCESS FEEDBACK
// =====================================

function showCopyMessage(){


    let button =
    document.querySelector(
        ".copy-password-btn"
    );



    if(!button){

        return;

    }



    let oldText =
        button.innerHTML;



    button.innerHTML =
        "✅ " + oldText;



    button.classList.add(
        "copied"
    );



    setTimeout(()=>{


        button.innerHTML =
            oldText;



        button.classList.remove(
            "copied"
        );


    },2000);



}



// =====================================
// EDIT CENTRE - OPEN CONFIRM
// =====================================

let editCentreId = null;


function editCentre(id){


    console.log(
        "OPEN EDIT CONFIRM:",
        id
    );


    editCentreId = id;


    let modal =
    document.getElementById(
        "edit-confirm-modal"
    );


    if(modal){


        modal.classList.add(
            "show"
        );


    }


}


// =====================================
// CLOSE EDIT CONFIRM
// =====================================

function closeEditConfirm(){


    let modal =
    document.getElementById(
        "edit-confirm-modal"
    );


    if(modal){


        modal.classList.remove(
            "show"
        );


    }


}




// =====================================
// CONFIRM EDIT CENTRE
// OPEN EDIT DIALOG
// =====================================

function confirmEditCentre(){


    console.log(
        "CONFIRM EDIT CENTRE:",
        editCentreId
    );



    closeEditConfirm();



    let url =
        window.contextPath
        +
        "/CentreServlet?action=edit&id="
        +
        editCentreId;





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



        console.log(
            "EDIT HTML RECEIVED:",
            html
        );




        let modalBody =
        document.getElementById(
            "centre-modal-body"
        );



        let modal =
        document.getElementById(
            "centre-modal"
        );





        if(!modalBody || !modal){


            console.error(
                "EDIT MODAL NOT FOUND"
            );


            return;


        }




        /*
         * تنظيف المحتوى القديم
         */

        modalBody.innerHTML = "";




        /*
         * تحميل edit jsp
         */

        modalBody.innerHTML =
        html;





        /*
         * فتح dialog edit
         */

        modal.classList.add(
            "show"
        );




    })



    .catch(error => {



        console.error(
            "Edit Centre Error:",
            error
        );



    });



}


// =====================================
// SAVE EDIT CENTRE PROFILE
// =====================================

function saveEditCentre(){



    let form =
    document.getElementById(
        "editCentreForm"
    );



    if(!form){

        console.error(
            "EDIT FORM NOT FOUND"
        );

        return;

    }




    let data =
    new URLSearchParams();




    data.append(
        "action",
        "updateProfile"
    );




    data.append(
        "id",
        form.querySelector(
            "[name='id']"
        ).value
    );




    data.append(
        "name",
        form.querySelector(
            "[name='name']"
        ).value
    );




    data.append(
        "owner_name",
        form.querySelector(
            "[name='owner_name']"
        ).value
    );




    data.append(
        "phone",
        form.querySelector(
            "[name='phone']"
        ).value
    );






    fetch(
        window.contextPath
        +
        "/CentreServlet",
        {

            method:"POST",

            headers:{
                "Content-Type":
                "application/x-www-form-urlencoded;charset=UTF-8"
            },

            body:data.toString()

        }
    )




    .then(
        response => {

            return response.text();

        }
    )




    .then(
        text => {


            let json =
            JSON.parse(text);




            if(json.success){


                closeCentreModal();


                loadCentres(1);


            }
            else{


                alert(
                    json.error
                );


            }



        }
    )




    .catch(
        error=>{


            console.error(
                "SAVE ERROR:",
                error
            );


        }
    );



}
