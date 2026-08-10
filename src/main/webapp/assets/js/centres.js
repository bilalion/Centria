/*
 * ==========================================================
 * CENTRIA
 * Centres Management JavaScript
 *
 * AJAX Search / Filter / Sort / Pagination / Modals
 *
 * No Java or Servlet logic is modified.
 * ==========================================================
 */

"use strict";


/* ======================================================
   PAGE STATE
====================================================== */

let centreSearchTimer;
let resetCentreId = null;
let editCentreId = null;
let activeCentrePage = 1;
let centresAbortController = null;
let centresModalEventsBound = false;


/* ======================================================
   HELPERS
====================================================== */

function getCentresContextPath() {

    return typeof window.contextPath === "string"
        ? window.contextPath
        : "";

}


function getCentresElement(id) {

    return document.getElementById(id);

}


function getCentreFilterValue(id, fallback) {

    const element = getCentresElement(id);

    return element
        ? element.value
        : fallback;

}


function openCentreModal(modal) {

    if (!modal) {
        return;
    }

    const modalClose = modal.querySelector(".modal-close");
    const resetDialog = modal.querySelector(".reset-dialog");

    if (modalClose) {

        if (resetDialog) {

            modalClose.style.display = "none";

        } else {

            modalClose.style.display = "flex";

        }

    }

    modal.classList.add("show");
    modal.setAttribute("aria-hidden", "false");

}


function closeCentreModalElement(modal) {

    if (!modal) {
        return;
    }

    const modalClose = modal.querySelector(".modal-close");

    if (modalClose) {
        modalClose.style.display = "flex";
    }

    modal.classList.remove("show");
    modal.setAttribute("aria-hidden", "true");

}


function normaliseCentreStatus(status) {

    const value = String(status || "")
        .trim()
        .toUpperCase();

    const allowedStatuses = [
        "ACTIVE",
        "PENDING",
        "SUSPENDED",
        "ARCHIVED",
        "INACTIVE"
    ];

    return allowedStatuses.includes(value)
        ? value
        : "";

}


function escapeCentreHtml(value) {

    const container = document.createElement("div");

    container.textContent = value || "";

    return container.innerHTML;

}


/* ======================================================
   TABLE STATES
====================================================== */

function showCentresLoading() {

    const container = getCentresElement(
        "centres-table-container"
    );

    if (!container) {
        return;
    }

    container.setAttribute("aria-busy", "true");

    container.innerHTML = `
        <div class="centres-table-loading" aria-live="polite">
            <i class="fa-solid fa-spinner fa-spin"
               aria-hidden="true"></i>
        </div>
    `;

}


function showCentresLoadError() {

    const container = getCentresElement(
        "centres-table-container"
    );

    if (!container) {
        return;
    }

    container.setAttribute("aria-busy", "false");

    container.innerHTML = `
        <div class="centres-table-error" role="alert">
            <i class="fa-solid fa-circle-exclamation"
               aria-hidden="true"></i>
        </div>
    `;

}


/* ======================================================
   LOAD CENTRES
====================================================== */

function loadCentres(page) {

    const currentPage = Number(page) || 1;

    activeCentrePage = currentPage;

    const search = getCentreFilterValue(
        "centreSearch",
        ""
    );

    const status = getCentreFilterValue(
        "centreStatus",
        "ALL"
    );

    const order = getCentreFilterValue(
        "centreOrder",
        "NEW"
    );

    const parameters = new URLSearchParams();

    parameters.append("action", "list");
    parameters.append("ajax", "true");
    parameters.append("page", currentPage);
    parameters.append("search", search);
    parameters.append("status", status);
    parameters.append("order", order);
    parameters.append("_refresh", Date.now());

    const url =
        getCentresContextPath()
        + "/CentreServlet?"
        + parameters.toString();

    if (centresAbortController) {
        centresAbortController.abort();
    }

    centresAbortController = window.AbortController
        ? new AbortController()
        : null;

    const options = centresAbortController
        ? { signal: centresAbortController.signal }
        : {};

    showCentresLoading();

    fetch(url, options)

        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "HTTP ERROR " + response.status
                );
            }

            return response.text();

        })

        .then(html => {

            const container = getCentresElement(
                "centres-table-container"
            );

            if (!container) {
                return;
            }

            container.innerHTML = html;
            container.setAttribute("aria-busy", "false");

            activateCentreEvents();
            refreshCentresSummary();

        })

        .catch(error => {

            if (error.name === "AbortError") {
                return;
            }

            showCentresLoadError();

        });

}


