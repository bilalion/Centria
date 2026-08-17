/* ==========================================================
   CENTRIA - PROFILE JAVASCRIPT
   ========================================================== */

document.addEventListener("DOMContentLoaded", function () {

    initAvatarUpload();

    loadSavedAvatar();

});


/* ==========================================================
   01 - AVATAR UPLOAD
========================================================== */

function initAvatarUpload() {

    const avatarInput =
        document.getElementById("avatarInput");

    const avatarImage =
        document.getElementById("profileAvatar");

    const avatarButton =
        document.getElementById("avatarUploadButton");


    if (!avatarInput ||
        !avatarImage ||
        !avatarButton) {

        return;
    }


    /* ----------------------------------------------
       OPEN FILE SELECTOR
    ---------------------------------------------- */

    avatarButton.addEventListener("click", function () {

        avatarInput.click();

    });


    /* ----------------------------------------------
       FILE SELECTED
    ---------------------------------------------- */

    avatarInput.addEventListener("change", function () {

        const file =
            avatarInput.files[0];


        if (!file) {
            return;
        }


        /* ------------------------------------------
           CHECK FILE TYPE
        ------------------------------------------ */

        if (!file.type.startsWith("image/")) {

            alert(
                "Please select a valid image."
            );

            avatarInput.value = "";

            return;
        }


        /* ------------------------------------------
           CHECK FILE SIZE
           Maximum: 5 MB
        ------------------------------------------ */

        const maxSize =
            5 * 1024 * 1024;


        if (file.size > maxSize) {

            alert(
                "Image size must not exceed 5 MB."
            );

            avatarInput.value = "";

            return;
        }


        /* ------------------------------------------
           PREVIEW
        ------------------------------------------ */

        const reader =
            new FileReader();


        reader.onload = function (event) {

            avatarImage.src =
                event.target.result;

        };


        reader.readAsDataURL(file);


        /* ------------------------------------------
           UPLOAD
        ------------------------------------------ */

        uploadAvatar(file);

    });

}


/* ==========================================================
   02 - UPLOAD AVATAR
========================================================== */

function uploadAvatar(file) {

    const formData =
        new FormData();


    formData.append(
        "action",
        "uploadAvatar"
    );


    formData.append(
        "avatar",
        file
    );


    /*
     * IMPORTANT:
     * Get the current application context path.
     */

    const contextPath =
        window.contextPath ||
        "";


    fetch(
        contextPath + "/ProfileServlet",
        {
            method: "POST",
            body: formData
        }
    )

    .then(function (response) {

        if (!response.ok) {

            throw new Error(
                "Upload failed: HTTP " +
                response.status
            );
        }


        return response.json();

    })

    .then(function (data) {

        console.log(
            "Avatar upload response:",
            data
        );


        if (data.success) {

            /*
             * Servlet returns the saved path
             * inside data.
             */

            if (data.data) {

                const avatarImage =
                    document.getElementById(
                        "profileAvatar"
                    );


                if (avatarImage) {

                    avatarImage.src =
                        contextPath +
                        "/" +
                        data.data +
                        "?t=" +
                        new Date().getTime();

                }

            }


            showProfileMessage(
                "success",
                "Avatar updated successfully."
            );


        } else {

            showProfileMessage(
                "error",
                data.data ||
                "Unable to update avatar."
            );

        }

    })

    .catch(function (error) {

        console.error(
            "Profile avatar upload error:",
            error
        );


        showProfileMessage(
            "error",
            "An error occurred while uploading the avatar."
        );

    });

}


/* ==========================================================
   03 - LOAD SAVED AVATAR
========================================================== */

function loadSavedAvatar() {

    const avatarImage =
        document.getElementById(
            "profileAvatar"
        );


    if (!avatarImage) {
        return;
    }


    const contextPath =
        window.contextPath ||
        "";


    fetch(
        contextPath +
        "/ProfileServlet?action=getAvatar"
    )

    .then(function (response) {

        if (!response.ok) {

            throw new Error(
                "Unable to load avatar."
            );
        }


        return response.json();

    })

    .then(function (data) {

        console.log(
            "Saved avatar response:",
            data
        );


        if (!data.success) {
            return;
        }


        if (!data.data ||
            data.data.trim() === "") {

            return;
        }


        /*
         * Load avatar stored in database.
         */

        avatarImage.src =
            contextPath +
            "/" +
            data.data +
            "?t=" +
            new Date().getTime();

    })

    .catch(function (error) {

        console.error(
            "Load avatar error:",
            error
        );

    });

}


/* ==========================================================
   04 - PROFILE MESSAGE
========================================================== */

function showProfileMessage(
    type,
    message
) {

    let messageBox =
        document.getElementById(
            "profileMessage"
        );


    if (!messageBox) {

        messageBox =
            document.createElement("div");


        messageBox.id =
            "profileMessage";


        document.body.appendChild(
            messageBox
        );

    }


    messageBox.className =
        "profile-message profile-message-" +
        type;


    messageBox.textContent =
        message;


    messageBox.style.display =
        "block";


    /* ----------------------------------------------
       AUTO HIDE
    ---------------------------------------------- */

    setTimeout(function () {

        messageBox.style.display =
            "none";

    }, 3000);

}