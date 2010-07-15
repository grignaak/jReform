package org.jreform.types;

import java.util.Date;

import org.jreform.InputDataType;

public class Types
{

    public static InputDataType<Boolean> booleanType()
    {
        return BooleanType.booleanType();
    }

    public static InputDataType<Character> charType()
    {
        return CharType.charType();
    }

    public static InputDataType<Short> shortType()
    {
        return ShortType.shortType();
    }

    public static InputDataType<Integer> intType()
    {
        return IntType.intType();
    }

    public static InputDataType<Long> longType()
    {
        return LongType.longType();
    }

    public static InputDataType<Double> doubleType()
    {
        return DoubleType.doubleType();
    }

    public static InputDataType<Float> floatType()
    {
        return FloatType.floatType();
    }

    public static InputDataType<String> stringType()
    {
        return StringType.stringType();
    }

    public static InputDataType<Date> dateType(String dateFormatPattern)
    {
        return DateType.dateType(dateFormatPattern);
    }

}
