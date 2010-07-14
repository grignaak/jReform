package org.jreform.types;

import static org.jreform.types.BooleanType.booleanType;
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
	
	
}
