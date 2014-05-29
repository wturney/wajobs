package com.wtl.wawork.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.service.JurisdictionService;
import com.wtl.wawork.core.service.dto.JurisdictionDetails;
import com.wtl.wawork.core.service.dto.ListJurisdictionsRequest;
import com.wtl.wawork.rest.view.Jurisdiction;

/**
 * Controller representing jurisdiction resources and their available operations
 * 
 * @author Weston Turney-Loos
 * 
 */
@Controller
@RequestMapping("/jurisdictions")
public class JurisdictionController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(JurisdictionController.class);

    @Autowired
    private JurisdictionService jurisdictionService;

    /**
     * Finds all jurisdictions in the system
     * 
     * @return the jurisdictions
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Jurisdiction> getJurisdictions() {
        final ListJurisdictionsRequest jreq = new ListJurisdictionsRequest();
        final List<Jurisdiction> jurisdictions = Lists.newArrayList();

        for (JurisdictionDetails detail : jurisdictionService.listJurisdictions(jreq).getJurisdictionDetails()) {
            jurisdictions.add(Jurisdiction.fromJurisdictionDetails(detail));
        }

        return jurisdictions;
    }
}
