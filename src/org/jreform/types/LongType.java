package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

public final class LongType implements InputDataType<Long>
{
    private static final LongType type = new LongType();
    
    private LongType() {}
    
    public Maybe<Long> parseValue(String value)
    {
        try
        {
            return Maybe.soUnlessNull(Long.valueOf(value));
        }
        catch(NumberFormatException ex)
        {
            return Maybe.not();
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
