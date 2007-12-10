package test.org.jreform;

import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.range;

import org.jreform.HtmlForm;
import org.jreform.MultiInput;

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
    
    protected HtmlForm getForm()
    {
        return form;
    }
    
    /** Required input fails without a value */
    public void testRequiredFieldFailsWithoutAValue()
    {
        assertFalse(validateForm());
        
        assertTrue(form.requiredInt().getValueAttributes().length == 0);
        assertTrue(form.requiredString().getValueAttributes().length == 0);
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.requiredString().isValid());
        
        assertTrue(form.requiredInt().getOnError().startsWith("Missing required"));
        assertTrue(form.requiredString().getOnError().startsWith("Missing required"));
    }
    
    /** Input fails if value can't be converted to input's type */
    public void testFieldFailsIfGivenInvalidType()
    {
        setRequiredFields(
                new String[] {"Passing string instead of an int."},
                new String[] {"some value"});
        
        setOptionalFields(
                new String[] {"Passing string instead of an int."},
                new String[] {"some value"});
        
        
        assertFalse(validateForm());
        
        assertTrue(form.requiredInt().getValues().isEmpty());
        assertTrue(form.optionalInt().getValues().isEmpty());
        
        assertFalse(form.requiredInt().isValid());
        assertFalse(form.optionalInt().isValid());
    }
    
    /** Field fails if criteria are not satisfied */
    public void testFieldFailsIfCriteriaAreNotSatisfied()
    {
        String stringTooShort = "x";
        Integer numTooBig = 100;
        
        setRequiredFields(
                new String[] {String.valueOf(numTooBig)},
                new String[] {"some input"});
        
        setOptionalFields(
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
        
        setRequiredFields(
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
        setRequiredFields(new String[] {"15"}, new String[] {"some input"});
        
        assertTrue(validateForm());
        
        assertTrue(form.optionalInt().getValues().isEmpty());
        assertTrue(form.optionalInt().getValueAttributes().length == 0);
        
        assertTrue(form.optionalString().getValues().isEmpty());
        assertTrue(form.optionalString().getValueAttributes().length == 0);
    }
    
    private void setRequiredFields(String[] intField, String[] stringField)
    {
        setParameters(REQ_INT, intField);
        setParameters(REQ_STRING, stringField);
    }

    private void setOptionalFields(String[] intField, String[] stringField)
    {
        setParameters(OPT_INT, intField);
        setParameters(OPT_STRING, stringField);
    }
    
    private class TestForm extends HtmlForm
    {
        public TestForm()
        {
            // required and optional fields with criteria
            addMulti(intType(), REQ_INT, range(MIN, MAX));
            addMulti(stringType(), OPT_STRING, minLength(MIN_LENGTH)).optional();
            
            // required and optional fields without criteria
            addMulti(stringType(), REQ_STRING);
            addMulti(intType(), OPT_INT).optional();
        }
        
        public MultiInput<Integer> requiredInt()
        {
            return getMultiInput(REQ_INT);
        }
        
        public MultiInput<Integer> optionalInt()
        {
            return getMultiInput(OPT_INT);
        }
        
        public MultiInput<String> requiredString()
        {
            return getMultiInput(REQ_STRING);
        }
        
        public MultiInput<String> optionalString()
        {
            return getMultiInput(OPT_STRING);
        }
    }
    
}
