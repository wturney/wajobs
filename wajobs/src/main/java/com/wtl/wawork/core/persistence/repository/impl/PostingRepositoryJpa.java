package com.wtl.wawork.core.persistence.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.stereotype.Repository;

import com.wtl.wawork.core.persistence.domain.Jurisdiction;
import com.wtl.wawork.core.persistence.domain.Posting;
import com.wtl.wawork.core.persistence.repository.PostingRepository;
import com.wtl.wawork.core.persistence.repository.criteria.PostingSearchCriteria;

@Repository
public class PostingRepositoryJpa extends CrudRepositoryJpa<Posting> implements PostingRepository {

    @Override
    public List<Posting> findAll() {
        final TypedQuery<Posting> q = em.createQuery("from Posting p", Posting.class);

        return q.getResultList();
    }

    @Override
    public List<Posting> findAll(PostingSearchCriteria criteria) {
        checkNotNull(criteria, "Expected non-null criteria");

        final Metamodel meta = em.getMetamodel();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Posting> cq = cb.createQuery(Posting.class);
        final Root<Posting> posting = cq.from(Posting.class);
        final EntityType<Jurisdiction> jmeta = meta.entity(Jurisdiction.class);
        final EntityType<Posting> pmeta = meta.entity(Posting.class);
        final Join<Posting, Jurisdiction> jjoin = posting.join("jurisdiction");

        Predicate clause = cb.conjunction();
        if (criteria.getJurisdictionIds() != null && !criteria.getJurisdictionIds().isEmpty()) {
            clause = cb.and(clause, jjoin.get(jmeta.getSingularAttribute("id")).in(criteria.getJurisdictionIds()));
        }

        if (criteria.getEmploymentType() != null) {
            clause = cb.and(clause, cb.equal(posting.get(pmeta.getSingularAttribute("employmentType")), criteria.getEmploymentType()));
        }

        Expression<?> expr;
        Order order;
        switch (criteria.getSortOrder()) {
        case EMPLOYMENT_TYPE:
            expr = posting.get(pmeta.getSingularAttribute("employmentType"));
            break;
        case JURISDICTION_NAME:
            expr = jjoin.get(jmeta.getSingularAttribute("name"));
            break;
        case OPEN_DATE:
        default:
            expr = posting.get(pmeta.getSingularAttribute("openDate"));
            break;
        }
        switch (criteria.getSortDirection()) {
        case DESC:
            order = cb.desc(expr);
            break;
        case ASC:
        default:
            order = cb.asc(expr);
            break;
        }

        cq.select(posting);
        cq.where(clause);
        cq.orderBy(order);

        final TypedQuery<Posting> q = em.createQuery(cq);

        return q.getResultList();
    }
}
