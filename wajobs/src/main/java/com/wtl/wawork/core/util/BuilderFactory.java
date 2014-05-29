package com.wtl.wawork.core.util;

import net.karneim.pojobuilder.FactoryProperties;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import com.wtl.wawork.core.persistence.domain.Jurisdiction;
import com.wtl.wawork.core.persistence.domain.Posting;
import com.wtl.wawork.core.service.dto.JurisdictionDetails;
import com.wtl.wawork.core.service.dto.PostingDetails;

/**
 * Dummy object used to define which domain objects should have a builder
 * pattern auto-generated. The builder generation library was provided by
 * 
 * <a href="https://github.com/mkarneim/pojobuilder">Michael Karneim</a>
 * 
 * @author Weston Turney-Loos
 * 
 */
public class BuilderFactory {
    @GeneratePojoBuilder(intoPackage = "*.builder")
    @FactoryProperties({ "name" })
    public static Jurisdiction createJurisdiction(String name) {
        return new Jurisdiction(name);
    }

    @GeneratePojoBuilder(intoPackage = "*.builder")
    @FactoryProperties({ "id", "name" })
    public static JurisdictionDetails createJurisdictionDetails(long id, String name) {
        return new JurisdictionDetails(id, name);
    }

    @GeneratePojoBuilder(intoPackage = "*.builder")
    @FactoryProperties({ "title" })
    public static Posting createPosting(String title) {
        return new Posting(title);
    }

    @GeneratePojoBuilder(intoPackage = "*.builder")
    public static PostingDetails createPostingDetails() {
        return new PostingDetails();
    }
}
