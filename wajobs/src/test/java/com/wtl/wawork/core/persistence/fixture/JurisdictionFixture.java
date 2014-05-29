package com.wtl.wawork.core.persistence.fixture;

import java.util.List;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.Jurisdiction;
import com.wtl.wawork.core.persistence.domain.builder.JurisdictionBuilder;

public class JurisdictionFixture {

    public static final String JURISDICTION_ONE_NAME = "City of Tacoma";
    public static final long JURISDICTION_ONE_ID = 1;

    public static final String JURISDICTION_TWO_NAME = "City of Seattle";
    public static final long JURISDICTION_TWO_ID = 1;

    public static final String JURISDICTION_THREE_NAME = "Thurston County";
    public static final long JURISDICTION_THREE_ID = 1;

    public static List<Jurisdiction> allJurisdictions() {
        List<Jurisdiction> jurisdictions = Lists.newArrayList();
        JurisdictionBuilder jdb = new JurisdictionBuilder();

        jurisdictions.add(jdb.withId(JURISDICTION_ONE_ID).withName(JURISDICTION_ONE_NAME).build());
        jurisdictions.add(jdb.withId(JURISDICTION_TWO_ID).withName(JURISDICTION_TWO_NAME).build());
        jurisdictions.add(jdb.withId(JURISDICTION_THREE_ID).withName(JURISDICTION_THREE_NAME).build());

        return jurisdictions;
    }

    public static Jurisdiction simpleJurisdiction() {
        JurisdictionBuilder jdb = new JurisdictionBuilder();
        return jdb.withId(JURISDICTION_ONE_ID).withName(JURISDICTION_ONE_NAME).build();
    }

}
