package org.jreform.internal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
    
    MultiSelectImpl(InputDataType<T> type, String name, Criterion...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new HashMap<String, SelectableState>();
    }
    
    boolean validate(HttpServletRequest req)
    {
        boolean isValid = super.validate(req);
        
        for(String value : getValueAttributes())
        {
            stateMap.put(value, SelectableState.SELECTED);
        }
        
        return isValid;
    }
    
    public Map<String, SelectableState> getState()
    {
        return stateMap;
    }
    
}