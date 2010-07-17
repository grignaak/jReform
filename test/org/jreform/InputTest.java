package org.jreform;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.jreform.criteria.Criteria.max;
import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.range;
import static org.jreform.types.Types.intType;
import static org.jreform.types.Types.stringType;

import org.jreform.impl.GenericForm;
import org.jreform.inputs.BasicInput;
import org.jreform.inputs.Input;
import org.jreform.types.Types;
import org.junit.Test;

//
// test required and optional fields:
//    with criteria
//    without criteria
//

public class InputTest extends BaseTestCase<InputTest.TestForm>
{
    private static final int MIN = 10;
    private static final int MAX = 20;
    
    private static final int MIN_LENGTH = 3;
    
    private static final String REQ_INT = "required_int_field";
    private static final String OPT_INT = "optional_int_field";
    
    private static final String REQ_STRING = "required_string_field";
    private static final String OPT_STRING = "optional_string_field";

    protected TestForm createForm()
    {
        return new TestForm();
    }
    
    @Test
    public void shouldSucceedWhenDirectlySettingValue()
    {
        Input<String> input = createAValidInputTypeByDirectlyInsertingTheValue();
        assertTrue(input.validate());
    }

    private Input<String> createAValidInputTypeByDirectlyInsertingTheValue()
    {
        Input<String> input = new BasicInput<String>(stringType(), "string type");
        input.setValue("a value");
        return input;
    }
    
    @Test
    public void shouldGetTheSameValueBackWhenDiretlySettingAValue()
    {
        Input<String> input = createAValidInputTypeByDirectlyInsertingTheValue();
        input.validate();
        assertEquals("a value", input.getValue());
    }

    
    @Test
    public void shouldFailWhenDirectlySettingANullValue()
    {
        Input<String> input = createInputWithDirectNullInserted();
        assertFalse(input.validate());
    }

    private Input<String> createInputWithDirectNullInserted()
    {
        Input<String> input = new BasicInput<String>(stringType(), "gets a null");
        input.setValue(null);
        return input;
    }
    
    @Test(expected=Exception.class)
    public void shouldThrowWhenAttemptingToRetrieveNullValue()
    {
        Input<String> input = createInputWithDirectNullInserted();
        input.validate();
        input.getValue();
    }
    
    @Test
    public void shouldFailWhenSettingAnInvalidValue()
    {
        Input<Integer> input = createInvalidInputThroughDirectlySettingValue();
        
        assertFalse("input out of range", input.validate());
    }

    private Input<Integer> createInvalidInputThroughDirectlySettingValue()
    {
        Input<Integer> input = new BasicInput<Integer>(intType(), "int type");
        input.addCriterion(max(4));

        input.setValue(5);
        return input;
    }
    
    @Test(expected=Exception.class)
    public void shouldThrowWhenAttemptingToRetrieveInvalidValue()
    {
        Input<Integer> input = createInvalidInputThroughDirectlySettingValue();
        input.validate();
        System.out.println(input.getValue());
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
    
    static class TestForm extends GenericForm
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
