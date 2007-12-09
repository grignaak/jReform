package org.jreform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



/**
 * A form contains a set of own inputs, errors and input groups.
 * It also references inputs of its child groups.
 * Input names and input group names must be unique within a form.
 * 
 * @author armandino (at) gmail.com
 */
abstract class AbstractForm extends AbstractInputCollection implements Form
{
    private final Map<String,Group> groups;
    
    AbstractForm()
    {
        groups = new HashMap<String,Group>();
    }
    
    /**
     * Adds the passed in group to the form.
     * 
     * @throws DuplicateNameException if there is an existing group
     *         with the same name.
     */
    final void addGroup(Group group)
    {
        String name = group.getName();
        
        if(groups.containsKey(name))
            throw new DuplicateNameException(
                "Duplicate group name within the same form: " + name);
        
        groups.put(name, group);
    }
    
    public final InputGroup getGroup(String name)
    {
        return (InputGroup)groups.get(name);
    }
    
    public final boolean validate(HttpServletRequest req)
    {
        validateInputs(req);
        
        Iterator iter = groups.keySet().iterator();
        AbstractInputCollection group;
        
        // validate input groups
        while(iter.hasNext())
        {
            group = (AbstractInputCollection)groups.get(iter.next());
            
            if(!group.validate(req))
                getErrors().addAll(group.getErrors());
        }
        
        additionalValidate();
        
        setValid(getErrors().isEmpty());
        
        return isValid();
    }
    
    final void validateInputs(HttpServletRequest req)
    {
        Iterator iter = getInputs().keySet().iterator();
        AbstractInputControl input;
        
        while(iter.hasNext())
        {
            input = (AbstractInputControl)getInputs().get(iter.next());
            
            // an input that belongs to a group is validated by its parent group
            if(!input.isGroupInput() && !input.validate(req))
                getErrors().add(input.getInputName());
        }
    }
    
}
