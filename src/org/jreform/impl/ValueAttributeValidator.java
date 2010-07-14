package org.jreform.impl;

import org.jreform.Criterion;
import org.jreform.util.Maybe;

/**
 * Validator for the <tt>value</tt> attribute of an input.
 * 
 * @author armandino (at) gmail.com
 */
public class ValueAttributeValidator<T>
{
    private AbstractInputControl<T> input;
    private String errorMessage;
    
    public ValueAttributeValidator(AbstractInputControl<T> input)
    {
        this.input = input;
    }
    
    public ValidationResult<T> validate(String valueAttribute)
    {
        boolean isValid = false;
        Maybe<T> maybeParsedValue = Maybe.not();
        
        if(!valueAttribute.isEmpty())
        {
            maybeParsedValue= input.getType().parseValue(valueAttribute);
            isValid = allCriteriaSatisfied(maybeParsedValue);
        }
        else
        {
            // blank input is valid if it's not required
            isValid = !input.isRequired();
        }
        
        if(!isValid && errorMessage == null)
            errorMessage = "Invalid or missing value";
        
        return new ValidationResult<T>(maybeParsedValue, isValid, errorMessage);
    }
    
    private boolean allCriteriaSatisfied(Maybe<T> parsedValue)
    {
        if(parsedValue.isNotSo())
            return false;
        
        Criterion<T>[] criteria = input.getCriteria();
        for(Criterion<T> criterion : criteria)
        {
            if(!criterion.isSatisfied(parsedValue.getValue()))
            {
                errorMessage = criterion.getOnError();
                return false;
            }
        }
        
        return true;
    }
    
}
