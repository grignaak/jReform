package test.org.jreform;

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
    
    private static class TestForm extends HtmlForm
    {
        public TestForm()
        {
            input(stringType(), STRING_INPUT).optional();
            input(intType(), INT_INPUT).optional();
            multiCheckbox(intType(), INT_MULTI_INPUT).optional();
        }
    }

}
