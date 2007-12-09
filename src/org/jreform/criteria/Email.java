package org.jreform.criteria;

/**
 * Checks that the value conforms to the email address format.
 * 
 * @author armandino (at) gmail.com
 */
public final class Email extends Regex
{
    private static final String REGEX = "\\p{Alnum}+@\\p{Alnum}+(\\.[A-Za-z]+)*";
    
    Email()
    {
        super(REGEX);
    }
    
    protected String generateErrorMessage()
    {
        return "Please enter a valid email address";
    }
    
}
