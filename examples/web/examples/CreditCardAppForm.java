package web.examples;

import static org.jreform.criteria.Criteria.emailAddress;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Group;
import org.jreform.impl.GenericForm;
import org.jreform.inputs.Input;
import org.jreform.inputs.Radio;
import org.jreform.inputs.Select;
import org.jreform.types.Types;

/**
 * A credit card application form.
 */
public class CreditCardAppForm extends GenericForm
{
    private static final EmploymentStatusDataType employmentType =
        new EmploymentStatusDataType();

    private HttpServletRequest request;

    public CreditCardAppForm()
    {
        // default no-arg contructor
    }
    
    @SuppressWarnings("unchecked")
    public CreditCardAppForm(HttpServletRequest request)
    {
        this.request = request;
        
        radio(Types.stringType(), "title");
        input(Types.stringType(), "firstName");
        input(Types.stringType(), "lastName");
        input(Types.dateType("dd-MM-yyyy"), "dob");
        
        input(Types.stringType(), "address");
        input(Types.stringType(), "city");
        radio(Types.stringType(), "ownOrRent");
        input(Types.stringType(), "email", emailAddress());
        input(Types.stringType(), "phoneNumber");

        // uses a custom InputDataType
        select(employmentType, "employmentStatus");
        
        Group employer = optionalGroup("employer");
        employer.input(Types.stringType(), "company");
        employer.input(Types.stringType(), "businessPhoneNumber");
        
        radio(Types.booleanType(), "hasAccountWithUs");

        Group account = optionalGroup("accountDetails");
        account.select(Types.stringType(), "accountType");
        account.input(Types.intType(), "accountNumber");
        account.input(Types.intType(), "branchNumber");

        input(Types.intType(), "monthlyIncome");
        input(Types.intType(), "monthlyExpenses");

    }

    /**
     * Additional validation:
     * 
     * If user is Employed, check that employment details were provided.
     * If user is a current customer, check that account details were provided.
     */
    @Override
    protected void additionalValidate()
    {
        EmploymentStatus empStatus = getEmploymentStatus().getValue();
        Group employerInfo = getGroup("employer");
        
        if(empStatus == EmploymentStatus.EMPLOYED)
        {
            employerInfo.setRequired(true);
            employerInfo.validateRequest(request);
        }
        
        Group accountDetails = getGroup("accountDetails");
        
        if(getHasAccountWithUs().getValue() == Boolean.TRUE)
        {
            accountDetails.setRequired(true);
            accountDetails.validateRequest(request);
        }
    }
    
    
    //
    // Getters
    //
    
    public Radio<String> getTitle()
    {
        return getRadio("title");
    }

    public Input<String> getFirstName()
    {
        return getInput("firstName");
    }

    public Input<String> getLastName()
    {
        return getInput("lastName");
    }

    public Input<Date> getDob()
    {
        return getInput("dob");
    }

    public Input<String> getAddress()
    {
        return getInput("address");
    }

    public Input<String> getCity()
    {
        return getInput("city");
    }

    public Radio<String> getOwnOrRent()
    {
        return getRadio("ownOrRent");
    }

    public Input<String> getEmail()
    {
        return getInput("email");
    }

    public Input<String> getPhoneNumber()
    {
        return getInput("phoneNumber");
    }

    public Select<EmploymentStatus> getEmploymentStatus()
    {
        return getSelect("employmentStatus");
    }

    public Input<String> getCompany()
    {
        return getInput("company");
    }

    public Input<String> getBusinessPhoneNumber()
    {
        return getInput("businessPhoneNumber");
    }

    public Radio<Boolean> getHasAccountWithUs()
    {
        return getRadio("hasAccountWithUs");
    }

    public Input<String> getAccountType()
    {
        return getInput("accountType");
    }

    public Input<Integer> getAccountNumber()
    {
        return getInput("accountNumber");
    }

    public Input<Integer> getBranchNumber()
    {
        return getInput("branchNumber");
    }

    public Input<Integer> getMonthlyIncome()
    {
        return getInput("monthlyIncome");
    }

    public Input<Integer> getMonthlyExpenses()
    {
        return getInput("monthlyExpenses");
    }
    
    public EmploymentStatus[] getEmploymentStatusValues()
    {
        return EmploymentStatus.values();
    }
    
}
