/*
==========================================================
File        : superlogin.js
Project     : CENTRIA

Description :
Super Admin Login Controller

Responsibilities :
- Password visibility
- Database status
- Login button state
- Form behaviour
- Multi Language Support

Version :
2.1

Status :
Development
==========================================================
*/





/* ======================================================
   01 - PAGE ELEMENTS
====================================================== */


const passwordInput =

document.getElementById(
    "password"
);




const databaseStatus =

document.getElementById(
    "dbStatus"
);




const loginButton =

document.getElementById(
    "loginBtn"
);




const loginForm =

document.querySelector(
    "form[action*='SuperLoginServlet']"
);







/* ======================================================
   02 - PASSWORD VISIBILITY
====================================================== */


function togglePassword(){


    if(!passwordInput){

        return;

    }



    if(passwordInput.type === "password"){


        passwordInput.type = "text";


    }

    else{


        passwordInput.type = "password";


    }


}









/* ======================================================
   03 - DATABASE STATUS
====================================================== */


function updateDatabaseStatus(
    connected,
    message
){


    if(!databaseStatus){

        return;

    }



    if(connected){



        databaseStatus.className =

        "db-status connected";



        databaseStatus.innerHTML = "";



        if(loginButton){


            loginButton.disabled = false;


        }




    }

    else{



        databaseStatus.className =

        "db-status failed";



        databaseStatus.innerHTML = "";



        if(loginButton){


            loginButton.disabled = true;


        }



    }



}









/* ======================================================
   04 - DATABASE CONNECTION CHECK
====================================================== */


function checkDatabaseStatus(){



    if(!databaseStatus){

        return;

    }




    databaseStatus.className =

    "db-status checking";



    databaseStatus.innerHTML = "";







    fetch(

        contextPath

        +

        "/DatabaseStatusServlet"

    )



    .then(function(response){


        return response.json();


    })



    .then(function(data){





        if(data.status === "connected"){



            updateDatabaseStatus(

                true

            );


        }



        else{



            updateDatabaseStatus(

                false

            );


        }




    })



    .catch(function(){



        updateDatabaseStatus(

            false

        );



    });



}
















/* ======================================================
   05 - LOGIN SUBMIT
====================================================== */


function initializeLoginForm(){



    if(!loginForm){

        return;

    }






    loginForm.addEventListener(

        "submit",

        function(){





            if(loginButton){





                loginButton.disabled = true;





                loginButton.innerHTML =

                loginLanguage.signingIn;





            }





        }

    );



}









/* ======================================================
   06 - PAGE INITIALIZATION
====================================================== */


document.addEventListener(

"DOMContentLoaded",

function(){



    checkDatabaseStatus();



    initializeLoginForm();



    resetLoginError();



});


/* ======================================================
   07 - LOGIN ERROR RESET
====================================================== */

function resetLoginError(){

    const errorMessage =
    document.querySelector(".error-message");


    if(errorMessage){


        setTimeout(function(){


            errorMessage.style.display =
            "none";


        },3000);


    }



    if(loginButton){


        setTimeout(function(){


            loginButton.classList.remove(
                "login-error-state"
            );


            loginButton.disabled = false;



        },3000);


    }


}




/* ======================================================
   END OF FILE
======================================================
*/