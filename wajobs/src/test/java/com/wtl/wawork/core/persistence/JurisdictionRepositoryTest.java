package com.wtl.wawork.core.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.wtl.wawork.core.persistence.domain.Jurisdiction;
import com.wtl.wawork.core.persistence.domain.builder.JurisdictionBuilder;
import com.wtl.wawork.core.persistence.repository.JurisdictionRepository;
import com.wtl.wawork.core.persistence.repository.impl.JurisdictionRepositoryJpa;

@ContextConfiguration
public class JurisdictionRepositoryTest extends RepositoryTest {

    @Configuration
    static class Config {

        @Bean
        public JurisdictionRepository jurisdictionRepository() {
            return new JurisdictionRepositoryJpa();
        }

    }

    @Autowired
    private JurisdictionRepository repository;

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatAllJurisdictionsCanBeQueried() {
        List<Jurisdiction> jurisdictions = repository.findAll();

        assertEquals(3, jurisdictions.size());
    }

    @Test
    @DataSet(names = { "test-data-empty.xml" })
    public void thatEmptyJurisdictionResultsIsNonNull() {
        List<Jurisdiction> jurisdictions = repository.findAll();
        assertNotNull(jurisdictions);
    }

    @Test
    @DataSet(names = { "test-data.xml" })
    public void thatJurisdictionsCanBeCreated() {
        Jurisdiction in = new JurisdictionBuilder().withName("City of Roy").build();

        getEntityManager().getTransaction().begin();
        repository.create(in);
        getEntityManager().getTransaction().commit();
        assertTrue(in.getId() > 0);

        Jurisdiction out = repository.find(in.getId());
        assertEquals(in.getName(), out.getName());
    }
}
