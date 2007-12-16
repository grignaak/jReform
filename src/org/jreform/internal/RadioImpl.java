package org.jreform.internal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
    
    RadioImpl(InputDataType<T> type, String name, Criterion...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new HashMap<String, CheckableState>();
    }
    
    boolean validate(HttpServletRequest req)
    {
        boolean isValid = super.validate(req);
        
        stateMap.put(getValueAttribute(), CheckableState.CHECKED);
        
        return isValid;
    }
    
    public Map<String, CheckableState> getState()
    {
        return stateMap;
    }
    
}
