/*
 * ==========================================================
 * CENTRIA
 * Centres Management JavaScript
 *
 * AJAX Search / Filter / Sort / Pagination
 *
 * Clean Production Version
 * ==========================================================
 */



/* ======================================================
   01 - LOAD CENTRES
   ====================================================== */


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
        () => {

        }
    );



}







/* ======================================================
   02 - SEARCH BUTTON
   ====================================================== */


function searchCentres(event){


    if(event){

        event.preventDefault();

    }


    loadCentres(1);


    return false;


}








/* ======================================================
   03 - FILTER CHANGE
   ====================================================== */


function filterCentres(){


    loadCentres(1);


}








/* ======================================================
   04 - PAGINATION
   ====================================================== */


function changeCentrePage(page){


    loadCentres(page);


}








/* ======================================================
   05 - UPDATE CENTRE STATUS
   ====================================================== */


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
        () => {


            alert(
                "Erreur serveur"
            );


        }
    );



}








/* ======================================================
   06 - ACTIVATE EVENTS
   ====================================================== */


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








/* ======================================================
   07 - LIVE SEARCH
   ====================================================== */


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








/* ======================================================
   08 - PAGE INITIALIZATION
   ====================================================== */


function initCentresPage(){



    activateCentreEvents();


    activateSearch();


    loadCentres(1);



}








/* ======================================================
   09 - INIT
   ====================================================== */


document.addEventListener(

"DOMContentLoaded",

function(){


    initCentresPage();



}

);

/* ======================================================
   10 - VIEW CENTRE MODAL
   ====================================================== */


function viewCentre(id){



    let url =

        window.contextPath

        +

        "/CentreServlet?action=view&id="

        +

        id;







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



        }
    )







    .catch(
        () => {

        }
    );



}









/* ======================================================
   11 - CLOSE CENTRE MODAL
   ====================================================== */


function closeCentreModal(){



    let modal =

    document.getElementById(
        "centre-modal"
    );





    if(modal){



        modal.classList.remove(
            "show"
        );




        let body =

        document.getElementById(
            "centre-modal-body"
        );




        if(body){


            body.innerHTML = "";


        }



    }



}









/* ======================================================
   12 - RESET PASSWORD
   ====================================================== */


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








/* ======================================================
   13 - CLOSE RESET CONFIRM
   ====================================================== */


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









/* ======================================================
   14 - CONFIRM RESET PASSWORD
   ====================================================== */


function confirmResetPassword(){



    closeResetConfirm();





    let url =


        window.contextPath

        +

        "/CentreServlet?action=resetPassword&id="

        +

        resetCentreId;









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



        }
    )







    .catch(
        () => {



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



        }
    );



}









/* ======================================================
   15 - COPY LOGIN INFORMATION
   ====================================================== */


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



    .then(
        ()=> {



            let btn =

            document.querySelector(
                ".copy-password-btn"
            );





            if(btn){



                let old =

                btn.innerHTML;




                btn.innerHTML =

                "✅ " + old;





                setTimeout(
                    ()=>{


                        btn.innerHTML =
                        old;


                    },

                    1500
                );



            }



        }
    )






    .catch(
        ()=> {

        }
    );



}









/* ======================================================
   16 - COPY SUCCESS FEEDBACK
   ====================================================== */


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








    setTimeout(
        ()=>{


            button.innerHTML =
            oldText;




            button.classList.remove(
                "copied"
            );



        },

        2000
    );



}

 /* ======================================================
   17 - EDIT CENTRE CONFIRM
   ====================================================== */


let editCentreId = null;






function editCentre(id){



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









/* ======================================================
   18 - CLOSE EDIT CONFIRM
   ====================================================== */


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









/* ======================================================
   19 - OPEN EDIT FORM
   ====================================================== */


function confirmEditCentre(){



    closeEditConfirm();





    let url =


        window.contextPath

        +

        "/CentreServlet?action=edit&id="

        +

        editCentreId;









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



            let modalBody =

            document.getElementById(
                "centre-modal-body"
            );





            let modal =

            document.getElementById(
                "centre-modal"
            );








            if(!modalBody || !modal){


                return;


            }








            modalBody.innerHTML =

            html;






            modal.classList.add(
                "show"
            );



        }
    )








    .catch(
        ()=>{

        }
    );



}









/* ======================================================
   20 - SAVE EDIT CENTRE PROFILE
   ====================================================== */


function saveEditCentre(){



    let form =

    document.getElementById(
        "editCentreForm"
    );






    if(!form){



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

        response =>

        response.json()

    )









    .then(

        json => {



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

        ()=>{


        }

    );



}