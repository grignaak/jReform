package org.jreform;

import static junit.framework.Assert.*;
import static org.jreform.BaseTestCase.assertContains;
import static org.jreform.criteria.Criteria.max;
import static org.jreform.types.Types.*;

import java.util.Date;

import org.jreform.inputs.BasicInput;
import org.jreform.inputs.Input;
import org.junit.Ignore;
import org.junit.Test;

public class InputTest
{    
    @Test
    public void shouldSucceedWhenDirectlySettingValue()
    {
        Input<String> input = directStringInput("a value");
        assertTrue(input.validate());
    }

    private Input<String> directStringInput(String value)
    {
        Input<String> input = new BasicInput<String>(stringType(), "my name");
        input.setValue(value);
        return input;
    }

    @Test
    public void shouldGetTheSameValueBackWhenDiretlySettingAValue()
    {
        Input<String> input = directStringInput("a value");
        input.validate();
        assertEquals("a value", input.getValue());
    }

    
    @Test
    public void shouldFailWhenDirectlySettingANullValue()
    {
        Input<String> input = directStringInput(null);
        assertFalse("null value given", input.validate());
    }

    @Test(expected=Exception.class)
    public void shouldThrowWhenAttemptingToRetrieveNullValue()
    {
        Input<String> input = directStringInput(null);
        input.validate();
        input.getValue();
    }
    
    @Test
    public void shouldFailWhenSettingAnInvalidValue()
    {
        Input<Integer> input = directIntegerInput();
        input.addCriterion(max(4));
        
        assertFalse("input out of range", input.validate());
    }
    
    @Test
    public void shouldHaveAnErrorWhenACriteriaFails()
    {
        Input<Integer> input = directIntegerInput();
        input.addCriterion(max(4));
        
        input.validate();
        assertFalse("must have an error", input.getErrors().isEmpty());
    }

    private Input<Integer> directIntegerInput()
    {
        Input<Integer> input = integerInput();
        input.setValue(5);
        return input;
    }
    
    @Test(expected=Exception.class)
    public void shouldThrowWhenAttemptingToRetrieveInvalidValue()
    {
        Input<Integer> input = directIntegerInput();
        input.addCriterion(max(4));
        
        input.validate();
        input.getValue();
    }
    
    @Test
    public void shouldSucceedWhenSettingThroughValueAttribute()
    {
        assertTrue("a valid integer", validateInteger("123"));
    }
    
    private boolean validateInteger(String value)
    {
        Input<Integer> input = integerInput(value);
        return input.validate();
    }

    private Input<Integer> integerInput(String value)
    {
        Input<Integer> input = integerInput();
        input.setValueAttribute(value);
        return input;
    }

    private Input<Integer> integerInput()
    {
        return new BasicInput<Integer>(intType(), "who knows");
    }

    @Test
    public void shouldGetParsedValueOnSuccess()
    {
        Input<Integer> input = integerInput("34");
        input.validate();
        assertEquals("should parse number", 34, (int)input.getValue());
    }
    
    @Test
    public void shouldFailWhenPassingInEmptyString()
    {
        assertFalse("required field cannot be blank", validateInteger(""));
    }
    
    @Test
    public void shouldHaveInformedErrorWhenPassingInEmptyString()
    {
        Input<Integer> input = integerInput("");
        input.validate();
        assertContains("Missing value", input.getErrors());
    }

    @Test
    public void shouldFailWhenPassingInNull()
    {
        assertFalse("required field cannot accept null", validateInteger(null));
    }
    
    @Test
    public void shouldHaveInformedErrorOnParseFail()
    {
        Input<Integer> input = integerInput("not an integer");
        input.validate();
        assertContains("Cannot convert to a number", input.getErrors());
    }
    
    @Test
    public void shouldPassWhenBlankAndOptional()
    {
        Input<Integer> input = optionalIntegerInput("");
        assertTrue("optional field can be blank", input.validate());
    }

    private Input<Integer> optionalIntegerInput(String value)
    {
        Input<Integer> input = integerInput(value);
        input.setRequired(false);
        return input;
    }
    
    @Test
    public void shouldPassWhenNullAndOptional()
    {
        Input<Integer> input = optionalIntegerInput(null);
        assertTrue("optional field can accept null", input.validate());
    }
    
    @Test
    public void shouldNotBeBlankWhenSettingValueDirectly()
    {
        Input<Integer> input = integerInput();
        input.setValue(1);
        assertFalse(input.isBlank());
    }
    
    @Test
    @Ignore // TODO
    public void shouldGetParsableValueWhenSettingValueDirectly()
    {
        Input<Date> input1 = new BasicInput<Date>(dateType("dd MMM yyyy"), "a date");
        Input<Date> input2 = new BasicInput<Date>(dateType("dd MMM yyyy"), "another date");
        
        Date today = new Date();
        input1.setValue(today);
        
        String valueAttribute = input1.getValueAttribute();
        input2.setValueAttribute(valueAttribute);
        assertEquals(today, input2.getValue());
    }
}
