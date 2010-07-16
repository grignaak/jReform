package org.jreform.types;

import org.jreform.util.ParsedValue;

public final class IntType extends AbstractType<Integer>
{
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
}
