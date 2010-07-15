package web.examples;

import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

/**
 * This class is an example of a custom InputDataType.
 * 
 * It converts a string value into an instance of EmploymentStatus enum.
 */
public class EmploymentStatusDataType implements InputDataType<EmploymentStatus>
{
    public Class<EmploymentStatus> getInputDataClass()
    {
        return EmploymentStatus.class;
    }
    
    public ParsedValue<EmploymentStatus> parseValue(String value)
    {
        try
        {
            EmploymentStatus status = EmploymentStatus.getByID(Integer.parseInt(value));
            return ParsedValue.setUnlessNull(status);
        }
        catch (Exception ex)
        {
            return ParsedValue.error(ex.getMessage());
        }
    }
    
}