/* ======================================================
   SEARCH / FILTER / PAGINATION
====================================================== */

function searchCentres(event) {

    if (event) {
        event.preventDefault();
    }

    loadCentres(1);

    return false;

}


function filterCentres() {

    syncCentreStatusFilters(
        getCentreFilterValue("centreStatus", "ALL")
    );

    loadCentres(1);

}


function changeCentrePage(page) {

    loadCentres(page);

}


/* ======================================================
   QUICK STATUS FILTERS
====================================================== */

function activateCentreStatusFilters() {

    const filters = document.querySelectorAll(
        "[data-centre-status-filter]"
    );

    filters.forEach(filter => {

        if (filter.dataset.centresBound === "true") {
            return;
        }

        filter.dataset.centresBound = "true";

        filter.addEventListener("click", function () {

            const status =
                this.dataset.centreStatusFilter;

            const statusSelect = getCentresElement(
                "centreStatus"
            );

            if (!statusSelect || !status) {
                return;
            }

            statusSelect.value = status;

            syncCentreStatusFilters(status);

            loadCentres(1);

        });

    });

}


function syncCentreStatusFilters(status) {

    const activeStatus =
        normaliseCentreStatus(status) || "ALL";

    const filters = document.querySelectorAll(
        "[data-centre-status-filter]"
    );

    filters.forEach(filter => {

        const filterStatus =
            filter.dataset.centreStatusFilter;

        const isActive =
            filterStatus === activeStatus;

        filter.classList.toggle(
            "is-active",
            isActive
        );

        filter.setAttribute(
            "aria-pressed",
            isActive ? "true" : "false"
        );

    });

}


/* ======================================================
   STATUS COUNTS
====================================================== */

function getCentreRowStatus(row) {

    if (!row) {
        return "";
    }

    const rowStatus =
        row.dataset.status
        || row.dataset.centreStatus;

    if (rowStatus) {
        return normaliseCentreStatus(rowStatus);
    }

    const statusElement = row.querySelector(
        "[data-centre-status], [data-status]"
    );

    if (statusElement) {

        const elementStatus =
            statusElement.dataset.centreStatus
            || statusElement.dataset.status;

        if (elementStatus) {
            return normaliseCentreStatus(
                elementStatus
            );
        }

    }

  const statusSelect = row.querySelector(
    "select[data-id], "
    + "select.status-active, "
    + "select.status-inactive, "
    + "select.status-pending, "
    + "select.status-suspended, "
    + "select.status-archived"
);

    if (statusSelect) {
        return normaliseCentreStatus(
            statusSelect.value
        );
    }

    return "";

}


function refreshCentresSummary() {

    const container = getCentresElement(
        "centres-table-container"
    );

    if (!container) {
        return;
    }

const counts = {
    ALL: 0,
    ACTIVE: 0,
    INACTIVE: 0,
    PENDING: 0,
    SUSPENDED: 0,
    ARCHIVED: 0
};

    const rows = Array.from(
        container.querySelectorAll("table tr")
    ).filter(row => {

        return row.querySelector("td")
            && !row.querySelector("td[colspan]");

    });

    rows.forEach(row => {

        counts.ALL += 1;

        const status = getCentreRowStatus(row);

        if (status && counts[status] !== undefined) {
            counts[status] += 1;
        }

    });

    document.querySelectorAll(
        "[data-centre-status-count]"
    ).forEach(countElement => {

        const status =
            countElement.dataset.centreStatusCount;

        countElement.textContent =
            counts[status] || 0;

    });

    const totalElement = document.querySelector(
        "[data-centres-total]"
    );

    if (totalElement) {
        totalElement.textContent = counts.ALL || "";
    }

}


