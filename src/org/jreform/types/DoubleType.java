package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

public final class DoubleType implements InputDataType<Double>
{
    private static final DoubleType type = new DoubleType();
    
    private DoubleType() {}
    
    public Maybe<Double> parseValue(String value)
    {
        try
        {
            return Maybe.soUnlessNull(Double.valueOf(value));
        }
        catch(NumberFormatException ex)
        {
            return Maybe.not();
        }
    }
    
    public Class<Double> getInputDataClass()
    {
        return Double.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
    
    public static InputDataType<Double> doubleType()
    {
        return type;
    }
    
}
