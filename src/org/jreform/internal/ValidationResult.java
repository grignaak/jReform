package org.jreform.internal;

/**
 * The result of a validation operation by a {@link ValueAttributeValidator}.
 * 
 * @author armandino (at) gmail.com
 */
class ValidationResult<T>
{
    private T parsedValue;
    private boolean isValid;
    private String errorMessage;
    
    ValidationResult(T parsedValue, boolean isValid, String errorMessage)
    {
        this.parsedValue = parsedValue;
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }
    
    T getParsedValue()
    {
        return parsedValue;
    }
    
    boolean isValid()
    {
        return isValid;
    }
    
    String getErrorMessage()
    {
        return errorMessage;
    }
    
}
