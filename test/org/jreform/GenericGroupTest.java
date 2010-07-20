package org.jreform;

import static junit.framework.Assert.assertTrue;
import static org.jreform.types.Types.*;
import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jreform.impl.GenericGroup;
import org.jreform.inputs.Input;
import org.junit.*;

public class GenericGroupTest
{
    private static final String AN_INPUT = "an input";
    private Group group;

    @Before
    public void setup()
    {
        group = optionalGroup();
        group.input(intType(), AN_INPUT);
    }

    private GenericGroup optionalGroup()
    {
        return new GenericGroup("a group");
    }

    @Test
    public void shouldBeValidIfOptionalAndEmpty()
    {
        assertTrue(optionalGroup().validate());
    }
    
    @Test
    public void shouldBeValidIfOptionalAndInputsAreEmpty()
    {
        assertTrue(group.validate());
    }

    
    @Test
    public void shouldBeInvalidIfRequiredAndEmpty()
    {
        group.setRequired(true);
        assertFalse(group.validate());
    }
    
    @Test
    public void shouldNotBeEmptyWhenSettingValue()
    {
        getInput().setValue("a value");
        assertFalse(group.isEmpty());
    }

    private Input<Object> getInput()
    {
        return group.getInput(AN_INPUT);
    }
    
    /* A radio input, once checked, will always be checked.
     * 
     * To account for this, a group should not count radio inputs against
     * its emptiness. An edge case could be if the radio input is the group's
     * only input--then it should be counted.
     * 
     */
    @Test
    @Ignore // TODO
    public void shouldBeEmptyIfRadioInputSuppliedButOtherwiseEmpty()
    {
        group.radio(stringType(), "a radio");
        group.getInput("a radio").setValueAttribute("a value");
        assertTrue(group.isEmpty());   
    }
    
    @Test
    public void shouldBeValidIfInputsAreValid()
    {
        getInput().setValueAttribute("123");
        assertTrue(group.validate());
    }
    
    @Test
    public void shouldCallAdditionalValidate()
    {
        final AtomicBoolean isAdditionalValidateCalled = new AtomicBoolean();
        
        Group group = new GenericGroup("additional validate")
        {
            protected void additionalValidate()
            {
                isAdditionalValidateCalled.set(true);
            }
        };
        
        group.validate();
        
        assertTrue(isAdditionalValidateCalled.get());
    }
}
