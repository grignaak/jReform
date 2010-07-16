package org.jreform;

import static junit.framework.Assert.*;

import static org.jreform.criteria.Criteria.accept;
import static org.jreform.criteria.Criteria.acceptString;

import org.jreform.impl.GenericForm;
import org.jreform.inputs.Radio;
import org.jreform.types.Types;
import org.junit.Test;

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
    
    protected Form getForm()
    {
        return form;
    }
    
    /** validation fails if required radio button is not checked */
    @Test
    public void testValidationFailsIfRequiredRadioNotChecked()
    {
        assertFalse(validateForm());
        
        assertTrue(form.subscribe().isRequired());
        assertFalse(form.subscribe().isValid());
        
        assertTrue(form.subscribe().getValueAttribute().equals(""));
        
        assertContains(SUBSCRIBE_ERROR_MSG, form.subscribe().getErrors());
    }
    
    /** validation fails if criteria fail for required radio button */
    @Test
    public void testValidationFailsIfRequiredRadioCriteriaFail()
    {
        final String value = "need_yes_or_no_to_validate_successfully";
        setParameter(SUBSCRIBE, value);
        
        assertFalse(validateForm());
        assertFalse(form.subscribe().isValid());
        
        assertEquals(form.subscribe().getValue(), value);
        assertContains(SUBSCRIBE_ERROR_MSG, form.subscribe().getErrors());
    }
    
    /** validation passes if required radio button has a valid value */
    @Test
    public void testValidationPassesIfRequiredRadioButtonHasValidValue()
    {
        final String yes = "YES"; // ignoreCase() enabled
        
        setParameter(SUBSCRIBE, yes);
        
        assertTrue(validateForm());
        assertTrue(form.subscribe().isValid());
        assertTrue(form.subscribe().getErrors().isEmpty());
        
        assertEquals(form.subscribe().getValue(), yes);
        assertEquals(yes, form.subscribe().getValue());
    }
    
    /** validation fails if optional radio button has invalid value */
    @Test
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
        assertContains(GENDER_ERROR_MSG, form.gender().getErrors());
    }
    
    /** validation passes when optional and required radios have valid values */
    @Test
    public void testValidationPassesIfOptionalRadioHasValidValue()
    {
        final String no = "no";
        final char validValue = 'F';
        
        setParameter(SUBSCRIBE, no); // set required radio
        setParameter(GENDER, String.valueOf(validValue));
        
        assertTrue(validateForm());
        
        assertTrue(form.subscribe().isValid());
        assertTrue(form.gender().isValid());
        assertTrue(form.gender().getErrors().isEmpty());
        
        assertEquals(form.gender().getValue(), validValue);
        
        assertEquals(no, form.subscribe().getValue());
        assertEquals(validValue, form.gender().getValue());
    }
    
    @Test
    public void testSetValueAttributeSetsState()
    {
        form.gender().setValueAttribute("X");
        assertEquals('X', form.gender().getValue());
    }

    
    private static class TestForm extends GenericForm
    {
        public TestForm()
        {
            // required
            radio(Types.stringType(), SUBSCRIBE)
                .criterion(acceptString("yes", "no").ignoreCase()).onError(SUBSCRIBE_ERROR_MSG);
            
            // optional
            radio(Types.charType(), GENDER)
                .criterion(accept('M', 'F')).optional().onError(GENDER_ERROR_MSG);
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
