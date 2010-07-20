package org.jreform;


import static junit.framework.Assert.*;

import java.util.Arrays;

import org.jreform.criteria.Criteria;
import org.jreform.inputs.BasicMultiInput;
import org.jreform.inputs.MultiInput;
import org.jreform.types.Types;
import org.junit.Before;
import org.junit.Test;

public class MultiInputTest
{
    private MultiInput<Integer> input;

    @Before
    public void setup()
    {
        input = new BasicMultiInput<Integer>(Types.intType(), "invalid");
    }
    
    @Test
    public void shouldBeBlankWhenNoInputsAdded()
    {
        assertTrue(input.isBlank());
    }
    
    @Test
    public void shouldConvertNullValuesToEmptyStrings()
    {
        input.setValueAttributes(Arrays.asList("1", null, "2"));
        assertEquals(Arrays.asList("1", "", "2"), input.getValueAttributes());
    }
    
    @Test
    public void shouldBeBlankWhenEmptyStringsAdded()
    {
        input.setValueAttributes(Arrays.asList("", null, ""));
        assertTrue(input.isBlank());
    }
    
    @Test
    public void shouldBeBlankWithSpacesOnlyStrings()
    {
        input.setValueAttributes(Arrays.asList(" ", "\t"));
        assertTrue(input.isBlank());
    }
    
    @Test
    public void shouldAcceptMultipleValues()
    {
        input.setValueAttributes(Arrays.asList("1", "2", "3", "4"));
        
        input.validate();
        assertEquals(Arrays.asList(1, 2, 3, 4), input.getValues());
    }
    
    @Test
    public void shouldBeNotBeValidIfAnyAreInvalid()
    {
        input.setValueAttributes(Arrays.asList("1", "a"));
        assertFalse(input.validate());
    }

    @Test(expected=Exception.class)
    public void shouldThrowWhenRetrievingInvalidValues()
    {
        input.addCriterion(Criteria.max(4));
        
        input.setValues(Arrays.asList(5));
        input.validate();
        
        input.getValues();
    }
    
    @Test
    public void shouldBeValidIfOptionalAndBlank()
    {
        input.setRequired(false);
        input.setValueAttributes(Arrays.asList(""));
        assertTrue(input.validate());
    }
}
