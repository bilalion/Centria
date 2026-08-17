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
- Fixed global title from JSP
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
    SET MESSAGE
    --------------------------------------------------
    */

    setGlobalDialogMessage(message);


    /*
    --------------------------------------------------
    IMPORTANT
    --------------------------------------------------

    TITLE IS GLOBAL.

    The title is defined directly inside:

        dialog-error.jsp

    using LanguageManager.

    Example:

        Arabic  → خطأ
        French  → Erreur
        English → Error

    JavaScript NEVER changes the title.

    --------------------------------------------------
    */


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

/*
----------------------------------------------------------
GLOBAL TITLE

The title is controlled by:

    dialog-error.jsp

through:

    LanguageManager.get(
        "common.error",
        session
    )

Therefore this JavaScript file does NOT modify
the title.

This function is intentionally NOT used by
showDialog().

----------------------------------------------------------
*/

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


    let translatedMessage = message || "";


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

Usage:

showErrorDialog(
    "archive.error.load"
);

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
   END OF FILE
====================================================== */