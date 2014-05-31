package com.wtl.wawork.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller providing endpoints for the non-service version of the site
 * 
 * @author Weston Turney-Loos
 * 
 */
@Controller
@RequestMapping(value = "/client", produces = "text/html")
public class ClientController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    /*
     * Unused. Redirects to posting list.
     */
    @RequestMapping()
    public RedirectView getClient() {
        final RedirectView rv = new RedirectView("/client/postings");
        rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return rv;
    }

    /*
     * Posting list view (currently an AngularJS web app)
     */
    @RequestMapping("/postings")
    public String getPostings() {
        return "/client/index.html";
    }
}
