package org.jreform.criteria;

import org.jreform.core.Criterion;

/**
 * A collection of static methods to create commonly used criteria.
 * 
 * @author armandino (at) gmail.com
 */
public class Criteria
{
    private static final Email email = new Email();
    
    public static Criterion and(Criterion...criteria)
    {
        return new And(criteria);
    }
    
    public static Criterion or(Criterion...criteria)
    {
        return new Or(criteria);
    }
    
    public static <T> Criterion<T> values(T...values)
    {
        return new Values<T>(values);
    }
    
    public static StringValues values(String...values)
    {
        return new StringValues(values);
    }
    
    public static <T extends Comparable<T>> Criterion min(T min)
    {
        return new Min<T>(min);
    }
    
    public static <T extends Comparable<T>> Criterion max(T max)
    {
        return new Max<T>(max);
    }
    
    public static <T extends Comparable<T>> Criterion range(T min, T max)
    {
        return new Range<T>(min, max);
    }
    
    public static Criterion length(int length)
    {
        return new Length<CharSequence>(length);
    }
    
    public static Criterion minLength(int min)
    {
        return new MinLength<CharSequence>(min);
    }
    
    public static Criterion maxLength(int max)
    {
        return new MaxLength<CharSequence>(max);
    }
    
    public static Criterion regex(String pattern)
    {
        return new Regex(pattern);
    }
    
    public static Criterion startsWith(String...prefix)
    {
        return new StartsWith(prefix);
    }
    
    public static Criterion emailAddress()
    {
        return email;
    }
    
}
