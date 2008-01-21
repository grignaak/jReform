package org.jreform.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jreform.InputDataType;

public final class DateType implements InputDataType<Date>
{
    private DateFormat dateFormat;
    
    private DateType(String dateFormatPattern)
    {
        dateFormat = new SimpleDateFormat(dateFormatPattern);
        dateFormat.setLenient(false);
    }
    
    public Date parseValue(String value)
    {
        try
        {
            return dateFormat.parse(value);
        }
        catch(ParseException ex)
        {
            return null;
        }
    }
    
    public Class<Date> getInputDataClass()
    {
        return Date.class;
    }
    
    public static InputDataType<Date> dateType(String dateFormatPattern)
    {
        return new DateType(dateFormatPattern);
    }
    
}
