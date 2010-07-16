package org.jreform.types;

import org.jreform.util.ParsedValue;

public final class LongType extends AbstractType<Long>
{
    public ParsedValue<Long> parseValue(String value)
    {
        try
        {
            return ParsedValue.setUnlessNull(Long.valueOf(value));
        }
        catch (NumberFormatException ex)
        {
            return ParsedValue.error(ex.getMessage());
        }
    }
    
    public Class<Long> getInputDataClass()
    {
        return Long.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
}
