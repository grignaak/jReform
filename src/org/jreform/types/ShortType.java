package org.jreform.types;

import org.jreform.InputDataType;

public final class ShortType implements InputDataType<Short>
{
    private static final ShortType type = new ShortType();
    
    private ShortType() {}
    
    public Short parseValue(String value)
    {
        try
        {
            return Short.valueOf(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Short> getInputDataClass()
    {
        return Short.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName().toString();
    }
    
    public static InputDataType<Short> shortType()
    {
        return type;
    }
    
}
