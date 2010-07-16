package org.jreform.criteria;

import org.jreform.Criterion;

/**
 * A collection of static methods to create commonly used criteria.
 * 
 * @author armandino (at) gmail.com
 */
public class Criteria
{
    public static <T> Criterion<T> and(Criterion<T>...criteria)
    {
        return new And<T>(criteria);
    }
    
    public static <T> Criterion<T> or(Criterion<T>...criteria)
    {
        return new Or<T>(criteria);
    }
    
    public static <T> Accept<T> accept(T...values)
    {
        return new Accept<T>(values);
    }
    
    public static AcceptString acceptString(String...values)
    {
        return new AcceptString(values);
    }
    
    public static <T extends Comparable<T>> Min<T> min(T min)
    {
        return new Min<T>(min);
    }
    
    public static <T extends Comparable<T>> Max<T> max(T max)
    {
        return new Max<T>(max);
    }
    
    public static <T extends Comparable<T>> Range<T> range(T min, T max)
    {
        return new Range<T>(min, max);
    }
    
    public static Length length(int min, int max)
    {
        return new Length(min, max);
    }
    
    public static ExactLength exactLength(int length)
    {
        return new ExactLength(length);
    }
    
    public static MinLength minLength(int min)
    {
        return new MinLength(min);
    }
    
    public static MaxLength maxLength(int max)
    {
        return new MaxLength(max);
    }
    
    public static Criterion<String> regex(String pattern)
    {
        return new Regex(pattern);
    }
    
    public static Criterion<String> startsWith(String...prefix)
    {
        return new StartsWith(prefix);
    }
    
    public static Criterion<String> emailAddress()
    {
        return new Email();
    }
    
    public static Criterion<String> zipcode()
    {
        return new ZipCode();
    }
    
    public static Criterion<String> postcodeCA()
    {
        return new PostcodeCA();
    }
    
}
