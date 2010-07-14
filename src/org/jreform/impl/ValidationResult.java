package org.jreform.impl;

import org.jreform.util.Maybe;

/**
 * The result of a validation operation by a {@link ValueAttributeValidator}.
 * 
 * @author armandino (at) gmail.com
 */
public class ValidationResult<T>
{
    private Maybe<T> parsedValue;
    private boolean isValid;
    private String errorMessage;
    
    ValidationResult(Maybe<T> parsedValue, boolean isValid, String errorMessage)
    {
        this.parsedValue = parsedValue;
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }
    
    public Maybe<T> getParsedValue()
    {
        return parsedValue;
    }
    
    public boolean isValid()
    {
        return isValid;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
}
