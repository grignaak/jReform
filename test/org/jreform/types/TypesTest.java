package org.jreform.types;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.jreform.types.Types.booleanType;
import static org.jreform.types.Types.charType;

import org.junit.Test;


public class TypesTest
{
    @Test
	public void testParseBoolean()
	{
		assertTrue(booleanType().parseValue("TRUE").getValue());
		assertTrue(booleanType().parseValue("true").getValue());
		assertFalse(booleanType().parseValue("False").getValue());
		assertFalse(booleanType().parseValue(null).getValue());
	}
	
    @Test
	public void testShouldAcceptCustomErrorMessage()
	{
	    String customError = "Custom Message";
        AbstractType<Character> charParser = charType().onError(customError);
        assertEquals(customError, charParser.parse("Not a character").getError());
	}
	
}
