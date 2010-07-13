package org.jreform;

import static org.jreform.criteria.Criteria.emailAddress;
import static org.jreform.types.StringType.stringType;

import org.jreform.DuplicateNameException;
import org.jreform.Group;
import org.jreform.HtmlFormSupport;
import org.jreform.Input;

public class InputGroupTest extends BaseTestCase
{
    private static final String CONTACT = "contact";
    private static final String METRIC = "metric";
    private static final String IMPERIAL = "imperial";
    private static final String MISSING_HEIGHT_WEIGHT = "missingHeightWeight";
    
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    
    private static final String HEIGHT_M = "heightMetres"; 
    private static final String WEIGHT_KG = "weightKg";
    
    private static final String HEIGHT_FT = "heightFt";
    private static final String HEIGHT_IN = "heightInches";
    private static final String WEIGHT_LB = "weightLbs";
    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected HtmlFormSupport getForm()
    {
        return form;
    }
    
    /** test add duplicate input throws exception */
    public void testAddDuplicateInputThrowsException()
    {   
        try
        {
            // add to another group
            form.getGroup(METRIC).input(stringType(), HEIGHT_M);
            fail("cannot add inputs with identical names");
        }
        catch(DuplicateNameException ex) { /* empty */ }
    }
    
    /** test input instance is the same from form and group */
    public void xtestInputInstanceIsTheSameFromFormAndGroup()
    {
        setContactGroupWithValidData();
        setMetric("1.9", "95");
        
        assertTrue(validateForm());
        assertSame(form.getInput(EMAIL), form.getGroup(CONTACT).getInput(EMAIL));
    }
    
    /** validation fails if contact group is empty */
    public void testValidationFailsIfContactGroupIsEmpty()
    {
        setMetric("1.9", "95");
        
        assertFalse(validateForm());
        
        assertTrue(form.getGroup(CONTACT).isEmpty());
        assertFalse(form.getGroup(CONTACT).isValid());
        
        assertFalse(form.getGroup(METRIC).isEmpty());
        assertTrue(form.getGroup(METRIC).isValid());
        
        assertTrue(form.getGroup(IMPERIAL).isEmpty());
        assertTrue(form.getGroup(IMPERIAL).isValid());
        
        assertFalse(form.getErrors().contains(MISSING_HEIGHT_WEIGHT));
    }
    
    /** test additonal validation fails if both optional groups are empty */
    public void testAdditionalValidationFailsIfBothOptionalGroupsAreEmpty()
    {
        setContactGroupWithValidData();
        
        assertFalse(validateForm());
        assertTrue(form.getGroup(METRIC).isEmpty());
        assertTrue(form.getGroup(IMPERIAL).isEmpty());
        assertTrue(form.getErrors().contains(MISSING_HEIGHT_WEIGHT));
    }
    
    /** validation passes if metric group has valid data */
    public void testValidationPassesIfMetricGroupHasValidData()
    {
        final double heightM = 1.75;
        final int weightKg = 65;
        
        setContactGroupWithValidData();
        
        setMetric(String.valueOf(heightM), String.valueOf(weightKg));
        
        assertTrue(validateForm());
        assertTrue(form.getGroup(METRIC).isValid());
        assertTrue(form.getGroup(IMPERIAL).isValid());
        
        assertTrue(form.heightM().getValue() == heightM);
        assertTrue(form.weightKg().getValue() == weightKg);
    }
    
    /** validation passes if imperial group has valid data */
    public void testValidationPassesIfImperialGroupHasValidData()
    {
        final int heightFt = 5;
        final int heightIn = 7;
        final int weightLb = 135;
        
        setContactGroupWithValidData();
        
        setImperial(String.valueOf(heightFt), String.valueOf(heightIn), String.valueOf(weightLb));
        
        assertTrue(validateForm());
        assertTrue(form.getGroup(METRIC).isValid());
        assertTrue(form.getGroup(IMPERIAL).isValid());
        
        assertTrue(form.heightFt().getValue() == heightFt);
        assertTrue(form.heightIn().getValue() == heightIn);
        assertTrue(form.weightLb().getValue() == weightLb);
    }
    
