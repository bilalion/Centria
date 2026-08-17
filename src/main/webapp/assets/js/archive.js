/*
 * ==========================================================
 * CENTRIA
 * Archive Management JavaScript
 *
 * AJAX Archive / Bulk Operations / Restore / Delete
 *
 * Global error dialog integration
 *
 * No Java or Servlet logic is modified.
 * ==========================================================
 */

"use strict";


/*
==================================================
 PAGE STATE
==================================================
*/

let archiveDeleteCentres = [];

let activeArchivePage = 1;


/*
==================================================
01 - INITIALIZE ARCHIVE PAGE
==================================================
*/

function initArchivePage() {

    /*
    --------------------------------------------------
    Check Archive page
    --------------------------------------------------
    */

    const archivePage =
            document.querySelector(
                    ".archive-page"
            );


    if (!archivePage) {

        return;

    }


    /*
    --------------------------------------------------
    SELECT ALL
    --------------------------------------------------
    */

    const selectAll =
            document.getElementById(
                    "archiveSelectAll"
            );


    /*
    --------------------------------------------------
    ROW CHECKBOXES
    --------------------------------------------------
    */

    const rowCheckboxes =
            document.querySelectorAll(
                    ".archive-row-checkbox"
            );


    /*
    --------------------------------------------------
    APPLY BUTTON
    --------------------------------------------------
    */

    const applyButton =
            document.getElementById(
                    "archiveApplyButton"
            );


    /*
    --------------------------------------------------
    OPERATION SELECT
    --------------------------------------------------
    */

    const operationSelect =
            document.getElementById(
                    "archiveOperation"
            );


    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if (!selectAll) {

        return;

    }


    /*
    --------------------------------------------------
    INITIAL BULK STATE
    --------------------------------------------------
    */

    updateArchiveBulkActionState(
            rowCheckboxes,
            applyButton,
            operationSelect
    );


    /*
    ==================================================
    SELECT ALL EVENT
    ==================================================
    */

    selectAll.addEventListener(
            "change",
            function () {

                rowCheckboxes.forEach(
                        function (checkbox) {

                            checkbox.checked =
                                    selectAll.checked;

                        }
                );


                updateArchiveSelectAllState(
                        selectAll,
                        rowCheckboxes
                );


                updateArchiveBulkActionState(
                        rowCheckboxes,
                        applyButton,
                        operationSelect
                );

            }
    );


    /*
    ==================================================
    ROW CHECKBOX EVENTS
    ==================================================
    */

    rowCheckboxes.forEach(
            function (checkbox) {

                checkbox.addEventListener(
                        "change",
                        function () {

                            updateArchiveSelectAllState(
                                    selectAll,
                                    rowCheckboxes
                            );


                            updateArchiveBulkActionState(
                                    rowCheckboxes,
                                    applyButton,
                                    operationSelect
                            );

                        }
                );

            }
    );


    /*
    ==================================================
    APPLY EVENT
    ==================================================
    */

    if (applyButton) {

        applyButton.addEventListener(
                "click",
                function () {

                    applyArchiveOperation(
                            rowCheckboxes,
                            operationSelect,
                            applyButton
                    );

                }
        );

    }


    /*
    ==================================================
    ARCHIVE SEARCH
    ==================================================
    */

    const archiveSearch =
            document.getElementById(
                    "archiveSearch"
            );


    if (archiveSearch) {

        archiveSearch.addEventListener(
                "input",
                function () {

                    searchArchivedCentres();

                }
        );

    }


    /*
    ==================================================
    ARCHIVE STATUS FILTER
    ==================================================
    */

    const archiveStatus =
            document.getElementById(
                    "archiveStatus"
            );


    if (archiveStatus) {

        archiveStatus.addEventListener(
                "change",
                function () {

                    searchArchivedCentres();

                }
        );

    }


    /*
    ==================================================
    LOAD ARCHIVE
    ==================================================
    */

    loadArchive(1);


    /*
    ==================================================
    INITIALIZE SEARCH
    ==================================================
    */

    initArchiveSearch();

}


