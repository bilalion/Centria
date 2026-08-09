package com.centria.models;

import java.util.Date;

public class Archive {

    private int id;

    private String centreCode;

    private String archiveStatus;

    private Date archivedAt;

    private Date retentionUntil;

    private Date restoredAt;

    private Date deletedAt;

    public Archive() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCentreCode() {
        return centreCode;
    }

    public void setCentreCode(String centreCode) {
        this.centreCode = centreCode;
    }

    public String getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(String archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public Date getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Date archivedAt) {
        this.archivedAt = archivedAt;
    }

    public Date getRetentionUntil() {
        return retentionUntil;
    }

    public void setRetentionUntil(Date retentionUntil) {
        this.retentionUntil = retentionUntil;
    }

    public Date getRestoredAt() {
        return restoredAt;
    }

    public void setRestoredAt(Date restoredAt) {
        this.restoredAt = restoredAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
   