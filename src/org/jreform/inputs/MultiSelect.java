package org.jreform.inputs;

import java.util.HashMap;
import java.util.Map;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.MultiInput;
import org.jreform.internal.DefaultValueMap;
import org.jreform.internal.MultiInputImpl;

/**
 * @author armandino (at) gmail.com
 */
public class MultiSelect<T> extends MultiInputImpl<T> implements MultiInput<T>
{
    private Map<String, SelectableState> stateMap;
    
    public MultiSelect(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new DefaultValueMap<String, SelectableState>(
                new HashMap<String, SelectableState>(),
                SelectableState.UNSELECTED);
    }
    
    @Override
    public void setValueAttributes(String[] input)
    {
        super.setValueAttributes(input);
        for(String value : getValueAttributes())
        {
            stateMap.put(value, SelectableState.SELECTED);
        }
    }
    
    public Map<String, SelectableState> getState()
    {
        return stateMap;
    }
    
}
