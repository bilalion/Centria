/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 *
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js
 * to edit this template
 */


/*
==================================================
CENTRIA
Archive JavaScript
==================================================

Responsible for:

- Archive table selection
- Select All
- Row selection
- Bulk action state
- Bulk operation

Restore / Delete:
Restore is currently supported.
Delete will be added later.
==================================================
*/




/*
==================================================
 LOAD ARCHIVE TABLE
==================================================
*/

function loadArchive() {


    const container =
            document.getElementById(
                    "archive-table-container"
            );


    if (!container) {

        return;

    }


    const url =
            window.contextPath
            +
            "/ArchiveServlet?action=list&ajax=true";


    fetch(url)

        .then(
                response => {

                    if (!response.ok) {

                        throw new Error(
                                "HTTP "
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


                    /*
                    ----------------------------------
                    Reinitialize Archive controls
                    ----------------------------------
                    */

                    initArchivePage();

                }
        )


        .catch(
                error => {

                    console.error(
                            "[CENTRIA ARCHIVE] "
                            + "Load error:",
                            error
                    );

                }
        );

}
/*
==================================================
01 - ARCHIVE PAGE INITIALIZATION
==================================================
*/

function initArchivePage() {


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
    No Archive Table
    --------------------------------------------------
    */

    if (!selectAll) {

        return;

    }


    /*
    ==================================================
    INITIAL STATE
    ==================================================
    */

    updateArchiveBulkActionState(
            rowCheckboxes,
            applyButton,
            operationSelect
    );


    /*
    ==================================================
    SELECT ALL
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


                /*
                Update Select All state
                */

                updateArchiveSelectAllState(
                        selectAll,
                        rowCheckboxes
                );


                /*
                Update bulk actions
                */

                updateArchiveBulkActionState(
                        rowCheckboxes,
                        applyButton,
                        operationSelect
                );


            }
    );


    /*
    ==================================================
    ROW SELECTION
    ==================================================
    */

    rowCheckboxes.forEach(
            function (checkbox) {


                checkbox.addEventListener(
                        "change",
                        function () {


                            /*
                            Update Select All
                            */

                            updateArchiveSelectAllState(
                                    selectAll,
                                    rowCheckboxes
                            );


                            /*
                            Update Apply button
                            */

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
    APPLY BUTTON
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
    No rows
    --------------------------------------------------
    */

    if (rowCheckboxes.length === 0) {

        selectAll.checked = false;

        selectAll.indeterminate = false;

        return;

    }


    /*
    --------------------------------------------------
    Count selected rows
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
    All selected
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
    Some selected
    --------------------------------------------------
    */

    else if (selectedCount > 0) {

        selectAll.checked = false;

        selectAll.indeterminate = true;

    }


    /*
    --------------------------------------------------
    None selected
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
    Safety
    --------------------------------------------------
    */

    if (!rowCheckboxes) {

        return;

    }


    /*
    --------------------------------------------------
    Count selected rows
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
    Selection exists
    --------------------------------------------------
    */

    if (selectedCount > 0) {


        /*
        Enable APPLY
        */

        if (applyButton) {

            applyButton.disabled = false;

        }


        /*
        Enable OPERATION
        */

        if (operationSelect) {

            operationSelect.disabled = false;

        }

    }


    /*
    --------------------------------------------------
    No selection
    --------------------------------------------------
    */

    else {


        /*
        Disable APPLY
        */

        if (applyButton) {

            applyButton.disabled = true;

        }


        /*
        Disable OPERATION
        */

        if (operationSelect) {

            operationSelect.disabled = true;

            /*
            Reset operation
            */

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
05 - APPLY ARCHIVE OPERATION
==================================================
*/

function applyArchiveOperation(
        rowCheckboxes,
        operationSelect,
        applyButton
) {


    /*
    --------------------------------------------------
    Validate operation selector
    --------------------------------------------------
    */

    if (!operationSelect) {

        return;

    }


    /*
    --------------------------------------------------
    Get selected operation
    --------------------------------------------------
    */

    const operation =
            operationSelect.value;


    /*
    --------------------------------------------------
    Operation required
    --------------------------------------------------
    */

    if (!operation) {

        alert(
                "Please select an operation."
        );

        return;

    }


    /*
    --------------------------------------------------
    Get selected centres
    --------------------------------------------------
    */

    const selectedCentres =
            getSelectedArchiveCentres();


    /*
    --------------------------------------------------
    At least one centre required
    --------------------------------------------------
    */

    if (
            selectedCentres.length === 0
    ) {

        alert(
                "Please select at least one centre."
        );

        return;

    }


    /*
    --------------------------------------------------
    RESTORE
    --------------------------------------------------
    */

    if (
            operation ===
            "RESTORE"
    ) {


        /*
        Disable button while processing
        */

        if (applyButton) {

            applyButton.disabled = true;

        }


        /*
        --------------------------------------------------
        Build request
        --------------------------------------------------
        */

        const formData =
                new URLSearchParams();


        formData.append(
                "action",
                "apply"
        );


        formData.append(
                "operation",
                "RESTORE"
        );


        /*
        Add selected centre codes
        */

        selectedCentres.forEach(
                function (centreCode) {

                    formData.append(
                            "centreCodes",
                            centreCode
                    );

                }
        );


        /*
        --------------------------------------------------
        Send request
        --------------------------------------------------
        */

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
                                response.status
                        );

                    }


                    return response.text();

                }
        )


        .then(
                result => {


                    console.log(
                            "Archive operation result:",
                            result
                    );


                    /*
                    --------------------------------------------------
                    Reload Archive page
                    --------------------------------------------------
                    */

                    loadContent(
                            "ArchiveServlet?action=list",
                            null
                    );


                }
        )


        .catch(
                error => {


                    console.error(
                            "Archive operation error:",
                            error
                    );


                    /*
                    Re-enable button
                    */

                    if (applyButton) {

                        applyButton.disabled = false;

                    }

                }
        );

    }

}


/*
==================================================
06 - INITIALIZE ARCHIVE PAGE
==================================================
*/

document.addEventListener(
        "DOMContentLoaded",
        function () {

            initArchivePage();

            loadArchive();

        }
);