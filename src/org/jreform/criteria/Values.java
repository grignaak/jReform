package org.jreform.criteria;

import java.util.Arrays;

/**
 * Checks that value is equal to one of the passed in values.
 * 
 * @author armandino (at) gmail.com
 */
public class Values<T> extends AbstractCriterion<T>
{
    private T[] values;
    
    Values(T...values)
    {
        this.values = values;   
    }
    
    protected final boolean verify(T value)
    {
        for(T v : values)
        {
            if(areEqual(v, value))
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
        return "Please enter one of the allowed values " +Arrays.asList(values);
    }
    
}
