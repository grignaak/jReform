package org.jreform;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.jreform.types.Types.intType;

import org.jreform.inputs.Checkbox;
import org.jreform.inputs.MultiCheckbox;
import org.junit.Test;

public class CheckboxTest
{
    @Test(expected=Exception.class)
    public void shouldAlwaysBeOptional()
    {
        checkbox().setRequired(true);
    }

    private Checkbox<Integer> checkbox()
    {
        return new Checkbox<Integer>(intType(), "a checkbox");
    }
    
    @Test
    public void shouldBeValidIfEmpty()
    {
        assertTrue("valid when empty", checkbox().validate());
    }
    
    
    @Test
    public void shouldStillBeInvalidOnParseError()
    {
        Checkbox<Integer> checkbox = checkbox();
        checkbox.setValueAttribute("not an int");
        
        assertFalse("invalid on error", checkbox.validate());
    }

    @Test
    public void multiCheckBoxShouldDefaultToOptional()
    {
        assertFalse("default to optional", multicheckbox().isRequired());
    }
    
    @Test
    public void shouldStillSetMultiCheckBoxToRequired()
    {
        MultiCheckbox<Integer> multicheckbox = multicheckbox();
        multicheckbox.setRequired(true);
        assertTrue("can be required", multicheckbox.isRequired());
    }
    

    private MultiCheckbox<Integer> multicheckbox()
    {
        return new MultiCheckbox<Integer>(intType(), "multi check");
    }
}
