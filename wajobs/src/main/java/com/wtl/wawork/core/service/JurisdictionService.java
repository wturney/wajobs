package com.wtl.wawork.core.service;

import com.wtl.wawork.core.service.dto.ListJurisdictionsRequest;
import com.wtl.wawork.core.service.dto.ListJurisdictionsResponse;

public interface JurisdictionService {

    /**
     * Finds all jurisdictions
     * 
     * @param jurisdictionsRequest
     *            the request details.
     * @return the response, not null.
     */
    public ListJurisdictionsResponse listJurisdictions(ListJurisdictionsRequest jurisdictionsRequest);

}
