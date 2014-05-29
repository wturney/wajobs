package com.wtl.wawork.core.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import com.wtl.wawork.core.service.dto.PostingDetails;

@Entity
public class Posting {

    public static Posting fromPostingDetails(PostingDetails details) {
        Posting posting = new Posting(details.getTitle());

        BeanUtils.copyProperties(details, posting);

        posting.setJurisdiction(Jurisdiction.fromJurisdictionDetails(details.getJurisdiction()));

        return posting;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column
    private DateTime parseDate;

    @Column
    private DateTime openDate;

    @Column
    private DateTime closeDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Jurisdiction jurisdiction;

    @Column
    private String url;

    @SuppressWarnings("unused")
    private Posting() {
        this(null);
    }

    public Posting(String title) {
        this.title = title;
        this.employmentType = EmploymentType.NA;
    }

    public DateTime getCloseDate() {
        return closeDate;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public long getId() {
        return id;
    }

    public Jurisdiction getJurisdiction() {
        return jurisdiction;
    }

    public DateTime getOpenDate() {
        return openDate;
    }

    public DateTime getParseDate() {
        return parseDate;
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

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setJurisdiction(Jurisdiction jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public void setOpenDate(DateTime openDate) {
        this.openDate = openDate;
    }

    public void setParseDate(DateTime parseDate) {
        this.parseDate = parseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PostingDetails toPostingDetails() {
        PostingDetails details = new PostingDetails();

        BeanUtils.copyProperties(this, details);

        details.setJurisdiction(this.jurisdiction.toJurisdictionDetails());

        return details;
    }
}
