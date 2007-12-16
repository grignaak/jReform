package org.jreform;

import org.jreform.internal.InputControlModifier;

/**
 * @author armandino (at) gmail.com
 */
public interface InputCollection
{
    public <T> Input<T> getInput(String name);
    public <T> MultiInput<T> getMultiInput(String name);
    public <T> Checkbox<T> getCheckbox(String name);
    public <T> MultiCheckbox<T> getMultiCheckbox(String name);
    public <T> Radio<T> getRadio(String name);
    public <T> Select<T> getSelect(String name);
    public <T> MultiSelect<T> getMultiSelect(String name);
    
    public <T> InputControlModifier input(InputDataType<T> type, String name, Criterion...criteria);
    public <T> InputControlModifier input(String name, Criterion...criteria);
    public <T> InputControlModifier multiInput(InputDataType<T> type, String name, Criterion...criteria);
    public <T> InputControlModifier multiInput(String name, Criterion...criteria);
    public <T> InputControlModifier checkbox(InputDataType<T> type, String name, Criterion...criteria);
    public <T> InputControlModifier multiCheckbox(InputDataType<T> type, String name, Criterion...criteria);
    public <T> InputControlModifier radio(InputDataType<T> type, String name, Criterion...criteria);
    public <T> InputControlModifier select(InputDataType<T> type, String name, Criterion...criteria);
    public <T> InputControlModifier multiSelect(InputDataType<T> type, String name, Criterion...criteria);
    
}
