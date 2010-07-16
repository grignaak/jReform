package org.jreform.criteria;

import static junit.framework.Assert.*;

import org.junit.Test;

import static org.jreform.criteria.Criteria.accept;
import static org.jreform.criteria.Criteria.acceptString;
import static org.jreform.criteria.Criteria.and;
import static org.jreform.criteria.Criteria.emailAddress;
import static org.jreform.criteria.Criteria.exactLength;
import static org.jreform.criteria.Criteria.length;
import static org.jreform.criteria.Criteria.max;
import static org.jreform.criteria.Criteria.maxLength;
import static org.jreform.criteria.Criteria.min;
import static org.jreform.criteria.Criteria.minLength;
import static org.jreform.criteria.Criteria.or;
import static org.jreform.criteria.Criteria.postcodeCA;
import static org.jreform.criteria.Criteria.range;
import static org.jreform.criteria.Criteria.startsWith;
import static org.jreform.criteria.Criteria.zipcode;

@SuppressWarnings("unchecked")
public class CriteriaTest
{
    @Test
    public void testMin()
    {
        assertFalse(min(10).isSatisfied(3));
        assertTrue(min(10).isSatisfied(11));
        assertTrue(min(10).isSatisfied(10));
    }
    
    @Test
    public void testMax()
    {
        assertFalse(max(10).isSatisfied(15));
        assertTrue(max(10).isSatisfied(7));
        assertTrue(max(10).isSatisfied(10));
    }

    @Test
    public void testMinLength()
    {
        assertFalse(minLength(5).isSatisfied("abc"));
        assertTrue(minLength(5).isSatisfied("abcde"));
        assertTrue(minLength(5).isSatisfied("abcdefgh"));
    }
    
    @Test
    public void testMaxLength()
    {
        assertFalse(maxLength(5).isSatisfied("abcdefgh"));
        assertTrue(maxLength(5).isSatisfied("abcde"));
        assertTrue(maxLength(5).isSatisfied("a"));
    }
    
    @Test
    public void testExactLength()
    {
        assertFalse(exactLength(5).isSatisfied("abc"));
        assertTrue(exactLength(5).isSatisfied("abcde"));
    }
    
    @Test
    public void testLenght()
    {
        assertTrue(length(1, 5).isSatisfied("a"));
        assertTrue(length(1, 5).isSatisfied("abcde"));
        assertTrue(length(0, 0).isSatisfied(""));
        
        assertFalse(length(1, 5).isSatisfied(""));
        assertFalse(length(1, 5).isSatisfied("abcdef"));
    }
    
    @Test
    public void testRange()
    {
        assertTrue(range(5, 10).isSatisfied(5));
        assertTrue(range(5, 10).isSatisfied(7));
        assertTrue(range(5, 10).isSatisfied(10));
        
        assertFalse(range(5, 10).isSatisfied(4));
        assertFalse(range(5, 10).isSatisfied(11));
    }

    @Test
    public void testAccept()
    {
        assertTrue(accept(-3, 7, 145).isSatisfied(-3));
        assertTrue(accept(-3, 7, 145).isSatisfied(7));
        assertTrue(accept(-3, 7, 145).isSatisfied(145));
        assertFalse(accept(-3, 7, 145).isSatisfied(-2));
        assertFalse(accept(-3, 7, 145).isSatisfied(0));
        assertFalse(accept(-3, 7, 145).isSatisfied(8));
        
        assertTrue(accept('A', 'B', 'C').isSatisfied('A'));
        assertTrue(accept('A', 'B', 'C').isSatisfied('B'));
        assertTrue(accept('A', 'B', 'C').isSatisfied('C'));
        assertFalse(accept('A', 'B', 'C').isSatisfied('a'));
    }
    
    @Test
    public void testAcceptString()
    {
        assertFalse(acceptString("A", "B", "C").isSatisfied("a"));
        assertFalse(acceptString("A", "B", "C").isSatisfied("b"));
        assertTrue(acceptString("A", "B", "C").isSatisfied("B"));
        
        assertTrue(acceptString("A", "B", "C").ignoreCase().isSatisfied("A"));
        assertTrue(acceptString("A", "B", "C").ignoreCase().isSatisfied("B"));
        assertTrue(acceptString("A", "B", "C").ignoreCase().isSatisfied("a"));
        assertTrue(acceptString("A", "B", "C").ignoreCase().isSatisfied("b"));
    }
    
