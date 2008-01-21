package web.examples;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        CreditCardAppForm form = new CreditCardAppForm(req);
        
        if(req.getParameter("submit") != null)
        {
            if(form.validate(req))
            {
                // Grab values from the form
                String firstName = form.getFirstName().getValue();
                String lastName = form.getLastName().getValue();
                Date dob = form.getDob().getValue();
                
                //
                // Note that input values can also be accessed directly
                // using the form.getInputValue("xxx") method.
                //
                
                String email = form.getInputValue("email");
                EmploymentStatus status = form.getInputValue("employmentStatus");
                Integer monthlyIncome = form.getInputValue("monthlyIncome");
                
                // etc...
            }
        }
        
        req.setAttribute("form", form);
        getServletContext().getRequestDispatcher("/CreditCardApp.jsp").forward(req, res);
    }
    
}
