package test.org.jreform;

import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.range;

import org.jreform.HtmlForm;
import org.jreform.Input;

//
// test required and optional fields:
//    with criteria
//    without criteria
//

public class InputTest extends BaseTestCase
{
    private static final int MIN = 10;
    private static final int MAX = 20;
    
    private static final int MIN_LENGTH = 3;
    
    private static final String REQ_INT = "required_int_field";
    private static final String OPT_INT = "optional_int_field";
    
    private static final String REQ_STRING = "required_string_field";
    private static final String OPT_STRING = "optional_string_field";

    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected HtmlForm getForm()
    {
        return form;
    }
    
    /** Required input fails without a value */
    public void testRequiredFieldFailsWithoutAValue()
    {
        assertFalse(validateForm());
        
        assertNull(form.requiredInt().getValueAttribute());
        assertNull(form.requiredString().getValueAttribute());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.requiredString().isValid());
        
        assertTrue(form.requiredInt().getOnError().startsWith("Missing required"));
        assertTrue(form.requiredString().getOnError().startsWith("Missing required"));
    }
    
    /** Input fails if value can't be converted to input's type */
    public void testFieldFailsIfGivenInvalidType()
    {
        setRequiredFields("Passing string instead of an int.", "some value");
        setOptionalFields("Passing string instead of an int.", "some value");
        
        assertFalse(validateForm());
        
        assertNull(form.requiredInt().getValue());
        assertNull(form.optionalInt().getValue());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.optionalInt().isValid());
    }
    
    /** Field fails if criteria are not satisfied */
    public void testFieldFailsIfCriteriaAreNotSatisfied()
    {
        String stringTooShort = "x";
        Integer numTooBig = 100;
        
        setRequiredFields(String.valueOf(numTooBig), "some input");
        setOptionalFields(String.valueOf(15), stringTooShort);
        
        assertFalse("Criteria not satisfied", validateForm());
        
        assertEquals(stringTooShort, form.optionalString().getValueAttribute());
        assertEquals(numTooBig, form.requiredInt().getValue());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.optionalString().isValid());
        assertTrue(form.optionalInt().isValid());
        assertTrue(form.requiredString().isValid());
        
        assertTrue(form.requiredInt().getOnError().equals(
                "The value must be between " + MIN + " and " + MAX));
    }

    /** Required input passes with a valid value */
    public void testRequiredFieldPassesWithValidValue()
    {
        int number = 15;
        
        setRequiredFields(String.valueOf(number), "some input");
        
        assertTrue(number >= MIN && number <= MAX);
        assertTrue(validateForm());
        
        assertTrue(form.requiredInt().isValid());
        assertTrue(form.requiredInt().getValue() == number);
        assertTrue(form.requiredInt().getValue() >= MIN);
        assertTrue(form.requiredInt().getValue() <= MAX);
        
        assertTrue(form.requiredString().isValid());
        assertTrue(form.requiredString().getValue().length() >= MIN_LENGTH);
    }
    
    /** Optional input passes without a value */
    public void testOptionalFieldPassesWithoutAValue()
    {
        setRequiredFields("15", "some input");
        
        assertTrue(validateForm());
        
        assertNull(form.optionalInt().getValue());
        assertNull(form.optionalInt().getValueAttribute());
        
        assertNull(form.optionalString().getValue());
        assertNull(form.optionalString().getValueAttribute());
    }
    
    private void setRequiredFields(String intField, String stringField)
    {
        setParameter(REQ_INT, intField);
        setParameter(REQ_STRING, stringField);
    }

    private void setOptionalFields(String intField, String stringField)
    {
        setParameter(OPT_INT, intField);
        setParameter(OPT_STRING, stringField);
    }
    
    private class TestForm extends HtmlForm
    {
        public TestForm()
        {
            // required and optional fields with criteria
            add(intType(), REQ_INT, range(MIN, MAX));
            add(stringType(), OPT_STRING, minLength(MIN_LENGTH)).optional();
            
            // required and optional fields without criteria
            add(stringType(), REQ_STRING);
            add(intType(), OPT_INT).optional();
        }
        
        public Input<Integer> requiredInt()
        {
            return getInput(REQ_INT);
        }
        
        public Input<Integer> optionalInt()
        {
            return getInput(OPT_INT);
        }
        
        public Input<String> requiredString()
        {
            return getInput(REQ_STRING);
        }
        
        public Input<String> optionalString()
        {
            return getInput(OPT_STRING);
        }
    }
    
}