/*
==================================================
02 - UPDATE SELECT ALL STATE
==================================================
*/

function updateArchiveSelectAllState(
        selectAll,
        rowCheckboxes
) {

    /*
    --------------------------------------------------
    NO ROWS
    --------------------------------------------------
    */

    if (
            !rowCheckboxes
            ||
            rowCheckboxes.length === 0
    ) {

        selectAll.checked = false;

        selectAll.indeterminate = false;

        return;

    }


    /*
    --------------------------------------------------
    COUNT SELECTED ROWS
    --------------------------------------------------
    */

    let selectedCount = 0;


    rowCheckboxes.forEach(
            function (checkbox) {

                if (checkbox.checked) {

                    selectedCount++;

                }

            }
    );


    /*
    --------------------------------------------------
    ALL SELECTED
    --------------------------------------------------
    */

    if (
            selectedCount ===
            rowCheckboxes.length
            &&
            selectedCount > 0
    ) {

        selectAll.checked = true;

        selectAll.indeterminate = false;

    }


    /*
    --------------------------------------------------
    SOME SELECTED
    --------------------------------------------------
    */

    else if (
            selectedCount > 0
    ) {

        selectAll.checked = false;

        selectAll.indeterminate = true;

    }


    /*
    --------------------------------------------------
    NONE SELECTED
    --------------------------------------------------
    */

    else {

        selectAll.checked = false;

        selectAll.indeterminate = false;

    }

}


/*
==================================================
03 - UPDATE BULK ACTION STATE
==================================================
*/

function updateArchiveBulkActionState(
        rowCheckboxes,
        applyButton,
        operationSelect
) {

    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if (!rowCheckboxes) {

        return;

    }


    /*
    --------------------------------------------------
    COUNT SELECTED ROWS
    --------------------------------------------------
    */

    let selectedCount = 0;


    rowCheckboxes.forEach(
            function (checkbox) {

                if (checkbox.checked) {

                    selectedCount++;

                }

            }
    );


    /*
    --------------------------------------------------
    SELECTION EXISTS
    --------------------------------------------------
    */

    if (
            selectedCount > 0
    ) {

        if (applyButton) {

            applyButton.disabled = false;

        }


        if (operationSelect) {

            operationSelect.disabled = false;

        }

    }


    /*
    --------------------------------------------------
    NO SELECTION
    --------------------------------------------------
    */

    else {

        if (applyButton) {

            applyButton.disabled = true;

        }


        if (operationSelect) {

            operationSelect.disabled = true;

            operationSelect.value = "";

        }

    }

}


/*
==================================================
04 - GET SELECTED CENTRES
==================================================
*/

function getSelectedArchiveCentres() {

    const rowCheckboxes =
            document.querySelectorAll(
                    ".archive-row-checkbox:checked"
            );


    const selectedCentres = [];


    rowCheckboxes.forEach(
            function (checkbox) {

                selectedCentres.push(
                        checkbox.value
                );

            }
    );


    return selectedCentres;

}


/*
==================================================
05 - LOAD ARCHIVE
==================================================
*/

