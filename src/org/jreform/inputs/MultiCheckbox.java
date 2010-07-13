package org.jreform.inputs;

import java.util.HashMap;
import java.util.Map;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.MultiInput;
import org.jreform.internal.DefaultValueMap;
import org.jreform.internal.MultiInputImpl;

public class MultiCheckbox<T> extends MultiInputImpl<T> implements MultiInput<T>
{
    private Map<String, CheckableState> stateMap;
    
    public MultiCheckbox(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new DefaultValueMap<String, CheckableState>(
                new HashMap<String, CheckableState>(),
                CheckableState.UNCHECKED);
    }
    
    public void setValueAttributes(String[] valueAttributes)
    {
        super.setValueAttributes(valueAttributes);
        for(String value : getValueAttributes())
        {
            stateMap.put(value, CheckableState.CHECKED);
        }
    }
    
    public Map<String, CheckableState> getState()
    {
        return stateMap;
    }
    
}
