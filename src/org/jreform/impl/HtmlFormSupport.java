package org.jreform.impl;

import java.util.Date;

import org.jreform.Group;
import org.jreform.InputDataType;
import org.jreform.types.BooleanType;
import org.jreform.types.CharType;
import org.jreform.types.DateType;
import org.jreform.types.DoubleType;
import org.jreform.types.FloatType;
import org.jreform.types.IntType;
import org.jreform.types.LongType;
import org.jreform.types.ShortType;
import org.jreform.types.StringType;

/**
 * This is a base class that should be extended to create a form.
 * 
 * @author armandino (at) gmail.com
 */
public class HtmlFormSupport extends DefaultForm
{
    // --------------------------------------------------------------------
    // ---------------- Types supported out-of-the-box---------------------
    // --------------------------------------------------------------------
    
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

    protected final Group requiredGroup(String name)
    {
        return addNewGroup(name, true);
    }

    protected final Group optionalGroup(String name)
    {
        return addNewGroup(name, false);
    }

    private Group addNewGroup(String name, boolean isRequired)
    {
        Group group = new DefaultGroup(name, isRequired);
        addGroup(group);
        return group;
    }
    
}
