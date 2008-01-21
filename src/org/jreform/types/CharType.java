package org.jreform.types;

import org.jreform.InputDataType;

public final class CharType implements InputDataType<Character>
{
    private static final CharType type = new CharType();
    
    private CharType() {}
    
    public Character parseValue(String value)
    {
        return value.length() == 1 ? value.charAt(0) : null;
    }
    
    public Class<Character> getInputDataClass()
    {
        return Character.class;
    }
    
    public String toString()
    {
        return getInputDataClass().getName().toString();
    }
    
    public static InputDataType<Character> charType()
    {
        return type;
    }
    
}
