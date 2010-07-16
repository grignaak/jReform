package org.jreform.types;


public class Types
{

    public static BooleanType booleanType()
    {
        return new BooleanType();
    }

    public static CharType charType()
    {
        return new CharType();
    }

    public static ShortType shortType()
    {
        return new ShortType();
    }

    public static IntType intType()
    {
        return new IntType();
    }

    public static LongType longType()
    {
        return new LongType();
    }

    public static DoubleType doubleType()
    {
        return new DoubleType();
    }

    public static FloatType floatType()
    {
        return new FloatType();
    }

    public static StringType stringType()
    {
        return new StringType();
    }

    public static DateType dateType(String dateFormatPattern)
    {
        return new DateType(dateFormatPattern);
    }

}
