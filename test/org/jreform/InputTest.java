package org.jreform;

import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.range;

import org.jreform.impl.HtmlFormSupport;
import org.jreform.inputs.Input;

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
    
    protected HtmlFormSupport getForm()
    {
        return form;
    }
    
    /** Required input fails without a value */
    public void testRequiredFieldFailsWithoutAValue()
    {
        assertFalse(validateForm());
        
        assertEquals(form.requiredInt().getValueAttribute(), "");
        assertEquals(form.requiredString().getValueAttribute(), "");
        
        assertTrue(form.requiredInt().isBlank());
        assertTrue(form.requiredString().isBlank());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.requiredString().isValid());
        
        assertTrue(form.requiredInt().getOnError().startsWith("Missing value"));
        assertTrue(form.requiredString().getOnError().startsWith("Missing value"));
    }
    
    /** Input fails if value can't be converted to input's type */
    public void testFieldFailsIfGivenInvalidType()
    {
        setRequiredRequestParameters("Passing string instead of an int.", "some value");
        setOptionalRequestParameters("Passing string instead of an int.", "some value");
        
        // values blank prior to validate method
        assertTrue(form.requiredInt().isBlank());
        assertTrue(form.requiredString().isBlank());
        
        assertFalse(validateForm());
        
        assertFalse(form.requiredInt().isBlank());
        assertFalse(form.requiredString().isBlank());
        
        assertNull(form.requiredInt().getValue());
        assertNull(form.optionalInt().getValue());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.optionalInt().isValid());
        
        assertNotNull(form.requiredInt().getOnError());
        assertNotNull(form.optionalInt().getOnError());
        
        assertTrue(form.requiredInt().getOnError().startsWith("Invalid value"));
        assertTrue(form.optionalInt().getOnError().startsWith("Invalid value"));
    }
    
    /** Field fails if criteria are not satisfied */
    public void testFieldFailsIfCriteriaAreNotSatisfied()
    {
        String stringTooShort = "x";
        Integer numTooBig = 100;
        
        setRequiredRequestParameters(String.valueOf(numTooBig), "some input");
        setOptionalRequestParameters(String.valueOf(15), stringTooShort);
        
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
        
        setRequiredRequestParameters(String.valueOf(number), "some input");
        
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
        setRequiredRequestParameters("15", "some input");
        
        assertTrue(validateForm());
        
        assertNull(form.optionalInt().getValue());
        assertEquals(form.optionalInt().getValueAttribute(), "");
        
        assertEquals(form.optionalString().getValueAttribute(), "");
    }
    
    private void setRequiredRequestParameters(String intField, String stringField)
    {
        setParameter(REQ_INT, intField);
        setParameter(REQ_STRING, stringField);
    }

    private void setOptionalRequestParameters(String intField, String stringField)
    {
        setParameter(OPT_INT, intField);
        setParameter(OPT_STRING, stringField);
    }
    
    private static class TestForm extends HtmlFormSupport
    {
        @SuppressWarnings("unchecked")
        public TestForm()
        {
            // required and optional fields with criteria
            input(intType(), REQ_INT, range(MIN, MAX));
            input(stringType(), OPT_STRING, minLength(MIN_LENGTH)).optional();
            
            // required and optional fields without criteria
            input(stringType(), REQ_STRING);
            input(intType(), OPT_INT).optional();
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
