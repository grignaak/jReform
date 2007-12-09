package org.jreform.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jreform.core.InputDataType;

public final class DateType implements InputDataType<Date>
{
    private DateFormat dateFormat;
    
    private DateType(String dateFormatPattern)
    {
        dateFormat = new SimpleDateFormat(dateFormatPattern);
    }
    
    // TODO: test the parseValue method - dateFormat sometimes doesn't
    //       fail if the input string is "invalid"
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
    
    public Class<Date> getType()
    {
        return Date.class;
    }
    
    public static InputDataType<Date> dateType(String dateFormatPattern)
    {
        return new DateType(dateFormatPattern);
    }
    
}
