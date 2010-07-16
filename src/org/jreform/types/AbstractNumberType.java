package org.jreform.types;

import org.jreform.util.ParsedValue;

public abstract class AbstractNumberType<T extends Number> extends AbstractType<T>
{
    public ParsedValue<T> parseValue(String value)
    {
        try
        {
            return ParsedValue.setUnlessNull(parseNumber(value));
        }
        catch (NumberFormatException ex)
        {
            return ParsedValue.error("Cannot convert to a number");
        }
    }

    protected abstract T parseNumber(String value);
}