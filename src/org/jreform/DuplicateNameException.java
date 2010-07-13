package org.jreform;

/**
 * Thrown when a group or an input with duplicate name is added to a form.
 * 
 * @author armandino (at) gmail.com
 */
public class DuplicateNameException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public DuplicateNameException(String msg)
    {
        super(msg);
    }
    
}
