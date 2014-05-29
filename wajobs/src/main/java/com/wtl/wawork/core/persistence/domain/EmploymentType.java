package com.wtl.wawork.core.persistence.domain;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public enum EmploymentType implements IdentityEnum {
    NA(1),
    FULL_TIME(2),
    PART_TIME(3),
    SEASONAL(4),
    TEMPORARY(5),
    INTERNSHIP(6),
    ON_CALL(7);

    public static class IdentityEnumSerializer extends JsonSerializer<IdentityEnum> {
        @Override
        public void serialize(IdentityEnum type, JsonGenerator gen, SerializerProvider provider) throws IOException,
                JsonProcessingException {
            gen.writeNumber(type.getId());
        }
    }

    private static final Map<Integer, EmploymentType> ID_LOOKUP_MAP = new HashMap<Integer, EmploymentType>();

    static {
        for (EmploymentType type : values()) {

            ID_LOOKUP_MAP.put(type.getId(), type);
        }
    }

    public static IdentityEnum valueOf(int id) {
        checkArgument(ID_LOOKUP_MAP.containsKey(id));
        return ID_LOOKUP_MAP.get(id);
    }

    private int id;

    private EmploymentType(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
