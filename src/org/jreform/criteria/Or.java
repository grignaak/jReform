package org.jreform.criteria;

import org.jreform.Criterion;

/**
 * Performs an <tt>OR</tt> over all criteria on the given value.
 * 
 * @author armandino (at) gmail.com
 */
public final class Or<T> extends AbstractCriterion<T>
{
    private Criterion[] criteria;
    private Criterion failedCriteron;
    
    Or(Criterion...criteria)
    {
        if(criteria.length < 2)
            throw new IllegalArgumentException(getClass().getName()
                    + " requires at least two criteria");
        
        this.criteria = criteria;
    }
    
    protected boolean verify(T value)
    {
        for(Criterion<T> criterion : criteria)
        {
            if(criterion.isSatisfied(value))
                return true;
            else
                failedCriteron = criterion;
        }
        
        return false;
    }
    
    protected String generateErrorMessage()
    {
        return failedCriteron.getOnError();
    }
    
}
