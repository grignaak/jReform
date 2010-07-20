package org.jreform;

import org.jreform.inputs.Input;
import org.jreform.inputs.MultiInput;

/**
 * @author armandino (at) gmail.com
 */
public interface InputCollection extends HttpServletRequestValidator
{
    public <T> Input<T> getInput(String name);
    public <T> MultiInput<T> getMultiInput(String name);
  
    public <T> InputControlModifier<T> input(InputDataType<T> type, String name);
    public <T> InputControlModifier<T> checkbox(InputDataType<T> type, String name);
    public <T> InputControlModifier<T> multiInput(InputDataType<T> type, String name);
    public <T> InputControlModifier<T> radio(InputDataType<T> type, String name);
}
