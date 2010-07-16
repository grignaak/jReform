package org.jreform.criteria;

import org.jreform.Criterion;

/**
 * An abstract criterion that verifies itself and sets an error message
 * if validation fails. 
 * 
 * @author armandino (at) gmail.com
 */
public abstract class AbstractCriterion<T> implements Criterion<T>
{
    private String messageOnError;
    
    /**
     * Verifies that the argument satisfies this criterion.
     */
    protected abstract boolean verify(T value);
    
    /**
     * Generates an error message for this criterion.
     */
    protected abstract String generateErrorMessage();
    
    public final boolean isSatisfied(T value)
    {
        if (verify(value))
            return true;
        
        setOnError(generateErrorMessage());
        return false;
    }
    
    public final String getOnError()
    {
        return messageOnError;
    }
    
    public final void setOnError(String messageOnError)
    {
        this.messageOnError = messageOnError; 
    }
    
}
