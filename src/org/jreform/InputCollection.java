package org.jreform;

import org.jreform.internal.InputControlModifier;

/**
 * @author armandino (at) gmail.com
 */
public interface InputCollection extends HttpServletRequestValidator
{
    public <T> Input<T> getInput(String name);
    public <T> Checkbox<T> getCheckbox(String name);
    public <T> MultiCheckbox<T> getMultiCheckbox(String name);
    public <T> Radio<T> getRadio(String name);
    public <T> Select<T> getSelect(String name);
    public <T> MultiSelect<T> getMultiSelect(String name);
    
    public <T> InputControlModifier<T> input(InputDataType<T> type, String name, Criterion<T>...criteria);
    public <T> InputControlModifier<T> checkbox(InputDataType<T> type, String name, Criterion<T>...criteria);
    public <T> InputControlModifier<T> multiCheckbox(InputDataType<T> type, String name, Criterion<T>...criteria);
    public <T> InputControlModifier<T> radio(InputDataType<T> type, String name, Criterion<T>...criteria);
    public <T> InputControlModifier<T> select(InputDataType<T> type, String name, Criterion<T>...criteria);
    public <T> InputControlModifier<T> multiSelect(InputDataType<T> type, String name, Criterion<T>...criteria);
    
}
