/*
==========================================================
File        : dialog.js
Project     : CENTRIA
Layer       : Components
Component   : Global Dialog

Description :
Reusable global dialog controller.

Responsibilities :
- Open / close global dialog
- Dynamic type
- Dynamic global title
- Dynamic message
- Dynamic icon
- RTL / LTR support
- Keyboard Escape
==========================================================
*/


/* ======================================================
   SECTION 01 - GLOBAL DIALOG STATE
====================================================== */

let globalDialog = null;


/* ======================================================
   SECTION 02 - INITIALIZATION
====================================================== */

document.addEventListener("DOMContentLoaded", function(){

    globalDialog =
        document.getElementById("global-dialog");


    if(!globalDialog){

        return;
    }


    /*
    --------------------------------------------------
    CLOSE BUTTONS
    --------------------------------------------------
    */

    globalDialog
        .querySelectorAll("[data-dialog-close]")
        .forEach(function(element){

            element.addEventListener(
                "click",
                closeGlobalDialog
            );

        });


    /*
    --------------------------------------------------
    KEYBOARD
    --------------------------------------------------
    */

    document.addEventListener(
        "keydown",
        handleGlobalDialogKeydown
    );

});


/* ======================================================
   SECTION 03 - OPEN DIALOG
====================================================== */

function showDialog(options){

    /*
    --------------------------------------------------
    GET GLOBAL DIALOG
    --------------------------------------------------
    */

    if(!globalDialog){

        globalDialog =
            document.getElementById("global-dialog");

    }


    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if(!globalDialog){

        return;
    }


    /*
    --------------------------------------------------
    OPTIONS
    --------------------------------------------------
    */

    options = options || {};


    /*
    --------------------------------------------------
    TYPE
    --------------------------------------------------
    */

    const type =
        options.type || "error";


    /*
    --------------------------------------------------
    MESSAGE
    --------------------------------------------------
    */

    const message =
        options.message || "";


    /*
    --------------------------------------------------
    SET TYPE
    --------------------------------------------------
    */

    setGlobalDialogType(type);


    /*
    --------------------------------------------------
    SET TITLE
    --------------------------------------------------
    */

    let title = "";


    if(
        window.centriaDialogMessages
    ){

        if(type === "success"){

            title =
                window.centriaDialogMessages[
                    "common.success"
                ];

        }

        else if(type === "error"){

            title =
                window.centriaDialogMessages[
                    "common.error"
                ];

        }

        else if(type === "warning"){

            title =
                window.centriaDialogMessages[
                    "common.warning"
                ]
                ||
                window.centriaDialogMessages[
                    "common.error"
                ];

        }

        else if(type === "info"){

            title =
                window.centriaDialogMessages[
                    "common.info"
                ]
                ||
                window.centriaDialogMessages[
                    "common.error"
                ];

        }

    }


    /*
    --------------------------------------------------
    FALLBACK TITLE
    --------------------------------------------------
    */

    if(!title){

        if(type === "success"){

            title = "Success";

        }

        else if(type === "warning"){

            title = "Warning";

        }

        else if(type === "info"){

            title = "Information";

        }

        else{

            title = "Error";

        }

    }


    /*
    --------------------------------------------------
    APPLY TITLE
    --------------------------------------------------
    */

    setGlobalDialogTitle(title);


    /*
    --------------------------------------------------
    SET MESSAGE
    --------------------------------------------------
    */

    setGlobalDialogMessage(message);


    /*
    --------------------------------------------------
    OPEN
    --------------------------------------------------
    */

    globalDialog.classList.add(
        "is-open"
    );


    globalDialog.setAttribute(
        "aria-hidden",
        "false"
    );


    /*
    --------------------------------------------------
    LOCK BODY SCROLL
    --------------------------------------------------
    */

    document.body.classList.add(
        "global-dialog-open"
    );


    /*
    --------------------------------------------------
    FOCUS OK BUTTON
    --------------------------------------------------
    */

    const confirmButton =
        document.getElementById(
            "global-dialog-confirm"
        );


    if(confirmButton){

        confirmButton.focus();

    }

}


