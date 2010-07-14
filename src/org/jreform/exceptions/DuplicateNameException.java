package org.jreform.exceptions;

/**
 * Thrown when a group or an input with duplicate name is added to a form.
 * 
 * @author armandino (at) gmail.com
 */
public class DuplicateNameException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public DuplicateNameException(String name)
    {
        super("Duplicate input name within the same form: " + name);
    }
    
}
