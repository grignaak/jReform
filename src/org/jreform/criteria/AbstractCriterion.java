package org.jreform.criteria;

import org.jreform.Criterion;

/**
 * An abstract criterion that verifies itself and sets an error message
 * if validation fails. 
 * 
 * @author armandino (at) gmail.com
 * @author michael.deardeuff (at) gmail.com
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
        
        if (!isCustomMessageSet())
            messageOnError = generateErrorMessage();
        
        return false;
    }

    private boolean isCustomMessageSet()
    {
        return messageOnError != null;
    }
    
    public final String getOnError()
    {
        return messageOnError;
    }
    
    @Deprecated
    public final void setOnError(String messageOnError)
    {
        this.messageOnError = messageOnError; 
    }
    
    public final AbstractCriterion<T> onError(String customErrorMessage)
    {
        messageOnError = customErrorMessage;
        return this;
    }
}
