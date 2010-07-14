package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

public final class ShortType implements InputDataType<Short>
{
    private static final ShortType type = new ShortType();
    
    private ShortType() {}
    
    public Maybe<Short> parseValue(String value)
    {
        try
        {
            return Maybe.soUnlessNull(Short.valueOf(value));
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
        return getInputDataClass().getName();
    }
    
    public static InputDataType<Short> shortType()
    {
        return type;
    }
    
}
