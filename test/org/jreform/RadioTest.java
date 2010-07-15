package org.jreform;

import static org.jreform.criteria.Criteria.accept;
import static org.jreform.criteria.Criteria.acceptString;

import org.jreform.impl.HtmlFormSupport;
import org.jreform.inputs.Radio;

public class RadioTest extends BaseTestCase
{
    private static final String SUBSCRIBE = "subscribe";
    private static final String GENDER = "gender";
    
    private static final String SUBSCRIBE_ERROR_MSG = "Must be yes or no";
    private static final String GENDER_ERROR_MSG = "M or F expected";
    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected HtmlFormSupport getForm()
    {
        return form;
    }
    
    /** validation fails if required radio button is not checked */
    public void testValidationFailsIfRequiredRadioNotChecked()
    {
        assertFalse(validateForm());
        
        assertTrue(form.subscribe().isRequired());
        assertFalse(form.subscribe().isValid());
        
        assertTrue(form.subscribe().getValueAttribute().equals(""));
        
        assertEquals(form.subscribe().getOnError(), SUBSCRIBE_ERROR_MSG);
    }
    
    /** validation fails if criteria fail for required radio button */
    public void testValidationFailsIfRequiredRadioCriteriaFail()
    {
        final String value = "need_yes_or_no_to_validate_successfully";
        setParameter(SUBSCRIBE, value);
        
        assertFalse(validateForm());
        assertFalse(form.subscribe().isValid());
        
        assertEquals(form.subscribe().getValue(), value);
        assertEquals(form.subscribe().getOnError(), SUBSCRIBE_ERROR_MSG);
    }
    
    /** validation passes if required radio button has a valid value */
    public void testValidationPassesIfRequiredRadioButtonHasValidValue()
    {
        final String yes = "YES"; // ignoreCase() enabled
        
        setParameter(SUBSCRIBE, yes);
        
        assertTrue(validateForm());
        assertTrue(form.subscribe().isValid());
        assertTrue(form.subscribe().getOnError().equals(""));
        
        assertEquals(form.subscribe().getValue(), yes);
        assertEquals(yes, form.subscribe().getValue());
    }
    
    /** validation fails if optional radio button has invalid value */
    public void testValidationFailsIfOptionalRadioCriteriaFail()
    {
        char invalidValue = 'm'; // criterion is case sensitive
        
        setParameter(SUBSCRIBE, "no"); // set required radio
        setParameter(GENDER, String.valueOf(invalidValue));
        
        assertFalse(validateForm());
        
        assertTrue(form.subscribe().isValid());
        
        assertFalse(form.gender().isValid());
        assertFalse(form.gender().isRequired());
        
        assertEquals(form.gender().getValue(), invalidValue);
        assertEquals(form.gender().getOnError(), GENDER_ERROR_MSG);
    }
    
    /** validation passes when optional and required radios have valid values */
    public void testValidationPassesIfOptionalRadioHasValidValue()
    {
        final String no = "no";
        final char validValue = 'F';
        
        setParameter(SUBSCRIBE, no); // set required radio
        setParameter(GENDER, String.valueOf(validValue));
        
        assertTrue(validateForm());
        
        assertTrue(form.subscribe().isValid());
        assertTrue(form.gender().isValid());
        assertTrue(form.gender().getOnError().equals(""));
        
        assertEquals(form.gender().getValue(), validValue);
        
        assertEquals(no, form.subscribe().getValue());
        assertEquals(validValue, form.gender().getValue());
    }
    
    public void testSetValueAttributeSetsState()
    {
        form.gender().setValueAttribute("X");
        assertEquals('X', form.gender().getValue());
    }

    
    private static class TestForm extends HtmlFormSupport
    {
        @SuppressWarnings("unchecked")
        public TestForm()
        {
            // required
            radio(stringType(), SUBSCRIBE, acceptString("yes", "no").ignoreCase())
                    .onError(SUBSCRIBE_ERROR_MSG);
            
            // optional
            radio(charType(), GENDER, accept('M', 'F')).optional()
                    .onError(GENDER_ERROR_MSG);
        }
        
        public Radio<String> subscribe()
        {
            return getRadio(SUBSCRIBE);
        }
        
        public Radio<String> gender()
        {
            return getRadio(GENDER);
        }
    }
    
}
