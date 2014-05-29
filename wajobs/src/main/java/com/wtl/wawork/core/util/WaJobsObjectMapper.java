package com.wtl.wawork.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.wtl.wawork.core.persistence.domain.EmploymentType.IdentityEnumSerializer;
import com.wtl.wawork.core.persistence.domain.IdentityEnum;

public class WaJobsObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    public WaJobsObjectMapper() {
        JodaModule module = new JodaModule();
        module.addSerializer(IdentityEnum.class, new IdentityEnumSerializer());
        registerModule(module);
    }

}
