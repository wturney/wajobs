package com.wtl.wawork.rest.controller.fixture;

import java.util.List;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.service.dto.JurisdictionDetails;
import com.wtl.wawork.core.service.dto.ListJurisdictionsResponse;
import com.wtl.wawork.core.service.dto.builder.JurisdictionDetailsBuilder;

public class JurisdictionControllerFixture {

    public static final String JURISDICTION_ONE_NAME = "City of Tacoma";
    public static final long JURISDICTION_ONE_ID = 1;

    public static final String JURISDICTION_TWO_NAME = "City of Seattle";
    public static final long JURISDICTION_TWO_ID = 2;

    public static final String JURISDICTION_THREE_NAME = "Thurston County";
    public static final long JURISDICTION_THREE_ID = 3;

    public static ListJurisdictionsResponse allJurisdictionsResponse() {
        List<JurisdictionDetails> details = Lists.newArrayList();

        JurisdictionDetailsBuilder jdb = new JurisdictionDetailsBuilder();

        details.add(jdb.withId(JURISDICTION_ONE_ID).withName(JURISDICTION_ONE_NAME).build());
        details.add(jdb.withId(JURISDICTION_TWO_ID).withName(JURISDICTION_TWO_NAME).build());
        details.add(jdb.withId(JURISDICTION_THREE_ID).withName(JURISDICTION_THREE_NAME).build());

        return new ListJurisdictionsResponse(details);
    }

    public static ListJurisdictionsResponse emptyJurisdictionsResponse() {
        List<JurisdictionDetails> details = Lists.newArrayList();

        return new ListJurisdictionsResponse(details);
    }

    public static JurisdictionDetails simpleJurisdictionDetails() {
        JurisdictionDetailsBuilder jdb = new JurisdictionDetailsBuilder();
        return jdb.withId(JURISDICTION_ONE_ID).withName(JURISDICTION_ONE_NAME).build();
    }

}
