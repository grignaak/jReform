package org.jreform.inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jreform.InputDataType;
import org.jreform.impl.AbstractInputControl;
import org.jreform.util.ParsedValue;

/**
 * @author armandino (at) gmail.com
 */
public class BasicMultiInput<T> extends AbstractInputControl<T> implements MultiInput<T>
{
    private ParsedValue<List<T>> values = ParsedValue.error("Empty value");
    private List<String> valueAttributes = Collections.emptyList();
    
    protected BasicMultiInput(InputDataType<T> type, String name)
    {
        super(type, name);
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
        this.values = ParsedValue.setUnlessNull(value);
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
            addError("Invalid or missing value");

        setValid(isValid);
        return isValid;
    }
    
    private boolean isValidInput()
    {
        if (values.isNotParsed())
        {
            addError("Invalid or missing value");
            return false; 
        }
        
        boolean isValid = true;
        for (T value : values.getValue())
        {
            Set<String> criteriaErrors = getCriteriaErrors(ParsedValue.setUnlessNull(value));
            if (!criteriaErrors.isEmpty())
            {
                getErrors().addAll(criteriaErrors);
                isValid = false;
            }
        }
        
        return isValid;
    }

    public void processRequest(HttpServletRequest req)
    {
        String[] parameterValues = req.getParameterValues(getInputName());
        setValueAttributes(parameterValues);
    }

    private ParsedValue<List<T>> parseValues()
    {
        List<T> parsedValues = new ArrayList<T>();
        for (String attributeValue : getValueAttributes())
        {
            ParsedValue<T> parsedValue = getType().parseValue(attributeValue);
            
            if (parsedValue.isNotParsed())
                return ParsedValue.error(parsedValue.getError());
            
            parsedValues.add(parsedValue.getValue());
        }
        
        return ParsedValue.setUnlessNull(parsedValues);
    }
    
    public final String toString()
    {
        return Arrays.toString(getValueAttributes());
    }
    
}
