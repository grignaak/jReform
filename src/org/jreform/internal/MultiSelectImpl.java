package org.jreform.internal;

import java.util.HashMap;
import java.util.Map;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.MultiSelect;
import org.jreform.SelectableState;

/**
 * @author armandino (at) gmail.com
 */
class MultiSelectImpl<T> extends MultiInputImpl<T> implements MultiSelect<T>
{
    private Map<String, SelectableState> stateMap;
    
    MultiSelectImpl(InputDataType<T> type, String name, Criterion<T>...criteria)
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
