<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.utils.LanguageManager"%>


<div class="centre-view-container">


    <div class="confirm-header">


        <div class="confirm-icon">

            ✏️

        </div>



        <h4 class="confirm-title">

            <%=LanguageManager.get(
                "centers.edit.title",
                session
            )%>

        </h4>


    </div>





    <div class="empty-state">


        <h3>
            ✅
        </h3>


        <p>

            EDIT CENTRE DIALOG LOADED SUCCESSFULLY

        </p>


    </div>





</div>