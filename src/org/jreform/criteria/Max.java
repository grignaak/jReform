package org.jreform.criteria;

/**
 * Checks that the value is less than or equal to the given {@link Comparable}.
 * 
 * @author armandino (at) gmail.com
 */
public final class Max<T extends Comparable<T>> extends AbstractCriterion<T>
{
    private T max;
    
    Max(T max)
    {
        this.max = max;
    }
    
    protected boolean verify(T value)
    {
        return value.compareTo(max) <= 0;
    }
    
    protected String generateErrorMessage()
    {
        return "The value must not be greater than " + max;
    }
    
}
