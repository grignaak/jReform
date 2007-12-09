package org.jreform.criteria;

/**
 * Checks that value is greater than or equal to the given {@link Comparable}.
 * 
 * @author armandino (at) gmail.com
 */
public final class Min<T extends Comparable<T>> extends AbstractCriterion<T>
{
    private T min;
    
    Min(T min)
    {
        this.min = min;   
    }
    
    protected boolean verify(T value)
    {
        return value.compareTo(min) >= 0;
    }
    
    protected String generateErrorMessage()
    {
        return "The value must not be less than " + min;
    }
    
}
