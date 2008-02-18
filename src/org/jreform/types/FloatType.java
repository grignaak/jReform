package org.jreform.types;

import org.jreform.InputDataType;

public final class FloatType implements InputDataType<Float>
{
    private static final FloatType type = new FloatType();
    
    private FloatType() {}
    
    public Float parseValue(String value)
    {
        try
        {
            return Float.valueOf(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Float> getInputDataClass()
    {
        return Float.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName().toString();
    }
    
    public static InputDataType<Float> floatType()
    {
        return type;
    }
    
}