/* ======================================================
   UPDATE CENTRE STATUS
====================================================== */

function updateCentreStatus(select) {

    if (!select) {
        return;
    }

    const id = select.getAttribute("data-id");
    const status = select.value;

    if (!id || !status) {
        return;
    }

    const parameters = new URLSearchParams();

    parameters.append("action", "status");
    parameters.append("id", id);
    parameters.append("status", status);

    const url =
        getCentresContextPath()
        + "/CentreServlet?"
        + parameters.toString();

    fetch(url)

        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "HTTP ERROR " + response.status
                );
            }

            return response.json();

        })

        .then(data => {

            if (!data.success) {

                alert("Erreur modification statut");

                return;
            }

        select.classList.remove(
    "status-pending",
    "status-active",
    "status-inactive",
    "status-suspended",
    "status-archived"
);

            select.classList.add(
                "status-"
                + String(status).toLowerCase()
            );

            const row = select.closest("tr");

            if (row) {
                row.dataset.status = status;
            }

            refreshCentresSummary();

        })

        .catch(() => {

            alert("Erreur serveur");

        });

}


/* ======================================================
   EVENTS
====================================================== */

function activateCentreEvents() {

    const form = getCentresElement(
        "centresFilterForm"
    );

    if (form) {
        form.onsubmit = searchCentres;
    }

    const status = getCentresElement(
        "centreStatus"
    );

    if (status) {
        status.onchange = filterCentres;
    }

    const order = getCentresElement(
        "centreOrder"
    );

    if (order) {
        order.onchange = filterCentres;
    }

    activateSearch();
    activateCentreStatusFilters();

    syncCentreStatusFilters(
        getCentreFilterValue("centreStatus", "ALL")
    );

}


function activateSearch() {

    const search = getCentresElement(
        "centreSearch"
    );

    if (!search) {
        return;
    }

    search.oninput = function () {

        clearTimeout(centreSearchTimer);

        centreSearchTimer = setTimeout(
            function () {

                loadCentres(1);

            },
            400
        );

    };

}


/* ======================================================
   VIEW CENTRE MODAL
====================================================== */

function viewCentre(id) {

    if (!id) {
        return;
    }

    const url =
        getCentresContextPath()
        + "/CentreServlet?action=view&id="
        + encodeURIComponent(id);

    fetch(url)

        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "HTTP ERROR " + response.status
                );
            }

            return response.text();

        })

        .then(html => {

            const modalBody = getCentresElement(
                "centre-modal-body"
            );

            const modal = getCentresElement(
                "centre-modal"
            );

            if (!modalBody || !modal) {
                return;
            }

            modalBody.innerHTML = html;

            openCentreModal(modal);

        })

        .catch(() => {

            showCentreActionError(
                "Unable to load centre details."
            );

        });

}


function closeCentreModal() {

    const modal = getCentresElement(
        "centre-modal"
    );

    const modalBody = getCentresElement(
        "centre-modal-body"
    );

    const reloadTable =
        modalBody &&
        modalBody.querySelector(
            ".created-dialog"
        );

    closeCentreModalElement(modal);

    if (modalBody) {
        modalBody.innerHTML = "";
    }

    if (reloadTable) {
        loadCentres(activeCentrePage);
    }

}


/* ======================================================
   ADD CENTRE
====================================================== */

function openAddCentre() {

    const modal =
        getCentresElement("centre-modal");

    const modalBody =
        getCentresElement("centre-modal-body");

    if (!modal || !modalBody) {

        console.error(
            "Centre modal not found."
        );

        return;

    }

    fetch(
        getCentresContextPath()
        + "/CentreServlet?action=add"
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "HTTP " + response.status
            );

        }

        return response.text();

    })

    .then(html => {

        modalBody.innerHTML = html;

        const form =
            document.getElementById("addCentreForm");

        if (form) {

            form.addEventListener(
                "submit",
                submitAddCentre
            );

        }

        openCentreModal(modal);

    })

    .catch(error => {

        console.error(
            "Open Add Centre:",
            error
        );

    });

}



