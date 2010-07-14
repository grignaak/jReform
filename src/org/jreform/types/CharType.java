package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

public final class CharType implements InputDataType<Character>
{
    private static final CharType type = new CharType();
    
    private CharType() {}
    
    public Maybe<Character> parseValue(String value)
    {
        if (value.length() != 1)
            return Maybe.not();
        
        return Maybe.soUnlessNull(value.charAt(0));
    }
    
    public Class<Character> getInputDataClass()
    {
        return Character.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName();
    }
    
    public static InputDataType<Character> charType()
    {
        return type;
    }
    
}
