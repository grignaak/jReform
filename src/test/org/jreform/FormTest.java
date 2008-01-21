package test.org.jreform;

import java.util.List;

import org.jreform.HtmlForm;
import org.jreform.UndefinedInputControlException;

public class FormTest extends BaseTestCase
{
    private static final String STRING_INPUT = "StringInput";
    private static final String INT_INPUT = "intInput";
    private static final String INT_MULTI_INPUT = "intMultiInput";
    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected HtmlForm getForm()
    {
        return form;
    }
    
    public void testGetInputThatDoesNotExistThrowsException()
    {
        try
        {
            form.getInput("input doesn't exist");
            fail("Error - exception expected");
        } catch(UndefinedInputControlException e) {}
    }
    
    public void testGetInputValue()
    {
        final int value = 10;
        setParameter(INT_INPUT, String.valueOf(value));
        assertTrue(validateForm());
        
        final Integer inputValue = form.getInputValue(INT_INPUT);
        assertTrue(value == inputValue);
    }
    
    public void testGetMultiInputValue()
    {
        final String[] values = new String[] {"3", "5", "0", "99", "-4"};
        setParameters(INT_MULTI_INPUT, values);
        assertTrue(validateForm());
        
        List<Integer> list = form.getMultiInputValue(INT_MULTI_INPUT);
        
        for(int i=0; i < values.length; i++)
        {
            assertEquals(list.get(i), new Integer(values[i]));
        }
    }
    
    public void testGetInputValueThrowsClassCastException()
    {
        final String value = "string value";
        setParameter(STRING_INPUT, value);
        assertTrue(validateForm());
        
        final String inputValue = form.getInputValue(STRING_INPUT);
        assertEquals(value, inputValue);
        
        try
        {
            @SuppressWarnings("unused")
            Integer failedCast = form.getInputValue(STRING_INPUT);
            fail("Error - exception expected");
        }
        catch(ClassCastException e) {}
    }
    
    public void testGetMultiInputValueThrowsClassCastException()
    {
        final String[] values = new String[] {"3", "5", "0", "99", "-4"};
        setParameters(INT_MULTI_INPUT, values);
        assertTrue(validateForm());
        
        // List<String> instead of List<Integer>
        List<String> list = form.getMultiInputValue(INT_MULTI_INPUT);
        
        try
        {
            @SuppressWarnings("unused")
            String failedCast = list.get(0);
            fail("Error - exception expected");
        }
        catch(ClassCastException e) {}
    }
    
    private class TestForm extends HtmlForm
    {
        public TestForm()
        {
            input(stringType(), STRING_INPUT).optional();
            input(intType(), INT_INPUT).optional();
            multiCheckbox(intType(), INT_MULTI_INPUT).optional();
        }
    }

}
