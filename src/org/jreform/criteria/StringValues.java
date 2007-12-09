package org.jreform.criteria;

import org.jreform.core.Criterion;

/**
 * Checks if string is equal to one of the passed in strings.
 * 
 * @author armandino (at) gmail.com
 */
public final class StringValues extends Values<String>
{
    private boolean caseSensitive = false;
    
    StringValues(String...values)
    {
        super(values);
    }
    
    protected boolean areEqual(String v1, String v2)
    {
        if(caseSensitive)
            return super.areEqual(v1, v2);
        
        return v1.equalsIgnoreCase(v2);
    }
    
    /**
     * Specifies string comparison to be case-sensitive. 
     */
    public Criterion<String> caseSensitive()
    {
        caseSensitive = true;
        return this;
    }
    
}