    /** validation fails if metric group has invalid data */
    public void testValidationFailsIfMetricGroupHasInvalidData()
    {
        final double heightM = 1.75;
        
        setContactGroupWithValidData();
        
        setMetric(String.valueOf(heightM), "this should be a number");
        
        assertFalse(validateForm());
        
        assertTrue(form.getGroup(CONTACT).isValid());
        assertFalse(form.getGroup(CONTACT).isEmpty());
        
        assertFalse(form.getGroup(METRIC).isValid());
        assertFalse(form.getGroup(METRIC).isEmpty());
        
        assertTrue(form.getGroup(IMPERIAL).isValid());
        assertTrue(form.getGroup(IMPERIAL).isEmpty());
        
        assertTrue(form.heightM().getValue() == heightM);
    }
    
    
    /** validation fails if contact group has invalid data */
    public void testValidationFailsIfContactGroupHasInvalidData()
    {
        setContact("Adam", "Smith", "this should be a valid email address");
        
        setMetric("1.55", "65");
        
        assertFalse(validateForm());
        
        assertFalse(form.getGroup(CONTACT).isValid());
        assertFalse(form.getGroup(CONTACT).isEmpty());
        assertFalse(form.getGroup(CONTACT).getInput(EMAIL).isValid());
        
        assertTrue(form.getGroup(METRIC).isValid());
        assertFalse(form.getGroup(METRIC).isEmpty());
        
        assertTrue(form.getGroup(IMPERIAL).isValid());
        assertTrue(form.getGroup(IMPERIAL).isEmpty());
    }
    
    private void setContactGroupWithValidData()
    {
        setContact("Adam", "Smith", "adam@smith.com");
    }
    
    private void setContact(String surname, String phone, String email)
    {
        setParameter(SURNAME, surname);
        setParameter(PHONE, phone);
        setParameter(EMAIL, email);
    }
    
    private void setMetric(String m, String kg)
    {
        setParameter(HEIGHT_M, m);
        setParameter(WEIGHT_KG, kg);
    }
    
    private void setImperial(String ft, String in, String lb)
    {
        setParameter(HEIGHT_FT, ft);
        setParameter(HEIGHT_IN, in);
        setParameter(WEIGHT_LB, lb);
    }
    
    private static class TestForm extends HtmlFormSupport
    {
        @SuppressWarnings("unchecked")
        public TestForm()
        {
            Group contact = requiredGroup(CONTACT);
            contact.input(stringType(), SURNAME);
            contact.input(stringType(), PHONE);
            contact.input(stringType(), EMAIL, emailAddress()).optional();
            
            Group metric = optionalGroup(METRIC);
            metric.input(doubleType(), HEIGHT_M);
            metric.input(intType(), WEIGHT_KG);
            
            Group imperial = optionalGroup(IMPERIAL);
            imperial.input(intType(), HEIGHT_FT);
            imperial.input(intType(), HEIGHT_IN);
            imperial.input(intType(), WEIGHT_LB);
            
            try
            {
                @SuppressWarnings("unused")
                Group duplicate = optionalGroup(IMPERIAL);
                fail("Error - duplicate group name must throw an exception");
            }
            catch(DuplicateNameException ex) {}
        }
        
        protected void additionalValidate()
        {
            Group metric = getGroup(METRIC);
            Group imperial = getGroup(IMPERIAL);
            
            if(metric.isEmpty() && imperial.isEmpty())
            {
                addError(MISSING_HEIGHT_WEIGHT);
            }
        }
        
        public Input<Double> heightM()
        {
            return getGroup(METRIC).getInput(HEIGHT_M);
        }
        
        public Input<Integer> weightKg()
        {
            return getGroup(METRIC).getInput(WEIGHT_KG);
        }
        
        public Input<Integer> heightFt()
        {
            return getGroup(IMPERIAL).getInput(HEIGHT_FT);
        }
        
        public Input<Integer> heightIn()
        {
            return getGroup(IMPERIAL).getInput(HEIGHT_IN);
        }
        
        public Input<Integer> weightLb()
        {
            return getGroup(IMPERIAL).getInput(WEIGHT_LB);
        }
        
    }
    
}
