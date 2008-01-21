package org.jreform.internal;

/**
 * @author armandino (at) gmail.com
 */
public class InputControlModifier<T>
{
    private AbstractInputControl<T> input;
    
    public InputControlModifier(AbstractInputControl<T> input)
    {
        this.input = input;
    }
    
    public InputControlModifier<T> optional()
    {
        input.setRequired(false);
        return this;
    }
    
    public InputControlModifier<T> onError(String message)
    {
        input.setOnError(message);
        return this;
    }
}
