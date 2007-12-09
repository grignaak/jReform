package org.jreform.types;

import org.jreform.core.InputDataType;

public final class DoubleType implements InputDataType<Double>
{
    private static final DoubleType type = new DoubleType();
    
    private DoubleType() {}
    
    public Double parseValue(String value)
    {
        try
        {
            return Double.parseDouble(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Double> getType()
    {
        return double.class;
    }
    
    public String toString()
    {
        return getType().getName().toString();
    }
    
    public static InputDataType<Double> doubleType()
    {
        return type;
    }
    
}
