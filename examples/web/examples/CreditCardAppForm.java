package web.examples;

import static org.jreform.criteria.Criteria.emailAddress;

import java.util.Date;

import org.jreform.Group;
import org.jreform.impl.GenericForm;
import org.jreform.inputs.Input;
import org.jreform.types.Types;

/**
 * A credit card application form.
 */
public class CreditCardAppForm extends GenericForm
{
    private static final EmploymentStatusDataType employmentType =
        new EmploymentStatusDataType();
    public CreditCardAppForm()
    {
        radio(Types.stringType(), "title");
        input(Types.stringType(), "firstName");
        input(Types.stringType(), "lastName");
        input(Types.dateType("dd-MM-yyyy"), "dob");
        
        input(Types.stringType(), "address");
        input(Types.stringType(), "city");
        radio(Types.stringType(), "ownOrRent");
        input(Types.stringType(), "email").criterion(emailAddress());
        input(Types.stringType(), "phoneNumber");

        // uses a custom InputDataType
        input(employmentType, "employmentStatus");
        
        Group employer = optionalGroup("employer");
        employer.input(Types.stringType(), "company");
        employer.input(Types.stringType(), "businessPhoneNumber");
        
        radio(Types.booleanType(), "hasAccountWithUs");

        Group account = optionalGroup("accountDetails");
        account.input(Types.stringType(), "accountType");
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
            employerInfo.validate();
        }
        
        Group accountDetails = getGroup("accountDetails");
        
        if(getHasAccountWithUs().getValue())
        {
            accountDetails.setRequired(true);
            accountDetails.validate();
        }
    }
    
    
    //
    // Getters
    //
    
    public Input<String> getTitle()
    {
        return getInput("title");
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

    public Input<String> getOwnOrRent()
    {
        return getInput("ownOrRent");
    }

    public Input<String> getEmail()
    {
        return getInput("email");
    }

    public Input<String> getPhoneNumber()
    {
        return getInput("phoneNumber");
    }

    public Input<EmploymentStatus> getEmploymentStatus()
    {
        return getInput("employmentStatus");
    }

    public Input<String> getCompany()
    {
        return getInput("company");
    }

    public Input<String> getBusinessPhoneNumber()
    {
        return getInput("businessPhoneNumber");
    }

    public Input<Boolean> getHasAccountWithUs()
    {
        return getInput("hasAccountWithUs");
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
