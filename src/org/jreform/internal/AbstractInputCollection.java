package org.jreform.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jreform.DuplicateNameException;
import org.jreform.InputCollection;
import org.jreform.InputControl;

/**
 * @author armandino (at) gmail.com
 */
abstract class AbstractInputCollection implements InputCollection
{
    private final Map<String, InputControl<?>> inputs;
    private final Set<String> errors;
    private boolean isValid;
    
    AbstractInputCollection()
    {
        inputs = new HashMap<String, InputControl<?>>();
        errors = new HashSet<String>();
        isValid = false;
    }
    
    /**
     * Validates inputs against the passed in request. 
     */
    abstract boolean validate(HttpServletRequest req);
    
    /**
     * Adds the specified input to the collection.
     * 
     * @throws DuplicateNameException if there is an existing input
     *         with the same name in this collection
     */
    <T> void add(InputControl<T> input)
    {
        String name = input.getInputName();
        
        if(inputs.containsKey(name))
            throw new DuplicateNameException(
                "Duplicate input name within the same form: " + name);
        
        inputs.put(name, input);
    }
    
    final InputControl<?> getInputControl(String name)
    {
        return inputs.get(name);
    }
    
    public final Set<String> getErrors()
    {
        return errors;
    }
    
    public final void addError(String errorKey)
    {
        errors.add(errorKey);
    }
    
    public final boolean isValid()
    {
        return isValid;
    }

    final Map<String,InputControl<?>> getInputs()
    {
        return inputs;
    }
    
    final void setValid(boolean isValid)
    {
        this.isValid = isValid;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iter = inputs.keySet().iterator();
        
        while(iter.hasNext())
        {
            String inputName = iter.next();
            AbstractInputControl<?> input = (AbstractInputControl<?>)inputs.get(inputName);
            sb.append(inputName).append(": ")
              .append(input.getStringValue())
              .append(System.getProperty("line.separator"));
        }
        
        return sb.toString();
    }
    
}
