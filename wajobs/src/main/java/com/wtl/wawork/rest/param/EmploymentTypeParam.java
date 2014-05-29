package com.wtl.wawork.rest.param;

import java.beans.PropertyEditorSupport;

import com.wtl.wawork.core.persistence.domain.EmploymentType;
import com.wtl.wawork.core.persistence.domain.IdentityEnum;

/**
 * HTTP request parameter representing an {@link EmploymentType}
 * 
 * @author Weston Turney-Loos
 * 
 */
public class EmploymentTypeParam {

    /**
     * Provides bi-directional mapping between an {@link EmploymentTypeParam}
     * and its associated request String
     * 
     * @author Weston Turney-Loos
     * 
     */
    public static final class EmploymentTypeParamPropertyEditor extends PropertyEditorSupport {

        @Override
        public String getAsText() {
            EmploymentTypeParam p = (EmploymentTypeParam) getValue();

            if (p == null) {
                return null;
            } else {
                return Integer.toString(p.getEmploymentType().getId());
            }
        }

        @Override
        public void setAsText(String data) throws IllegalArgumentException {
            try {
                int id = Integer.parseInt(data);
                final IdentityEnum type = EmploymentType.valueOf(id);
                setValue(new EmploymentTypeParam(type));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unable to parse parameter as number");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("EmploymentType with ID: %d does not exist");
            }
        }
    }

    private IdentityEnum employmentType;

    public EmploymentTypeParam(IdentityEnum employmentType) {
        this.employmentType = employmentType;
    }

    public IdentityEnum getEmploymentType() {
        return employmentType;
    }

}
