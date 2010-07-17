package org.jreform.inputs;

import org.jreform.InputDataType;

public class Checkbox<T> extends BasicInput<T>
{
    // TODO map the valueAttributes to two different values
    public Checkbox(InputDataType<T> type, String name)
    {
        super(type, name);
        
        super.setRequired(false); // single checkbox always optional
    }
    
    public boolean isSelected()
    {
        return !getValueAttribute().isEmpty();
    }
    
    public void setRequired(boolean isRequired)
    {
        if (isRequired)
            throw new IllegalArgumentException("cannot make checkbox required");
    }
}
