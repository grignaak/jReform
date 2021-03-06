package org.jreform;

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
    public T parseValue(String value);
    
    /**
     * Returns class of input's data.
     */
    public Class<T> getInputDataClass();
    
}
