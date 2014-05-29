package com.wtl.wawork.core.persistence.fixture;

import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.Posting;
import com.wtl.wawork.core.persistence.domain.builder.PostingBuilder;

public class PostingFixture {

    public static final String POSTING_ONE_TITLE = "Tacoma Job One";
    public static final long POSTING_ONE_ID = 1;

    public static final String POSTING_TWO_TITLE = "Tacoma Job Two";
    public static final long POSTING_TWO_ID = 2;

    public static final String POSTING_THREE_TITLE = "Tacoma Job Three";
    public static final long POSTING_THREE_ID = 3;

    public static List<Posting> allPostings() {
        List<Posting> postings = Lists.newArrayList();
        PostingBuilder pdb = new PostingBuilder();

        pdb.withOpenDate(DateTime.now())
                .withCloseDate(DateTime.now().plusMonths(1))
                .withJurisdiction(JurisdictionFixture.simpleJurisdiction());

        postings.add(pdb.withId(POSTING_ONE_ID).withTitle(POSTING_ONE_TITLE).build());
        postings.add(pdb.withId(POSTING_TWO_ID).withTitle(POSTING_TWO_TITLE).build());
        postings.add(pdb.withId(POSTING_THREE_ID).withTitle(POSTING_THREE_TITLE).build());

        return postings;
    }

    public static Posting simplePosting() {
        PostingBuilder pdb = new PostingBuilder();

        return pdb.withJurisdiction(JurisdictionFixture.simpleJurisdiction()).withId(POSTING_ONE_ID).withTitle(POSTING_ONE_TITLE).build();
    }

}
