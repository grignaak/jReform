package org.jreform.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jreform.DuplicateNameException;
import org.jreform.Form;
import org.jreform.Group;

/**
 * A form that contains a set of its own inputs, errors and input groups.
 * It also contains references to inputs of its child groups.
 * Input names and input group names must be unique within a form.
 * 
 * @author armandino (at) gmail.com
 */
public class AbstractForm extends AbstractInputCollection implements Form
{
    private final Map<String,Group> groups;
    
    public AbstractForm()
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
    
    public final Group getGroup(String name)
    {
        return groups.get(name);
    }
    
    public final boolean validate(HttpServletRequest req)
    {
        validateInputs(req);
        
        Iterator<String> groupIter = groups.keySet().iterator();
        Group group;
        
        // validate input groups
        while(groupIter.hasNext())
        {
            group = groups.get(groupIter.next());
            
            if(!group.validate(req))
                getErrors().addAll(group.getErrors());
        }
        
        additionalValidate();
        
        setValid(getErrors().isEmpty());
        
        return isValid();
    }
    
    final void validateInputs(HttpServletRequest req)
    {
        Iterator<String> iter = getInputs().keySet().iterator();
        AbstractInputControl<?> input;
        
        while(iter.hasNext())
        {
            input = (AbstractInputControl<?>)getInputs().get(iter.next());
            
            // an input that belongs to a group is validated by its parent group
            if(!input.isGroupInput() && !input.validate(req))
                getErrors().add(input.getInputName());
        }
    }

    protected final Group requiredGroup(String name)
    {
        return addNewGroup(name, true);
    }

    protected final Group optionalGroup(String name)
    {
        return addNewGroup(name, false);
    }

    private Group addNewGroup(String name, boolean isRequired)
    {
        Group group = new GroupImpl(this, name, isRequired);
        addGroup(group);
        return group;
    }
    
}
