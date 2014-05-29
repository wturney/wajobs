package com.wtl.wawork.core.persistence.repository.criteria;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.wtl.wawork.core.persistence.domain.IdentityEnum;

/**
 * Represents the various options for querying postings
 * 
 * @author Weston Turney-Loos
 * 
 */
public class PostingSearchCriteria {

    public static enum PostingSortCriteria {
        OPEN_DATE, EMPLOYMENT_TYPE, JURISDICTION_NAME;
    }

    public static enum SortDirection {
        ASC, DESC;
    }

    private List<Long> jurisdictionIds;

    private IdentityEnum employmentType;

    private PostingSortCriteria sortCriteria;

    private SortDirection sortDirection;

    public PostingSearchCriteria() {
        this.sortCriteria = PostingSortCriteria.OPEN_DATE;
        this.sortDirection = SortDirection.ASC;
    }

    public IdentityEnum getEmploymentType() {
        return employmentType;
    }

    public List<Long> getJurisdictionIds() {
        return jurisdictionIds;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public PostingSortCriteria getSortOrder() {
        return sortCriteria;
    }

    public void setEmploymentType(IdentityEnum employmentType) {
        this.employmentType = employmentType;
    }

    public void setJurisdictionIds(List<Long> jurisdictionIds) {
        this.jurisdictionIds = jurisdictionIds;
    }

    public void setSortCriteria(PostingSortCriteria sortOrder) {
        checkNotNull(sortOrder);
        this.sortCriteria = sortOrder;
    }

    public void setSortDirection(SortDirection sortDirection) {
        checkNotNull(sortCriteria);
        this.sortDirection = sortDirection;
    }

}
