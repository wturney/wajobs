package com.wtl.wawork.rest.controller.functional;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.wtl.wawork.core.util.FunctionalTest;
import com.wtl.wawork.rest.view.Jurisdiction;

public class JurisdictionControllerFunctionalTest {

    private static final Logger LOG = LoggerFactory.getLogger(JurisdictionControllerFunctionalTest.class);

    private static final String BASE_URL = "http://localhost:8080";

    private static final String VALID_USER = "spider";
    private static final String VALID_PASS = "man";
    private static final String INVALID_USER = "bad";
    private static final String INVALID_PASS = "credentials";

    private HttpHeaders getHeaders(String user, String pass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        byte[] encodedAuthorisation = Base64.encode(String.format("%s:%s", user, pass).getBytes());
        headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

        return headers;
    }

    @Test
    @Category(FunctionalTest.class)
    public void thatJurisdictionsCanBeQueried() throws Exception {
        HttpEntity<String> request = new HttpEntity<>(getHeaders(VALID_USER, VALID_PASS));
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Jurisdiction[]> response = rt.exchange(BASE_URL + "/jurisdictions", HttpMethod.GET, request, Jurisdiction[].class);
        Jurisdiction[] jurisdictions = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("thatJurisdictionsRequireAnAuthorizedUser - %d Jurisdictions Returned", jurisdictions.length));
        }
    }

    @Test
    @Category(FunctionalTest.class)
    public void thatJurisdictionsRequireAnAuthorizedUser() throws Exception {
        HttpEntity<String> request = new HttpEntity<>(getHeaders(INVALID_USER, INVALID_PASS));
        RestTemplate rt = new RestTemplate();
        try {
            rt.exchange(BASE_URL + "/jurisdictions", HttpMethod.GET, request, Jurisdiction[].class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());
        }
    }

}
