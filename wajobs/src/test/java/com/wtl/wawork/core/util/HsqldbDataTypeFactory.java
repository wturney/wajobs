package com.wtl.wawork.core.util;

import java.util.Arrays;
import java.util.Collection;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.hsqldb.types.Types;

@SuppressWarnings("rawtypes")
public class HsqldbDataTypeFactory extends DefaultDataTypeFactory {

    private static final Collection DATABASE_PRODUCTS = Arrays.asList(new String[] { "hsql" });

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        if (sqlType == Types.BOOLEAN) {
            return DataType.BOOLEAN;
        }

        return super.createDataType(sqlType, sqlTypeName);
    }

    @Override
    public Collection getValidDbProducts() {
        return DATABASE_PRODUCTS;
    }

}
