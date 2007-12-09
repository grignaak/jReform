package org.jreform;

import java.util.ArrayList;
import java.util.List;


/**
 * Validates a multi-value input.
 * 
 * @author armandino (at) gmail.com
 */
class MultiInputValidator<T>
{
    private AbstractInputControl<T> input;
    private List<T> values;
    private boolean isValid;
    private String errorMessage;
    
    MultiInputValidator(MultiInput<T> input)
    {
        this.input = (AbstractInputControl<T>)input;
    }
    
    ValidationResult<List<T>> validate(String[] valueAttributes)
    {
        isValid = false;
        
        if(valueAttributes != null && valueAttributes.length > 0)
        {
            isValid = isValidInput(valueAttributes);
        }
        else
        {
            if(!input.isRequired())
            {
                isValid = true;
            }
            else
            {
                errorMessage = "Missing required field";
            }
        }
        
        return new ValidationResult<List<T>>(values, isValid, errorMessage);
    }
    
    private boolean isValidInput(String[] valueAttributes)
    {
        boolean allValid = true;
        List<T> parsedValues = new ArrayList<T>(valueAttributes.length);
        ValueAttributeValidator<T> validator = new ValueAttributeValidator<T>(input);
        
        for(String valueAttribute : valueAttributes)
        {
            ValidationResult<T> result = validator.validate(valueAttribute);
            
            if(result.isValid())
            {
                 parsedValues.add(result.getParsedValue());
            }
            else
            {
                allValid = false;
                errorMessage = result.getErrorMessage();
                break;
            }
        }
        
        values = allValid ? parsedValues : null; // all or nothing
        
        return allValid;
    }
    
}
