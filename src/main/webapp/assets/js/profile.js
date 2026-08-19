/* ==========================================================
   CENTRIA - PROFILE JAVASCRIPT
   ========================================================== */


/* ==========================================================
   00 - DOM READY
========================================================== */

document.addEventListener(
    "DOMContentLoaded",
    function () {

        /*
         * Load complete profile from database.
         */
        loadProfile();


        /*
         * Avatar.
         */
        initAvatarUpload();

        loadSavedAvatar();


        /*
         * Profile editing.
         */
        initProfileEditing();

    }
);


/* ==========================================================
   01 - LOAD PROFILE
========================================================== */

function loadProfile() {

    const contextPath =
        window.contextPath ||
        "";


    /*
     * Get complete profile from servlet.
     *
     * ProfileServlet
     *      ↓
     * ProfileDAO
     *      ↓
     * super_admins
     */
    fetch(
        contextPath +
        "/ProfileServlet?action=getProfile",
        {
            method: "GET",
            cache: "no-store"
        }
    )

    .then(
        function (response) {

            if (!response.ok) {

                throw new Error(
                    "Unable to load profile. HTTP " +
                    response.status
                );

            }

            return response.json();

        }
    )

    .then(
        function (data) {

            console.log(
                "[CENTRIA PROFILE] Profile response:",
                data
            );


            /*
             * Server error.
             */
            if (!data.success) {

                showProfileMessage(
                    "error",
                    data.data ||
                    "Unable to load profile."
                );

                return;

            }


            /*
             * Make sure profile data exists.
             */
            if (!data.data) {

                showProfileMessage(
                    "error",
                    "Profile data not found."
                );

                return;

            }


            const profile =
                data.data;


            console.log(
                "[CENTRIA PROFILE] Database profile:",
                profile
            );


            /* ==================================================
               EDITABLE INFORMATION
            ================================================== */


            setProfileField(
                "profileUsername",
                profile.username
            );


            setProfileField(
                "profileEmail",
                profile.email
            );


            setProfileField(
                "profilePhone",
                profile.phone
            );


            /* ==================================================
               READONLY INFORMATION

               IMPORTANT:

               profile.jsp does NOT have:

               #profileRole
               #profileStatus
               #profileCreatedAt

               Therefore we update the real
               .profile-readonly-row elements.
            ================================================== */

            updateReadonlyProfileInformation(
                profile
            );


            /* ==================================================
               MAIN USERNAME
            ================================================== */

            updateMainProfileUsername(
                profile.username
            );


            /* ==================================================
               HEADER USERNAME
            ================================================== */

            updateHeaderUsername(
                profile.username
            );


            /* ==================================================
               AVATAR
            ================================================== */

            if (
                profile.avatar &&
                profile.avatar.trim() !== ""
            ) {

                const avatarUrl =
                    contextPath +
                    "/" +
                    profile.avatar +
                    "?t=" +
                    new Date().getTime();


                const avatarImage =
                    document.getElementById(
                        "profileAvatar"
                    );


                if (avatarImage) {

                    avatarImage.src =
                        avatarUrl;

                }


                updateHeaderAvatar(
                    avatarUrl
                );

            }


            /* ==================================================
               SAVE ORIGINAL EDITABLE VALUES
            ================================================== */

            updateProfileOriginalValues(
                profile.username,
                profile.email,
                profile.phone
            );

        }
    )

    .catch(
        function (error) {

            console.error(
                "[CENTRIA PROFILE] Load profile error:",
                error
            );


            showProfileMessage(
                "error",
                "An error occurred while loading profile data."
            );

        }
    );

}


/* ==========================================================
   02 - UPDATE READONLY PROFILE INFORMATION
========================================================== */

