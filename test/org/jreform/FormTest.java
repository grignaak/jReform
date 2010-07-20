package org.jreform;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

import org.jreform.exceptions.UndefinedInputControlException;
import org.jreform.impl.GenericForm;
import org.jreform.inputs.Input;
import org.jreform.types.Types;
import org.jreform.util.HttpServletRequestStub;
import org.junit.Before;
import org.junit.Test;

public class FormTest
{
    private static final String REQUIRED_INPUT = "required input";
    private static final String REQUIRED_GROUP = "required group";
    private static final String REQUIRED_GROUP_INPUT = "required group input";
    private static final String OPTIONAL_GROUP = "optional group";
    private static final String OPTIONAL_GROUP_INPUT = "optional group input";
    
    static class TestForm extends GenericForm
    {
        private boolean additionalvalidate;

        TestForm()
        {
            input(Types.stringType(), REQUIRED_INPUT);
            
            Group requiredGroup = requiredGroup(REQUIRED_GROUP);
            requiredGroup.input(Types.intType(), REQUIRED_GROUP_INPUT);
            
            Group optionalGroup = optionalGroup(OPTIONAL_GROUP);
            optionalGroup.input(Types.intType(), OPTIONAL_GROUP_INPUT);
        }
        
        Group getRequiredGroup()
        {
            return getGroup(REQUIRED_GROUP);
        }
        
        Group getOptionalGroup()
        {
            return getGroup(OPTIONAL_GROUP);
        }
        
        Input<Integer> getRequiredGroupInput()
        {
            return getRequiredGroup().getInput(REQUIRED_GROUP_INPUT);
        }
        

        Input<Integer> getInput()
        {
            return getInput(REQUIRED_INPUT);
        }
        
        protected void additionalValidate()
        {
            additionalvalidate = true;
        }

        public Input<Integer> getOptionalGroupInput()
        {
            return getOptionalGroup().getInput(OPTIONAL_GROUP_INPUT);
        }
    }

    private TestForm form;
    
    @Before
    public void setup()
    {
        form = new TestForm();
    }
    
    @Test(expected=UndefinedInputControlException.class)
    public void testGetInputThatDoesNotExistThrowsException()
    {
        form.getInput("input doesn't exist");
    }
    
    @Test
    public void shouldCallAdditionalValidate()
    {
        form.validate();
        assertTrue(form.additionalvalidate);
    }
    
    @Test
    public void shouldBeValidOnRequiredInputs()
    {
        setValidRequiredInputs();
        assertTrue(form.validate());
    }

    @Test
    public void shouldBeInvalidOnInvalidInputs()
    {
        setValidRequiredInputs();
        form.getInput().setValueAttribute("");
        assertFalse(form.validate());
    }

    private void setValidRequiredInputs()
    {
        form.getInput().setValueAttribute("any old value");
        form.getRequiredGroupInput().setValueAttribute("123");
    }
    
    @Test
    public void shouldBeInvalidOnInvalidGroups()
    {
        setValidRequiredInputs();
        form.getOptionalGroupInput().setValueAttribute("not a number");
        assertFalse(form.validate());
    }
    
    @Test
    public void shouldBeInvalidIfOptionalInputInvalid()
    {
        setValidRequiredInputs();
        form.getOptionalGroupInput().setValueAttribute("not a number");
        assertFalse(form.validate());
    }
    
    @Test
    public void shouldBeValidIfOptionalInputValid()
    {
        setValidRequiredInputs();
        form.getOptionalGroupInput().setValueAttribute("123");
        assertTrue(form.validate());
    }
    
    @Test
    public void shouldBeValidWhenProcessingValidRequest()
    {
        HttpServletRequestStub request = new HttpServletRequestStub();
        request.setParameter(REQUIRED_INPUT, "any old value");
        request.setParameter(OPTIONAL_GROUP_INPUT, "123");
        request.setParameter(REQUIRED_GROUP_INPUT, "123");
        
        assertTrue(form.validateRequest(request));
    }
    
    @Test
    public void shouldBeInvalidWhenProcessingInvalidRequest()
    {
        assertFalse(form.validateRequest(new HttpServletRequestStub()));
    }
}
