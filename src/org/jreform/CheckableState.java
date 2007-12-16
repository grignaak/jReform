package org.jreform;

/**
 * Represents the state of a checkable input: checked / unchecked.
 * 
 * @author armandino (at) gmail.com
 */
public enum CheckableState
{
    CHECKED("checked"),
    UNCHECKED("");
    
    private String state;
    
    CheckableState(String state)
    {
        this.state = state;
    }
    
    public String toString()
    {
        return state;
    }
    
}
