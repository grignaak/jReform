package org.jreform.inputs;

import java.util.HashMap;
import java.util.Map;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.util.DefaultValueMap;

/**
 * @author armandino (at) gmail.com
 */
public class Select<T> extends BasicInput<T>
{
    private static final int MAP_CAPACITY = 1;
    
    private Map<String, SelectableState> stateMap;
    
    public Select(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new DefaultValueMap<String, SelectableState>(
                new HashMap<String, SelectableState>(MAP_CAPACITY),
                SelectableState.UNSELECTED);
    }
    
    @Override
    public void setValueAttribute(String input)
    {
        super.setValueAttribute(input);
        stateMap.put(getValueAttribute(), SelectableState.SELECTED);
    }
    
    public Map<String, SelectableState> getState()
    {
        return stateMap;
    }
    
}