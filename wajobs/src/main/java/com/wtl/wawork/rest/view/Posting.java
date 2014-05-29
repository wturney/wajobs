package com.wtl.wawork.rest.view;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import com.wtl.wawork.core.persistence.domain.IdentityEnum;
import com.wtl.wawork.core.service.dto.PostingDetails;

/**
 * REST view of a given posting
 * 
 * @author Weston Turney-Loos
 * 
 */
public class Posting {

    public static Posting fromPostingDetails(PostingDetails details) {
        Posting posting = new Posting();

        BeanUtils.copyProperties(details, posting);
        posting.setJurisdiction(details.getJurisdiction().getId());

        return posting;
    }

    private String title;

    private DateTime openDate;

    private DateTime closeDate;

    private IdentityEnum employmentType;

    private long jurisdiction;

    private String url;

    public DateTime getCloseDate() {
        return closeDate;
    }

    public IdentityEnum getEmploymentType() {
        return employmentType;
    }

    public long getJurisdiction() {
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

    public void setJurisdiction(long jurisdiction) {
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
