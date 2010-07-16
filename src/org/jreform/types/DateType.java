package org.jreform.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jreform.util.ParsedValue;

public final class DateType extends AbstractType<Date>
{
    private DateFormat dateFormat;
    
    public DateType(String dateFormatPattern)
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
}
