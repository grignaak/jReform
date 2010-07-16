package org.jreform;

import javax.servlet.http.HttpServletRequest;

/**
 * A form containing inputs and optionally input groups.
 * 
 * @author armandino (at) gmail.com
 */
public interface Form extends InputCollection
{
    /**
     * Fills in the input using the passed in {@link HttpServletRequest} and then validates the form.
     * 
     * <p>This is exactly equivelent to
     * <pre>
     * this.processRequest(req);
     * return this.validate();
     * </pre>
     * </p>
     * 
     * @see #processRequest(HttpServletRequest)
     * @see #validate()
     */
    public boolean validateRequest(HttpServletRequest req);
    
    /**
     * Returns a group with the specified name.
     */
    public Group getGroup(String name);
    
}