function updateReadonlyProfileInformation(
    profile
) {

    if (!profile) {

        return;

    }


    /*
     * Get all readonly rows.
     *
     * profile.jsp structure:
     *
     * Row 1 = ROLE
     * Row 2 = STATUS
     * Row 3 = CREATED AT
     */
    const rows =
        document.querySelectorAll(
            ".profile-readonly-list .profile-readonly-row"
        );


    if (!rows ||
        rows.length === 0) {

        console.warn(
            "[CENTRIA PROFILE] Readonly profile rows not found."
        );

        return;

    }


    /* ==================================================
       ROLE
    ================================================== */

    if (rows[0]) {

        const roleValue =
            rows[0].querySelector(
                ".profile-readonly-content strong"
            );


        if (roleValue) {

            /*
             * We intentionally do not replace
             * the role with raw SUPER_ADMIN here.
             *
             * JSP already converts it to the
             * correct translated display.
             */

        }

    }


    /* ==================================================
       STATUS
    ================================================== */

    if (rows[1]) {

        const statusValue =
            rows[1].querySelector(
                ".profile-readonly-content strong"
            );


        if (statusValue) {

            let status =
                profile.status;


            if (
                status == null ||
                status.toString().trim() === ""
            ) {

                status = "—";

            }


            statusValue.textContent =
                status;

        }

    }


    /* ==================================================
       CREATED AT
    ================================================== */

    if (rows[2]) {

        const createdAtValue =
            rows[2].querySelector(
                ".profile-readonly-content strong"
            );


        if (createdAtValue) {

            createdAtValue.textContent =
                formatProfileDate(
                    profile.createdAt
                );

        }

    }


    /*
     * Last login is optional.
     *
     * If later we add a fourth readonly row
     * with id/class for last login, it can be
     * handled separately.
     */
    const lastLoginElement =
        document.getElementById(
            "profileLastLogin"
        );


    if (lastLoginElement) {

        lastLoginElement.textContent =
            formatProfileDate(
                profile.lastLogin
            );

    }

}


/* ==========================================================
   03 - SET PROFILE INPUT
========================================================== */

function setProfileField(
    elementId,
    value
) {

    const element =
        document.getElementById(
            elementId
        );


    if (!element) {

        return;

    }


    element.value =
        value == null
        ? ""
        : value;

}


/* ==========================================================
   04 - FORMAT PROFILE DATE
========================================================== */

function formatProfileDate(
    value
) {

    if (
        value == null ||
        value === ""
    ) {

        return "—";

    }


    let dateValue =
        value.toString().trim();


    if (dateValue === "") {

        return "—";

    }


    /*
     * Servlet returns Timestamp.toString().
     *
     * Example:
     *
     * 2026-07-20 16:14:46.0
     */


    /*
     * Remove milliseconds.
     */
    if (
        dateValue.length > 19
    ) {

        dateValue =
            dateValue.substring(
                0,
                19
            );

    }


    return dateValue;

}


/* ==========================================================
   05 - UPDATE MAIN PROFILE USERNAME
========================================================== */

function updateMainProfileUsername(
    username
) {

    if (!username) {

        return;

    }


    const mainUsername =
        document.querySelector(
            ".profile-main-card h2"
        );


    if (mainUsername) {

        mainUsername.textContent =
            username;

    }

}


/* ==========================================================
   06 - UPDATE PROFILE ORIGINAL VALUES
========================================================== */

function updateProfileOriginalValues(
    username,
    email,
    phone
) {

    const safeUsername =
        username || "";


    const safeEmail =
        email || "";


    const safePhone =
        phone || "";


    /*
     * Global values.
     */
    window.profileOriginalValues = {

        username:
            safeUsername,

        email:
            safeEmail,

        phone:
            safePhone

    };


    /*
     * Synchronize inputs.
     */

    const usernameInput =
        document.getElementById(
            "profileUsername"
        );


    const emailInput =
        document.getElementById(
            "profileEmail"
        );


    const phoneInput =
        document.getElementById(
            "profilePhone"
        );


    if (usernameInput) {

        usernameInput.value =
            safeUsername;

    }


    if (emailInput) {

        emailInput.value =
            safeEmail;

    }


    if (phoneInput) {

        phoneInput.value =
            safePhone;

    }

}


