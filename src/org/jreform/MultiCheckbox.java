package org.jreform;

import java.util.Map;

/**
 * A checkbox that supports multiple values.
 * 
 * @author armandino (at) gmail.com
 */
public interface MultiCheckbox<T> extends MultiInput<T>
{
    public Map<String, CheckableState> getState();
}
