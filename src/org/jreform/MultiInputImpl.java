package org.jreform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jreform.core.Criterion;
import org.jreform.core.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
class MultiInputImpl<T> extends AbstractInputControl<T> implements MultiInput<T>
{
    private static final String[] EMPTY_ARRAY = {};
    
    private List<T> values;
    private String[] valueAttributes;
    
    MultiInputImpl(InputDataType<T> type, String name, Criterion...criteria)
    {
        super(type, name, criteria);
    }
    
    /**
     * Returns a list of parsed values or an empty list if none.
     */
    @SuppressWarnings("unchecked")
    public final List<T> getValues()
    {
        return values == null ? Collections.EMPTY_LIST : values;
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
        return valueAttributes == null ? EMPTY_ARRAY : valueAttributes;
    }
    
    public final void setValueAttributes(String[] input)
    {
        this.valueAttributes = input;
    }
    
    public String getStringValue()
    {
        return values.toString();
    }
    
    boolean validate(HttpServletRequest req)
    {
        String[] values = req.getParameterValues(getInputName());
        setValueAttributes(values);

        MultiInputValidator<T> validator = new MultiInputValidator<T>(this);
        ValidationResult<List<T>> result = validator.validate(valueAttributes);
        
        setValues(result.getParsedValue());
        setValid(result.isValid());
        setOnError(result.getErrorMessage());
        
        return isValid();

    }
    
    public final String toString()
    {
        return getValueAttributes() == null ? "" : Arrays.toString(getValueAttributes());
    }
    
}
