package web.examples;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for accessing/updating the user profile page.
 */
@SuppressWarnings("serial")
public class UserProfile extends HttpServlet
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
        UserProfileForm form = new UserProfileForm();
        
        if(req.getParameter("submit") != null)
        {
            // Validate form data
            if(form.validateRequest(req))
            {
                String fullName = form.getFullName().getValue();
                String phone = form.getPhone().getValue();
				int age = form.getAge().getValue();
                char gender = form.getGender().getValue();
                
                // process data..
            }
        }
        
        req.setAttribute("form", form);
        getServletContext().getRequestDispatcher("/UserProfile.jsp").forward(req, res);
    }
    
}
