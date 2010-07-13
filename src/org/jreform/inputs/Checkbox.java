package org.jreform.inputs;

import static org.jreform.inputs.CheckableState.CHECKED;
import static org.jreform.inputs.CheckableState.UNCHECKED;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.Input;
import org.jreform.InputDataType;
import org.jreform.internal.InputImpl;

public class Checkbox<T> extends InputImpl<T> implements Input<T>
{
    public Checkbox(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
        
        setRequired(false); // single checkbox always optional
    }
    
    /**
     * A checkbox is always valid (and optional) since its value is
     * <code>null</code> when it's submitted unchecked.
     */
    protected final boolean validate(HttpServletRequest req)
    {
        return super.validate(req);
    }
    
    public CheckableState getState()
    {
        return "".equals(getValueAttribute()) ? UNCHECKED : CHECKED;
    }
    
}
