package org.jreform.internal;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.Input;
import org.jreform.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
class InputImpl<T> extends AbstractInputControl<T> implements Input<T>
{
    private T value;
    private String valueAttribute;
    
    InputImpl(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
    }
    
    public final T getValue()
    {
        return value;
    }
    
    public final void setValue(T value)
    {
        this.value = value;
    }
    
    /**
     * Returns the <tt>value</tt> attribute or an empty string
     * if it's <code>null</code>.
     */
    public final String getValueAttribute()
    {
        return valueAttribute == null ? "" : valueAttribute;
    }
    
    /**
     * Sets value attribute trimming leading/trailing whitespace.
     */
    public void setValueAttribute(String input)
    {
        valueAttribute = input == null ? null : input.trim();
    }
    
    public final boolean isBlank()
    {
        return valueAttribute == null || valueAttribute.equals("");
    }
    
    /**
     * Returns the string representation of the value as returned
     * by <tt>getValue().toString()</tt> or an empty string if the
     * value is <code>null</code>.
     */
    public final String getStringValue()
    {
        return value == null ? "" : value.toString();
    }
    
    boolean validate(HttpServletRequest req)
    {
        String value = req.getParameter(getInputName());
        setValueAttribute(value);
        
        ValueAttributeValidator<T> validator = new ValueAttributeValidator<T>(this);
        ValidationResult<T> result = validator.validate(getValueAttribute());
        
        setValue(result.getParsedValue());
        setValid(result.isValid());
        setOnError(result.getErrorMessage());
        
        return isValid();
    }
    
    public final String toString()
    {
        return getValueAttribute() == null ? "" : getValueAttribute();
    }
    
}
