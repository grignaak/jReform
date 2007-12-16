package org.jreform;

/**
 * Represents the state of a selectable input: selected / unselected.
 * 
 * @author armandino (at) gmail.com
 */
public enum SelectableState
{
    SELECTED("selected"),
    UNSELECTED("");
    
    private String state;
    
    SelectableState(String state)
    {
        this.state = state;
    }
    
    public String toString()
    {
        return state;
    }
    
}
