package org.jreform.inputs;

import java.util.HashMap;
import java.util.Map;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.util.DefaultValueMap;

/**
 * @author armandino (at) gmail.com
 */
public class Radio<T> extends BasicInput<T>
{
    private Map<String, CheckableState> stateMap;
    
    public Radio(InputDataType<T> type, String name, Criterion<T>...criteria)
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