/* ==========================================================
   07 - AVATAR UPLOAD
========================================================== */

function initAvatarUpload() {

    const avatarInput =
        document.getElementById(
            "avatarInput"
        );


    const avatarImage =
        document.getElementById(
            "profileAvatar"
        );


    const avatarButton =
        document.getElementById(
            "avatarUploadButton"
        );


    if (
        !avatarInput ||
        !avatarImage ||
        !avatarButton
    ) {

        return;

    }


    /*
     * OPEN FILE SELECTOR
     */

    avatarButton.addEventListener(
        "click",
        function () {

            avatarInput.click();

        }
    );


    /*
     * FILE SELECTED
     */

    avatarInput.addEventListener(
        "change",
        function () {

            const file =
                avatarInput.files[0];


            if (!file) {

                return;

            }


            /*
             * CHECK FILE TYPE
             */

            if (
                !file.type.startsWith(
                    "image/"
                )
            ) {

                showProfileMessage(
                    "error",
                    "Please select a valid image."
                );

                avatarInput.value = "";

                return;

            }


            /*
             * CHECK FILE SIZE
             *
             * Maximum = 5 MB
             */

            const maxSize =
                5 * 1024 * 1024;


            if (
                file.size > maxSize
            ) {

                showProfileMessage(
                    "error",
                    "Image size must not exceed 5 MB."
                );

                avatarInput.value = "";

                return;

            }


            /*
             * PREVIEW
             */

            const reader =
                new FileReader();


            reader.onload =
                function (event) {

                    avatarImage.src =
                        event.target.result;

                };


            reader.readAsDataURL(
                file
            );


            /*
             * UPLOAD
             */

            uploadAvatar(
                file
            );

        }
    );

}


/* ==========================================================
   08 - UPLOAD AVATAR
========================================================== */

function uploadAvatar(
    file
) {

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


    const contextPath =
        window.contextPath ||
        "";


    fetch(
        contextPath +
        "/ProfileServlet",
        {
            method: "POST",
            body: formData
        }
    )

    .then(
        function (response) {

            if (!response.ok) {

                throw new Error(
                    "Upload failed: HTTP " +
                    response.status
                );

            }


            return response.json();

        }
    )

    .then(
        function (data) {

            console.log(
                "[CENTRIA PROFILE] Avatar response:",
                data
            );


            if (data.success) {

                if (data.data) {

                    const avatarImage =
                        document.getElementById(
                            "profileAvatar"
                        );


                    const avatarUrl =
                        contextPath +
                        "/" +
                        data.data +
                        "?t=" +
                        new Date().getTime();


                    if (avatarImage) {

                        avatarImage.src =
                            avatarUrl;

                    }


                    updateHeaderAvatar(
                        avatarUrl
                    );

                }


                showProfileMessage(
                    "success",
                    "Avatar updated successfully."
                );

            }

            else {

                showProfileMessage(
                    "error",
                    data.data ||
                    "Unable to update avatar."
                );

            }

        }
    )

    .catch(
        function (error) {

            console.error(
                "[CENTRIA PROFILE] Avatar upload error:",
                error
            );


            showProfileMessage(
                "error",
                "An error occurred while uploading the avatar."
            );

        }
    );

}


