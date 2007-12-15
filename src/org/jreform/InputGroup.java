package org.jreform;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

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
public class InputGroup extends AbstractInputCollection implements Group
{
    private AbstractForm parent;
    private String name;
    private boolean isRequired;
    private boolean isEmpty;
    
    /**
     * Create a new input group.
     * @param parent to which this group will belong.
     * @param name of the group.
     * @param isRequired if this group should be treated as valid when empty.
     */
    InputGroup(AbstractForm parent, String name, boolean isRequired)
    {
        this.parent = parent;
        this.name = name;
        this.isRequired = isRequired;
        isEmpty = true;
    }
    
    /**
     * Adds the input to this input group and its parent form.
     */
    public final <T> void add(InputControl<T> input)
    {
        ((AbstractInputControl<T>)input).setGroupInput(true);
        super.add(input);
        parent.add(input);
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
    
    final boolean validate(HttpServletRequest req)
    {
        isEmpty = !containsInputData(req);
        
        if(isRequired || !isEmpty)
        {
            validateInputs(req);
        }
        
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
            
            if(!input.validate(req))
                getErrors().add(input.getInputName());
        }
    }
    
    /*
     * Returns true if the request contains any data that belong
     * to this group's inputs. 
     */
    private boolean containsInputData(HttpServletRequest req)
    {
        boolean foundInputData = false;
        Iterator iter = getInputs().keySet().iterator();
        
        while(iter.hasNext() && !foundInputData)
        {
            InputControl input = getInputs().get(iter.next());
            
            if(input instanceof Input)
            {
                String value = req.getParameter(input.getInputName());
                
                if(value != null && !value.trim().equals(""))
                    foundInputData = true;
            }
            else
            {
                String[] values = req.getParameterValues(input.getInputName());
                
                if(values != null && values.length > 0)
                    foundInputData = true;
            }
        }
        
        return foundInputData;
    }
    
}
