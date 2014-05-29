package com.wtl.wawork.rest.controller;

import static com.wtl.wawork.core.util.LogbackPrintingResultHandler.log;
import static com.wtl.wawork.rest.controller.fixture.JurisdictionControllerFixture.JURISDICTION_ONE_ID;
import static com.wtl.wawork.rest.controller.fixture.PostingControllerFixture.EMPLOYMENT_TYPE;
import static com.wtl.wawork.rest.controller.fixture.PostingControllerFixture.JURISDICTION_IDS;
import static com.wtl.wawork.rest.controller.fixture.PostingControllerFixture.POSTING_THREE_TITLE;
import static com.wtl.wawork.rest.controller.fixture.PostingControllerFixture.allPostingsResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.wtl.wawork.core.service.PostingService;
import com.wtl.wawork.core.service.dto.ListPostingsRequest;
import com.wtl.wawork.core.util.WaJobsObjectMapper;

public class PostingControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(PostingControllerTest.class);

    @Captor
    private ArgumentCaptor<ListPostingsRequest> requestCaptor;

    @InjectMocks
    private PostingController postingController;

    @Mock
    private PostingService postingService;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new WaJobsObjectMapper());
        mvc = standaloneSetup(postingController).setMessageConverters(converter).build();
    }

    @Test
    public void thatViewPostingRendersAsJson() throws Exception {
        when(postingService.listPostings(any(ListPostingsRequest.class))).thenReturn(allPostingsResponse());

        mvc.perform(get("/postings")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].title", equalTo(POSTING_THREE_TITLE)))
                .andExpect(jsonPath("$[2].employmentType", equalTo(EMPLOYMENT_TYPE.getId())))
                .andExpect(jsonPath("$[2].jurisdiction", equalTo((int) JURISDICTION_ONE_ID)));
    }

    @Test
    public void thatViewPostingUsesBadRequestOnInvalidEmploymentType() throws Exception {
        mvc.perform(get("/postings?et=99999")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void thatViewPostingUsesBadRequestOnInvalidJurisdictions() throws Exception {
        mvc.perform(get("/postings?j=alpha,beta")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void thatViewPostingUsesHttpOkOnSuccess() throws Exception {
        when(postingService.listPostings(any(ListPostingsRequest.class))).thenReturn(allPostingsResponse());

        mvc.perform(get("/postings")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isOk());
    }

    @Test
    public void thatViewPostingUsesHttpOkOnValidEmploymentType() throws Exception {
        when(postingService.listPostings(any(ListPostingsRequest.class))).thenReturn(allPostingsResponse());

        mvc.perform(get("/postings?et=" + EMPLOYMENT_TYPE.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isOk());

        verify(postingService).listPostings(requestCaptor.capture());
        verifyNoMoreInteractions(postingService);

        ListPostingsRequest req = requestCaptor.getValue();
        assertEquals(EMPLOYMENT_TYPE, req.getEmploymentType());
        assertNull(req.getJurisdictionIds());
    }

    @Test
    public void thatViewPostingUsesHttpOkOnValidJurisdictions() throws Exception {
        when(postingService.listPostings(any(ListPostingsRequest.class))).thenReturn(allPostingsResponse());

        mvc.perform(get("/postings?j=" + StringUtils.join(JURISDICTION_IDS, ","))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isOk());

        verify(postingService).listPostings(requestCaptor.capture());
        verifyNoMoreInteractions(postingService);

        ListPostingsRequest req = requestCaptor.getValue();
        assertNull(req.getEmploymentType());
        assertTrue(JURISDICTION_IDS.equals(req.getJurisdictionIds()));
    }

}
