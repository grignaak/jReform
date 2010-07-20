package org.jreform;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.jreform.types.Types.intType;

import org.jreform.inputs.Checkbox;
import org.junit.Before;
import org.junit.Test;

public class CheckboxTest
{
    private Checkbox<Integer> checkbox;

    @Before
    public void setup()
    {
        checkbox = new Checkbox<Integer>(intType(), "a checkbox");
    }


    @Test
    public void shouldBeOptional()
    {
        assertFalse("checkboxes are optional", checkbox.isRequired());
    }


    @Test(expected=Exception.class)
    public void shouldAlwaysBeOptional()
    {
        checkbox.setRequired(true);
    }
    
    @Test
    public void shouldBeValidIfEmpty()
    {
        assertTrue("valid when empty", checkbox.validate());
    }
    
    
    @Test
    public void shouldStillBeInvalidOnParseError()
    {
        checkbox.setValueAttribute("not an int");
        
        assertFalse("invalid on error", checkbox.validate());
    }
}