/* ======================================================
   SECTION 04 - CLOSE DIALOG
====================================================== */

function closeGlobalDialog(){

    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if(!globalDialog){

        return;
    }


    /*
    --------------------------------------------------
    CLOSE
    --------------------------------------------------
    */

    globalDialog.classList.remove(
        "is-open"
    );


    globalDialog.setAttribute(
        "aria-hidden",
        "true"
    );


    /*
    --------------------------------------------------
    UNLOCK BODY SCROLL
    --------------------------------------------------
    */

    document.body.classList.remove(
        "global-dialog-open"
    );

}


/* ======================================================
   SECTION 05 - SET TYPE
====================================================== */

function setGlobalDialogType(type){

    /*
    --------------------------------------------------
    ALLOWED TYPES
    --------------------------------------------------
    */

    const allowedTypes = [

        "error",

        "success",

        "warning",

        "info"

    ];


    /*
    --------------------------------------------------
    VALIDATE TYPE
    --------------------------------------------------
    */

    if(!allowedTypes.includes(type)){

        type = "error";

    }


    /*
    --------------------------------------------------
    REMOVE PREVIOUS TYPE
    --------------------------------------------------
    */

    globalDialog.classList.remove(

        "global-dialog--error",

        "global-dialog--success",

        "global-dialog--warning",

        "global-dialog--info"

    );


    /*
    --------------------------------------------------
    ADD CURRENT TYPE
    --------------------------------------------------
    */

    globalDialog.classList.add(
        "global-dialog--" + type
    );


    /*
    --------------------------------------------------
    UPDATE ICON
    --------------------------------------------------
    */

    setGlobalDialogIcon(type);

}


/* ======================================================
   SECTION 06 - SET ICON
====================================================== */

function setGlobalDialogIcon(type){

    /*
    --------------------------------------------------
    GET ICON CONTAINER
    --------------------------------------------------
    */

    const icon =
        document.getElementById(
            "global-dialog-icon"
        );


    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if(!icon){

        return;

    }


    /*
    --------------------------------------------------
    ICONS
    --------------------------------------------------
    */

    const icons = {

        error:
            "fas fa-circle-exclamation",

        success:
            "fas fa-circle-check",

        warning:
            "fas fa-triangle-exclamation",

        info:
            "fas fa-circle-info"

    };


    /*
    --------------------------------------------------
    SET ICON
    --------------------------------------------------
    */

    icon.innerHTML =
        '<i class="' +
        icons[type] +
        '"></i>';

}


/* ======================================================
   SECTION 07 - SET TITLE
====================================================== */

function setGlobalDialogTitle(title){

    const element =
        document.getElementById(
            "global-dialog-title"
        );


    if(!element){

        return;

    }


    element.textContent =
        title || "";

}


/* ======================================================
   SECTION 08 - SET MESSAGE
====================================================== */

function setGlobalDialogMessage(message){

    const element =
        document.getElementById(
            "global-dialog-message"
        );


    if(!element){

        return;

    }


    let translatedMessage =
        message || "";


    /*
    --------------------------------------------------
    Resolve language key
    --------------------------------------------------
    */

    if(
        window.centriaDialogMessages &&
        Object.prototype.hasOwnProperty.call(
            window.centriaDialogMessages,
            message
        )
    ){

        translatedMessage =
            window.centriaDialogMessages[message];

    }


    element.textContent =
        translatedMessage;

}


/* ======================================================
   SECTION 09 - KEYBOARD
====================================================== */

function handleGlobalDialogKeydown(event){

    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if(!globalDialog){

        return;

    }


    /*
    --------------------------------------------------
    ESCAPE
    --------------------------------------------------
    */

    if(
        event.key === "Escape"
        &&
        globalDialog.getAttribute(
            "aria-hidden"
        ) === "false"
    ){

        closeGlobalDialog();

    }

}


/* ======================================================
   SECTION 10 - SHORTCUT FUNCTIONS
====================================================== */


/*
----------------------------------------------------------
ERROR
----------------------------------------------------------
*/