/* ======================================================
   SUBMIT ADD CENTRE
====================================================== */

function submitAddCentre(event) {

    event.preventDefault();

    const form =
        document.getElementById("addCentreForm");

    if (!form) {
        return;
    }

   const data =
    new URLSearchParams(
        new FormData(form)
    );

  const actionUrl =
    form.getAttribute("action");

fetch(
    actionUrl,
    {
        method: "POST",
        body: data
    }
)

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "HTTP ERROR " + response.status
            );

        }

        return response.json();

    })

    .then(json => {

        if (!json.success) {

            alert(
                json.error ||
                "Error creating centre."
            );

            return;

        }

        closeCentreModal();

        openCreatedCentre();

    })

    .catch(error => {

        console.error(
            "Create Centre:",
            error
        );

    });

}




/* ======================================================
   RESET PASSWORD
====================================================== */

function resetCentrePassword(id) {

    if (!id) {
        return;
    }

    resetCentreId = id;

    openCentreModal(
        getCentresElement("reset-confirm-modal")
    );

}


function closeResetConfirm() {

    closeCentreModalElement(
        getCentresElement("reset-confirm-modal")
    );

}


function confirmResetPassword() {

    if (!resetCentreId) {
        return;
    }

    const centreId = resetCentreId;

    resetCentreId = null;

    closeResetConfirm();

    const url =
        getCentresContextPath()
        + "/CentreServlet?action=resetPassword&id="
        + encodeURIComponent(centreId);

    fetch(url)

        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "HTTP ERROR " + response.status
                );
            }

            return response.text();

        })

        .then(html => {

            const modalBody = getCentresElement(
                "centre-modal-body"
            );

            const modal = getCentresElement(
                "centre-modal"
            );

            if (!modalBody || !modal) {
                return;
            }

            modalBody.innerHTML = html;

            openCentreModal(modal);

        })

        .catch(() => {

            showCentreActionError(
                "Error resetting password."
            );

        });

}


/* ======================================================
   COPY LOGIN INFORMATION
====================================================== */

function copyLoginInfo() {

    const text = getCentresElement(
        "loginInfoText"
    );

    if (!text) {
        return;
    }

    const value = text.value || text.textContent || "";

    if (
        navigator.clipboard
        && navigator.clipboard.writeText
    ) {

        navigator.clipboard
            .writeText(value)
            .then(showCopyMessage)
            .catch(() => {});

        return;
    }

    text.select();

    document.execCommand("copy");

    showCopyMessage();

}


function showCopyMessage() {

    const button = document.querySelector(
        ".copy-password-btn"
    );

    if (!button) {
        return;
    }

    const originalContent =
        button.dataset.originalContent
        || button.innerHTML;

    button.dataset.originalContent =
        originalContent;

    button.classList.add("copied");

    setTimeout(() => {

        button.innerHTML = originalContent;

        button.classList.remove("copied");

    }, 2000);

}


/* ======================================================
   EDIT CENTRE
====================================================== */

function editCentre(id) {

    if (!id) {
        return;
    }

    editCentreId = id;

    openCentreModal(
        getCentresElement("edit-confirm-modal")
    );

}


function closeEditConfirm() {

    closeCentreModalElement(
        getCentresElement("edit-confirm-modal")
    );

}

function confirmEditCentre() {

    if (!editCentreId) {
        return;
    }

    const centreId = editCentreId;

    editCentreId = null;

    closeEditConfirm();

    const url =
        getCentresContextPath()
        + "/CentreServlet?action=edit&id="
        + encodeURIComponent(centreId);

    fetch(url)

        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "HTTP ERROR " + response.status
                );
            }

            return response.text();

        })

        .then(html => {

            const modalBody = getCentresElement(
                "centre-modal-body"
            );

            const modal = getCentresElement(
                "centre-modal"
            );

            if (!modalBody || !modal) {
                return;
            }

            modalBody.innerHTML = html;

            openCentreModal(modal);

        })

        .catch(() => {

            showCentreActionError(
                "Unable to load the edit form."
            );

        });

}

