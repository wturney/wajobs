package com.wtl.wawork.rest.controller;

import static com.wtl.wawork.core.util.LogbackPrintingResultHandler.log;
import static com.wtl.wawork.rest.controller.fixture.JurisdictionControllerFixture.JURISDICTION_THREE_ID;
import static com.wtl.wawork.rest.controller.fixture.JurisdictionControllerFixture.JURISDICTION_THREE_NAME;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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

import com.wtl.wawork.core.service.JurisdictionService;
import com.wtl.wawork.core.service.dto.ListJurisdictionsRequest;
import com.wtl.wawork.core.util.WaJobsObjectMapper;
import com.wtl.wawork.rest.controller.fixture.JurisdictionControllerFixture;

public class JurisdictionControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(JurisdictionControllerTest.class);

    @Captor
    private ArgumentCaptor<ListJurisdictionsRequest> requestCaptor;

    @InjectMocks
    private JurisdictionController jurisdictionController;

    @Mock
    private JurisdictionService jurisdictionService;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new WaJobsObjectMapper());
        mvc = standaloneSetup(jurisdictionController).setMessageConverters(converter).build();
    }

    @Test
    public void thatJurisdictionsRenderAsJson() throws Exception {
        when(jurisdictionService.listJurisdictions(any(ListJurisdictionsRequest.class))).thenReturn(
                JurisdictionControllerFixture.allJurisdictionsResponse());

        mvc.perform(get("/jurisdictions")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].name", equalTo(JURISDICTION_THREE_NAME)))
                .andExpect(jsonPath("$[2].id", equalTo((int) JURISDICTION_THREE_ID)));
    }

    @Test
    public void thatJurisdictionsUsesHttpOkOnSuccess() throws Exception {
        when(jurisdictionService.listJurisdictions(any(ListJurisdictionsRequest.class))).thenReturn(
                JurisdictionControllerFixture.allJurisdictionsResponse());

        mvc.perform(get("/jurisdictions")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(log(LOG))
                .andExpect(status().isOk());

        verify(jurisdictionService).listJurisdictions(requestCaptor.capture());
        verifyNoMoreInteractions(jurisdictionService);

        ListJurisdictionsRequest req = requestCaptor.getValue();
        assertNotNull(req);
    }

}
