package org.jreform.internal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jreform.CheckableState;
import org.jreform.Criterion;
import org.jreform.InputDataType;
import org.jreform.MultiCheckbox;

class MultiCheckboxImpl<T> extends MultiInputImpl<T> implements MultiCheckbox<T>
{
    private Map<String, CheckableState> stateMap;
    
    MultiCheckboxImpl(InputDataType<T> type, String name, Criterion...criteria)
    {
        super(type, name, criteria);
        
        stateMap = new DefaultValueMap<String, CheckableState>(
                new HashMap<String, CheckableState>(),
                CheckableState.UNCHECKED);
    }
    
    /**
     * Unlike a single value checkbox, multi checkbox cab be required:
     * i.e. user must make one or more selections.
     */
    boolean validate(HttpServletRequest req)
    {
        boolean isValid = super.validate(req);
        
        for(String value : getValueAttributes())
        {
            stateMap.put(value, CheckableState.CHECKED);
        }
        
        return isValid;
    }
    
    public Map<String, CheckableState> getState()
    {
        return stateMap;
    }
    
}
