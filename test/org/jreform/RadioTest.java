package org.jreform;

import static junit.framework.Assert.*;

import static org.jreform.types.Types.intType;

import org.jreform.inputs.Radio;
import org.junit.Before;
import org.junit.Test;

public class RadioTest
{
    private Radio<Integer> radio;

    @Before
    public void setup()
    {
        radio = new Radio<Integer>(intType(), "a radio");
    }

    public void shouldBeRequired()
    {
        assertTrue("radios are required", radio.isRequired());
    }

    @Test(expected=Exception.class)
    public void shouldAlwaysBeRequired()
    {
        radio.setRequired(false);
    }
    
    @Test
    public void shouldBeInvalidOnBlankRadio()
    {
        radio.setValueAttribute("");
        assertFalse("never allowed to be blank", radio.validate());
    }
}
