package org.jreform;

/**
 * A form containing inputs and optionally input groups.
 * 
 * @author armandino (at) gmail.com
 */
public interface Form extends InputCollection
{
    /**
     * Returns a group with the specified name.
     */
    public Group getGroup(String name);
    
}
