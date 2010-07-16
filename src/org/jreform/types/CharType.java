package org.jreform.types;

import org.jreform.util.ParsedValue;

public final class CharType extends AbstractType<Character>
{
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
}
