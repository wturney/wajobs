package com.wtl.wawork.core.service.dto;

import java.util.Collections;
import java.util.List;

public class ListPostingsResponse {

    private final List<PostingDetails> postingDetails;

    public ListPostingsResponse(List<PostingDetails> postings) {
        super();
        this.postingDetails = Collections.unmodifiableList(postings);
    }

    public List<PostingDetails> getPostingDetails() {
        return postingDetails;
    }

}
