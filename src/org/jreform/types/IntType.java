package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

public final class IntType implements InputDataType<Integer>
{
    private static final IntType type = new IntType();
    
    private IntType() {}
    
    public Maybe<Integer> parseValue(String value)
    {
        try
        {
            return Maybe.soUnlessNull(Integer.valueOf(value));
        }
        catch(NumberFormatException ex)
        {
            return Maybe.not();
        }
    }
    
    public Class<Integer> getInputDataClass()
    {
        return Integer.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
    
    public static InputDataType<Integer> intType()
    {
        return type;
    }
    
}
