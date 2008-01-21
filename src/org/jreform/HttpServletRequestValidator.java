package org.jreform;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author armandino (at) gmail.com
 */
public interface HttpServletRequestValidator
{
    /**
     * Validates the passed in {@link HttpServletRequest}.
     */
    public boolean validate(HttpServletRequest req);
    
    /**
     * Returns a set of error keys describing validation errors
     * or an empty set if the object has not been validated or
     * validation was successful.
     */
    public Set<String> getErrors();
    
}
