package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

public final class IntType implements InputDataType<Integer>
{
    private static final IntType type = new IntType();
    
    private IntType() {}
    
    public ParsedValue<Integer> parseValue(String value)
    {
        try
        {
            return ParsedValue.setUnlessNull(Integer.valueOf(value));
        }
        catch (NumberFormatException ex)
        {
            return ParsedValue.error(ex.getMessage());
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
