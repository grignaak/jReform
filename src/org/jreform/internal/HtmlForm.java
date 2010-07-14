package org.jreform.internal;

import java.util.HashMap;
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
public class HtmlForm extends AbstractInputCollection implements Form
{
    private final Map<String,Group> groups;
    
    public HtmlForm()
    {
        groups = new HashMap<String,Group>();
    }
    
    /**
     * Adds the passed in group to the form.
     * 
     * @throws DuplicateNameException if there is an existing group
     *         with the same name.
     */
    protected final void addGroup(Group group)
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
    
    public void processRequest(HttpServletRequest req)
    {
        super.processRequest(req);
        
        for (Group g : groups.values())
        {
            g.processRequest(req);
        }
    }
    
    public final boolean validateRequest(HttpServletRequest req)
    {
        processRequest(req);
        return validate();
    }

    public boolean validate()
    {
        validateInputs();
        validateGroups();
        additionalValidate();
        
        setValid(getErrors().isEmpty());
        return isValid();
    }

    private void validateGroups()
    {
        for (Group group : groups.values())
        {
            if (!group.validate())
                getErrors().addAll(group.getErrors());
        }
    }
    
}
