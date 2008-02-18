package org.jreform.types;

import org.jreform.InputDataType;

public final class StringType implements InputDataType<String>
{
    private static final StringType type = new StringType();
    
    private StringType() {}
    
    public String parseValue(String value)
    {
        return value;
    }
    
    public Class<String> getInputDataClass()
    {
        return String.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
    
    public static InputDataType<String> stringType()
    {
        return type;
    }
    
}
