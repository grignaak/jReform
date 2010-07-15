package org.jreform.inputs;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.impl.AbstractInputControl;
import org.jreform.util.Maybe;

/**
 * @author armandino (at) gmail.com
 */
public class BasicInput<T> extends AbstractInputControl<T> implements Input<T>
{
    private Maybe<T> maybeValue = Maybe.not();
    private String valueAttribute = "";
    
    public BasicInput(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
    }
    
    public final T getValue()
    {
        // TODO make this throw
        return maybeValue.getValueOrDefault(null);
    }
    
    @Deprecated
    public final void setValue(T value)
    {
        this.maybeValue = Maybe.soUnlessNull(value);
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
        if (maybeValue.isNotSo())
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
            setValid(!isRequired());
            setOnError("Missing value");
        }
        else
        {
            setValid(allCriteriaSatisfied(maybeValue));
            
            if(!isValid() && getOnError() == null)
                setOnError("Invalid value");
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
