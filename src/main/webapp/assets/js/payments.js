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


let selectedSubscriptionCentre = null

let selectedSubscriptionFacture = null;

let paymentCounters = {
    UNPAID: 0,
    PAID: 0,
    HISTORY: 0
};


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



    let dateFromInput =
        document.getElementById(
            "paymentDateFrom"
        );



    let dateToInput =
        document.getElementById(
            "paymentDateTo"
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



    let dateFrom =
        dateFromInput
        ?
        dateFromInput.value
        :
        "";



    let dateTo =
        dateToInput
        ?
        dateToInput.value
        :
        "";







    let url =

        window.contextPath

        +

        "/PaymentServlet?action=list"

        +

        "&ajax=true"

        +

        "&page="

        +

        page

        +

        "&tab="

        +

        encodeURIComponent(
            currentPaymentTab
        )

        +

        "&search="

        +

        encodeURIComponent(
            search
        )

        +

        "&order="

        +

        encodeURIComponent(
            order
        )

        +

        "&dateFrom="

        +

        encodeURIComponent(
            dateFrom
        )

        +

        "&dateTo="

        +

        encodeURIComponent(
            dateTo
        );







    console.log(
        "LOAD PAYMENTS:",
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


    let temp = document.createElement("div");

    temp.innerHTML = html;



    /*
    ===============================
    TABLE
    ===============================
    */

    let fragment =
        temp.querySelector(
            "#payment-fragment"
        );


    if(fragment){

        container.innerHTML =
            fragment.innerHTML;

    }




    /*
    ===============================
    PAGINATION
    ===============================
    */

    let pagination =
        temp.querySelector(
            ".payments-pagination"
        );



    let paginationContainer =
        document.getElementById(
            "payments-pagination-container"
        );



    if(paginationContainer){


        if(pagination){

            paginationContainer.innerHTML =
                pagination.innerHTML;

        }
        else{

            paginationContainer.innerHTML = "";

        }

    }




    /*
    ===============================
    SCRIPTS
    ===============================
    */

    let scripts =
        temp.querySelectorAll("script");


    scripts.forEach(script => {

        eval(script.innerHTML);

    });



    updatePaymentCounters();


})

    .catch(error => {


        console.error(
            "Payment loading error:",
            error
        );


        container.innerHTML =
        "<p class='error-message'>Unable to load payments</p>";


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

    let startDate =

    document.querySelector(
        '.payment-start-date[data-centre="' +
        selectedCentreCode +
        '"]'
    ).value;



    let duration =

    document.querySelector(
        '.payment-duration[data-centre="' +
        selectedCentreCode +
        '"]'
    ).value;



    if(startDate === ""){

        alert("Please select the subscription start date.");

        return;

    }



    let formData = new URLSearchParams();


    formData.append(
        "action",
        "confirm"
    );


    formData.append(
        "centreCode",
        selectedCentreCode
    );


    formData.append(
        "startDate",
        startDate
    );


    formData.append(
        "duration",
        duration
    );



    fetch(

        window.contextPath +
        "/PaymentServlet",

        {

            method:"POST",

            headers:{

                "Content-Type":
                "application/x-www-form-urlencoded"

            },

            body:
            formData.toString()

        }

    )


    .then(response=>response.text())


    .then(result=>{


        console.log(result);



        if(result.trim()==="SUCCESS"){


            closePaymentConfirm();


            loadPayments(1);


        }
        else{


            alert(
                "Payment confirmation failed."
            );


        }


    })


    .catch(error=>{


        console.error(error);


    });


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

/*
==========================================================
 TAB2 - PAID SUBSCRIPTION MANAGEMENT
 UPGRADE / EXTENSION
==========================================================
*/


function openSubscriptionConfirm(
        centreCode,
        codeFacture
){


    selectedSubscriptionCentre = centreCode;
     selectedSubscriptionFacture = codeFacture;


    let operationElement =

    document.querySelector(
        '.subscription-operation[data-centre="' +
        centreCode +
        '"]'
    );



    let operation =

    operationElement
    ?
    operationElement.value
    :
    "";




    let message =

    document.getElementById(
        "subscription-confirm-message"
    );




    if(message){



        if(operation === "UPGRADE"){


            message.innerHTML =
            window.subscriptionMessages.upgrade;


        }
        else if(operation === "EXTENDED"){


            message.innerHTML =
            window.subscriptionMessages.extended;


        }
        else{


            message.innerHTML =
            window.subscriptionMessages.update;


        }


    }






    let modal =

    document.getElementById(
        "subscription-confirm-modal"
    );



    if(modal){


        modal.classList.add(
            "show"
        );


    }



}





function closeSubscriptionConfirm(){


    let modal = document.getElementById(
        "subscription-confirm-modal"
    );


    if(modal){

        modal.classList.remove("show");

    }

}






function updateSubscription(){



    if(!selectedSubscriptionCentre){


        alert(
            "Centre not selected"
        );


        return;


    }




    let operation =

    document.querySelector(
        '.subscription-operation[data-centre="' +
        selectedSubscriptionCentre +
        '"]'
    ).value;



    let duration =

    document.querySelector(
        '.subscription-plan[data-centre="' +
        selectedSubscriptionCentre +
        '"]'
    ).value;





    let formData = new URLSearchParams();



    formData.append(
        "action",
       "updateSubscription"
    );



    formData.append(
        "centreCode",
        selectedSubscriptionCentre
    );

formData.append(
    "codeFacture",
    selectedSubscriptionFacture
);

    formData.append(
        "operation",
        operation
    );



    formData.append(
        "duration",
        duration
    );






    fetch(

        window.contextPath +
        "/PaymentServlet",

        {

            method:"POST",

            headers:{

                "Content-Type":
                "application/x-www-form-urlencoded"

            },

            body:
            formData.toString()

        }

    )


    .then(response=>response.text())


    .then(result=>{


        console.log(result);



        if(result.trim()==="SUCCESS"){



            closeSubscriptionConfirm();



            currentPaymentTab = "PAID";



            loadPayments(1);



        }
        else{


            alert(
                "Subscription update failed"
            );


        }



    })


    .catch(error=>{


        console.error(error);


    });



}

/*
==========================================================
 PAYMENT TAB COUNTERS
==========================================================
*/

function updatePaymentCounters(){


    if(window.paymentCounters){


        document.getElementById(
            "unpaidCount"
        ).innerText =
            window.paymentCounters.UNPAID;



        document.getElementById(
            "paidCount"
        ).innerText =
            window.paymentCounters.PAID;



        document.getElementById(
            "historyCount"
        ).innerText =
            window.paymentCounters.HISTORY;


    }
    
    
    function openPaymentView(){

    document.getElementById(
        "paymentViewDialog"
    ).style.display = "flex";

}



function closePaymentView(){

    document.getElementById(
        "paymentViewDialog"
    ).style.display = "none";

}

}

/*
==========================================================
 PAGINATION
==========================================================
*/

function changePaymentPage(page){

    loadPayments(page);

}


function printInvoice(codeFacture){

    window.open(
        window.contextPath
        + "/PaymentServlet?action=print&invoice="
        + encodeURIComponent(codeFacture),
        "_blank"
    );

}