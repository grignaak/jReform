package org.jreform.types;


public final class IntType extends AbstractNumberType<Integer>
{
    protected Integer parseNumber(String value)
    {
        return Integer.valueOf(value);
    }
    
    public Class<Integer> getInputDataClass()
    {
        return Integer.class;
    }
}
