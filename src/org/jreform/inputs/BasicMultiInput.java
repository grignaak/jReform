package org.jreform.inputs;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.impl.AbstractInputControl;
import org.jreform.impl.MultiInputValidator;
import org.jreform.impl.ValidationResult;

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
        MultiInputValidator<T> validator = new MultiInputValidator<T>(this);
        ValidationResult<List<T>> result = validator.validate(valueAttributes);
        
        List<T> defaultValues = Collections.emptyList();
        setValues(result.getParsedValue().getValueOrDefault(defaultValues));
        setValid(result.isValid());
        setOnError(result.getErrorMessage());
        
        return isValid();
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
