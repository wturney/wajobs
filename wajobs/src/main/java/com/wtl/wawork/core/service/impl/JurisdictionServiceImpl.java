package com.wtl.wawork.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.Jurisdiction;
import com.wtl.wawork.core.persistence.repository.JurisdictionRepository;
import com.wtl.wawork.core.service.JurisdictionService;
import com.wtl.wawork.core.service.dto.JurisdictionDetails;
import com.wtl.wawork.core.service.dto.ListJurisdictionsRequest;
import com.wtl.wawork.core.service.dto.ListJurisdictionsResponse;

public class JurisdictionServiceImpl implements JurisdictionService {

    @Autowired
    private JurisdictionRepository jurisdictionRepository;

    @Override
    public ListJurisdictionsResponse listJurisdictions(ListJurisdictionsRequest jurisdictionsRequest) {
        final List<Jurisdiction> results = jurisdictionRepository.findAll();

        List<JurisdictionDetails> details = Lists.newArrayList();
        for (Jurisdiction jurisdiction : results) {
            details.add(jurisdiction.toJurisdictionDetails());
        }
        ListJurisdictionsResponse response = new ListJurisdictionsResponse(details);

        return response;
    }
}
