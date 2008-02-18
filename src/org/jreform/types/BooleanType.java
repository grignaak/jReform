package org.jreform.types;

import org.jreform.InputDataType;

public final class BooleanType implements InputDataType<Boolean>
{
    private static final BooleanType type = new BooleanType();
    
    private BooleanType() {}
    
    public Boolean parseValue(String value)
    {
    	return Boolean.valueOf(value);
    }
    
    public Class<Boolean> getInputDataClass()
    {
        return Boolean.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName().toString();
    }
    
    public static InputDataType<Boolean> booleanType()
    {
        return type;
    }
    
}
