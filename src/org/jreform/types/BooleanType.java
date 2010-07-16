package org.jreform.types;

import org.jreform.util.ParsedValue;

public final class BooleanType extends AbstractType<Boolean>
{
    public ParsedValue<Boolean> parseValue(String value)
    {
        return ParsedValue.setUnlessNull(Boolean.valueOf(value));
    }
    
    public Class<Boolean> getInputDataClass()
    {
        return Boolean.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
}
