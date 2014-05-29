package com.wtl.wawork.core.service.dto;

import java.util.List;

public class ListJurisdictionsResponse {

    private List<JurisdictionDetails> jurisdictionDetails;

    public ListJurisdictionsResponse(List<JurisdictionDetails> jurisdictionDetails) {
        super();
        this.jurisdictionDetails = jurisdictionDetails;
    }

    public List<JurisdictionDetails> getJurisdictionDetails() {
        return jurisdictionDetails;
    }

}
