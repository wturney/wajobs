package com.wtl.wawork.core.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wtl.wawork.core.persistence.fixture.JurisdictionFixture;
import com.wtl.wawork.core.persistence.repository.JurisdictionRepository;
import com.wtl.wawork.core.service.dto.ListJurisdictionsRequest;
import com.wtl.wawork.core.service.impl.JurisdictionServiceImpl;

public class JurisdictionServiceTest {

    @Mock
    private JurisdictionRepository mockRepository;

    @InjectMocks
    private JurisdictionService jurisdictionService = new JurisdictionServiceImpl();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatAllJurisdictionsCanBeQueried() {
        when(mockRepository.findAll()).thenReturn(JurisdictionFixture.allJurisdictions());

        ListJurisdictionsRequest req = new ListJurisdictionsRequest();
        jurisdictionService.listJurisdictions(req);

        verify(mockRepository).findAll();
        verifyNoMoreInteractions(mockRepository);
    }

}
