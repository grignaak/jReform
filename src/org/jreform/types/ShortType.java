package org.jreform.types;

import org.jreform.util.ParsedValue;

public final class ShortType extends AbstractType<Short>
{
    public ParsedValue<Short> parseValue(String value)
    {
        try
        {
            return ParsedValue.setUnlessNull(Short.valueOf(value));
        }
        catch(NumberFormatException ex)
        {
            return null;
        }
    }
    
    public Class<Short> getInputDataClass()
    {
        return Short.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }    
}
