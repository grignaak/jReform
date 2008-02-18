package org.jreform.types;

import org.jreform.InputDataType;

public final class IntType implements InputDataType<Integer>
{
    private static final IntType type = new IntType();
    
    private IntType() {}
    
    public Integer parseValue(String value)
    {
        try
        {
            return Integer.valueOf(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Integer> getInputDataClass()
    {
        return Integer.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName().toString();
    }
    
    public static InputDataType<Integer> intType()
    {
        return type;
    }
    
}
