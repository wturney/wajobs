package com.wtl.wawork.core.service;

import com.wtl.wawork.core.service.dto.ListPostingsRequest;
import com.wtl.wawork.core.service.dto.ListPostingsResponse;

public interface PostingService {

    /**
     * Finds any postings in the system that match the filters provided in the
     * {@link ListPostingsRequest}
     * 
     * @param postingRequest
     *            request details to filter postings.
     * @return the response, not null.
     */
    public ListPostingsResponse listPostings(ListPostingsRequest postingRequest);
}
