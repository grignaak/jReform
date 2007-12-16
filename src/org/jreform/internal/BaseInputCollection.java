package org.jreform.internal;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Checkbox;
import org.jreform.Criterion;
import org.jreform.Input;
import org.jreform.InputCollection;
import org.jreform.InputDataType;
import org.jreform.MultiCheckbox;
import org.jreform.MultiInput;
import org.jreform.MultiSelect;
import org.jreform.Radio;
import org.jreform.Select;
import org.jreform.types.StringType;

/**
 * Implements methods from the {@link InputCollection}.
 * 
 * @author armandino (at) gmail.com
 */
class BaseInputCollection extends AbstractInputCollection
{
    @SuppressWarnings("unchecked")
    public <T> Input<T> getInput(String name)
    {
        return (Input<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public <T> MultiInput<T> getMultiInput(String name)
    {
        return (MultiInput<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public <T> Checkbox<T> getCheckbox(String name)
    {
        return (Checkbox<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public <T> MultiCheckbox<T> getMultiCheckbox(String name)
    {
        return (MultiCheckbox<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public <T> Radio<T> getRadio(String name)
    {
        return (Radio<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public <T> Select<T> getSelect(String name)
    {
        return (Select<T>)getInputControl(name);
    }
    
    @SuppressWarnings("unchecked")
    public <T> MultiSelect<T> getMultiSelect(String name)
    {
        return (MultiSelect<T>)getInputControl(name);
    }
    
    // --------------------------------------------------------------------
    // ------------ Convenience methods for adding inputs -----------------
    // --------------------------------------------------------------------
    
    public final <T> InputControlModifier input(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new InputImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    public final <T> InputControlModifier input(String name, Criterion...criteria)
    {
        return input(StringType.stringType(), name, criteria);
    }
    
    public final <T> InputControlModifier multiInput(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new MultiInputImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    public final <T> InputControlModifier multiInput(String name, Criterion...criteria)
    {
        return multiInput(StringType.stringType(), name, criteria);
    }
    
    public final <T> InputControlModifier checkbox(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new CheckboxImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    public final <T> InputControlModifier multiCheckbox(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new MultiCheckboxImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    public final <T> InputControlModifier radio(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new RadioImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    public final <T> InputControlModifier select(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new SelectImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    public final <T> InputControlModifier multiSelect(
            InputDataType<T> type, String name, Criterion...criteria)
    {
        AbstractInputControl<T> input = new MultiSelectImpl<T>(type, name, criteria);
        add(input);
        return new InputControlModifier(input);
    }
    
    
    boolean validate(HttpServletRequest req)
    {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Perform additional validation of form data where necessary.
     */
    protected void additionalValidate() {}
    
}
