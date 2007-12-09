package org.jreform.types;

import org.jreform.core.InputDataType;

public final class FloatType implements InputDataType<Float>
{
    private static final FloatType type = new FloatType();
    
    private FloatType() {}
    
    public Float parseValue(String value)
    {
        try
        {
            return Float.parseFloat(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Float> getType()
    {
        return float.class;
    }
    
    public String toString()
    {
        return getType().getName().toString();
    }
    
    public static InputDataType<Float> floatType()
    {
        return type;
    }
    
}
