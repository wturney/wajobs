package com.wtl.wawork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wtl.wawork.core.persistence.repository.JurisdictionRepository;
import com.wtl.wawork.core.persistence.repository.PostingRepository;
import com.wtl.wawork.core.persistence.repository.impl.JurisdictionRepositoryJpa;
import com.wtl.wawork.core.persistence.repository.impl.PostingRepositoryJpa;
import com.wtl.wawork.core.service.JurisdictionService;
import com.wtl.wawork.core.service.PostingService;
import com.wtl.wawork.core.service.impl.JurisdictionServiceImpl;
import com.wtl.wawork.core.service.impl.PostingServiceImpl;

@Configuration
public class CoreConfig {

    @Bean
    public JurisdictionRepository jurisdictionRepository() {
        return new JurisdictionRepositoryJpa();
    }

    @Bean
    public JurisdictionService jurisdictionService() {
        return new JurisdictionServiceImpl();
    }

    @Bean
    public PostingRepository postingRepository() {
        return new PostingRepositoryJpa();
    }

    @Bean
    public PostingService postingService() {
        return new PostingServiceImpl();
    }
}
