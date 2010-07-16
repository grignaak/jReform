package org.jreform.types;


public final class LongType extends AbstractNumberType<Long>
{
    public Long parseNumber(String value)
    {
        return Long.valueOf(value);
    }
    
    public Class<Long> getInputDataClass()
    {
        return Long.class;
    }
}
