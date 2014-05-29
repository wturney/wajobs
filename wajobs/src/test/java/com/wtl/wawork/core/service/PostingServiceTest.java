package com.wtl.wawork.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.EmploymentType;
import com.wtl.wawork.core.persistence.domain.IdentityEnum;
import com.wtl.wawork.core.persistence.fixture.PostingFixture;
import com.wtl.wawork.core.persistence.repository.PostingRepository;
import com.wtl.wawork.core.persistence.repository.criteria.PostingSearchCriteria;
import com.wtl.wawork.core.service.dto.ListPostingsRequest;
import com.wtl.wawork.core.service.impl.PostingServiceImpl;

public class PostingServiceTest {

    @Captor
    private ArgumentCaptor<PostingSearchCriteria> searchCaptor;

    @Mock
    private PostingRepository mockRepository;

    @InjectMocks
    private PostingService postingService = new PostingServiceImpl();

    private static final List<Long> JURISDICTION_IDS = Lists.newArrayList(1l, 5l, 23l);

    private static final IdentityEnum EMPLOYMENT_TYPE = EmploymentType.INTERNSHIP;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void thatAllPostingsCanBeQueried() {
        when(mockRepository.findAll(Matchers.any(PostingSearchCriteria.class)))
                .thenReturn(PostingFixture.allPostings());

        ListPostingsRequest request = new ListPostingsRequest();
        postingService.listPostings(request);

        verify(mockRepository).findAll(searchCaptor.capture());
        verifyNoMoreInteractions(mockRepository);

        PostingSearchCriteria criteria = searchCaptor.getValue();
        assertNull(criteria.getEmploymentType());
        assertNull(criteria.getJurisdictionIds());
    }

    @Test
    public void thatPostingsCanBeQueriedByEmploymentType() {
        when(mockRepository.findAll(Matchers.any(PostingSearchCriteria.class)))
                .thenReturn(PostingFixture.allPostings());

        ListPostingsRequest request = new ListPostingsRequest(null, EMPLOYMENT_TYPE);
        postingService.listPostings(request);

        verify(mockRepository).findAll(searchCaptor.capture());
        verifyNoMoreInteractions(mockRepository);

        PostingSearchCriteria criteria = searchCaptor.getValue();
        assertNull(criteria.getJurisdictionIds());
        assertEquals(EmploymentType.INTERNSHIP, criteria.getEmploymentType());
    }

    @Test
    public void thatPostingsCanBeQueriedByEmploymentTypeAndJurisdiction() {
        when(mockRepository.findAll(Matchers.any(PostingSearchCriteria.class)))
                .thenReturn(PostingFixture.allPostings());

        ListPostingsRequest request = new ListPostingsRequest(JURISDICTION_IDS, EMPLOYMENT_TYPE);
        postingService.listPostings(request);

        verify(mockRepository).findAll(searchCaptor.capture());
        verifyNoMoreInteractions(mockRepository);

        PostingSearchCriteria criteria = searchCaptor.getValue();
        assertTrue(JURISDICTION_IDS.equals(criteria.getJurisdictionIds()));
        assertEquals(EmploymentType.INTERNSHIP, criteria.getEmploymentType());
    }

    @Test
    public void thatPostingsCanBeQueriedByJurisdictions() {
        when(mockRepository.findAll(Matchers.any(PostingSearchCriteria.class)))
                .thenReturn(PostingFixture.allPostings());

        ListPostingsRequest request = new ListPostingsRequest(JURISDICTION_IDS, null);
        postingService.listPostings(request);

        verify(mockRepository).findAll(searchCaptor.capture());
        verifyNoMoreInteractions(mockRepository);

        PostingSearchCriteria criteria = searchCaptor.getValue();
        assertTrue(JURISDICTION_IDS.equals(criteria.getJurisdictionIds()));
        assertNull(criteria.getEmploymentType());
    }

}