/* ==========================================================
   09 - LOAD SAVED AVATAR
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
        "/ProfileServlet?action=getAvatar",
        {
            method: "GET",
            cache: "no-store"
        }
    )

    .then(
        function (response) {

            if (!response.ok) {

                throw new Error(
                    "Unable to load avatar."
                );

            }


            return response.json();

        }
    )

    .then(
        function (data) {

            console.log(
                "[CENTRIA PROFILE] Saved avatar response:",
                data
            );


            if (!data.success) {

                return;

            }


            if (
                !data.data ||
                data.data.trim() === ""
            ) {

                return;

            }


            const avatarUrl =
                contextPath +
                "/" +
                data.data +
                "?t=" +
                new Date().getTime();


            avatarImage.src =
                avatarUrl;


            updateHeaderAvatar(
                avatarUrl
            );

        }
    )

    .catch(
        function (error) {

            console.error(
                "[CENTRIA PROFILE] Load avatar error:",
                error
            );

        }
    );

}


/* ==========================================================
   10 - UPDATE HEADER AVATAR
========================================================== */

function updateHeaderAvatar(
    avatarUrl
) {

    if (!avatarUrl) {

        return;

    }


    const headerAvatars =
        document.querySelectorAll(
            ".header-avatar img"
        );


    headerAvatars.forEach(
        function (img) {

            img.src =
                avatarUrl;

        }
    );

}


/* ==========================================================
   11 - PROFILE MESSAGE
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
            document.createElement(
                "div"
            );


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


    setTimeout(
        function () {

            messageBox.style.display =
                "none";

        },
        3000
    );

}


/* ==========================================================
   12 - PROFILE EDITING
========================================================== */

