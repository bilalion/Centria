<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.centria.models.Archive"%>
<%@page import="com.centria.language.LanguageManager"%>
<%@page import="java.text.SimpleDateFormat"%>


<%

/*
======================================================
ARCHIVE VIEW
======================================================

Displays the archive information of a centre.

Expected request attribute:

    archive

The Archive object contains:

    id
    centreCode
    centreName
    archiveStatus
    archivedAt
    retentionUntil
    restoredAt
    deletedAt
======================================================
*/


Archive archive =
        (Archive) request.getAttribute(
                "archive"
        );


/*
======================================================
ARCHIVE NOT FOUND
======================================================
*/

if (archive == null) {

%>


<div class="empty-state">

    <p>

        <%= LanguageManager.get(
                "archive.notfound",
                session
        ) %>

    </p>

</div>


<%

return;

}


/*
======================================================
DATE FORMAT
======================================================
*/

SimpleDateFormat sdf =
        new SimpleDateFormat(
                "dd/MM/yyyy HH:mm"
        );


/*
======================================================
ARCHIVE STATUS
======================================================
*/

String archiveStatus =
        archive.getArchiveStatus();


if (
        archiveStatus == null
        ||
        archiveStatus.trim().isEmpty()
) {

    archiveStatus = "ARCHIVED";

}


String statusClass =
        "archive-status-" +
        archiveStatus.toLowerCase();


String statusLabel =
        LanguageManager.get(
                "archive.status." +
                archiveStatus.toLowerCase(),
                session
        );

%>


<div class="archive-view-container">


    <!-- =================================================
         DIALOG STRIPE
    ================================================== -->


    <div class="dialog-stripe dialog-view">


        <i class="fa-solid fa-box-archive"></i>


        <span class="dialog-divider">

            |

        </span>


        <span class="dialog-title">


            <%= LanguageManager.get(
                    "archive.details.title",
                    session
            ) %>


        </span>


    </div>




    <!-- =================================================
         ARCHIVE INFORMATION
    ================================================== -->


    <div class="centre-card">


        <!-- =================================================
             CENTRE CODE
        ================================================== -->


        <div class="info-row">


            <span>

                <%= LanguageManager.get(
                        "archive.details.code",
                        session
                ) %>

            </span>


            <strong>


                <%= archive.getCentreCode() != null
                        ? archive.getCentreCode()
                        : "-" %>


            </strong>


        </div>




        <!-- =================================================
             CENTRE NAME
        ================================================== -->


        <div class="info-row">


            <span>

                <%= LanguageManager.get(
                        "archive.details.name",
                        session
                ) %>

            </span>


            <strong>


                <%= archive.getCentreName() != null
                        ? archive.getCentreName()
                        : "-" %>


            </strong>


        </div>




        <!-- =================================================
             ARCHIVE STATUS
        ================================================== -->


        <div class="info-row">


            <span>

                <%= LanguageManager.get(
                        "archive.details.status",
                        session
                ) %>

            </span>


            <strong
                    class="status-badge <%= statusClass %>">


                <%= statusLabel %>


            </strong>


        </div>




        <!-- =================================================
             ARCHIVED AT
        ================================================== -->


        <div class="info-row">


            <span>

                <%= LanguageManager.get(
                        "archive.details.archived.at",
                        session
                ) %>

            </span>


            <strong>


                <%= archive.getArchivedAt() != null
                        ? sdf.format(
                                archive.getArchivedAt()
                          )
                        : "-" %>


            </strong>


        </div>




        <!-- =================================================
             RETENTION UNTIL
        ================================================== -->


        <div class="info-row">


            <span>

                <%= LanguageManager.get(
                        "archive.details.retention.until",
                        session
                ) %>

            </span>


            <strong>


                <%= archive.getRetentionUntil() != null
                        ? sdf.format(
                                archive.getRetentionUntil()
                          )
                        : "-" %>


            </strong>


        </div>




        <!-- =================================================
             RESTORED AT
        ================================================== -->


        <div class="info-row">


            <span>

                <%= LanguageManager.get(
                        "archive.details.restored.at",
                        session
                ) %>

            </span>


            <strong>


                <%= archive.getRestoredAt() != null
                        ? sdf.format(
                                archive.getRestoredAt()
                          )
                        : "-" %>


            </strong>


        </div>




        <!-- =================================================
             DELETED AT
        ================================================== -->


        <div class="info-row">


            <span>

                <%= LanguageManager.get(
                        "archive.details.deleted.at",
                        session
                ) %>

            </span>


            <strong>


                <%= archive.getDeletedAt() != null
                        ? sdf.format(
                                archive.getDeletedAt()
                          )
                        : "-" %>


            </strong>


        </div>


    </div>


</div>