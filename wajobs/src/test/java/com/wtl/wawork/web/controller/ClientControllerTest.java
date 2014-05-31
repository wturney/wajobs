package com.wtl.wawork.web.controller;

import static com.wtl.wawork.core.util.LogbackPrintingResultHandler.log;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class ClientControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(ClientControllerTest.class);

    @InjectMocks
    private ClientController indexController;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = standaloneSetup(indexController).build();
    }

    @Test
    public void thatClientRedirectsToPostings() throws Exception {
        mvc.perform(get("/client")
                .accept(MediaType.TEXT_HTML))
                .andDo(log(LOG))
                .andExpect(status().isMovedPermanently())
                .andExpect(redirectedUrl("/client/postings"));

        mvc.perform(get("/client/")
                .accept(MediaType.TEXT_HTML))
                .andDo(log(LOG))
                .andExpect(status().isMovedPermanently())
                .andExpect(redirectedUrl("/client/postings"));
    }

    @Test
    public void thatClientRenderAsHtml() throws Exception {
        mvc.perform(get("/client/postings")
                .accept(MediaType.TEXT_HTML))
                .andDo(log(LOG))
                .andExpect(status().isOk())
                .andExpect(view().name("/client/index.html"));
    }

}
