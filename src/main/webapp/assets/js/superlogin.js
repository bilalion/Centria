/*
==========================================================
 CENTRIA
 Super Admin Login JavaScript
 Clean Professional Version
==========================================================
*/


/* ======================================================
   01 - PASSWORD VISIBILITY
   ====================================================== */


function togglePassword(){


    let pass =
    document.getElementById(
        "password"
    );


    if(pass.type === "password"){


        pass.type =
        "text";


    }
    else{


        pass.type =
        "password";


    }


}







/* ======================================================
   02 - DATABASE STATUS CHECK
   ====================================================== */


function checkDatabaseStatus(){



    let status =
    document.getElementById(
        "dbStatus"
    );



    let btn =
    document.getElementById(
        "loginBtn"
    );



    fetch(
        contextPath
        +
        "/DatabaseStatusServlet"
    )



    .then(
        response => {


            return response.json();


        }
    )



    .then(
        data => {



            if(data.status === "connected"){



                status.innerHTML =
                "🟢";



                status.className =
                "db-status connected";



                btn.disabled =
                false;



            }

            else {



                status.innerHTML =
                "🔴 "
                +
                (data.message || "");



                status.className =
                "db-status failed";



                btn.disabled =
                true;



            }



        }
    )



    .catch(
        error => {



            status.innerHTML =
            "🟠";



            status.className =
            "db-status failed";



            btn.disabled =
            true;



        }
    );


}







/* ======================================================
   03 - PAGE INITIALIZATION
   ====================================================== */


document.addEventListener(
"DOMContentLoaded",
function(){


    checkDatabaseStatus();


});