package com.wtl.wawork.rest.controller.fixture;

import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.EmploymentType;
import com.wtl.wawork.core.persistence.domain.IdentityEnum;
import com.wtl.wawork.core.service.dto.ListPostingsResponse;
import com.wtl.wawork.core.service.dto.PostingDetails;
import com.wtl.wawork.core.service.dto.builder.PostingDetailsBuilder;

public class PostingControllerFixture {

    public static final String POSTING_ONE_TITLE = "Tacoma Job One";
    public static final long POSTING_ONE_ID = 1;

    public static final String POSTING_TWO_TITLE = "Tacoma Job Two";
    public static final long POSTING_TWO_ID = 2;

    public static final String POSTING_THREE_TITLE = "Tacoma Job Three";
    public static final long POSTING_THREE_ID = 3;

    public static final IdentityEnum EMPLOYMENT_TYPE = EmploymentType.TEMPORARY;

    public static final List<Long> JURISDICTION_IDS = Lists.newArrayList(POSTING_ONE_ID, POSTING_TWO_ID, POSTING_THREE_ID);

    public static ListPostingsResponse allPostingsResponse() {
        List<PostingDetails> postings = Lists.newArrayList();
        PostingDetailsBuilder pdb = new PostingDetailsBuilder();

        pdb.withOpenDate(DateTime.now())
                .withEmploymentType(EMPLOYMENT_TYPE)
                .withCloseDate(DateTime.now().plusMonths(1))
                .withJurisdiction(JurisdictionControllerFixture.simpleJurisdictionDetails());

        postings.add(pdb.withId(POSTING_ONE_ID).withTitle(POSTING_ONE_TITLE).build());
        postings.add(pdb.withId(POSTING_TWO_ID).withTitle(POSTING_TWO_TITLE).build());
        postings.add(pdb.withId(POSTING_THREE_ID).withTitle(POSTING_THREE_TITLE).build());

        return new ListPostingsResponse(postings);
    }
}
