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
import org.jreform.impl.ValidationResult;
import org.jreform.impl.ValueAttributeValidator;

/**
 * @author armandino (at) gmail.com
 */
public class BasicMultiInput<T> extends AbstractInputControl<T> implements MultiInput<T>
{
    private List<T> values = Collections.emptyList();
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
        return values;
    }
    
    public final void setValues(List<T> value)
    {
        this.values = value;
    }
    
    /**
     * Returns an array of <tt>value</tt> attributes or an empty array if none.
     */
    public final String[] getValueAttributes()
    {
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
    }
    
    public final boolean isBlank()
    {
        for(String valueAttribute : valueAttributes)
        {
            if(valueAttribute != null && !valueAttribute.equals(""))
                return false;
        }
        return true;
    }
    
    public String getStringValue()
    {
        return values.toString();
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
        {
            isValid = isValidInput(valueAttributes);
        }
        else
        {
            if (!isRequired())
            {
                isValid = true;
            }
            else
            {
                setOnError("Invalid or missing value");
            }
        }

        setValid(isValid);
        return isValid;
    }
    
    private boolean isValidInput(List<String> valueAttributes)
    {
        boolean allValid = true;
        List<T> parsedValues = new ArrayList<T>(valueAttributes.size());
        ValueAttributeValidator<T> validator = new ValueAttributeValidator<T>(this);
        
        // parse in all values even if they don't satisfy the criteria
        for(String valueAttribute : valueAttributes)
        {
            ValidationResult<T> result = validator.validate(valueAttribute);
            
            if(result.getParsedValue().isSo())
                parsedValues.add(result.getParsedValue().getValue());
            
            if(!result.isValid())
            {
                allValid = false;
                setOnError(result.getErrorMessage());
                break;
            }
        }
        
        values = parsedValues;
        
        return allValid;
    }

    public void processRequest(HttpServletRequest req)
    {
        String[] values = req.getParameterValues(getInputName());
        setValueAttributes(values);
    }
    
    public final String toString()
    {
        return Arrays.toString(getValueAttributes());
    }
    
}
