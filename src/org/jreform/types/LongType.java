package org.jreform.types;

import org.jreform.InputDataType;

public final class LongType implements InputDataType<Long>
{
    private static final LongType type = new LongType();
    
    private LongType() {}
    
    public Long parseValue(String value)
    {
        try
        {
            return Long.valueOf(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Long> getInputDataClass()
    {
        return Long.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
    
    public static InputDataType<Long> longType()
    {
        return type;
    }
    
}
