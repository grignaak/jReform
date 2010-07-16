package org.jreform.criteria;

/**
 * Checks that value is within the given range.
 * 
 * @author armandino (at) gmail.com
 */
public final class Range<T extends Comparable<T>> extends AbstractCriterion<T>
{
    private T min;
    private T max;
    
    Range(T min, T max)
    {
        this.min = min;
        this.max = max;
    }
    
    protected boolean verify(T value)
    {
        return value.compareTo(max) <= 0 && value.compareTo(min) >= 0;
    }
    
    protected String generateErrorMessage()
    {
        return "The value must be between " + min + " and " + max;
    }
    
}
