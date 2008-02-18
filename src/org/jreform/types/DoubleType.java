package org.jreform.types;

import org.jreform.InputDataType;

public final class DoubleType implements InputDataType<Double>
{
    private static final DoubleType type = new DoubleType();
    
    private DoubleType() {}
    
    public Double parseValue(String value)
    {
        try
        {
            return Double.valueOf(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Double> getInputDataClass()
    {
        return Double.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName().toString();
    }
    
    public static InputDataType<Double> doubleType()
    {
        return type;
    }
    
}
