package com.wtl.wawork.rest.view;

import com.wtl.wawork.core.service.dto.JurisdictionDetails;

/**
 * REST view model of a jurisdiction
 * 
 * @author Weston Turney-Loos
 * 
 */
public class Jurisdiction {

    public static Jurisdiction fromJurisdictionDetails(JurisdictionDetails details) {
        return new Jurisdiction(details.getId(), details.getName());
    }

    private long id;

    private String name;

    public Jurisdiction() {
    }

    public Jurisdiction(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
