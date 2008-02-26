package org.jreform.internal;

import java.util.HashMap;
import java.util.Map;

import org.jreform.CheckableState;
import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.Radio;

/**
 * @author armandino (at) gmail.com
 */
class RadioImpl<T> extends InputImpl<T> implements Radio<T>
{
    private Map<String, CheckableState> stateMap;
    
    RadioImpl(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new DefaultValueMap<String, CheckableState>(
                new HashMap<String, CheckableState>(),
                CheckableState.UNCHECKED);
    }
    
    public void setValueAttribute(String valueAttribute)
    {
        super.setValueAttribute(valueAttribute);
        stateMap.put(getValueAttribute(), CheckableState.CHECKED);
    }
    
    public Map<String, CheckableState> getState()
    {
        return stateMap;
    }
    
}
