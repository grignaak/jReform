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
            return Short.parseShort(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Short> getType()
    {
        return short.class;
    }
    
    public String toString()
    {
        return getType().getName().toString();
    }
    
    public static InputDataType<Short> shortType()
    {
        return type;
    }
    
}
