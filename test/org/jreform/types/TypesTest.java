package org.jreform.types;

import static org.jreform.types.Types.*;

import junit.framework.TestCase;

public class TypesTest extends TestCase
{
	public void testParseBoolean()
	{
		assertTrue(booleanType().parseValue("TRUE").getValue());
		assertTrue(booleanType().parseValue("true").getValue());
		assertFalse(booleanType().parseValue("False").getValue());
		assertFalse(booleanType().parseValue(null).getValue());
	}
	
	public void testShouldAcceptCustomErrorMessage()
	{
	    String customError = "Custom Message";
        AbstractType<Character> charParser = charType().onError(customError);
        assertEquals(customError, charParser.parse("Not a character").getError());
	}
	
}
