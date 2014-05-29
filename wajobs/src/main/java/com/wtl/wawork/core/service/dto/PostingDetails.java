package com.wtl.wawork.core.service.dto;

import org.joda.time.DateTime;

import com.wtl.wawork.core.persistence.domain.IdentityEnum;

public class PostingDetails {

    private long id;

    private String title;

    private DateTime openDate;

    private DateTime closeDate;

    private IdentityEnum employmentType;

    private JurisdictionDetails jurisdiction;

    private String url;

    public DateTime getCloseDate() {
        return closeDate;
    }

    public IdentityEnum getEmploymentType() {
        return employmentType;
    }

    public long getId() {
        return id;
    }

    public JurisdictionDetails getJurisdiction() {
        return jurisdiction;
    }

    public DateTime getOpenDate() {
        return openDate;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setCloseDate(DateTime closeDate) {
        this.closeDate = closeDate;
    }

    public void setEmploymentType(IdentityEnum employmentType) {
        this.employmentType = employmentType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setJurisdiction(JurisdictionDetails jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public void setOpenDate(DateTime openDate) {
        this.openDate = openDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
