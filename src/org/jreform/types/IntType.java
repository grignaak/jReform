package org.jreform.types;

import org.jreform.core.InputDataType;

public final class IntType implements InputDataType<Integer>
{
    private static final IntType type = new IntType();
    
    private IntType() {}
    
    public Integer parseValue(String value)
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Integer> getType()
    {
        return int.class;
    }
    
    public String toString()
    {
        return getType().getName().toString();
    }
    
    public static InputDataType<Integer> intType()
    {
        return type;
    }
    
}
