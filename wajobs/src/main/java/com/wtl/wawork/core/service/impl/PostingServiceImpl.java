package com.wtl.wawork.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.Posting;
import com.wtl.wawork.core.persistence.repository.PostingRepository;
import com.wtl.wawork.core.persistence.repository.criteria.PostingSearchCriteria;
import com.wtl.wawork.core.service.PostingService;
import com.wtl.wawork.core.service.dto.ListPostingsRequest;
import com.wtl.wawork.core.service.dto.ListPostingsResponse;
import com.wtl.wawork.core.service.dto.PostingDetails;

public class PostingServiceImpl implements PostingService {

    @Autowired
    private PostingRepository postingRepository;

    @Override
    public ListPostingsResponse listPostings(ListPostingsRequest postingRequest) {
        final PostingSearchCriteria criteria = new PostingSearchCriteria();
        criteria.setJurisdictionIds(postingRequest.getJurisdictionIds());
        criteria.setEmploymentType(postingRequest.getEmploymentType());

        final List<Posting> results = postingRepository.findAll(criteria);
        final List<PostingDetails> postings = Lists.newArrayList();
        for (Posting posting : results) {
            postings.add(posting.toPostingDetails());
        }
        ListPostingsResponse response = new ListPostingsResponse(postings);

        return response;
    }
}
