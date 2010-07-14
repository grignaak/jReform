package org.jreform;

import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.range;

import org.jreform.impl.HtmlFormSupport;
import org.jreform.inputs.MultiCheckbox;
import org.jreform.inputs.MultiSelect;

//
// test required and optional fields:
//    with criteria
//    without criteria
//

public class MultiInputTest extends BaseTestCase
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
        
        assertTrue(form.requiredInt().isBlank());
        assertTrue(form.requiredString().isBlank());
        
        assertTrue(form.requiredInt().getValueAttributes().length == 0);
        assertTrue(form.requiredString().getValueAttributes().length == 0);
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.requiredString().isValid());
        
        assertTrue(form.requiredInt().getOnError().startsWith("Invalid or missing value"));
        assertTrue(form.requiredString().getOnError().startsWith("Invalid or missing value"));
    }
    
    /** Input fails if value can't be converted to input's type */
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
        
        assertTrue(form.requiredInt().getValues().isEmpty());
        assertTrue(form.optionalInt().getValues().isEmpty());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.optionalInt().isValid());
        
        assertNotNull(form.requiredInt().getOnError());
        assertNotNull(form.optionalInt().getOnError());
        
        assertTrue(form.requiredInt().getOnError().startsWith("Invalid or missing value"));
        assertTrue(form.optionalInt().getOnError().startsWith("Invalid or missing value"));
    }
    
    /** Field fails if criteria are not satisfied */
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
        
        assertEquals(stringTooShort, form.optionalString().getValueAttributes()[0]);
        assertEquals(numTooBig, form.requiredInt().getValues().get(0));
        
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
    public void testOptionalFieldPassesWithoutAValue()
    {
        setRequiredRequestParameters(new String[] {"15"}, new String[] {"some input"});
        
        assertTrue(validateForm());
        
        assertTrue(form.optionalInt().getValues().isEmpty());
        assertTrue(form.optionalInt().getValueAttributes().length == 0);
        
        assertTrue(form.optionalString().getValues().isEmpty());
        assertTrue(form.optionalString().getValueAttributes().length == 0);
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
    
    private static class TestForm extends HtmlFormSupport
    {
        @SuppressWarnings("unchecked")
        public TestForm()
        {
            // required and optional fields with criteria
            multiSelect(intType(), REQ_INT, range(MIN, MAX));
            multiCheckbox(stringType(), OPT_STRING, minLength(MIN_LENGTH)).optional();
            
            // required and optional fields without criteria
            multiCheckbox(stringType(), REQ_STRING);
            multiSelect(intType(), OPT_INT).optional();
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
