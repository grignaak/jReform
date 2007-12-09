package org.jreform;

import org.jreform.core.MessageOnError;
import org.jreform.core.InputDataType;

/**
 * An input control in an html form.
 * 
 * @author armandino (at) gmail.com
 * @param <T> data type of this input.
 */
public interface InputControl<T> extends MessageOnError
{
    /**
     * Returns this input's data type.
     */
    public InputDataType<T> getType();
    
    /**
     * Returns this input's <tt>name</tt> attribute.
     */
    public String getInputName();
    
    /**
     * Returns <code>true</code> if this input must have a value.
     */
    public boolean isRequired();
    
    /**
     * Returns <code>true</code> if this input's data is valid.
     */
    public boolean isValid();
    
    /**
     * Returns a string representation of this input's value
     * as returned by the {@link #toString()} method.
     */
    public String getStringValue();

}
