package org.jreform.inputs;

import java.util.List;

import org.jreform.InputControl;

/**
 * An input that can have multiple values.
 * 
 * @author armandino (at) gmail.com
 */
public interface MultiInput<T> extends InputControl<T>
{
    /**
     * Returns this input's values.
     */
    public List<T> getValues();
    
    /**
     * Sets this input's values.
     */
    public void setValues(List<T> value);
    
    /**
     * Return's this input's <tt>value</tt> attributes.
     */
    public List<String> getValueAttributes();
    
    /**
     * Sets this input's <tt>value</tt> attributes.
     */
    public void setValueAttributes(List<String> valueAttributes);
    
}
