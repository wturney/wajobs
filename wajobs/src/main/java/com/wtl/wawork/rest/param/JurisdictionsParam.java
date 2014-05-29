package com.wtl.wawork.rest.param;

import static com.google.common.base.Preconditions.checkArgument;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * HTTP request parameter representing a list of jurisdiction IDs
 * 
 * @author Weston Turney-Loos
 * 
 */
public class JurisdictionsParam {

    /**
     * Provides mapping between a {@link JurisdictionsParam} and its associated
     * request string
     * 
     * @author Weston Turney-Loos
     * 
     */
    public static final class JurisdictionsParamPropertyEditor extends PropertyEditorSupport {

        private static final Splitter CSV_SPLITTER = Splitter.on(',');

        @Override
        public String getAsText() {
            JurisdictionsParam p = (JurisdictionsParam) getValue();

            if (p == null) {
                return null;
            } else {
                return StringUtils.join(p.getIds(), ",");
            }
        }

        @Override
        public void setAsText(String data) throws IllegalArgumentException {
            try {
                final Iterable<String> result = CSV_SPLITTER.split(data);
                final List<Long> ids = Lists.newArrayList();
                for (String id : result) {
                    if (id.isEmpty()) {
                        throw new IllegalArgumentException();
                    }
                    ids.add(Long.parseLong(id));
                }

                checkArgument(!ids.isEmpty());

                setValue(new JurisdictionsParam(ids));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
    }

    private List<Long> ids;

    public JurisdictionsParam(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

}
