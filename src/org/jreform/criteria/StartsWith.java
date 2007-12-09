package org.jreform.criteria;

/**
 * Checks if value starts with the given string.
 * 
 * @author armandino (at) gmail.com
 */
public final class StartsWith extends AbstractCriterion<String>
{
    private String[] prefixes;
    
    StartsWith(String...prefixes)
    {
        this.prefixes = prefixes;
    }
    
    protected boolean verify(String value)
    {
        for(String prefix : prefixes)
        {
            if(value.startsWith(prefix))
                return true;
        }
        
        return false;
    }
    
    protected String generateErrorMessage()
    {
        return "Please enter a valid value";
    }
    
}
