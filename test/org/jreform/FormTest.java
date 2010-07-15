package org.jreform;

import org.jreform.exceptions.UndefinedInputControlException;
import org.jreform.impl.GenericForm;
import org.jreform.types.Types;

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
    
    protected Form getForm()
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
    
    private static class TestForm extends GenericForm
    {
        public TestForm()
        {
            input(Types.stringType(), STRING_INPUT).optional();
            input(Types.intType(), INT_INPUT).optional();
            multiCheckbox(Types.intType(), INT_MULTI_INPUT).optional();
        }
    }

}
