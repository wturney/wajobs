package com.wtl.wawork.rest.param;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.wtl.wawork.rest.param.JurisdictionsParam.JurisdictionsParamPropertyEditor;

public class JurisdictionParamPropertyEditorTest {

    private static final List<Long> IDS = Lists.newArrayList(1l, 76l, 34l);
    private static final String IDS_STRING = StringUtils.join(IDS, ",");

    JurisdictionsParamPropertyEditor paramEditor;

    @Before
    public void setup() {
        paramEditor = new JurisdictionsParamPropertyEditor();
    }

    @Test
    public void thatGetAsTextConvertsCorrectly() {
        JurisdictionsParam jp = new JurisdictionsParam(IDS);
        paramEditor.setValue(jp);

        String result = paramEditor.getAsText();

        assertEquals(IDS_STRING, result);
    }

    @Test
    public void thatGetAsTextReturnsNullWhenValueIsNull() {
        String result = paramEditor.getAsText();

        assertNull(result);
    }

    @Test
    public void thatSetAsTextConvertsValidData() {
        paramEditor.setAsText(IDS_STRING);

        JurisdictionsParam jp = (JurisdictionsParam) paramEditor.getValue();

        assertTrue(IDS.equals(jp.getIds()));
    }

    @Test
    public void thatSetAsTextThrowsOnInvalidData() {
        String[] invalidData = new String[] {
                ",", " ,", "afronaut", "12three4", "1,two,3", "1.502", "1 ,2, 3", "3,,2,"
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
