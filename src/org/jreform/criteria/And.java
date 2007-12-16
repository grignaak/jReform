package org.jreform.criteria;

import org.jreform.Criterion;

/**
 * Performs an <tt>AND</tt> over all criteria on the given value.
 * 
 * @author armandino (at) gmail.com
 */
public final class And<T> extends AbstractCriterion<T>
{
    private Criterion[] criteria;
    private Criterion failedCriterion;
    
    And(Criterion...criteria)
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
            if(!criterion.isSatisfied(value))
            {
                failedCriterion = criterion;
                return false;
            }
        }
        
        return true;
    }
    
    protected String generateErrorMessage()
    {
        return failedCriterion.getOnError();
    }
    
}
