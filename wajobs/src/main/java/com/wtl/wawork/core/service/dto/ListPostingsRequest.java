package com.wtl.wawork.core.service.dto;

import java.util.List;

import com.wtl.wawork.core.persistence.domain.IdentityEnum;

public class ListPostingsRequest {

    private List<Long> jurisdictionIds;

    private IdentityEnum employmentType;

    public ListPostingsRequest() {
        super();
    }

    public ListPostingsRequest(List<Long> jurisdictionIds, IdentityEnum employmentType) {
        super();
        this.jurisdictionIds = jurisdictionIds;
        this.employmentType = employmentType;
    }

    /**
     * @return employment type for filtering postings. nullable.
     */
    public IdentityEnum getEmploymentType() {
        return employmentType;
    }

    /**
     * @return a list of jurisdiction IDs, nullable.
     */
    public List<Long> getJurisdictionIds() {
        return jurisdictionIds;
    }

}
