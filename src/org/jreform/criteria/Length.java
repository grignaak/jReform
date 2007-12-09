package org.jreform.criteria;

/**
 * Checks that the length of the value equals the given length. 
 * 
 * @author armandino (at) gmail.com
 */
public final class Length<T extends CharSequence> extends AbstractCriterion<T>
{
    private int length;
    
    Length(int length)
    {
        this.length = length;
    }
    
    protected boolean verify (T value)
    {
        return value.length() == length;
    }
    
    protected String generateErrorMessage()
    {
        return "The value must be " + length + " characters long";
    }
    
}
