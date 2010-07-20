package org.jreform;

import static org.jreform.types.Types.*;
import static org.junit.Assert.*;

import org.jreform.exceptions.DuplicateNameException;
import org.jreform.impl.AbstractInputCollection;
import org.jreform.inputs.Input;
import org.junit.Before;
import org.junit.Test;

public class AbstractInputCollectionTest
{
    private static final String INPUT_NAME = "an input";

    private static class MockInputCollection extends AbstractInputCollection
    {
        @Override
        public boolean validate()
        {
            validateInputs();
            return isValid();
        }
    }

    private MockInputCollection inputs;

    @Before
    public void setUp() throws Exception
    {
        inputs = new MockInputCollection();
        inputs.input(intType(), INPUT_NAME);
    }

    private Input<Integer> getInput()
    {
        return inputs.getInput(INPUT_NAME);
    }
    
    @Test
    public void shouldAddInputs()
    {
        inputs.input(intType(), "another input");
        assertNotNull(inputs.getInput("another input"));
    }
    
    @Test
    public void shouldBeValidIfNoInputsPresent()
    {
        assertTrue(new MockInputCollection().validate());
    }
    
    @Test
    public void shouldBeInvalidForEmptyInputs()
    {
        assertFalse(inputs.validate());
    }
    
    @Test
    public void shouldHaveErrorsForEmptyInput()
    {
        inputs.validate();
        assertFalse(inputs.getErrors().isEmpty());
    }
    
    @Test
    public void shouldBeInvalidForInvalidInput()
    {
        getInput().setValueAttribute("not a number");
        assertFalse(inputs.validate());
    }

    @Test
    public void shouldHaveAnErrorWhenInvalidInput()
    {
        getInput().setValueAttribute("not a number");
        inputs.validate();
        assertFalse(inputs.getErrors().isEmpty());
    }
    
    @Test
    public void shouldBeValidForValidInput()
    {
        getInput().setValueAttribute("123");
        assertTrue(inputs.validate());
    }
    
    @Test(expected=DuplicateNameException.class)
    public void shouldNotAcceptTwoInputsWithSameName()
    {
        MockInputCollection collection = new MockInputCollection();
        collection.input(intType(), "an input");
        collection.checkbox(intType(), "an input");
    }
}
