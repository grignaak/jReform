package org.jreform.criteria;

/**
 * Checks that value is not less than the specified minimum.
 * 
 * @author armandino (at) gmail.com
 */
public final class MinLength extends AbstractCriterion<String>
{
    private int minLength;
    
    MinLength(int minLength)
    {
        this.minLength = minLength;
    }
    
    protected boolean verify(String value)
    {
        return value.length() >= minLength;
    }
    
    protected String generateErrorMessage()
    {
        return "The value must not be less than "+minLength+" characters long";
    }
    
}
