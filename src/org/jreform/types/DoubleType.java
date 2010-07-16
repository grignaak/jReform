package org.jreform.types;


public final class DoubleType extends AbstractNumberType<Double>
{
    public Double parseNumber(String value)
    {
        return Double.valueOf(value);
    }
    
    public Class<Double> getInputDataClass()
    {
        return Double.class;
    }
    
}
