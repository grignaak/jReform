package org.jreform.internal;

import java.util.HashMap;
import java.util.Map;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.Select;
import org.jreform.SelectableState;

/**
 * @author armandino (at) gmail.com
 */
class SelectImpl<T> extends InputImpl<T> implements Select<T>
{
    private static final int MAP_CAPACITY = 1;
    
    private Map<String, SelectableState> stateMap;
    
    SelectImpl(InputDataType<T> type, String name, Criterion<T>...criteria)
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