/* ======================================================
   SAVE EDIT CENTRE PROFILE
====================================================== */

function getEditCentreFieldValue(form, name) {

    const field = form.querySelector(
        "[name='" + name + "']"
    );

    return field
        ? field.value
        : "";

}

function saveEditCentre() {

    const form = getCentresElement(
        "editCentreForm"
    );

    if (!form) {
        return;
    }

    const id = getEditCentreFieldValue(
        form,
        "id"
    );

    if (!id) {
        return;
    }

    const data = new URLSearchParams();

    data.append("action", "updateProfile");
    data.append("id", id);

    data.append(
        "name",
        getEditCentreFieldValue(form, "name")
    );

    data.append(
        "owner_name",
        getEditCentreFieldValue(form, "owner_name")
    );

    data.append(
        "phone",
        getEditCentreFieldValue(form, "phone")
    );

    fetch(
        getCentresContextPath()
        + "/CentreServlet",
        {
            method: "POST",

            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded;charset=UTF-8"
            },

            body: data.toString()
        }
    )

        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "HTTP ERROR " + response.status
                );
            }

            return response.json();

        })

        .then(json => {

            if (!json.success) {

                alert(json.error);

                return;
            }

            closeCentreModal();

            loadCentres(1);

        })

        .catch(() => {});

}

/* ======================================================
   MODAL ERROR / CLOSE EVENTS
====================================================== */

function showCentreActionError(message) {

    const modalBody = getCentresElement(
        "centre-modal-body"
    );

    const modal = getCentresElement(
        "centre-modal"
    );

    if (!modalBody || !modal) {
        return;
    }

    modalBody.innerHTML = `
        <div class="empty-state centre-action-error">
            <i class="fa-solid fa-circle-exclamation"
               aria-hidden="true"></i>
            <p>${escapeCentreHtml(message)}</p>
        </div>
    `;

    openCentreModal(modal);

}

function bindCentresModalEvents() {

    if (centresModalEventsBound) {
        return;
    }

    centresModalEventsBound = true;

    document.addEventListener("click", function (event) {

        if (
            !event.target.classList
            || !event.target.classList.contains(
                "centre-modal"
            )
        ) {
            return;
        }

        closeCentreModalElement(event.target);

    });

    document.addEventListener("keydown", function (event) {

        if (event.key !== "Escape") {
            return;
        }

        document.querySelectorAll(
            ".centre-modal.show"
        ).forEach(modal => {

            closeCentreModalElement(modal);

        });

    });

}

/* ======================================================
   PAGE INITIALIZATION
====================================================== */

function initCentresPage() {

    const centresPage = document.querySelector(
        ".centres-page"
    );

    if (!centresPage) {
        return;
    }

    activateCentreEvents();

    bindCentresModalEvents();

    loadCentres(1);

    startCentresAutoRefresh();

}


document.addEventListener(
    "DOMContentLoaded",
    initCentresPage
);


document.addEventListener(
    "centria:centres-ready",
    initCentresPage
);

