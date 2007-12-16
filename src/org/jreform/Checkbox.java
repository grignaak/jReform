package org.jreform;

/**
 * A checkbox with a single value.
 * 
 * @author armandino (at) gmail.com
 */
public interface Checkbox<T> extends Input<T>
{
    public CheckableState getState();
}
