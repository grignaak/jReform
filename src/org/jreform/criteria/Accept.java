package org.jreform.criteria;

import java.util.Arrays;
import java.util.List;

/**
 * Checks that value is equal to one of the acceptable values.
 * 
 * @author armandino (at) gmail.com
 * @author michael.deardeuff (at) gmail.com
 */
public class Accept<T> extends AbstractCriterion<T>
{
    private List<T> acceptableValues;
    
    public Accept(T...values)
    {
        this.acceptableValues = Arrays.asList(values);
    }
    
    protected final boolean verify(T value)
    {
        for (T v : acceptableValues)
        {
            if (areEqual(v, value))
                return true;
        }
        
        return false;
    }
    
    protected boolean areEqual(T v1, T v2)
    {
        return v1.equals(v2);
    }
    
    protected String generateErrorMessage()
    {
        return "Please enter one of the allowed values " + acceptableValues;
    }
}
