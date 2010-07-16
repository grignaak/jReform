package org.jreform.types;

import org.jreform.util.ParsedValue;

public final class StringType extends AbstractType<String>
{
    public ParsedValue<String> parseValue(String value)
    {
        return ParsedValue.setUnlessNull(value);
    }
    
    public Class<String> getInputDataClass()
    {
        return String.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
    
}
