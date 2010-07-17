package org.jreform;

import static junit.framework.Assert.*;

import org.jreform.exceptions.UndefinedInputControlException;
import org.jreform.impl.GenericForm;
import org.jreform.types.Types;
import org.junit.Test;

public class FormTest extends BaseTestCase<FormTest.TestForm>
{
    private static final String STRING_INPUT = "StringInput";
    private static final String INT_INPUT = "intInput";
    private static final String INT_MULTI_INPUT = "intMultiInput";
    
    protected TestForm createForm()
    {
        return new TestForm();
    }
    
    @Test
    public void testGetInputThatDoesNotExistThrowsException()
    {
        try
        {
            form.getInput("input doesn't exist");
            fail("Error - exception expected");
        } catch(UndefinedInputControlException e) {}
    }
    
    static class TestForm extends GenericForm
    {
        public TestForm()
        {
            input(Types.stringType(), STRING_INPUT).optional();
            input(Types.intType(), INT_INPUT).optional();
            multiCheckbox(Types.intType(), INT_MULTI_INPUT).optional();
        }
    }

}
