package org.jreform;

import java.util.Map;

/**
 * A radio button. Can only have a single value. 
 * 
 * @author armandino (at) gmail.com
 */
public interface Radio<T> extends Input<T>
{
    public Map<String, CheckableState> getState();
}
