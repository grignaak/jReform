package web.examples;

import static org.jreform.criteria.Criteria.accept;
import static org.jreform.criteria.Criteria.length;
import static org.jreform.criteria.Criteria.range;
import static org.jreform.criteria.Criteria.regex;

import org.jreform.impl.GenericForm;
import org.jreform.inputs.Input;
import org.jreform.types.Types;

/**
 * User profile form.
 */
public class UserProfileForm extends GenericForm
{
	// Accepted format: 123-456-7890
    private static final String PHONE_FORMAT = "^\\d{3}-\\d{3}-\\d{4}$";
    
    public UserProfileForm()
    {
        // A text input that must be between 5 and 32 characters long
        input(Types.stringType(), "fullName").criterion(length(5, 32));
        
        // An integer field with a value between 18 and 99 inclusive
        input(Types.intType(), "age").criterion(range(18, 99));
        
        // A character field that accepts 'M' or 'F' only
        input(Types.charType(), "gender").criterion(accept('M', 'F'));
        
        // Optional phnoe number field that must match the specified regex
        input(Types.stringType(), "phone").criterion(regex(PHONE_FORMAT)).optional();
    }
    
    /*
     * Convenience methods to get input values.
     */
    
    public Input<String> getFullName()
    {
        return getInput("fullName");
    }
    
    public Input<Integer> getAge()
    {
        return getInput("age");
    }
    
    public Input<Character> getGender()
    {
        return getInput("gender");
    }
    
    public Input<String> getPhone()
    {
        return getInput("phone");
    }
    
}
