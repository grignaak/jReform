package org.jreform.impl;

import java.util.ArrayList;
import java.util.List;

import org.jreform.inputs.MultiInput;
import org.jreform.util.Maybe;

/**
 * Validates a multi-value input.
 * 
 * @author armandino (at) gmail.com
 */
public class MultiInputValidator<T>
{
    private AbstractInputControl<T> input;
    private List<T> values;
    private boolean isValid;
    private String errorMessage;
    
    @SuppressWarnings("unchecked")
    public MultiInputValidator(MultiInput<T> input)
    {
        // FIXME is this needed?
        this.input = (AbstractInputControl<T>)input;
    }
    
    public ValidationResult<List<T>> validate(List<String> valueAttributes)
    {
        isValid = false;
        
        if(!valueAttributes.isEmpty())
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
                errorMessage = "Invalid or missing value";
            }
        }
        
        return new ValidationResult<List<T>>(Maybe.soUnlessNull(values), isValid, errorMessage);
    }
    
    private boolean isValidInput(List<String> valueAttributes)
    {
        boolean allValid = true;
        List<T> parsedValues = new ArrayList<T>(valueAttributes.size());
        ValueAttributeValidator<T> validator = new ValueAttributeValidator<T>(input);
        
        // parse in all values even if they don't satisfy the criteria
        for(String valueAttribute : valueAttributes)
        {
            ValidationResult<T> result = validator.validate(valueAttribute);
            
            if(result.getParsedValue().isSo())
                parsedValues.add(result.getParsedValue().getValue());
            
            if(!result.isValid())
            {
                allValid = false;
                errorMessage = result.getErrorMessage();
                break;
            }
        }
        
        values = parsedValues;
        
        return allValid;
    }
    
}
