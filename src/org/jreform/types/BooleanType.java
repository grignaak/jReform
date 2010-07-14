package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

public final class BooleanType implements InputDataType<Boolean>
{
    private static final BooleanType type = new BooleanType();
    
    private BooleanType() {}
    
    public Maybe<Boolean> parseValue(String value)
    {
        return Maybe.soUnlessNull(Boolean.valueOf(value));
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
