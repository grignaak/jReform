package org.jreform.criteria;

/**
 * Checks that value is not greater than the specified maximum.
 * 
 * @author armandino (at) gmail.com
 */
public final class MaxLength extends AbstractCriterion<String>
{
    private int maxLength;
    
    MaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }
    
    protected boolean verify(String value)
    {
        return value.length() <= maxLength;
    }
    
    protected String generateErrorMessage()
    {
        return "The value must not be greater than "+maxLength+" characters long";
    }
    
}
