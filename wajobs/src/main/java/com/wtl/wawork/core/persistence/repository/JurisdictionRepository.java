package com.wtl.wawork.core.persistence.repository;

import java.util.List;

import com.wtl.wawork.core.persistence.domain.Jurisdiction;

public interface JurisdictionRepository extends CrudRepository<Jurisdiction> {

    /**
     * Find all jurisdictions
     * 
     * @return the jurisdictions
     */
    public List<Jurisdiction> findAll();

}
