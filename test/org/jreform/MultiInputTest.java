package org.jreform;

import static junit.framework.Assert.*;

import java.util.Arrays;

import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.range;

import org.jreform.criteria.Criteria;
import org.jreform.impl.GenericForm;
import org.jreform.inputs.BasicMultiInput;
import org.jreform.inputs.MultiCheckbox;
import org.jreform.inputs.MultiInput;
import org.jreform.inputs.MultiSelect;
import org.jreform.types.Types;
import org.junit.Test;

//
// test required and optional fields:
//    with criteria
//    without criteria
//

public class MultiInputTest extends BaseTestCase<MultiInputTest.TestForm>
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
    
    @Test(expected=Exception.class)
    public void shouldThrowWhenRetrievingInvalidValues()
    {
        MultiInput<Integer> input = new BasicMultiInput<Integer>(Types.intType(), "invalid");
        input.addCriterion(Criteria.max(4));
        
        input.setValues(Arrays.asList(5));
        input.validate();
        
        input.getValues();
    }
    
    /** Required input fails without a value */
    @Test
    public void testRequiredFieldFailsWithoutAValue()
    {
        assertFalse(validateForm());
        
        assertTrue(form.requiredInt().isBlank());
        assertTrue(form.requiredString().isBlank());
        
        assertTrue(form.requiredInt().getValueAttributes().isEmpty());
        assertTrue(form.requiredString().getValueAttributes().isEmpty());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.requiredString().isValid());
        
        assertContains("Invalid or missing value", form.requiredInt().getErrors());
        assertContains("Invalid or missing value", form.requiredString().getErrors());
    }
    
    /** Input fails if value can't be converted to input's type */
    @Test
    public void testFieldFailsIfGivenInvalidType()
    {
        setRequiredRequestParameters(
                new String[] {"Passing string instead of an int."},
                new String[] {"some value"});
        
        setOptionalRequestParameters(
                new String[] {"Passing string instead of an int."},
                new String[] {"some value"});
        
        
        assertTrue(form.requiredInt().isBlank());
        assertTrue(form.requiredString().isBlank());

        assertFalse(validateForm());
        
        assertFalse(form.requiredInt().isBlank());
        assertFalse(form.requiredString().isBlank());
        
        // TODO make sure these throw
        //assertTrue(form.requiredInt().getValues().isEmpty());
        //assertTrue(form.optionalInt().getValues().isEmpty());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.optionalInt().isValid());
        
        assertFalse(form.requiredInt().getErrors().isEmpty());
        assertNotNull(form.optionalInt().getErrors().isEmpty());
        
        assertContains("Cannot convert to a number", form.requiredInt().getErrors());
        assertContains("Cannot convert to a number", form.optionalInt().getErrors());
    }
    
    /** Field fails if criteria are not satisfied */
    @Test
    public void testFieldFailsIfCriteriaAreNotSatisfied()
    {
        String stringTooShort = "x";
        Integer numTooBig = 100;
        
        setRequiredRequestParameters(
                new String[] {String.valueOf(numTooBig)},
                new String[] {"some input"});
        
        setOptionalRequestParameters(
                new String[] {String.valueOf(15)},
                new String[] {stringTooShort});
        
        assertFalse("Criteria not satisfied", validateForm());
        
        assertEquals(stringTooShort, form.optionalString().getValueAttributes().get(0));
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.optionalString().isValid());
        assertTrue(form.optionalInt().isValid());
        assertTrue(form.requiredString().isValid());
        
        assertContains("The value must be between " + MIN + " and " + MAX,
                form.requiredInt().getErrors());
    }

    /** Required input passes with a valid value */
    @Test
    public void testRequiredFieldPassesWithValidValue()
    {
        int number = 15;
        
        setRequiredRequestParameters(
                new String[] {String.valueOf(number)},
                new String[] {"some input"});
        
        assertTrue(number >= MIN && number <= MAX);
        assertTrue(validateForm());
        
        assertTrue(form.requiredInt().isValid());
        
        int parsedInt = form.requiredInt().getValues().get(0);
        assertTrue(parsedInt == number);
        assertTrue(parsedInt >= MIN);
        assertTrue(parsedInt <= MAX);
        
        assertTrue(form.requiredString().isValid());
        assertTrue(form.requiredString().getValues().get(0).length() >= MIN_LENGTH);
    }
    
    /** Optional input passes without a value */
    @Test
    public void testOptionalFieldPassesWithoutAValue()
    {
        setRequiredRequestParameters(new String[] {"15"}, new String[] {"some input"});
        
        assertTrue(validateForm());
        
        assertTrue(form.optionalInt().getValues().isEmpty());
        assertTrue(form.optionalInt().getValueAttributes().isEmpty());
        
        assertTrue(form.optionalString().getValues().isEmpty());
        assertTrue(form.optionalString().getValueAttributes().isEmpty());
    }
    
    private void setRequiredRequestParameters(String[] intField, String[] stringField)
    {
        setParameters(REQ_INT, intField);
        setParameters(REQ_STRING, stringField);
    }

    private void setOptionalRequestParameters(String[] intField, String[] stringField)
    {
        setParameters(OPT_INT, intField);
        setParameters(OPT_STRING, stringField);
    }
    
    static class TestForm extends GenericForm
    {
        public TestForm()
        {
            // required and optional fields with criteria
            multiSelect(Types.intType(), REQ_INT).criterion(range(MIN, MAX));
            multiCheckbox(Types.stringType(), OPT_STRING).criterion(minLength(MIN_LENGTH)).optional();
            
            // required and optional fields without criteria
            multiCheckbox(Types.stringType(), REQ_STRING);
            multiSelect(Types.intType(), OPT_INT).optional();
        }
        
        public MultiSelect<Integer> requiredInt()
        {
            return getMultiSelect(REQ_INT);
        }
        
        public MultiSelect<Integer> optionalInt()
        {
            return getMultiSelect(OPT_INT);
        }
        
        public MultiCheckbox<String> requiredString()
        {
            return getMultiCheckbox(REQ_STRING);
        }
        
        public MultiCheckbox<String> optionalString()
        {
            return getMultiCheckbox(OPT_STRING);
        }
    }
    
}
