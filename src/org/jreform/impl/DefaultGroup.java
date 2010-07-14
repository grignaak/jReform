package org.jreform.impl;

import javax.servlet.http.HttpServletRequest;

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
 */
public class DefaultGroup extends AbstractInputCollection implements Group
{
    private String name;
    private boolean isRequired;
    private boolean isEmpty = true;
    
    /**
     * Create a new input group.
     * @param name of the group.
     * @param isRequired if this group should be treated as valid when empty.
     */
    public DefaultGroup(String name, boolean isRequired)
    {
        this.name = name;
        this.isRequired = isRequired;
    }
    
    public final String getName()
    {
        return name;
    }
    
    public final boolean isRequired()
    {
        return isRequired;
    }
    
    public final boolean isEmpty()
    {
        return isEmpty;
    }
    
    public final void setRequired(boolean isRequired)
    {
        this.isRequired = isRequired;
    }
    
    public final boolean validateRequest(HttpServletRequest req)
    {
        processRequest(req);
        return validate();
    }

    public boolean validate()
    {
        if(isRequired || !isEmpty)
            validateInputs();
        
        setValid(getErrors().isEmpty());
        
        return isValid();
    }
    
    public void processRequest(HttpServletRequest req)
    {
        super.processRequest(req);
        isEmpty = !containsInputData();
    }

    private boolean containsInputData()
    {
        for (InputControl<?> input : getInputs())
        {
            if (!input.isBlank())
                return true;
        }
        
        return false;
    }
    
}
