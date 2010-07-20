package org.jreform.inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jreform.InputDataType;
import org.jreform.impl.AbstractInputControl;
import org.jreform.util.ParsedValue;

/**
 * @author armandino (at) gmail.com
 * @author michael.deardeuff (at) gmail.com
 */
// TODO accept collections instead of lists
public class BasicMultiInput<T> extends AbstractInputControl<T> implements MultiInput<T>
{
    private List<ParsedValue<T>> values = Collections.emptyList();
    private List<String> valueAttributes = Collections.emptyList();
    
    public BasicMultiInput(InputDataType<T> type, String name)
    {
        super(type, name);
    }
    
    /**
     * Returns a list of parsed values or an empty list if none.
     */
    public final List<T> getValues()
    {
        if (!isValid())
            throw new IllegalStateException("Cannot retrieve invalid inputs");
        
        List<T> parsedValues = new ArrayList<T>(values.size());
        for (ParsedValue<T> parsedValue : values)
        {
            parsedValues.add(parsedValue.getValue());
        }
        return parsedValues;
    }
    
    public final void setValues(List<T> values)
    {
        this.values = new ArrayList<ParsedValue<T>>();
        for (T value : values)
        {
            this.values.add(ParsedValue.setUnlessNull(value));
        }
    }
    
    public final List<String> getValueAttributes()
    {
        return Collections.unmodifiableList(valueAttributes);
    }
    
    public void setValueAttributes(List<String> input)
    {
        this.valueAttributes = new ArrayList<String>(input.size());
        
        for (String value : input)
            valueAttributes.add(value != null ? value.trim() : "");
            
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
        return getValues().toString();
    }
    
    public boolean validate()
    {
        if (!isBlank())
            validateInput();
        else if (isRequired())
            addError("Invalid or missing value");
        
        return isValid();
    }
    
    private boolean validateInput()
    {
        boolean isValid = true;
        for (ParsedValue<T> value : values)
        {
            Set<String> criteriaErrors = getCriteriaErrors(value);
            if (!criteriaErrors.isEmpty())
            {
                addErrors(criteriaErrors);
                isValid = false;
            }
        }
        
        return isValid;
    }

    public void processRequest(HttpServletRequest req)
    {
        String[] parameterValues = req.getParameterValues(getInputName());
        if (parameterValues == null)
            parameterValues = new String[0];
        setValueAttributes(Arrays.asList(parameterValues));
    }

    private List<ParsedValue<T>> parseValues()
    {
        List<ParsedValue<T>> theRealParsedValues = new ArrayList<ParsedValue<T>>();
        for (String attributeValue : valueAttributes)
        {
            ParsedValue<T> parsedValue = getType().parseValue(attributeValue);
            theRealParsedValues.add(parsedValue);
        }
        return theRealParsedValues;
    }
    
    public final String toString()
    {
        return valueAttributes.toString();
    }
    
}
