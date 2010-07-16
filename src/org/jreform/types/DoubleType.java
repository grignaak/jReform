package org.jreform.types;

import org.jreform.util.ParsedValue;

public final class DoubleType extends AbstractType<Double>
{
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
    
}
