package org.jreform.criteria;

/**
 * Checks that the length of the value is equal to the given length. 
 * 
 * @author armandino (at) gmail.com
 */
public final class ExactLength<T extends CharSequence> extends AbstractCriterion<T>
{
    private int length;
    
    ExactLength(int length)
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
