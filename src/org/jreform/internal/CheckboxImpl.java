package org.jreform.internal;

import static org.jreform.CheckableState.CHECKED;
import static org.jreform.CheckableState.UNCHECKED;

import javax.servlet.http.HttpServletRequest;

import org.jreform.CheckableState;
import org.jreform.Checkbox;
import org.jreform.Criterion;
import org.jreform.InputDataType;

class CheckboxImpl<T> extends InputImpl<T> implements Checkbox<T>
{
    CheckboxImpl(InputDataType<T> type, String name, Criterion...criteria)
    {
        super(type, name, criteria);
        
        setRequired(false); // single checkbox always optional
    }
    
    /**
     * A checkbox is always valid (and optional) since its value is
     * <code>null</code> when it's submitted unchecked.
     */
    boolean validate(HttpServletRequest req)
    {
        return super.validate(req);
    }
    
    public CheckableState getState()
    {
        return "".equals(getValueAttribute()) ? UNCHECKED : CHECKED;
    }
    
}
