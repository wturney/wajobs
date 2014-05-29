package com.wtl.wawork.core.persistence.repository.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.wtl.wawork.core.persistence.domain.Jurisdiction;
import com.wtl.wawork.core.persistence.repository.JurisdictionRepository;

@Repository
public class JurisdictionRepositoryJpa extends CrudRepositoryJpa<Jurisdiction> implements JurisdictionRepository {

    @Override
    public List<Jurisdiction> findAll() {
        final TypedQuery<Jurisdiction> q = em.createQuery("from Jurisdiction j", Jurisdiction.class);

        return q.getResultList();
    }

}
