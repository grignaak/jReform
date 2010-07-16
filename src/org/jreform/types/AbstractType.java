package org.jreform.types;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

public abstract class AbstractType<T> implements InputDataType<T>
{
    private String errorMessage;

    public AbstractType<T> onError(String messageOnError)
    {
        this.errorMessage = messageOnError;
        return this;
    }

    public ParsedValue<T> parse(String value)
    {
        ParsedValue<T> parsedValue = parseValue(value);
        if (parsedValue.isParsed())
            return parsedValue;
            
        if (errorMessage != null)
            return ParsedValue.error(errorMessage);
        
        return parsedValue;
    }
}
