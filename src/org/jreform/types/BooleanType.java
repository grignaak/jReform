package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

public final class BooleanType implements InputDataType<Boolean>
{
    private static final BooleanType type = new BooleanType();
    
    private BooleanType() {}
    
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
    
    public static InputDataType<Boolean> booleanType()
    {
        return type;
    }
    
}
