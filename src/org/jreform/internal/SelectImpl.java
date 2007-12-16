package org.jreform.internal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.Select;
import org.jreform.SelectableState;

/**
 * @author armandino (at) gmail.com
 */
class SelectImpl<T> extends InputImpl<T> implements Select<T>
{
    private Map<String, SelectableState> stateMap;
    
    SelectImpl(InputDataType<T> type, String name, Criterion...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new HashMap<String, SelectableState>();
    }
    
    boolean validate(HttpServletRequest req)
    {
        boolean isValid = super.validate(req);
        
        stateMap.put(getValueAttribute(), SelectableState.SELECTED);
        
        return isValid;
    }
    
    public Map<String, SelectableState> getState()
    {
        return stateMap;
    }
    
}