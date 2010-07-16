package org.jreform;


/**
 * @author armandino (at) gmail.com
 */
public class InputControlModifier<T>
{
    private InputControl<T> input;
    
    public InputControlModifier(InputControl<T> input)
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
    
    public InputControlModifier<T> criterion(Criterion<T> criterion)
    {
        input.addCriterion(criterion);
        return this;   
    }
}