function showErrorDialog(message){

    showDialog({

        type: "error",

        message: message

    });

}


/*
----------------------------------------------------------
SUCCESS
----------------------------------------------------------
*/

function showSuccessDialog(message){

    showDialog({

        type: "success",

        message: message

    });

}


/*
----------------------------------------------------------
WARNING
----------------------------------------------------------
*/

function showWarningDialog(message){

    showDialog({

        type: "warning",

        message: message

    });

}


/*
----------------------------------------------------------
INFO
----------------------------------------------------------
*/

function showInfoDialog(message){

    showDialog({

        type: "info",

        message: message

    });

}


/* ======================================================
   SECTION 11 - BODY SCROLL LOCK
====================================================== */

const globalDialogStyle =
    document.createElement("style");


globalDialogStyle.textContent = `

    body.global-dialog-open{

        overflow:hidden;

    }

`;


document.head.appendChild(
    globalDialogStyle
);


/* ======================================================
   SECTION 12 - GLOBAL CONFIRM DIALOG
====================================================== */


/* ======================================================
   SECTION 12.1 - STATE
====================================================== */

let globalConfirmDialog = null;

let globalConfirmAction = null;


/* ======================================================
   SECTION 12.2 - INITIALIZATION
====================================================== */

document.addEventListener("DOMContentLoaded", function(){

    globalConfirmDialog =
        document.getElementById(
            "global-confirm-dialog"
        );


    if(!globalConfirmDialog){

        return;

    }


    /*
    --------------------------------------------------
    CLOSE BUTTONS
    --------------------------------------------------
    */

    globalConfirmDialog
        .querySelectorAll(
            "[data-confirm-dialog-close]"
        )
        .forEach(function(element){

            element.addEventListener(
                "click",
                closeGlobalConfirmDialog
            );

        });


    /*
    --------------------------------------------------
    CONFIRM BUTTON
    --------------------------------------------------
    */

    const confirmButton =
        document.getElementById(
            "global-confirm-dialog-confirm"
        );


    if(confirmButton){

        confirmButton.addEventListener(
            "click",
            executeGlobalConfirmDialog
        );

    }


    /*
    --------------------------------------------------
    KEYBOARD
    --------------------------------------------------
    */

    document.addEventListener(
        "keydown",
        handleGlobalConfirmDialogKeydown
    );

});


/* ======================================================
   SECTION 12.3 - OPEN CONFIRM DIALOG
====================================================== */

function openGlobalConfirmDialog(options){

    /*
    --------------------------------------------------
    GET GLOBAL CONFIRM DIALOG
    --------------------------------------------------
    */

    if(!globalConfirmDialog){

        globalConfirmDialog =
            document.getElementById(
                "global-confirm-dialog"
            );

    }


    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if(!globalConfirmDialog){

        return;

    }


    /*
    --------------------------------------------------
    OPTIONS
    --------------------------------------------------
    */

    options = options || {};


    /*
    --------------------------------------------------
    STORE CONFIRM ACTION
    --------------------------------------------------
    */

    globalConfirmAction =
        typeof options.onConfirm === "function"
            ? options.onConfirm
            : null;


    /*
    --------------------------------------------------
    SET TITLE
    --------------------------------------------------
    */

    setGlobalConfirmDialogTitle(
        options.title || "common.confirm.title"
    );


    /*
    --------------------------------------------------
    SET MESSAGE
    --------------------------------------------------
    */

    setGlobalConfirmDialogMessage(
        options.message || ""
    );


    /*
    --------------------------------------------------
    OPEN
    --------------------------------------------------
    */

    globalConfirmDialog.classList.add(
        "is-open"
    );


    globalConfirmDialog.setAttribute(
        "aria-hidden",
        "false"
    );


    /*
    --------------------------------------------------
    LOCK BODY SCROLL
    --------------------------------------------------
    */

    document.body.classList.add(
        "global-confirm-dialog-open"
    );


    /*
    --------------------------------------------------
    FOCUS CONFIRM BUTTON
    --------------------------------------------------
    */

    const confirmButton =
        document.getElementById(
            "global-confirm-dialog-confirm"
        );


    if(confirmButton){

        confirmButton.focus();

    }

}


