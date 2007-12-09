package org.jreform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jreform.core.Criterion;
import org.jreform.core.InputDataType;
import org.jreform.types.StringType;

/**
 * @author armandino (at) gmail.com
 */
abstract class AbstractInputCollection
{
    private final Map<String, InputControl> inputs;
    private final Set<String> errors;
    private boolean isValid;
    
    AbstractInputCollection()
    {
        inputs = new HashMap<String, InputControl>();
        errors = new HashSet<String>();
        isValid = false;
    }
    
    /**
     * Returns a single-value input with the specified name.
     */
    @SuppressWarnings("unchecked")
    public <T> Input<T> getInput(String name)
    {
        return (Input<T>)inputs.get(name);
    }
    
    /**
     * Returns a multi-value input with the specified name.
     */
    @SuppressWarnings("unchecked")
    public <T> MultiInput<T> getMultiInput(String name)
    {
        return (MultiInput<T>)inputs.get(name);
    }
    
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

    final Map<String,InputControl> getInputs()
    {
        return inputs;
    }
    
    final void setValid(boolean isValid)
    {
        this.isValid = isValid;
    }
    
    /**
     * Validates inputs against the passed in request. 
     */
    abstract boolean validate(HttpServletRequest req);
    
    /**
     * Perform additional validation of form data where necessary.
     */
    protected void additionalValidate() { }
    
    
    
    // --------------------------------------------------------------------
    // ------------ Convenience methods for adding inputs -----------------
    // --------------------------------------------------------------------
    
    /**
     * Add a single-value input.
     */
    public final <T> InputControlModifier add(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new InputImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    /**
     * Add a single-value input of type string.
     */
    public final <T> InputControlModifier add(String name, Criterion...criteria)
    {
        return add(StringType.stringType(), name, criteria);
    }
    
    /**
     * Add a multi-value input.
     */
    public final <T> InputControlModifier addMulti(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new MultiInputImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    /**
     * Add a multi-value input of type string.
     */
    public final <T> InputControlModifier addMulti(String name, Criterion...criteria)
    {
        return addMulti(StringType.stringType(), name, criteria);
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iter = inputs.keySet().iterator();
        
        while(iter.hasNext())
        {
            String inputName = iter.next();
            AbstractInputControl input = (AbstractInputControl)inputs.get(inputName);
            sb.append(inputName).append(": ")
              .append(input.getStringValue())
              .append(System.getProperty("line.separator"));
        }
        
        return sb.toString();
    }
    
}
