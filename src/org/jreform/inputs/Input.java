package org.jreform.inputs;

import java.util.Set;

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
     * 
     * @throws IllegalStateException if !{@link #isValid()}
     */
    public T getValue();
    
    /**
     * Sets this input's value.
     * 
     * <p>
     * Setting the value directly invalidates the form.
     * {@link #validate()} must be called to assure the input is valid.
     * </p>
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

    public Set<String> getErrors();
    
}
