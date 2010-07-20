package org.jreform.impl;

import org.jreform.Group;
import org.jreform.InputControl;


/**
 * An input group maintains its own set of inputs and errors and has its
 * own validation rules. The rules depend on whether the group
 * {@link #isRequired()}.<p>
 * 
 * <li>If required: all inputs must satisfy imposed criteria for
 *     the group to be deemed valid.
 *     
 * <li>Otherwise: either all inputs in this group are empty (have no values)
 *     for the group to be valid <b>OR</b> all inputs satisfy imposed criteria.
 *
 * @author armandino (at) gmail.com
 * @author michael.deardeuff (at) gmail.com
 */
public class GenericGroup extends AbstractInputCollection implements Group
{
    private String name;
    private boolean isRequired;
    
    /**
     * Create a new input group.
     */
    public GenericGroup(String name)
    {
        this.name = name;
    }
    
    public final String getName()
    {
        return name;
    }
    
    public final boolean isRequired()
    {
        return isRequired;
    }
    
    public boolean isEmpty()
    {
        for (InputControl<?> input : getInputs())
            if (!input.isBlank())
                return false;
        
        return true;
    }
    
    public final void setRequired(boolean isRequired)
    {
        this.isRequired = isRequired;
    }

    public boolean validate()
    {
        if(isRequired() || !isEmpty())
            validateInputs();
        
        additionalValidate();
        
        return isValid();
    }

    /**
     * Perform additional validation of form data where necessary.
     */
    protected void additionalValidate()
    {}
}
