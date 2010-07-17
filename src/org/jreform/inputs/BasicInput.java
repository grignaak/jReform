package org.jreform.inputs;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jreform.InputDataType;
import org.jreform.impl.AbstractInputControl;
import org.jreform.util.ParsedValue;

/**
 * @author armandino (at) gmail.com
 * @author michael.deardeuff (at) gmail.com
 */
public class BasicInput<T> extends AbstractInputControl<T> implements Input<T>
{
    private ParsedValue<T> value = ParsedValue.error("Empty value");
    private String valueAttribute = "";
    
    public BasicInput(InputDataType<T> type, String name)
    {
        super(type, name);
    }
    
    public final T getValue()
    {
        if (!isValid())
            throw new IllegalStateException("Cannot retrieve an invalid value");
        
        return value.getValue();
    }
    
    public final void setValue(T value)
    {
        setValueAttribute(value != null ? value.toString() : null);
        this.value = ParsedValue.setUnlessNull(value);
    }
    
    /**
     * Returns the <tt>value</tt> attribute or an empty string
     * if it's <code>null</code>.
     */
    public final String getValueAttribute()
    {
        return valueAttribute;
    }
    
    /**
     * Sets value attribute trimming leading/trailing whitespace.
     */
    public void setValueAttribute(String input)
    {
        valueAttribute = input == null ? "" : input.trim();
        value = getType().parseValue(valueAttribute);
    }
    
    public final boolean isBlank()
    {
        return valueAttribute.isEmpty();
    }
    
    /**
     * Returns the string representation of the value as returned
     * by <tt>getValue().toString()</tt> or an empty string if the
     * value is <code>null</code>.
     */
    public final String getStringValue()
    {
        if (value.isNotParsed())
            return "";
        
        return value.getValue().toString();
    }

    public boolean validate()
    {
        if (!isBlank())
        {
            Set<String> criteriaErrors = getCriteriaErrors(value);
            addErrors(criteriaErrors);
        }
        else if (isRequired())
            addError("Missing value");
        
        return isValid();
    }
    
    public void processRequest(HttpServletRequest req)
    {
        String parameter = req.getParameter(getInputName());
        setValueAttribute(parameter);
    }
    
    public final String toString()
    {
        return getValueAttribute();
    }
}
