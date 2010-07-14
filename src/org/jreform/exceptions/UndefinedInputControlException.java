package org.jreform.exceptions;

/**
 * @author armandino (at) gmail.com
 */
public class UndefinedInputControlException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public UndefinedInputControlException(String s)
    {
        super("Undefined input control:" + s);
    }
}