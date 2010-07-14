package org.jreform.util;

public abstract class Maybe<T>
{
    public boolean isSo() { return false; }
    public boolean isNotSo() { return false; }
    public abstract T getValueOrDefault(T defaultValue);
    public abstract T getValue();
    
    public static <T> Maybe<T> soUnlessNull(T value)
    {
        if (value == null)
            return Maybe.not();
        
        return new Maybe.So<T>(value);
    }

    public static <T> Maybe<T> not()
    {
        return new Maybe.Not<T>();
    }
    
    private static class So<T> extends Maybe<T>
    {
        private T value;
        
        private So(T value)
        {
            this.value = value;
        } 
        
        public boolean isSo()
        {
            return true;
        }
        
        public T getValue()
        {
            return value;
        }

        public T getValueOrDefault(T defaultValue)
        {
            return value;
        }
    }
    
    private static class Not<T> extends Maybe<T>
    {
        public boolean isNotSo()
        {
            return true;
        }
        
        public T getValue()
        {
            throw new IllegalStateException("Cannot access value: No value specified");
        }

        public T getValueOrDefault(T defaultValue)
        {
            return defaultValue;
        }
    }
}