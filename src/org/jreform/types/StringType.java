package org.jreform.types;

import org.jreform.core.InputDataType;

public final class StringType implements InputDataType<String>
{
    private static final StringType type = new StringType();
    
    private StringType() {}
    
    public String parseValue(String value)
    {
        return value;
    }
    
    public Class<String> getType()
    {
        return String.class;
    }
    
    public String toString()
    {
        return getType().getName().toString();
    }
    
    public static InputDataType<String> stringType()
    {
        return type;
    }
    
}
