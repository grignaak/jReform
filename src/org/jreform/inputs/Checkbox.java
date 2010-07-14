package org.jreform.inputs;

import static org.jreform.inputs.CheckableState.CHECKED;
import static org.jreform.inputs.CheckableState.UNCHECKED;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.InputDataType;

public class Checkbox<T> extends BasicInput<T> implements Input<T>
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
    public final boolean validateRequest(HttpServletRequest req)
    {
        return super.validateRequest(req);
    }
    
    public CheckableState getState()
    {
        return "".equals(getValueAttribute()) ? UNCHECKED : CHECKED;
    }
    
}
