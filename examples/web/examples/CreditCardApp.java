package web.examples;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CreditCardApp extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        doPost(req, res);
    }
    
    @SuppressWarnings("unused")
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        CreditCardAppForm form = new CreditCardAppForm();
        
        if(req.getParameter("submit") != null)
        {
            if(form.validateRequest(req))
            {
                // Grab values from the form
                String firstName = form.getFirstName().getValue();
                String lastName = form.getLastName().getValue();
                Date dob = form.getDob().getValue();
                Boolean isCustomer = form.getHasAccountWithUs().getValue();
                String email = form.getEmail().getValue();
                EmploymentStatus status = form.getEmploymentStatus().getValue();
                Integer monthlyIncome = form.getMonthlyIncome().getValue();
                
                // etc...
            }
        }
        
        req.setAttribute("form", form);
        getServletContext().getRequestDispatcher("/CreditCardApp.jsp").forward(req, res);
    }
    
}
