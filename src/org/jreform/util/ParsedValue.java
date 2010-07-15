package org.jreform.util;

public abstract class ParsedValue<T>
{
    public boolean isParsed() { return false; }
    public boolean isNotParsed() { return false; }
    public abstract T getValueOrDefault(T defaultValue);
    public abstract T getValue();
    public abstract String getError();
    
    public static <T> ParsedValue<T> setUnlessNull(T value)
    {
        if (value == null)
            return ParsedValue.error("Null value");
        
        return new ParsedValue.So<T>(value);
    }
    
    public static <T> ParsedValue<T> error(String message)
    {
        return new ParsedValue.Not<T>(message);
    }
    
    private static class So<T> extends ParsedValue<T>
    {
        private T value;
        
        private So(T value)
        {
            this.value = value;
        } 
        
        public boolean isParsed()
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
        
        public String getError()
        {
            throw new UnsupportedOperationException();
        }
    }
    
    private static class Not<T> extends ParsedValue<T>
    {
        private String message;

        public Not(String message)
        {
            this.message = message;
        }

        public boolean isNotParsed()
        {
            return true;
        }
        
        public String getError()
        {
            return this.message;
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