package org.jreform.inputs;

import java.util.*;

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
            parsedValues.add(parsedValue.getValue());
            
        return parsedValues;
    }
    
    public final void setValues(List<T> newValues)
    {
        values = new ArrayList<ParsedValue<T>>();
        for (T value : newValues)
            values.add(ParsedValue.setUnlessNull(value));
    }
    
    public final List<String> getValueAttributes()
    {
        return Collections.unmodifiableList(valueAttributes);
    }
    
    public void setValueAttributes(List<String> input)
    {
        valueAttributes = new ArrayList<String>(input.size());
        
        for (String value : input)
            valueAttributes.add(value != null ? value.trim() : "");
            
        values = parseValues();
    }
    
    public final boolean isBlank()
    {
        for(String valueAttribute : valueAttributes)
            if(!valueAttribute.isEmpty())
                return false;
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
    
    private void validateInput()
    {
        for (ParsedValue<T> value : values)
            addErrors(getCriteriaErrors(value));
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
        List<ParsedValue<T>> parsedValues = new ArrayList<ParsedValue<T>>();
        for (String valueAttribute : valueAttributes)
        {
            ParsedValue<T> parsedValue = getType().parseValue(valueAttribute);
            parsedValues.add(parsedValue);
        }
        return parsedValues;
    }
    
    public final String toString()
    {
        return valueAttributes.toString();
    }
    
}
