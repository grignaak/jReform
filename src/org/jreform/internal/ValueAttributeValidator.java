package org.jreform.internal;

import org.jreform.Criterion;

/**
 * Validator for the <tt>value</tt> attribute of an input.
 * 
 * @author armandino (at) gmail.com
 */
class ValueAttributeValidator<T>
{
    private AbstractInputControl<T> input;
    private String errorMessage;
    
    ValueAttributeValidator(AbstractInputControl<T> input)
    {
        this.input = input;
    }
    
    ValidationResult<T> validate(String valueAttribute)
    {
        boolean isValid = false;
        T parsedValue = null;
        
        if(valueAttribute != null && !"".equals(valueAttribute))
        {
            parsedValue = input.getType().parseValue(valueAttribute);
            isValid = allCriteriaSatisfied(parsedValue);
        }
        else
        {
            // blank input is valid if it's not required
            isValid = !input.isRequired();
        }
        
        if(!isValid && errorMessage == null)
            errorMessage = "Invalid or missing value";
        
        return new ValidationResult<T>(parsedValue, isValid, errorMessage);
    }
    
    private boolean allCriteriaSatisfied(T parsedValue)
    {
        if(parsedValue == null)
            return false;
        
        Criterion<T>[] criteria = input.getCriteria();
        for(Criterion<T> criterion : criteria)
        {
            if(!criterion.isSatisfied(parsedValue))
            {
                errorMessage = criterion.getOnError();
                return false;
            }
        }
        
        return true;
    }
    
}