function loadArchive(page) {

    /*
    --------------------------------------------------
    CURRENT PAGE
    --------------------------------------------------
    */

    const currentPage =
            Number(page) || 1;


    activeArchivePage =
            currentPage;


    /*
    --------------------------------------------------
    GET TABLE CONTAINER
    --------------------------------------------------
    */

    const container =
            document.getElementById(
                    "archive-table-container"
            );


    if (!container) {

        return;

    }


    /*
    --------------------------------------------------
    GET SEARCH
    --------------------------------------------------
    */

    const searchInput =
            document.getElementById(
                    "archiveSearch"
            );


    const search =
            searchInput
            ?
            searchInput.value.trim()
            :
            "";


    /*
    --------------------------------------------------
    GET STATUS
    --------------------------------------------------
    */

    const statusSelect =
            document.getElementById(
                    "archiveStatus"
            );


    const status =
            statusSelect
            ?
            statusSelect.value
            :
            "ALL";


    /*
    --------------------------------------------------
    BUILD PARAMETERS
    --------------------------------------------------
    */

    const parameters =
            new URLSearchParams();


    parameters.append(
            "action",
            "list"
    );


    parameters.append(
            "ajax",
            "true"
    );


    parameters.append(
            "page",
            currentPage
    );


    parameters.append(
            "search",
            search
    );


    parameters.append(
            "status",
            status
    );


    parameters.append(
            "_refresh",
            Date.now()
    );


    /*
    --------------------------------------------------
    BUILD URL
    --------------------------------------------------
    */

    const url =
            window.contextPath
            +
            "/ArchiveServlet?"
            +
            parameters.toString();


    /*
    --------------------------------------------------
    LOADING
    --------------------------------------------------
    */

    container.setAttribute(
            "aria-busy",
            "true"
    );


    /*
    --------------------------------------------------
    AJAX LOAD
    --------------------------------------------------
    */

    fetch(url)

        .then(
                response => {

                    if (!response.ok) {

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

                    container.innerHTML =
                            html;


                    container.setAttribute(
                            "aria-busy",
                            "false"
                    );


                    initArchiveControlsOnly();

                }
        )


        .catch(
                error => {

                    console.error(
                            "[CENTRIA ARCHIVE] "
                            +
                            "Load error:",
                            error
                    );


                    container.setAttribute(
                            "aria-busy",
                            "false"
                    );


                    /*
                    --------------------------------------------------
                    GLOBAL ERROR DIALOG
                    --------------------------------------------------
                    */

                    showArchiveErrorDialog(
                            "archive.error.load"
                    );

                }
        );

}


/*
==================================================
05.1 - SEARCH / FILTER ARCHIVED CENTRES
==================================================
*/

function searchArchivedCentres() {

    activeArchivePage = 1;

    loadArchive(1);

}


/*
==================================================
05.2 - CHANGE ARCHIVE PAGE
==================================================
*/

function changeArchivePage(page) {

    const requestedPage =
            Number(page);


    if (
            !requestedPage
            ||
            requestedPage < 1
    ) {

        return;

    }


    loadArchive(
            requestedPage
    );

}


/*
==================================================
06 - INITIALIZE ARCHIVE CONTROLS ONLY
==================================================
*/

function initArchiveControlsOnly() {

    /*
    --------------------------------------------------
    SELECT ALL
    --------------------------------------------------
    */

    const selectAll =
            document.getElementById(
                    "archiveSelectAll"
            );


    /*
    --------------------------------------------------
    ROW CHECKBOXES
    --------------------------------------------------
    */

    const rowCheckboxes =
            document.querySelectorAll(
                    ".archive-row-checkbox"
            );


    /*
    --------------------------------------------------
    APPLY BUTTON
    --------------------------------------------------
    */

    const applyButton =
            document.getElementById(
                    "archiveApplyButton"
            );


    /*
    --------------------------------------------------
    OPERATION SELECT
    --------------------------------------------------
    */

    const operationSelect =
            document.getElementById(
                    "archiveOperation"
            );


    if (!selectAll) {

        return;

    }


    /*
    --------------------------------------------------
    INITIAL STATE
    --------------------------------------------------
    */

    updateArchiveSelectAllState(
            selectAll,
            rowCheckboxes
    );


    updateArchiveBulkActionState(
            rowCheckboxes,
            applyButton,
            operationSelect
    );


    /*
    --------------------------------------------------
    SELECT ALL
    --------------------------------------------------
    */

    selectAll.addEventListener(
            "change",
            function () {

                rowCheckboxes.forEach(
                        function (checkbox) {

                            checkbox.checked =
                                    selectAll.checked;

                        }
                );


                updateArchiveSelectAllState(
                        selectAll,
                        rowCheckboxes
                );


                updateArchiveBulkActionState(
                        rowCheckboxes,
                        applyButton,
                        operationSelect
                );

            }
    );


    /*
    --------------------------------------------------
    ROW CHECKBOXES
    --------------------------------------------------
    */

    rowCheckboxes.forEach(
            function (checkbox) {

                checkbox.addEventListener(
                        "change",
                        function () {

                            updateArchiveSelectAllState(
                                    selectAll,
                                    rowCheckboxes
                            );


                            updateArchiveBulkActionState(
                                    rowCheckboxes,
                                    applyButton,
                                    operationSelect
                            );

                        }
                );

            }
    );


    /*
    --------------------------------------------------
    APPLY
    --------------------------------------------------
    */

    if (applyButton) {

        applyButton.addEventListener(
                "click",
                function () {

                    applyArchiveOperation(
                            rowCheckboxes,
                            operationSelect,
                            applyButton
                    );

                }
        );

    }

}


/*
==================================================
07 - DELETE MODAL
==================================================
*/


/*
--------------------------------------------------
OPEN DELETE CONFIRM MODAL
--------------------------------------------------
*/

function openArchiveDeleteConfirm(
        selectedCentres
) {

    archiveDeleteCentres =
            selectedCentres.slice();


    const modal =
            document.getElementById(
                    "archive-delete-confirm-modal"
            );


    const message =
            document.getElementById(
                    "archive-delete-confirm-message"
            );


    if (
            !modal
            ||
            !message
    ) {

        console.error(
                "[CENTRIA ARCHIVE] "
                + "DELETE modal not found."
        );

        showArchiveErrorDialog(
                "archive.error.dialog"
        );

        return;

    }


    /*
    --------------------------------------------------
    GET TRANSLATED MESSAGE
    --------------------------------------------------
    */

    const messageTemplate =
            modal.getAttribute(
                    "data-delete-message-template"
            );


    if (!messageTemplate) {

        console.error(
                "[CENTRIA ARCHIVE] "
                + "DELETE message template not found."
        );

        showArchiveErrorDialog(
                "archive.error.dialog"
        );

        return;

    }


    /*
    --------------------------------------------------
    REPLACE {0}
    --------------------------------------------------
    */

    message.textContent =
            messageTemplate.replace(
                    "{0}",
                    archiveDeleteCentres.length
            );


    /*
    --------------------------------------------------
    OPEN MODAL
    --------------------------------------------------
    */

    modal.classList.add(
            "show"
    );


    modal.setAttribute(
            "aria-hidden",
            "false"
    );

}


/*
--------------------------------------------------
CLOSE DELETE CONFIRM MODAL
--------------------------------------------------
*/

function closeArchiveDeleteConfirm() {

    const modal =
            document.getElementById(
                    "archive-delete-confirm-modal"
            );


    if (modal) {

        modal.classList.remove(
                "show"
        );


        modal.setAttribute(
                "aria-hidden",
                "true"
        );

    }


    archiveDeleteCentres = [];

}


/*
--------------------------------------------------
CONFIRM DELETE
--------------------------------------------------
*/

function confirmArchiveDelete() {

    if (
            archiveDeleteCentres.length === 0
    ) {

        return;

    }


    const selectedCentres =
            archiveDeleteCentres.slice();


    closeArchiveDeleteConfirm();


    const applyButton =
            document.getElementById(
                    "archiveApplyButton"
            );


    if (applyButton) {

        applyButton.disabled = true;

    }


    const formData =
            new URLSearchParams();


    formData.append(
            "action",
            "apply"
    );


    formData.append(
            "operation",
            "DELETE"
    );


    selectedCentres.forEach(
            function (centreCode) {

                formData.append(
                        "centreCodes",
                        centreCode
                );

            }
    );


    fetch(
            window.contextPath
            +
            "/ArchiveServlet",
            {
                method: "POST",

                headers: {
                    "Content-Type":
                            "application/x-www-form-urlencoded"
                },

                body:
                        formData.toString()
            }
    )


    .then(
            response => {

                if (!response.ok) {

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
            result => {

                console.log(
                        "Archive DELETE result:",
                        result
                );


                loadContent(
                        "ArchiveServlet?action=list",
                        null
                );

            }
    )


    .catch(
            error => {

                console.error(
                        "[CENTRIA ARCHIVE] "
                        + "DELETE error:",
                        error
                );


                if (applyButton) {

                    applyButton.disabled = false;

                }


                showArchiveErrorDialog(
                        "archive.error.delete"
                );

            }
    );

}


/*
==================================================
08 - APPLY ARCHIVE OPERATION
==================================================
*/

function applyArchiveOperation(
        rowCheckboxes,
        operationSelect,
        applyButton
) {

    /*
    --------------------------------------------------
    VALIDATE OPERATION
    --------------------------------------------------
    */

    if (!operationSelect) {

        return;

    }


    /*
    --------------------------------------------------
    GET OPERATION
    --------------------------------------------------
    */

    const operation =
            operationSelect.value;


    /*
    --------------------------------------------------
    OPERATION REQUIRED
    --------------------------------------------------
    */

    if (!operation) {

        showArchiveErrorDialog(
                "archive.error.operation.required"
        );

        return;

    }


    /*
    --------------------------------------------------
    GET SELECTED CENTRES
    --------------------------------------------------
    */

    const selectedCentres =
            getSelectedArchiveCentres();


    /*
    --------------------------------------------------
    AT LEAST ONE CENTRE
    --------------------------------------------------
    */

    if (
            selectedCentres.length === 0
    ) {

        showArchiveErrorDialog(
                "archive.error.selection.required"
        );

        return;

    }


    /*
    ==================================================
    RESTORE
    ==================================================
    */

    if (
            operation ===
            "RESTORE"
    ) {

        if (applyButton) {

            applyButton.disabled = true;

        }


        const formData =
                new URLSearchParams();


        formData.append(
                "action",
                "apply"
        );


        formData.append(
                "operation",
                operation
        );


        selectedCentres.forEach(
                function (centreCode) {

                    formData.append(
                            "centreCodes",
                            centreCode
                    );

                }
        );


        fetch(
                window.contextPath
                +
                "/ArchiveServlet",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                                "application/x-www-form-urlencoded"
                    },

                    body:
                            formData.toString()
                }
        )


        .then(
                response => {

                    if (!response.ok) {

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
                result => {

                    console.log(
                            "Archive RESTORE result:",
                            result
                    );


                    loadContent(
                            "ArchiveServlet?action=list",
                            null
                    );

                }
        )


        .catch(
                error => {

                    console.error(
                            "[CENTRIA ARCHIVE] "
                            + "RESTORE error:",
                            error
                    );


                    if (applyButton) {

                        applyButton.disabled = false;

                    }


                    showArchiveErrorDialog(
                            "archive.error.restore"
                    );

                }
        );


        return;

    }


    /*
    ==================================================
    DELETE
    ==================================================
    */

    if (
            operation ===
            "DELETE"
    ) {

        openArchiveDeleteConfirm(
                selectedCentres
        );


        return;

    }

}


/*
==================================================
08.1 - VIEW ARCHIVE
==================================================
*/

function viewArchivedCentre(centreCode) {

    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if (
            !centreCode
            ||
            centreCode.trim() === ""
    ) {

        showArchiveErrorDialog(
                "archive.error.centre.code"
        );

        return;

    }


    /*
    --------------------------------------------------
    GET VIEW MODAL
    --------------------------------------------------
    */

    const modal =
            document.getElementById(
                    "archive-view-modal"
            );


    const modalBody =
            document.getElementById(
                    "archive-view-modal-body"
            );


    /*
    --------------------------------------------------
    SAFETY
    --------------------------------------------------
    */

    if (
            !modal
            ||
            !modalBody
    ) {

        console.error(
                "[CENTRIA ARCHIVE] "
                + "Archive View modal not found."
        );

        showArchiveErrorDialog(
                "archive.error.dialog"
        );

        return;

    }


    /*
    --------------------------------------------------
    SHOW LOADING
    --------------------------------------------------
    */

    modalBody.innerHTML =
            `
            <div class="archive-view-loading">
                <i class="fa-solid fa-spinner fa-spin"></i>
            </div>
            `;


    /*
    --------------------------------------------------
    OPEN MODAL
    --------------------------------------------------
    */

    modal.classList.add(
            "show"
    );


    modal.setAttribute(
            "aria-hidden",
            "false"
    );


    /*
    --------------------------------------------------
    BUILD URL
    --------------------------------------------------
    */

    const parameters =
            new URLSearchParams();


    parameters.append(
            "action",
            "view"
    );


    parameters.append(
            "centreCode",
            centreCode.trim()
    );


    const url =
            window.contextPath
            +
            "/ArchiveServlet?"
            +
            parameters.toString();


    /*
    --------------------------------------------------
    LOAD VIEW FRAGMENT
    --------------------------------------------------
    */

    fetch(url)

        .then(
                response => {

                    if (!response.ok) {

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

                    modalBody.innerHTML =
                            html;

                }
        )


        .catch(
                error => {

                    console.error(
                            "[CENTRIA ARCHIVE] "
                            + "View error:",
                            error
                    );


                    /*
                    --------------------------------------------------
                    CLOSE VIEW MODAL
                    --------------------------------------------------
                    */

                    closeArchiveView();


                    /*
                    --------------------------------------------------
                    GLOBAL ERROR DIALOG
                    --------------------------------------------------
                    */

                    showArchiveErrorDialog(
                            "archive.error.view"
                    );

                }
        );

}


/*
--------------------------------------------------
CLOSE VIEW MODAL
--------------------------------------------------
*/

function closeArchiveView() {

    const modal =
            document.getElementById(
                    "archive-view-modal"
            );


    if (!modal) {

        return;

    }


    modal.classList.remove(
            "show"
    );


    modal.setAttribute(
            "aria-hidden",
            "true"
    );


    const modalBody =
            document.getElementById(
                    "archive-view-modal-body"
            );


    if (modalBody) {

        modalBody.innerHTML = "";

    }

}


/*
--------------------------------------------------
VIEW BUTTON EVENT DELEGATION
--------------------------------------------------
*/

document.addEventListener(
        "click",
        function (event) {

            const button =
                    event.target.closest(
                            ".archive-view-button"
                    );


            if (!button) {

                return;

            }


            event.preventDefault();


            const centreCode =
                    button.getAttribute(
                            "data-centre-code"
                    );


            viewArchivedCentre(
                    centreCode
            );

        }
);


/*
==================================================
09 - ARCHIVE ERROR DIALOG
==================================================
*/

/*
 * Global dialog.js is responsible for rendering
 * the actual dialog.
 *
 * Archive only sends the language key.
 */

function showArchiveErrorDialog(messageKey) {

    if (
            typeof showErrorDialog ===
            "function"
    ) {

        showErrorDialog(
                messageKey
        );

        return;

    }


    /*
    --------------------------------------------------
    FALLBACK
    --------------------------------------------------
    */

    console.error(
            "[CENTRIA ARCHIVE] " +
            "Global error dialog is not available:",
            messageKey
    );

}


/*
==================================================
10 - PAGE INITIALIZATION
==================================================
*/

document.addEventListener(
        "DOMContentLoaded",
        function () {

            if (
                    document.getElementById(
                            "archive-table-container"
                    )
            ) {

                initArchivePage();

            }

        }
); 