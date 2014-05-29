package com.wtl.wawork.core.persistence.repository;

import java.util.List;

import com.wtl.wawork.core.persistence.domain.Posting;
import com.wtl.wawork.core.persistence.repository.criteria.PostingSearchCriteria;

public interface PostingRepository extends CrudRepository<Posting> {

    /**
     * Finds all postings
     * 
     * @return the postings
     */
    public List<Posting> findAll();

    /**
     * Finds all postings in the system filtered and sorted by the provided
     * criteria
     * 
     * @param criteria
     *            the search criteria
     * @return the relevant postings, not null
     * @throws IllegalArgumentException
     *             if criteria is null
     */
    public List<Posting> findAll(PostingSearchCriteria searchCriteria);

}
