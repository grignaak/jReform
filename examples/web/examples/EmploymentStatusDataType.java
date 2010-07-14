package web.examples;

import org.jreform.InputDataType;
import org.jreform.util.Maybe;

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
    
    public Maybe<EmploymentStatus> parseValue(String value)
    {
        try
        {
            EmploymentStatus status = EmploymentStatus.getByID(Integer.parseInt(value));
            return Maybe.soUnlessNull(status);
        }
        catch(Exception ex)
        {
            return Maybe.not();
        }
    }
    
}
