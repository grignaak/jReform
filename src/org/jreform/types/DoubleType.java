package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

public final class DoubleType implements InputDataType<Double>
{
    private static final DoubleType type = new DoubleType();
    
    private DoubleType() {}
    
    public ParsedValue<Double> parseValue(String value)
    {
        try
        {
            return ParsedValue.setUnlessNull(Double.valueOf(value));
        }
        catch (NumberFormatException ex)
        {
            return ParsedValue.error(ex.getMessage());
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
