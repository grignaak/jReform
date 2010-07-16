package org.jreform.types;


public final class FloatType extends AbstractNumberType<Float>
{
    public Float parseNumber(String value)
    {
        return Float.valueOf(value);
    }
    
    public Class<Float> getInputDataClass()
    {
        return Float.class;
    }
    
}
