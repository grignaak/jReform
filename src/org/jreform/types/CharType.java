package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

public final class CharType implements InputDataType<Character>
{
    private static final CharType type = new CharType();
    
    private CharType() {}
    
    public ParsedValue<Character> parseValue(String value)
    {
        if (value.length() > 1)
            return ParsedValue.error("Too many characters");
        else if (value.isEmpty())
            return ParsedValue.error("Empty value");
        
        return ParsedValue.setUnlessNull(value.charAt(0));
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
