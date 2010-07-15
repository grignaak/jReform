package org.jreform.inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.impl.AbstractInputControl;
import org.jreform.util.Maybe;

/**
 * @author armandino (at) gmail.com
 */
public class BasicMultiInput<T> extends AbstractInputControl<T> implements MultiInput<T>
{
    private Maybe<List<T>> values = Maybe.not();
    private List<String> valueAttributes = Collections.emptyList();
    
    protected BasicMultiInput(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
    }
    
    /**
     * Returns a list of parsed values or an empty list if none.
     */
    public final List<T> getValues()
    {
        return values.getValue();
    }
    
    public final void setValues(List<T> value)
    {
        this.values = Maybe.soUnlessNull(value);
    }
    
    /**
     * Returns an array of <tt>value</tt> attributes or an empty array if none.
     */
    public final String[] getValueAttributes()
    {
        // TODO make this return the list
        return valueAttributes.toArray(new String[0]);
    }
    
    public void setValueAttributes(String[] input)
    {
        this.valueAttributes = input == null ?
                Collections.<String>emptyList()
                : Arrays.asList(input);
        
        Iterator<String> i = valueAttributes.iterator();
        while (i.hasNext())
        {
            if (i.next() == null)
                i.remove();
        }
        
        values = parseValues();
    }
    
    public final boolean isBlank()
    {
        for(String valueAttribute : valueAttributes)
        {
            if(!valueAttribute.isEmpty())
                return false;
        }
        return true;
    }
    
    public String getStringValue()
    {
        return values.getValue().toString();
    }
    
    @Deprecated
    public boolean validateRequest(HttpServletRequest req)
    {
        processRequest(req);
        return validate();

    }
    
    public boolean validate()
    {
        boolean isValid = false;  
        if (!valueAttributes.isEmpty())
            isValid = isValidInput();
        else if (!isRequired())
            isValid = true;
        else
            setOnError("Invalid or missing value");

        setValid(isValid);
        return isValid;
    }
    
    private boolean isValidInput()
    {
        if (values.isNotSo())
        {
            setOnError("Invalid or missing value");
            return false; 
        }
        
        for (T value : values.getValue())
        {
            if (!allCriteriaSatisfied(Maybe.soUnlessNull(value)))
                return false;
        }
        
        return true;
    }

    public void processRequest(HttpServletRequest req)
    {
        String[] parameterValues = req.getParameterValues(getInputName());
        setValueAttributes(parameterValues);
    }

    private Maybe<List<T>> parseValues()
    {
        List<T> parsedValues = new ArrayList<T>();
        for (String attributeValue : getValueAttributes())
        {
            Maybe<T> parsedValue = getType().parseValue(attributeValue);
            
            if (parsedValue.isNotSo())
                return Maybe.not();
            
            parsedValues.add(parsedValue.getValue());
        }
        
        return Maybe.soUnlessNull(parsedValues);
    }
    
    public final String toString()
    {
        return Arrays.toString(getValueAttributes());
    }
    
}
