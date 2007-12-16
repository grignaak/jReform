package org.jreform.internal;

import org.jreform.Group;

/**
 * @author armandino (at) gmail.com
 */
public class BaseHtmlForm extends AbstractForm
{
    protected BaseHtmlForm() {}
    
    protected final GroupImpl requiredGroup(String name)
    {
        return addNewGroup(name, true);
    }
    
    protected final GroupImpl optionalGroup(String name)
    {
        return addNewGroup(name, false);
    }
    
    private GroupImpl addNewGroup(String name, boolean isRequired)
    {
        Group group = new GroupImpl(this, name, isRequired);
        addGroup(group);
        return (GroupImpl)group;
    }
    
}