    @Test
    public void testStartsWith()
    {
        assertTrue(startsWith("moo").isSatisfied("moo"));
        assertTrue(startsWith("moo").isSatisfied("moose"));
        
        assertFalse(startsWith("moo").isSatisfied("mo"));
        assertFalse(startsWith("moo").isSatisfied("Moose"));
    }
    
    @Test
    public void testOr()
    {
        assertTrue(or(acceptString("a", "b"), acceptString("x", "y")).isSatisfied("a"));
        assertTrue(or(acceptString("a", "b"), acceptString("x", "y")).isSatisfied("b"));
        assertTrue(or(acceptString("a", "b"), acceptString("x", "y")).isSatisfied("x"));
        assertTrue(or(acceptString("a", "b"), acceptString("x", "y")).isSatisfied("y"));
        
        assertFalse(or(acceptString("a", "b"), acceptString("x", "y")).isSatisfied("R"));
        assertFalse(or(acceptString("a", "b"), acceptString("x", "y")).isSatisfied("z"));
        assertFalse(or(acceptString("a", "b"), acceptString("x", "y")).isSatisfied("n"));
        
        try
        {
            or(min(0));
            fail("and(...) requires at least two criterion arguments");
        }
        catch(IllegalArgumentException ex) { /* empty */ }
    }
    
    @Test
    public void testAnd()
    {
        assertTrue(and(min(200), max(300)).isSatisfied(200));
        assertTrue(and(min(200), max(300)).isSatisfied(250));
        assertTrue(and(min(200), max(300)).isSatisfied(300));

        assertFalse(and(min(200), max(300)).isSatisfied(5));
        assertFalse(and(min(200), max(300)).isSatisfied(301));
        assertFalse(and(min(200), max(300)).isSatisfied(-5));
        
        try
        {
            and(min(0));
            fail("and(...) requires at least two criterion arguments");
        }
        catch(IllegalArgumentException ex) { /* empty */ }
    }
    
    @Test
    public void testEmail()
    {
        assertTrue(emailAddress().isSatisfied("xxxx@domain.xx"));
        assertTrue(emailAddress().isSatisfied("xxxx@domain.xx.xx"));
        assertTrue(emailAddress().isSatisfied("xxxx@subdomain.domain.xx"));
        assertTrue(emailAddress().isSatisfied("xxxx.x.x@subdomain.domain.xx"));
        assertTrue(emailAddress().isSatisfied("xxx_-#%$&^*(&*xx.x.x@subdomain.domain.xx.xxxxxxx"));
    }
    
    @Test
    public void testZipcode()
    {
        assertTrue(zipcode().isSatisfied("12345"));
        assertTrue(zipcode().isSatisfied("12345-2345"));
        
        assertFalse(zipcode().isSatisfied("1234"));
        assertFalse(zipcode().isSatisfied("123456"));
        assertFalse(zipcode().isSatisfied("1234534-2345"));
        assertFalse(zipcode().isSatisfied("1234X"));
        assertFalse(zipcode().isSatisfied("-12345"));
        assertFalse(zipcode().isSatisfied("x12345"));
        assertFalse(zipcode().isSatisfied("abcde"));
    }
    
    @Test
    public void testPostcodeCA()
    {
        assertTrue(postcodeCA().isSatisfied("V1V 1V1"));
        assertTrue(postcodeCA().isSatisfied("v1v 1v1"));
        
        assertFalse(postcodeCA().isSatisfied("1V1V 1V1"));
        assertFalse(postcodeCA().isSatisfied("V1V 1V11"));
        
        assertFalse(postcodeCA().isSatisfied("V1V  1V1"));
        assertFalse(postcodeCA().isSatisfied("V1V1V1"));
        assertFalse(postcodeCA().isSatisfied("VVV 1V1"));
        assertFalse(postcodeCA().isSatisfied("1A1 1V1"));
        
        assertFalse(postcodeCA().isSatisfied("Z1Z 1Z1"));
    }
    
}
