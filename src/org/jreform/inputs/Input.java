package org.jreform.inputs;

import org.jreform.InputControl;

/**
 * An input that has only a single value.
 * 
 * @author armandino (at) gmail.com
 */
public interface Input<T> extends InputControl<T>
{
    /**
     * Returns this input's value.
     */
    public T getValue();
    
    /**
     * Sets this input's value.
     */
    public void setValue(T value);
    
    /**
     * Return's this input's <tt>value</tt> attribute.
     */
    public String getValueAttribute();
    
    /**
     * Sets this input's <tt>value</tt> attribute.
     */
    public void setValueAttribute(String valueAttribute);
    
}
