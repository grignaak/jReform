package org.jreform.impl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.jreform.*;
import org.jreform.exceptions.DuplicateNameException;
import org.jreform.exceptions.UndefinedInputControlException;
import org.jreform.inputs.*;

/**
 * @author armandino (at) gmail.com
 */
public abstract class AbstractInputCollection implements InputCollection
{
    private final Map<String, InputControl<?>> inputs = new HashMap<String, InputControl<?>>();
    private final Set<String> errors = new HashSet<String>();
    
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
            throw new DuplicateNameException(name);
        
        inputs.put(name, input);
    }
    
    final InputControl<?> getInputControl(String name)
    {
        if (!inputs.containsKey(name))
            throw new UndefinedInputControlException(name);
        
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
        return errors.isEmpty();
    }
    
    protected Iterable<InputControl<?>> getInputs()
    {
        return inputs.values();
    }
    
    /**
     * Perform additional validation of form data where necessary.
     */
    protected void additionalValidate() {}
    
    
    
    //
    // ----------------------------------------------------------------
    // ------------------InputCollection methods ----------------------
    // ----------------------------------------------------------------
    //
    
    //
    // Getters
    //
    
    @SuppressWarnings("unchecked")
    public final <T> Input<T> getInput(String name)
    {
        return (Input<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T> Checkbox<T> getCheckbox(String name)
    {
        return (Checkbox<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T> MultiInput<T> getMultiInput(String name)
    {
        return (MultiInput<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T> Radio<T> getRadio(String name)
    {
        return (Radio<T>)getInputControl(name);
    }
    
    
    //
    // Add methods
    //
    
    public final <T> InputControlModifier<T> input(InputDataType<T> type, String name)
    {
        return create(new BasicInput<T>(type, name));
    }
    
    public final <T> InputControlModifier<T> checkbox(InputDataType<T> type, String name)
    {
        return create(new Checkbox<T>(type, name));
    }
    
    public final <T> InputControlModifier<T> multiInput(InputDataType<T> type, String name)
    {
        return create(new BasicMultiInput<T>(type, name));
    }
    
    public final <T> InputControlModifier<T> radio(InputDataType<T> type, String name)
    {
        return create(new Radio<T>(type, name));
    }

    private <T> InputControlModifier<T> create(InputControl<T> input)
    {
        add(input);
        return new InputControlModifier<T>(input);
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        for (InputControl<?> input : inputs.values())
        {
            sb.append(input.getInputName()).append(": ")
                .append(input.getStringValue())
                .append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    protected void validateInputs()
    {
        for (InputControl<?> input : inputs.values())
            if (!input.validate())
                getErrors().add(input.getInputName());
    }

    public void processRequest(HttpServletRequest req)
    {
        for (InputControl<?> input : inputs.values())
            input.processRequest(req);
    }
}
