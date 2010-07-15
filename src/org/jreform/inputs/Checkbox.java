package org.jreform.inputs;

import org.jreform.Criterion;
import org.jreform.InputDataType;

public class Checkbox<T> extends BasicInput<T>
{
    // TODO map the valueAttributes to two different values
    public Checkbox(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
        
        setRequired(false); // single checkbox always optional
    }
    
    public boolean isSelected()
    {
        return !getValueAttribute().isEmpty();
    }
}
