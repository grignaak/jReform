package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

public final class FloatType implements InputDataType<Float>
{
    private static final FloatType type = new FloatType();
    
    private FloatType() {}
    
    public Maybe<Float> parseValue(String value)
    {
        try
        {
            return Maybe.soUnlessNull(Float.valueOf(value));
        }
        catch(NumberFormatException ex)
        {
            return Maybe.not();
        }
    }
    
    public Class<Float> getInputDataClass()
    {
        return Float.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
    
    public static InputDataType<Float> floatType()
    {
        return type;
    }
    
}