/* ======================================================
   SECTION 12.4 - CLOSE CONFIRM DIALOG
====================================================== */

function closeGlobalConfirmDialog(){

    if(!globalConfirmDialog){

        return;

    }


    /*
    --------------------------------------------------
    CLOSE
    --------------------------------------------------
    */

    globalConfirmDialog.classList.remove(
        "is-open"
    );


    globalConfirmDialog.setAttribute(
        "aria-hidden",
        "true"
    );


    /*
    --------------------------------------------------
    CLEAR ACTION
    --------------------------------------------------
    */

    globalConfirmAction = null;


    /*
    --------------------------------------------------
    UNLOCK BODY SCROLL
    --------------------------------------------------
    */

    document.body.classList.remove(
        "global-confirm-dialog-open"
    );

}


/* ======================================================
   SECTION 12.5 - SET TITLE
====================================================== */

function setGlobalConfirmDialogTitle(title){

    const element =
        document.getElementById(
            "global-confirm-dialog-title"
        );


    if(!element){

        return;

    }


    let translatedTitle =
        title || "";


    /*
    --------------------------------------------------
    RESOLVE LANGUAGE KEY
    --------------------------------------------------
    */

    if(
        window.centriaConfirmDialogMessages &&
        Object.prototype.hasOwnProperty.call(
            window.centriaConfirmDialogMessages,
            title
        )
    ){

        translatedTitle =
            window.centriaConfirmDialogMessages[
                title
            ];

    }


    /*
    --------------------------------------------------
    APPLY TITLE
    --------------------------------------------------
    */

    element.textContent =
        translatedTitle;

}


/* ======================================================
   SECTION 12.6 - SET MESSAGE
====================================================== */

function setGlobalConfirmDialogMessage(message){

    const element =
        document.getElementById(
            "global-confirm-dialog-message"
        );


    if(!element){

        return;

    }


    let translatedMessage =
        message || "";


    /*
    --------------------------------------------------
    RESOLVE LANGUAGE KEY
    --------------------------------------------------
    */

    if(
        window.centriaConfirmDialogMessages &&
        Object.prototype.hasOwnProperty.call(
            window.centriaConfirmDialogMessages,
            message
        )
    ){

        translatedMessage =
            window.centriaConfirmDialogMessages[
                message
            ];

    }


    /*
    --------------------------------------------------
    APPLY MESSAGE
    --------------------------------------------------
    */

    element.textContent =
        translatedMessage;

}


/* ======================================================
   SECTION 12.7 - EXECUTE CONFIRM ACTION
====================================================== */

function executeGlobalConfirmDialog(){

    const action =
        globalConfirmAction;


    /*
    --------------------------------------------------
    CLOSE FIRST
    --------------------------------------------------
    */

    closeGlobalConfirmDialog();


    /*
    --------------------------------------------------
    EXECUTE CALLBACK
    --------------------------------------------------
    */

    if(typeof action === "function"){

        action();

    }

}


/* ======================================================
   SECTION 12.8 - KEYBOARD
====================================================== */

function handleGlobalConfirmDialogKeydown(event){

    if(!globalConfirmDialog){

        return;

    }


    /*
    --------------------------------------------------
    ESCAPE
    --------------------------------------------------
    */

    if(
        event.key === "Escape"
        &&
        globalConfirmDialog.getAttribute(
            "aria-hidden"
        ) === "false"
    ){

        closeGlobalConfirmDialog();

    }

}


/* ======================================================
   SECTION 12.9 - BODY SCROLL LOCK
====================================================== */

const globalConfirmDialogStyle =
    document.createElement("style");


globalConfirmDialogStyle.textContent = `

    body.global-confirm-dialog-open{

        overflow:hidden;

    }

`;


document.head.appendChild(
    globalConfirmDialogStyle
);

/* ======================================================
   END OF FILE
====================================================== */