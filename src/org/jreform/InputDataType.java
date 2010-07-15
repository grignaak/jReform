package org.jreform;

import org.jreform.util.ParsedValue;

/**
 * Represents the type of an input field's data.
 * 
 * @author armandino (at) gmail.com
 */
public interface InputDataType<T>
{
    /**
     * Parses the specified string into an object of this data type's class.
     */
    public ParsedValue<T> parseValue(String value);
    
    /**
     * Returns class of input's data.
     */
    public Class<T> getInputDataClass();
    
}
