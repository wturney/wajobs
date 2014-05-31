package com.wtl.wawork.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.persistence.domain.IdentityEnum;
import com.wtl.wawork.core.service.PostingService;
import com.wtl.wawork.core.service.dto.ListPostingsRequest;
import com.wtl.wawork.core.service.dto.PostingDetails;
import com.wtl.wawork.rest.param.EmploymentTypeParam;
import com.wtl.wawork.rest.param.EmploymentTypeParam.EmploymentTypeParamPropertyEditor;
import com.wtl.wawork.rest.param.JurisdictionsParam;
import com.wtl.wawork.rest.param.JurisdictionsParam.JurisdictionsParamPropertyEditor;
import com.wtl.wawork.rest.view.Posting;

/**
 * Controller representing posting resources and their various operations
 * 
 * TODO: Apply versioning & vendor content type
 * 
 * @author Weston Turney-Loos
 * 
 */
@Controller
@RequestMapping("/postings")
public class PostingController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(PostingController.class);

    @Autowired
    private PostingService postingService;

    /**
     * Returns all postings in the system that match the provided search params.
     * If no params are provided all postings are returned.
     * 
     * @param jurisdictions
     *            a list of jurisdiction IDs to filter postings
     * @param employmentType
     *            the employment type to filter postings
     * @return json array of {@link Posting} that match the provided search
     *         params.
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Posting> getPostings(
            @RequestParam(required = false, value = "j") JurisdictionsParam jurisdictions,
            @RequestParam(required = false, value = "et") EmploymentTypeParam employmentType) {
        final List<Posting> postings = Lists.newArrayList();

        List<Long> ids = null;
        if (jurisdictions != null) {
            ids = Lists.newArrayList(jurisdictions.getIds());
        }

        IdentityEnum et = null;
        if (employmentType != null) {
            et = employmentType.getEmploymentType();
        }

        final ListPostingsRequest preq = new ListPostingsRequest(ids, et);
        for (PostingDetails detail : postingService.listPostings(preq).getPostingDetails()) {
            postings.add(Posting.fromPostingDetails(detail));
        }

        return postings;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(JurisdictionsParam.class, new JurisdictionsParamPropertyEditor());
        dataBinder.registerCustomEditor(EmploymentTypeParam.class, new EmploymentTypeParamPropertyEditor());
    }
}
