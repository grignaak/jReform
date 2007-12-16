package org.jreform;

import java.util.Map;

/**
 * A select that supports only one selection.
 * 
 * @author armandino (at) gmail.com
 */
public interface Select<T> extends Input<T>
{
    public Map<String, SelectableState> getState();
}
