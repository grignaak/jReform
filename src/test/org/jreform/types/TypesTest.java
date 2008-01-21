package test.org.jreform.types;

import static org.jreform.types.BooleanType.booleanType;
import junit.framework.TestCase;

public class TypesTest extends TestCase
{
	public void testParseBoolean()
	{
		assertTrue(booleanType().parseValue("TRUE"));
		assertTrue(booleanType().parseValue("true"));
		assertFalse(booleanType().parseValue("False"));
		assertFalse(booleanType().parseValue(null));
	}
	
	
}
