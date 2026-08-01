/*
==========================================================
 CENTRIA
 Payments Management JavaScript

 AJAX Search / Tabs / Confirm Payment

 Version: Clean AJAX Dashboard Compatible
==========================================================
*/


/*
==========================================================
 GLOBAL VARIABLES
==========================================================
*/


console.log(
    "PAYMENTS JS LOADED"
);



let currentPaymentTab = "UNPAID";


let selectedCentreCode = null;


let paymentSearchTimer = null;





/*
==========================================================
 01 - LOAD PAYMENTS
==========================================================
*/


function loadPayments(page = 1){



    let container =

    document.getElementById(
        "payments-table-container"
    );



    if(!container){


        console.log(
            "Payments page not active"
        );


        return;


    }






    let searchInput =

    document.getElementById(
        "paymentSearch"
    );



    let orderInput =

    document.getElementById(
        "paymentOrder"
    );





    let search =

    searchInput
    ?
    searchInput.value
    :
    "";





    let order =

    orderInput
    ?
    orderInput.value
    :
    "NEW";







    let url =

        window.contextPath

        +

        "/PaymentServlet?action=list"

        +

        "&ajax=true"

        +

        "&tab="

        +

        currentPaymentTab

        +

        "&page="

        +

        page

        +

        "&search="

        +

        encodeURIComponent(search)

        +

        "&order="

        +

        encodeURIComponent(order);







    console.log(
        "Loading payments:",
        url
    );







    fetch(url)



    .then(response => {


        if(!response.ok){


            throw new Error(
                "HTTP "
                +
                response.status
            );


        }



        return response.text();



    })





    .then(html => {



        container.innerHTML = html;



    })





    .catch(error => {



        console.error(
            "Payment loading error:",
            error
        );


    });



}









/*
==========================================================
 02 - CHANGE TAB
==========================================================
*/


function changePaymentTab(tab){



    currentPaymentTab = tab;





    let hidden =

    document.getElementById(
        "currentPaymentTab"
    );



    if(hidden){


        hidden.value = tab;


    }








    document
    .querySelectorAll(
        ".payment-tab"
    )
    .forEach(button => {


        button.classList.remove(
            "active"
        );


    });







    let active =

    document.getElementById(
        "tab-"
        +
        tab.toLowerCase()
    );



    if(active){


        active.classList.add(
            "active"
        );


    }






    loadPayments(1);



}









/*
==========================================================
 03 - LIVE SEARCH
==========================================================
*/


function activatePaymentSearch(){



    let input =

    document.getElementById(
        "paymentSearch"
    );





    if(!input){


        return;


    }







    input.oninput = function(){



        clearTimeout(
            paymentSearchTimer
        );





        paymentSearchTimer =

        setTimeout(
            function(){


                loadPayments(1);


            },
            400
        );



    };



}









/*
==========================================================
 04 - VIEW PAYMENT
==========================================================
*/


function viewPayment(centreCode){



    selectedCentreCode =
    centreCode;



    console.log(
        "VIEW PAYMENT",
        centreCode
    );



}









/*
==========================================================
 05 - CONFIRM PAYMENT MODAL
==========================================================
*/


function openPaymentConfirm(centreCode){



    selectedCentreCode =
    centreCode;




    let modal =

    document.getElementById(
        "payment-confirm-modal"
    );




    if(modal){


        modal.classList.add(
            "show"
        );


    }



}






function closePaymentConfirm(){



    let modal =

    document.getElementById(
        "payment-confirm-modal"
    );



    if(modal){


        modal.classList.remove(
            "show"
        );


    }



}






function confirmPayment(){



    console.log(
        "CONFIRM PAYMENT",
        selectedCentreCode
    );


    /*
        لاحقا:

        POST
        PaymentServlet?action=confirm

    */



}









/*
==========================================================
 06 - CLOSE DETAILS MODAL
==========================================================
*/


function closePaymentModal(){



    let modal =

    document.getElementById(
        "payment-modal"
    );



    if(modal){


        modal.classList.remove(
            "show"
        );


    }



}









/*
==========================================================
 07 - INITIALIZATION

 Called after AJAX loading dashboard content

==========================================================
*/


function initPaymentsPage(){



    console.log(
        "INIT PAYMENTS PAGE"
    );





    let container =

    document.getElementById(
        "payments-table-container"
    );





    if(!container){


        console.log(
            "Payment container missing"
        );


        return;


    }







    activatePaymentSearch();



    loadPayments(1);



}









/*
==========================================================
 08 - AUTO INIT WHEN PAGE EXISTS

 Useful when payments.jsp loaded directly

==========================================================
*/


document.addEventListener(
"DOMContentLoaded",
function(){



    if(
        document.getElementById(
            "payments-table-container"
        )
    ){



        initPaymentsPage();



    }



});