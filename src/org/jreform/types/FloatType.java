package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

public final class FloatType implements InputDataType<Float>
{
    private static final FloatType type = new FloatType();
    
    private FloatType() {}
    
    public ParsedValue<Float> parseValue(String value)
    {
        try
        {
            return ParsedValue.setUnlessNull(Float.valueOf(value));
        }
        catch (NumberFormatException ex)
        {
            return ParsedValue.error(ex.getMessage());
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
