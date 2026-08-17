document.addEventListener("DOMContentLoaded", function () {

    const headerProfile =
        document.getElementById("headerProfile");

    const userDropdown =
        document.getElementById("userDropdown");


    if (!headerProfile || !userDropdown) {
        return;
    }


    headerProfile.addEventListener("click", function (event) {

        event.stopPropagation();

        userDropdown.classList.toggle("show");

        headerProfile.classList.toggle(
            "dropdown-open"
        );

    });


    document.addEventListener("click", function () {

        userDropdown.classList.remove("show");

        headerProfile.classList.remove(
            "dropdown-open"
        );

    });


    userDropdown.addEventListener("click", function (event) {

        event.stopPropagation();

    });

});