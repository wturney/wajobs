package com.wtl.wawork.core.service.dto;

public class JurisdictionDetails {

    private long id;

    private String name;

    public JurisdictionDetails(long id, String name) {
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

    public JurisdictionDetails toJurisdictionDetails() {
        JurisdictionDetails details = new JurisdictionDetails(id, name);
        return details;
    }

}
