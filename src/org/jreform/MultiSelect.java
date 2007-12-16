package org.jreform;

import java.util.Map;

/**
 * A select that supports multiple selections.
 * 
 * @author armandino (at) gmail.com
 */
public interface MultiSelect<T> extends MultiInput<T>
{
    public Map<String, SelectableState> getState();
}
