package com.wtl.wawork.core.util;

import org.hibernate.dialect.HSQLDialect;

/**
 * Fix for Hibernate bug <a
 * href="https://hibernate.atlassian.net/browse/HHH-7002">HHH-7002</a>. Hsqldb
 * will report errors during create-drop operations involving table constraints.
 * 
 * @author Weston Turney-Loos
 * 
 */
public class HsqldbDialect extends HSQLDialect {
    @Override
    public boolean dropConstraints()
    {
        return false;
    }

    @Override
    public String getCascadeConstraintsString()
    {
        return " CASCADE ";
    }

    @Override
    public boolean supportsIfExistsAfterTableName()
    {
        return false;
    }

    @Override
    public boolean supportsIfExistsBeforeTableName()
    {
        return true;
    }
}
