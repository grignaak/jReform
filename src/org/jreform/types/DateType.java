package org.jreform.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

public final class DateType implements InputDataType<Date>
{
    private DateFormat dateFormat;
    
    private DateType(String dateFormatPattern)
    {
        dateFormat = new SimpleDateFormat(dateFormatPattern);
        dateFormat.setLenient(false);
    }
    
    public ParsedValue<Date> parseValue(String value)
    {
        try
        {
            return ParsedValue.setUnlessNull(dateFormat.parse(value));
        }
        catch (ParseException ex)
        {
            return ParsedValue.error(ex.getMessage());
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