/* ======================================================
   COPY GENERATED PASSWORD
====================================================== */
function copyGeneratedPassword() {

    const password =
        document.getElementById(
            "generatedPassword"
        );

    if (!password) {
        return;
    }

    const value =
        password.textContent.trim();

    const copyButton =
        document.getElementById(
            "copyPasswordButton"
        );

    function showCopiedState() {

        if (!copyButton) {
            return;
        }

        copyButton.innerHTML =
            '<i class="fa-solid fa-check"></i>';

        copyButton.classList.add(
            "copied"
        );

        setTimeout(function () {

            copyButton.innerHTML =
                '<i class="fa-solid fa-copy"></i>';

            copyButton.classList.remove(
                "copied"
            );

        }, 1500);

    }

    if (
        navigator.clipboard &&
        navigator.clipboard.writeText
    ) {

        navigator.clipboard
            .writeText(value)
            .then(function () {

                showCopiedState();

            });

        return;

    }

    const range =
        document.createRange();

    range.selectNodeContents(password);

    const selection =
        window.getSelection();

    selection.removeAllRanges();

    selection.addRange(range);

    document.execCommand("copy");

    selection.removeAllRanges();

    showCopiedState();

}
/* ======================================================
   CREATED CENTRE
====================================================== */
function openCreatedCentre() {

    const modal =
        getCentresElement("centre-modal");

    const modalBody =
        getCentresElement("centre-modal-body");

    if (!modal || !modalBody) {

        console.error(
            "Centre modal not found."
        );

        return;

    }

    fetch(
        getCentresContextPath()
        + "/CentreServlet?action=created"
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "HTTP " + response.status
            );

        }

        return response.text();

    })

    .then(html => {

        modalBody.innerHTML = html;

        openCentreModal(modal);

    })

    .catch(error => {

        console.error(
            "Open Created Centre:",
            error
        );

    });

}
/* ======================================================
   COPY CREATED CREDENTIALS
====================================================== */
function copyCreatedCredentials() {

    const centreCode =
        document.getElementById(
            "createdCentreCode"
        );

    const username =
        document.getElementById(
            "createdUsername"
        );

    const password =
        document.getElementById(
            "createdPassword"
        );

    if (
        !centreCode ||
        !username ||
        !password
    ) {
        return;
    }

const value =
    "CENTRIA"
    + "\n"
    + "------------------------"
    + "\n"
    + "Centre Code : "
    + centreCode.textContent.trim()
    + "\n"
    + "Username : "
    + username.textContent.trim()
    + "\n"
    + "Password : "
    + password.textContent.trim();
    
    const copyButton =
        document.getElementById(
            "createdCopyButton"
        );

    const copyIcon =
        document.getElementById(
            "createdCopyIcon"
        );

    function showCopiedState() {

        if (!copyButton || !copyIcon) {
            return;
        }

        copyIcon.className =
            "fa-solid fa-check";

        copyButton.classList.add(
            "copied"
        );

        setTimeout(function () {

            copyIcon.className =
                "fa-solid fa-copy";

            copyButton.classList.remove(
                "copied"
            );

        }, 1500);

    }

    if (
        navigator.clipboard &&
        navigator.clipboard.writeText
    ) {

        navigator.clipboard
            .writeText(value)
            .then(showCopiedState);

        return;

    }

    const textarea =
        document.createElement(
            "textarea"
        );

    textarea.value = value;

    document.body.appendChild(
        textarea
    );

    textarea.select();

    document.execCommand("copy");

    document.body.removeChild(
        textarea
    );

    showCopiedState();

}

/* ======================================================
   CLOSE CREATED CENTRE
====================================================== */

function closeCreatedCentre() {

    closeCentreModal();

    loadCentres(activeCentrePage);

}

/* ======================================================
   CENTRES AUTO REFRESH
   Refresh centres table every 60 seconds
   ====================================================== */

let centresAutoRefreshTimer = null;


function startCentresAutoRefresh() {

    /*
    --------------------------------------------------
    Prevent duplicate timers
    --------------------------------------------------
    */

    if (centresAutoRefreshTimer !== null) {

        return;

    }


    /*
    --------------------------------------------------
    Start timer
    --------------------------------------------------
    */

    centresAutoRefreshTimer = setInterval(
        function () {

            /*
            --------------------------------------------------
            Make sure Centres page is still visible
            --------------------------------------------------
            */

            const centresPage =
                    document.querySelector(
                            ".centres-page"
                    );


            if (!centresPage) {

                return;

            }

  console.log(
            "[CENTRIA] Auto refreshing centres page:",
            activeCentrePage
        );
            /*
            --------------------------------------------------
            Refresh current page
            --------------------------------------------------
            */

            loadCentres(
                    activeCentrePage
            );

        },
        60000
    );

}