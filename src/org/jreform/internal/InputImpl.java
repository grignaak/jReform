package org.jreform.internal;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.Input;
import org.jreform.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
public class InputImpl<T> extends AbstractInputControl<T> implements Input<T>
{
    private T value;
    private String valueAttribute;
    
    protected InputImpl(InputDataType<T> type, String name, Criterion<T>...criteria)
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
    
    @Deprecated
    protected boolean validate(HttpServletRequest req)
    {
        processRequest(req);
        return validate();
    }

    public boolean validate()
    {
        ValueAttributeValidator<T> validator = new ValueAttributeValidator<T>(this);
        ValidationResult<T> result = validator.validate(getValueAttribute());
        
        setValue(result.getParsedValue());
        setValid(result.isValid());
        setOnError(result.getErrorMessage());
        
        return isValid();
    }

    public void processRequest(HttpServletRequest req)
    {
        String value = req.getParameter(getInputName());
        setValueAttribute(value);
    }
    
    public final String toString()
    {
        return getValueAttribute() == null ? "" : getValueAttribute();
    }
    
}
