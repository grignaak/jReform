package test.org.jreform.criteria;

import static org.jreform.criteria.Criteria.max;
import static org.jreform.criteria.Criteria.*;
import junit.framework.TestCase;

@SuppressWarnings("unchecked")
public class CriteriaTest extends TestCase
{
    public void testMin()
    {
        assertFalse(min(10).isSatisfied(3));
        assertTrue(min(10).isSatisfied(11));
        assertTrue(min(10).isSatisfied(10));
    }
    
    public void testMax()
    {
        assertFalse(max(10).isSatisfied(15));
        assertTrue(max(10).isSatisfied(7));
        assertTrue(max(10).isSatisfied(10));
    }

    public void testMinLength()
    {
        assertFalse(minLength(5).isSatisfied("abc"));
        assertTrue(minLength(5).isSatisfied("abcde"));
        assertTrue(minLength(5).isSatisfied("abcdefgh"));
    }
    
    public void testMaxLength()
    {
        assertFalse(maxLength(5).isSatisfied("abcdefgh"));
        assertTrue(maxLength(5).isSatisfied("abcde"));
        assertTrue(maxLength(5).isSatisfied("a"));
    }
    
    public void testExactLength()
    {
        assertFalse(exactLength(5).isSatisfied("abc"));
        assertTrue(exactLength(5).isSatisfied("abcde"));
    }
    
    public void testLenght()
    {
        assertTrue(length(1, 5).isSatisfied("a"));
        assertTrue(length(1, 5).isSatisfied("abcde"));
        assertTrue(length(0, 0).isSatisfied(""));
        
        assertFalse(length(1, 5).isSatisfied(""));
        assertFalse(length(1, 5).isSatisfied("abcdef"));
    }
    
    public void testRange()
    {
        assertTrue(range(5, 10).isSatisfied(5));
        assertTrue(range(5, 10).isSatisfied(7));
        assertTrue(range(5, 10).isSatisfied(10));
        
        assertFalse(range(5, 10).isSatisfied(4));
        assertFalse(range(5, 10).isSatisfied(11));
    }

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
    
    public void testStartsWith()
    {
        assertTrue(startsWith("moo").isSatisfied("moo"));
        assertTrue(startsWith("moo").isSatisfied("moose"));
        
        assertFalse(startsWith("moo").isSatisfied("mo"));
        assertFalse(startsWith("moo").isSatisfied("Moose"));
    }
    
    
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
    
    public void testEmail()
    {
        assertTrue(emailAddress().isSatisfied("arman@arman.com"));
        assertTrue(emailAddress().isSatisfied("arman@arman.co.uk"));
        assertTrue(emailAddress().isSatisfied("arman@dialup.ptt.ru"));
        assertTrue(emailAddress().isSatisfied("arman.s.a@dialup.ptt.ru"));
        assertTrue(emailAddress().isSatisfied("arm_-#%$&^*(&*an.s.a@dialup.ptt.ru.cossssssm"));
    }
    
}
