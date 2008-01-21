package org.jreform.criteria;

import java.util.regex.Pattern;

/**
 * Checks if a string matches a regular expression.
 * 
 * @author armandino (at) gmail.com
 */
public class Regex extends AbstractCriterion<String>
{
    private Pattern pattern;
    
    Regex(String pattern)
    {
        this.pattern = Pattern.compile(pattern);
    }
    
    protected boolean verify(String value)
    {
        return pattern.matcher(value).find();
    }
    
    protected String generateErrorMessage()
    {
        return "The value must match the required format";
    }
    
}
