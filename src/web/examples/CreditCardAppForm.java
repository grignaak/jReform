package web.examples;

import static org.jreform.criteria.Criteria.emailAddress;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Group;
import org.jreform.HtmlFormSupport;
import org.jreform.Input;
import org.jreform.inputs.Radio;
import org.jreform.inputs.Select;

/**
 * A credit card application form.
 */
public class CreditCardAppForm extends HtmlFormSupport
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
        
        radio(stringType(), "title");
        input(stringType(), "firstName");
        input(stringType(), "lastName");
        input(dateType("dd-MM-yyyy"), "dob");
        
        input(stringType(), "address");
        input(stringType(), "city");
        radio(stringType(), "ownOrRent");
        input(stringType(), "email", emailAddress());
        input(stringType(), "phoneNumber");

        // uses a custom InputDataType
        select(employmentType, "employmentStatus");
        
        Group employer = optionalGroup("employer");
        employer.input(stringType(), "company");
        employer.input(stringType(), "businessPhoneNumber");
        
        radio(booleanType(), "hasAccountWithUs");

        Group account = optionalGroup("accountDetails");
        account.select(stringType(), "accountType");
        account.input(intType(), "accountNumber");
        account.input(intType(), "branchNumber");

        input(intType(), "monthlyIncome");
        input(intType(), "monthlyExpenses");

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
            employerInfo.validate(request);
        }
        
        Group accountDetails = getGroup("accountDetails");
        
        if(getHasAccountWithUs().getValue() == Boolean.TRUE)
        {
            accountDetails.setRequired(true);
            accountDetails.validate(request);
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
