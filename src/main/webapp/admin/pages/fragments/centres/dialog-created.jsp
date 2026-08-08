<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<%

/*
==================================================
READ GENERATED DATA FROM SESSION
==================================================
*/

String centreCode =
        (String) session.getAttribute(
                "centreCode"
        );

String username =
        (String) session.getAttribute(
                "username"
        );

String password =
        (String) session.getAttribute(
                "password"
        );


/*
==================================================
PROTECT AGAINST DIRECT ACCESS
==================================================
*/

if(centreCode == null ||
   username == null ||
   password == null){


    response.sendRedirect(
            request.getContextPath()
            +
            "/admin/dashboard.jsp?section=centres"
    );

    return;

}

%>

<div class="created-dialog">

    <!-- =================================================
         TOP BAR
    ================================================= -->

    <div class="created-dialog-top">

        <div class="created-dialog-header">

            <div class="created-dialog-icon">

                <i class="fa-solid fa-circle-check"></i>

            </div>

            <div class="created-dialog-divider"></div>

            <h2 class="created-dialog-title">

                <%= LanguageManager.get(
                        "centers.created.success",
                        session
                ) %>

            </h2>

        </div>

    </div>



    <!-- =================================================
         LOGIN INFORMATION
    ================================================= -->

    <div class="created-dialog-section">

        <div class="created-dialog-section-title">

            <i class="fa-solid fa-user-lock"></i>

            <%= LanguageManager.get(
                    "centers.credentials.title",
                    session
            ) %>

        </div>

        <div class="created-dialog-grid">

            <!-- Centre Code -->

            <div class="created-dialog-row">

                <div class="created-dialog-label">

                    <i class="fa-solid fa-building"></i>

                    <%= LanguageManager.get(
                            "centers.code.label",
                            session
                    ) %>

                </div>

                <div class="created-dialog-value">

                    <strong id="createdCentreCode">

                        <%= centreCode %>

                    </strong>

                </div>

            </div>



            <!-- Username -->

            <div class="created-dialog-row">

                <div class="created-dialog-label">

                    <i class="fa-solid fa-user"></i>

                    <%= LanguageManager.get(
                            "centers.username.label",
                            session
                    ) %>

                </div>

                <div class="created-dialog-value">

                    <strong id="createdUsername">

                        <%= username %>

                    </strong>

                </div>

            </div>



            <!-- Password -->

            <div class="created-dialog-row created-dialog-row-full">

                <div class="created-dialog-label">

                    <i class="fa-solid fa-lock"></i>

                    <%= LanguageManager.get(
                            "centers.password.label",
                            session
                    ) %>

                </div>

                <div class="created-dialog-password-box">

                    <div
                            id="createdPassword"
                            class="created-dialog-password-value">

                        <%= password %>

                    </div>

              <button
        type="button"
        id="createdCopyButton"
        class="created-copy-button"
        onclick="copyCreatedCredentials()"
        title="<%= LanguageManager.get(
                "centers.copy.credentials",
                session
        ) %>">

    <i
            id="createdCopyIcon"
            class="fa-solid fa-copy">

    </i>

</button>

                </div>

            </div>

        </div>

    </div>



    <!-- =================================================
         NOTE
    ================================================= -->

    <div class="created-dialog-note">

        <i class="fa-solid fa-circle-info"></i>

        <span>

            <%= LanguageManager.get(
                    "centers.created.note",
                    session
            ) %>

        </span>

    </div>



    <!-- =================================================
         FOOTER
    ================================================= -->

    <div class="created-dialog-footer">

        <button
                type="button"
                class="btn-primary"
               onclick="closeCreatedCentre()">

            <i class="fa-solid fa-arrow-left"></i>

            <%= LanguageManager.get(
                    "centers.back",
                    session
            ) %>

        </button>

    </div>

</div>
            
            <%

/*
==================================================
CLEAR SESSION
Display credentials only once
==================================================
*/

session.removeAttribute(
        "centreCode"
);

session.removeAttribute(
        "username"
);

session.removeAttribute(
        "password"
);

%>
            