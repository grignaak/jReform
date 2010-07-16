package org.jreform.inputs;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jreform.InputDataType;
import org.jreform.impl.AbstractInputControl;
import org.jreform.util.ParsedValue;

/**
 * @author armandino (at) gmail.com
 */
public class BasicInput<T> extends AbstractInputControl<T> implements Input<T>
{
    private ParsedValue<T> maybeValue = ParsedValue.error("Empty value");
    private String valueAttribute = "";
    
    public BasicInput(InputDataType<T> type, String name)
    {
        super(type, name);
    }
    
    public final T getValue()
    {
        // TODO make this throw
        return maybeValue.getValueOrDefault(null);
    }
    
    @Deprecated
    public final void setValue(T value)
    {
        this.maybeValue = ParsedValue.setUnlessNull(value);
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
        maybeValue = getType().parseValue(valueAttribute);
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
        if (maybeValue.isNotParsed())
            return "";
        
        return maybeValue.getValue().toString();
    }
    
    public boolean validateRequest(HttpServletRequest req)
    {
        processRequest(req);
        return validate();
    }

    public boolean validate()
    {
        if (valueAttribute.isEmpty())
        {
            if (!isRequired())
                setValid(true);
            else
            {
                setValid(false);
                addError("Missing value");
            }
        }
        else
        {
            Set<String> criteriaErrors = getCriteriaErrors(maybeValue);
            
            addErrors(criteriaErrors);
            
            setValid(criteriaErrors.isEmpty());
        }
        return isValid();
    }
    
    public void processRequest(HttpServletRequest req)
    {
        String value = req.getParameter(getInputName());
        setValueAttribute(value);
    }
    
    public final String toString()
    {
        return getValueAttribute();
    }
}
