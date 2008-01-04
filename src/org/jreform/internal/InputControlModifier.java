package org.jreform.internal;

/**
 * @author armandino (at) gmail.com
 */
public class InputControlModifier
{
    private AbstractInputControl<?> input;
    
    public InputControlModifier(AbstractInputControl<?> input)
    {
        this.input = input;
    }
    
    public InputControlModifier optional()
    {
        input.setRequired(false);
        return this;
    }
    
    public InputControlModifier onError(String message)
    {
        input.setOnError(message);
        return this;
    }
}
