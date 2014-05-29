package com.wtl.wawork.rest.param;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.wtl.wawork.core.persistence.domain.EmploymentType;
import com.wtl.wawork.core.persistence.domain.IdentityEnum;
import com.wtl.wawork.rest.param.EmploymentTypeParam.EmploymentTypeParamPropertyEditor;

public class EmploymentTypeParamPropertyEditorTest {

    private static final IdentityEnum VALUE = EmploymentType.ON_CALL;
    private static final String VALUE_STRING = Integer.toString(EmploymentType.ON_CALL.getId());

    EmploymentTypeParamPropertyEditor paramEditor;

    @Before
    public void setup() {
        paramEditor = new EmploymentTypeParamPropertyEditor();
    }

    @Test
    public void thatGetAsTextConvertsCorrectly() {
        EmploymentTypeParam etp = new EmploymentTypeParam(VALUE);
        paramEditor.setValue(etp);

        String result = paramEditor.getAsText();
        assertEquals(VALUE_STRING, result);
    }

    @Test
    public void thatGetAsTextReturnsNullWhenValueIsNull() {
        String result = paramEditor.getAsText();

        assertNull(result);
    }

    @Test
    public void thatSetAsTextConvertsValidData() {
        paramEditor.setAsText(Integer.toString(VALUE.getId()));

        EmploymentTypeParam etp = (EmploymentTypeParam) paramEditor.getValue();

        assertEquals(VALUE, etp.getEmploymentType());
    }

    @Test
    public void thatSetAsTextThrowsOnInvalidData() {
        String[] invalidData = new String[] {
                "", " ", "h", "^", "x", "-1", Integer.toString(Integer.MAX_VALUE), Long.toString(Long.MIN_VALUE)
        };

        for (String data : invalidData) {
            try {
                paramEditor.setAsText(data);
                fail("Invalid parameter value accepted");
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
