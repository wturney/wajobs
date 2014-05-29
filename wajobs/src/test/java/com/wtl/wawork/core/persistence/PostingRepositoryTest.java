package com.wtl.wawork.core.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.PersistenceException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.EmploymentType;
import com.wtl.wawork.core.persistence.domain.Jurisdiction;
import com.wtl.wawork.core.persistence.domain.Posting;
import com.wtl.wawork.core.persistence.domain.builder.PostingBuilder;
import com.wtl.wawork.core.persistence.repository.PostingRepository;
import com.wtl.wawork.core.persistence.repository.criteria.PostingSearchCriteria;
import com.wtl.wawork.core.persistence.repository.criteria.PostingSearchCriteria.PostingSortCriteria;
import com.wtl.wawork.core.persistence.repository.criteria.PostingSearchCriteria.SortDirection;
import com.wtl.wawork.core.persistence.repository.impl.PostingRepositoryJpa;

@ContextConfiguration
public class PostingRepositoryTest extends RepositoryTest {

    @Configuration
    static class Config {

        @Bean
        public PostingRepository postingRepository() {
            return new PostingRepositoryJpa();
        }

    }

    @Autowired
    private PostingRepository repository;

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatAllPostingsCanBeQueried() {
        List<Posting> postings = repository.findAll(new PostingSearchCriteria());

        assertEquals(4, postings.size());
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatEmptyPostingResultsAreNonNull() {
        List<Posting> postings = repository.findAll(new PostingSearchCriteria());
        assertNotNull(postings);

        List<Long> ids = Lists.newArrayList(8482084l, 1252616l);
        PostingSearchCriteria psc = new PostingSearchCriteria();
        psc.setJurisdictionIds(ids);
        postings = repository.findAll(psc);
        assertTrue(postings.isEmpty());
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingCreationRequiresTitle() {
        DateTime now = DateTime.now();
        Jurisdiction jurisdiction = getEntityManager().find(Jurisdiction.class, 1l);
        Posting posting = new PostingBuilder().withOpenDate(now).withJurisdiction(jurisdiction).build();

        try {
            repository.create(posting);
            fail("Posting title must be non-null");
        } catch (PersistenceException e) {
        }
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingQueriesFailIfCriteriaIsNull() {
        try {
            repository.findAll(null);
            fail("Search criteria must be non-null");
        } catch (NullPointerException e) {
        }
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingsCanBeCreated() {
        DateTime dt = DateTime.now();
        Jurisdiction j = getEntityManager().find(Jurisdiction.class, 1l);
        Posting in = new PostingBuilder().withTitle("Test Job").withOpenDate(dt).withJurisdiction(j).build();

        getEntityManager().getTransaction().begin();
        repository.create(in);
        getEntityManager().getTransaction().commit();
        assertTrue(in.getId() > 0);

        Posting out = repository.find(in.getId());
        assertEquals(in.getOpenDate(), out.getOpenDate());
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingsCanBeQueriedByEmploymentType() {
        PostingSearchCriteria psc = new PostingSearchCriteria();
        psc.setEmploymentType(EmploymentType.INTERNSHIP);
        List<Posting> postings = repository.findAll(psc);
        assertEquals(1, postings.size());
        assertEquals(EmploymentType.INTERNSHIP, postings.get(0).getEmploymentType());
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingsCanBeQueriedByJurisdiction() {
        List<Long> ids = Lists.newArrayList(1l, 3l);
        PostingSearchCriteria psc = new PostingSearchCriteria();
        psc.setJurisdictionIds(ids);
        List<Posting> postings = repository.findAll(psc);
        assertEquals(3, postings.size());
        for (Posting p : postings) {
            Jurisdiction j = p.getJurisdiction();
            assertTrue("", ids.contains(j.getId()));
        }
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingsCanBeQueriedByJurisdictionAndEmploymentType() {
        PostingSearchCriteria psc = new PostingSearchCriteria();
        List<Long> ids = Lists.newArrayList(1l);
        psc.setEmploymentType(EmploymentType.FULL_TIME);
        psc.setJurisdictionIds(ids);
        List<Posting> postings = repository.findAll(psc);
        assertEquals(1, postings.size());
        assertEquals(EmploymentType.FULL_TIME, postings.get(0).getEmploymentType());
        assertEquals(1l, postings.get(0).getJurisdiction().getId());
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingsCanBeSortedByEmploymentType() {
        PostingSearchCriteria psc = new PostingSearchCriteria();
        psc.setSortCriteria(PostingSortCriteria.EMPLOYMENT_TYPE);
        psc.setSortDirection(SortDirection.ASC);
        List<Posting> postings = repository.findAll(psc);

        EmploymentType prevType = null;
        for (Posting p : postings) {
            if (prevType != null) {
                assertTrue(prevType.name().compareTo(p.getEmploymentType().name()) <= 0);
            }

            prevType = p.getEmploymentType();
        }

        psc.setSortDirection(SortDirection.DESC);
        postings = repository.findAll(psc);
        prevType = null;
        for (Posting p : postings) {
            if (prevType != null) {
                assertTrue(prevType.name().compareTo(p.getEmploymentType().name()) >= 0);
            }

            prevType = p.getEmploymentType();
        }
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingsCanBeSortedByJurisdictionName() {
        PostingSearchCriteria psc = new PostingSearchCriteria();
        psc.setSortCriteria(PostingSortCriteria.JURISDICTION_NAME);
        psc.setSortDirection(SortDirection.ASC);
        List<Posting> postings = repository.findAll(psc);

        String prevName = null;
        for (Posting p : postings) {
            if (prevName != null) {
                assertTrue(prevName.compareTo(p.getJurisdiction().getName()) <= 0);
            }

            prevName = p.getJurisdiction().getName();
        }

        psc.setSortDirection(SortDirection.DESC);
        postings = repository.findAll(psc);
        prevName = null;
        for (Posting p : postings) {
            if (prevName != null) {
                assertTrue(prevName.compareTo(p.getJurisdiction().getName()) >= 0);
            }

            prevName = p.getJurisdiction().getName();
        }
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatPostingsCanBeSortedByOpenDate() {
        PostingSearchCriteria psc = new PostingSearchCriteria();
        psc.setSortCriteria(PostingSortCriteria.OPEN_DATE);
        psc.setSortDirection(SortDirection.ASC);
        List<Posting> postings = repository.findAll(psc);

        DateTime prevTime = null;
        for (Posting p : postings) {
            if (prevTime != null) {
                assertTrue(prevTime.isEqual(p.getOpenDate()) || prevTime.isBefore(p.getOpenDate()));
            }

            prevTime = p.getOpenDate();
        }

        psc.setSortDirection(SortDirection.DESC);
        postings = repository.findAll(psc);
        prevTime = null;
        for (Posting p : postings) {
            if (prevTime != null) {
                assertTrue(prevTime.isEqual(p.getOpenDate()) || prevTime.isAfter(p.getOpenDate()));
            }

            prevTime = p.getOpenDate();
        }
    }
}
