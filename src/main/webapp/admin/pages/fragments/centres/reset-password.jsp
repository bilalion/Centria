<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.language.LanguageManager"%>

<%
String centreCode =
        (String) request.getAttribute("centreCode");

String username =
        (String) request.getAttribute("username");

String password =
        (String) request.getAttribute("password");
%>

<div class="reset-dialog">

  <!-- =================================================
     TOP BAR
================================================= -->

<div class="reset-top">

    <div class="reset-header">

        <div class="reset-icon">

            <i class="fa-solid fa-key"></i>

        </div>

        <div class="reset-divider"></div>

        <h3 class="reset-title">

            <%= LanguageManager.get(
                    "centers.reset.title",
                    session
            ) %>

        </h3>

    </div>

</div>



    <!-- =================================================
         LOGIN INFORMATION
    ================================================= -->

    <div class="reset-section">

        <div class="reset-section-title">

            <i class="fa-solid fa-user-lock"></i>

            <%= LanguageManager.get(
                    "centers.reset.account",
                    session
            ) %>

        </div>



        <div class="reset-grid">


            <!-- Centre Code -->

            <div class="reset-row">

                <div class="reset-label">

                    <i class="fa-solid fa-key"></i>

                    <%= LanguageManager.get(
                            "centers.code",
                            session
                    ) %>

                </div>

                <div class="reset-value reset-centre-code">

                    <strong>

                        <%= centreCode %>

                    </strong>

                </div>

            </div>



            <!-- Username -->

            <div class="reset-row">

                <div class="reset-label">

                    <i class="fa-solid fa-user"></i>

                    <%= LanguageManager.get(
                            "centers.username",
                            session
                    ) %>

                </div>

                <div class="reset-value">

                    <strong>

                        <%= username %>

                    </strong>

                </div>

            </div>



            <!-- Password -->

            <div class="reset-row reset-row-full">

                <div class="reset-label">

                    <i class="fa-solid fa-lock"></i>

                    <%= LanguageManager.get(
                            "centers.password.new",
                            session
                    ) %>

                </div>

                <div class="reset-password-box">

                    <div
                            id="generatedPassword"
                            class="reset-password-value">

                        <%= password %>

                    </div>

              <button
        type="button"
        id="copyPasswordButton"
        class="reset-copy-button"
        onclick="copyGeneratedPassword()"
        title="<%= LanguageManager.get(
                "centers.copy.password",
                session
        ) %>">

    <i class="fa-solid fa-copy"></i>

</button>


                </div>

            </div>

        </div>

    </div>



    <!-- =================================================
         NOTE
    ================================================= -->

    <div class="reset-note">

        <i class="fa-solid fa-circle-info"></i>

        <span>

            <%= LanguageManager.get(
                    "centers.reset.note",
                    session
            ) %>

        </span>

    </div>

<!-- =================================================
     FOOTER
================================================= -->

<div class="reset-actions">

    <button
            type="button"
            class="btn-primary"
            onclick="closeCentreModal()">

        <i class="fa-solid fa-arrow-left"></i>

        <%= LanguageManager.get(
                "centers.back",
                session
        ) %>

    </button>
  

</div>

</div>