package org.jreform.types;


public final class ShortType extends AbstractNumberType<Short>
{
    public Short parseNumber(String value)
    {
        return Short.valueOf(value);
    }
    
    public Class<Short> getInputDataClass()
    {
        return Short.class;
    }    
}
