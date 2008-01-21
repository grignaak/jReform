package org.jreform;

/**
 * Thrown when a group or an input with duplicate name is added to a form.
 * 
 * @author armandino (at) gmail.com
 */
public class DuplicateNameException extends RuntimeException
{
    public DuplicateNameException(String msg)
    {
        super(msg);
    }
    
}
