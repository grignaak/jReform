package org.jreform;

import static junit.framework.Assert.*;

import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.range;

import org.jreform.impl.GenericForm;
import org.jreform.inputs.Input;
import org.jreform.types.Types;
import org.junit.Test;

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
    
    protected GenericForm getForm()
    {
        return form;
    }
    
    /** Required input fails without a value */
    @Test
    public void testRequiredFieldFailsWithoutAValue()
    {
        assertFalse(validateForm());
        
        assertEquals(form.requiredInt().getValueAttribute(), "");
        assertEquals(form.requiredString().getValueAttribute(), "");
        
        assertTrue(form.requiredInt().isBlank());
        assertTrue(form.requiredString().isBlank());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.requiredString().isValid());
        
        assertTrue(form.requiredInt().getErrors().contains("Missing value"));
    }
    
    @Test
    public void testParseErrorsAreCaptured()
    {
        setRequiredRequestParameters("not an int", "a string");
        setOptionalRequestParameters("also not an int", "a string");
        assertFalse(validateForm());
        assertContains("Cannot convert to a number", form.requiredInt().getErrors());
        assertContains("Cannot convert to a number", form.optionalInt().getErrors());
    }
    
    /** Input fails if value can't be converted to input's type */
    @Test
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
        
        assertFalse(form.requiredInt().getErrors().isEmpty());
        assertFalse(form.optionalInt().getErrors().isEmpty());
        
        assertContains("Cannot convert to a number", form.requiredInt().getErrors());
        assertContains("Cannot convert to a number", form.optionalInt().getErrors());
    }
    
    /** Field fails if criteria are not satisfied */
    @Test
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
        
        assertTrue(form.requiredInt().getErrors().contains(
                "The value must be between " + MIN + " and " + MAX));
    }

    /** Required input passes with a valid value */
    @Test
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
        
        assertTrue(form.requiredInt().getErrors().isEmpty());
        assertTrue(form.optionalInt().getErrors().isEmpty());
    }
    
    /** Optional input passes without a value */
    @Test
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
    
    private static class TestForm extends GenericForm
    {
        public TestForm()
        {
            // required and optional fields with criteria
            input(Types.intType(), REQ_INT).criterion(range(MIN, MAX));
            input(Types.stringType(), OPT_STRING).criterion(minLength(MIN_LENGTH)).optional();
            
            // required and optional fields without criteria
            input(Types.stringType(), REQ_STRING);
            input(Types.intType(), OPT_INT).optional();
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
