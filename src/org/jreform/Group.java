package org.jreform;

/**
 * A group of inputs within a form.
 * 
 * @author armandino (at) gmail.com
 */
public interface Group
{
    /**
     * Returns the name of this group.
     */
    public String getName();
    
    /**
     * If <b>required</b> all inputs must satisfy imposed criteria for the
     * group to be valid. If <b>optional</b> either all inputs must be null
     * OR all inputs must satisfy their criteria for the group to be valid.
     */
    public boolean isRequired();
    
    /**
     * Checks if this group has input data.
     * @return <code>true</code> if all inputs that belong to this group
     *         are blank, <code>false</code> otherwise.
     */
    public boolean isEmpty();
    
}
