package org.jreform.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.DuplicateNameException;
import org.jreform.Input;
import org.jreform.InputCollection;
import org.jreform.InputControl;
import org.jreform.InputDataType;
import org.jreform.UndefinedInputControlException;
import org.jreform.inputs.Checkbox;
import org.jreform.inputs.MultiCheckbox;
import org.jreform.inputs.MultiSelect;
import org.jreform.inputs.Radio;
import org.jreform.inputs.Select;

/**
 * @author armandino (at) gmail.com
 */
public abstract class AbstractInputCollection implements InputCollection
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
        InputControl<?> input = inputs.get(name);
        
        if(input == null)
            throw new UndefinedInputControlException(
                    "Undefined input control: " + name);
        
        return input;
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
    public final <T> MultiCheckbox<T> getMultiCheckbox(String name)
    {
        return (MultiCheckbox<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T> Radio<T> getRadio(String name)
    {
        return (Radio<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T> Select<T> getSelect(String name)
    {
        return (Select<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T> MultiSelect<T> getMultiSelect(String name)
    {
        return (MultiSelect<T>)getInputControl(name);
    }
    
    
    //
    // Add methods
    //
    
    public final <T> InputControlModifier<T> input(
            InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        return create(new InputImpl<T>(type, name, criteria));
    }
    
    public final <T> InputControlModifier<T> checkbox(
            InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        return create(new Checkbox<T>(type, name, criteria));
    }
    
    public final <T> InputControlModifier<T> multiCheckbox(
            InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        return create(new MultiCheckbox<T>(type, name, criteria));
    }
    
    public final <T> InputControlModifier<T> radio(
            InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        return create(new Radio<T>(type, name, criteria));
    }
    
    public final <T> InputControlModifier<T> select(
            InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        return create(new Select<T>(type, name, criteria));
    }
    
    public final <T> InputControlModifier<T> multiSelect(
            InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        return create(new MultiSelect<T>(type, name, criteria));
    }

    private <T> InputControlModifier<T> create(AbstractInputControl<T> input)
    {
        add(input);
        return new InputControlModifier<T>(input);
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iter = inputs.keySet().iterator();
        
        while(iter.hasNext())
        {
            String inputName = iter.next();
            InputControl<?> input = inputs.get(inputName);
            sb.append(inputName).append(": ")
              .append(input.getStringValue())
              .append(System.getProperty("line.separator"));
        }
        
        return sb.toString();
    }

    protected void validateInputs()
    {
        for (InputControl<?> input : getInputs().values())
        {
            if (!input.validate())
                getErrors().add(input.getInputName());
        }
    }

    public void processRequest(HttpServletRequest req)
    {
        for (InputControl<?> input : getInputs().values())
        {
            input.processRequest(req);
        }
    }
    
}