function initProfileEditing() {

    const checkbox =
        document.getElementById(
            "profileEditCheckbox"
        );


    const editButton =
        document.getElementById(
            "profileEditButton"
        );


    const usernameInput =
        document.getElementById(
            "profileUsername"
        );


    const emailInput =
        document.getElementById(
            "profileEmail"
        );


    const phoneInput =
        document.getElementById(
            "profilePhone"
        );


    if (
        !checkbox ||
        !editButton ||
        !usernameInput ||
        !emailInput ||
        !phoneInput
    ) {

        console.warn(
            "[CENTRIA PROFILE] Profile editing elements not found."
        );

        return;

    }


    const contextPath =
        window.contextPath ||
        "";


    /*
     * ORIGINAL VALUES
     */

    let originalValues = {

        username:
            usernameInput.value,

        email:
            emailInput.value,

        phone:
            phoneInput.value

    };


    /*
     * Make available globally.
     */

    window.profileOriginalValues =
        originalValues;


    /*
     * INITIAL STATE
     */

    checkbox.disabled =
        false;


    checkbox.checked =
        false;


    usernameInput.readOnly =
        true;


    emailInput.readOnly =
        true;


    phoneInput.readOnly =
        true;


    editButton.disabled =
        true;


    /*
     * CHECKBOX CHANGE
     */

    checkbox.addEventListener(
        "change",
        function () {

            const editing =
                checkbox.checked;


            /*
             * EDIT MODE
             */

            if (editing) {

                usernameInput.readOnly =
                    false;


                emailInput.readOnly =
                    false;


                phoneInput.readOnly =
                    false;


                editButton.disabled =
                    false;


                usernameInput.focus();

                return;

            }


            /*
             * READ ONLY MODE
             */

            usernameInput.readOnly =
                true;


            emailInput.readOnly =
                true;


            phoneInput.readOnly =
                true;


            editButton.disabled =
                true;


            /*
             * Restore database values.
             */

            usernameInput.value =
                originalValues.username;


            emailInput.value =
                originalValues.email;


            phoneInput.value =
                originalValues.phone;

        }
    );


    /*
     * EDIT / SAVE BUTTON
     */

    editButton.addEventListener(
        "click",
        function () {

            if (!checkbox.checked) {

                return;

            }


            /*
             * READ VALUES
             */

            const username =
                usernameInput.value.trim();


            const email =
                emailInput.value.trim();


            const phone =
                phoneInput.value.trim();


            /*
             * VALIDATION
             */

            if (!username) {

                showProfileMessage(
                    "error",
                    "Username is required."
                );

                usernameInput.focus();

                return;

            }


            if (!email) {

                showProfileMessage(
                    "error",
                    "Email is required."
                );

                emailInput.focus();

                return;

            }


            /*
             * EMAIL FORMAT
             */

            const emailPattern =
                /^[^\s@]+@[^\s@]+\.[^\s@]+$/;


            if (
                !emailPattern.test(email)
            ) {

                showProfileMessage(
                    "error",
                    "Please enter a valid email address."
                );

                emailInput.focus();

                return;

            }


            /*
             * CONFIRMATION
             */

            const confirmed =
                window.confirm(
                    "Are you sure you want to update your profile?"
                );


            if (!confirmed) {

                return;

            }


            /*
             * DISABLE BUTTON DURING REQUEST
             */

            editButton.disabled =
                true;


            /*
             * FORM DATA
             */

            const formData =
                new URLSearchParams();


            formData.append(
                "action",
                "updateProfile"
            );


            formData.append(
                "username",
                username
            );


            formData.append(
                "email",
                email
            );


            formData.append(
                "phone",
                phone
            );


            /*
             * SEND TO SERVLET
             */

            fetch(
                contextPath +
                "/ProfileServlet",
                {
                    method: "POST",

                    headers: {

                        "Content-Type":
                            "application/x-www-form-urlencoded; charset=UTF-8"

                    },

                    body:
                        formData.toString()

                }
            )

            .then(
                function (response) {

                    if (!response.ok) {

                        throw new Error(
                            "Profile update failed: HTTP " +
                            response.status
                        );

                    }


                    return response.json();

                }
            )

            .then(
                function (data) {

                    console.log(
                        "[CENTRIA PROFILE] Update response:",
                        data
                    );


                    /*
                     * SUCCESS
                     */

                    if (data.success) {

                        /*
                         * Save official values.
                         */

                        originalValues = {

                            username:
                                username,

                            email:
                                email,

                            phone:
                                phone

                        };


                        window.profileOriginalValues =
                            originalValues;


                        /*
                         * Exit edit mode.
                         */

                        checkbox.checked =
                            false;


                        usernameInput.readOnly =
                            true;


                        emailInput.readOnly =
                            true;


                        phoneInput.readOnly =
                            true;


                        editButton.disabled =
                            true;


                        /*
                         * Update username immediately.
                         */

                        updateMainProfileUsername(
                            username
                        );


                        updateHeaderUsername(
                            username
                        );


                        /*
                         * Success message.
                         */

                        showProfileMessage(
                            "success",
                            data.data ||
                            "Profile updated successfully."
                        );


                        /*
                         * Reload profile from DATABASE.
                         *
                         * This is important:
                         *
                         * UI
                         * ↓
                         * Servlet
                         * ↓
                         * DAO
                         * ↓
                         * MySQL
                         * ↓
                         * Servlet
                         * ↓
                         * UI
                         */

                        loadProfile();

                        return;

                    }


                    /*
                     * SERVER ERROR
                     */

                    editButton.disabled =
                        false;


                    showProfileMessage(
                        "error",
                        data.data ||
                        "Unable to update profile."
                    );

                }
            )

            .catch(
                function (error) {

                    console.error(
                        "[CENTRIA PROFILE] Profile update error:",
                        error
                    );


                    editButton.disabled =
                        false;


                    checkbox.checked =
                        true;


                    usernameInput.readOnly =
                        false;


                    emailInput.readOnly =
                        false;


                    phoneInput.readOnly =
                        false;


                    showProfileMessage(
                        "error",
                        "An error occurred while updating the profile."
                    );

                }
            );

        }
    );

}


/* ==========================================================
   13 - UPDATE HEADER USERNAME
========================================================== */

function updateHeaderUsername(
    username
) {

    if (!username) {

        return;

    }


    const elements =
        document.querySelectorAll(
            ".header-username, .admin-username"
        );


    elements.forEach(
        function (element) {

            element.textContent =
                username;

        }
    );

}