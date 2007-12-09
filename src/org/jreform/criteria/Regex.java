package org.jreform.criteria;

import java.util.regex.Pattern;

/**
 * Checks if the regular expression matches the given value.
 * 
 * @author armandino (at) gmail.com
 */
public class Regex extends AbstractCriterion<String>
{
    private String pattern;
    
    Regex(String pattern)
    {
        this.pattern = pattern;
    }
    
    protected boolean verify(String value)
    {
        return Pattern.compile(pattern).matcher(value).find();
    }
    
    protected String generateErrorMessage()
    {
        return "The value must match the required format";
    }
    
}
