<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>jReform - open source Java framework for processing HMTL forms</title>
  <link rel="stylesheet" href="style.css" type="text/css">
</head>
<body>

<div class="header" id="header"><?php include("inc/header.php");?></div>
<div class="menu" id="menu"><?php include("inc/menu.php");?></div>
<div class="main" id="main">

<h3>Simple User Profile Form</h3>
<p>This example describes how to create a simple user profile form
that has the following fields:</p>

<ul>
  <li>Full Name (required) - a field whose value must be between 5 and 32 characters long.
  <li>Age (required) - an integer field that must be between 18 and 99.
  <li>Gender (required) - a one-letter field that accepts values 'M' or 'F'.
  <li>Phone (optional) - if filled out, the value must be of the following format: 123-456-7890.
</ul>

<p>To achieve this goal we need</p>

<ol>
   <li>a form class that creates the input fields and validates itself
   <li>JSP page 
   <li>the servlet that handles the data
</ol>

<h3>The Form Class</h3>

<p>A form can be created by extending the <code>org.jreform.HtmlForm</code>
class. Inputs can be added to the form using the <code>input(...)</code>
methods as shown below. These methods specify</p>

<ul>
  <li>type of the input's data (e.g. string or integer)
  <li>name of the input
  <li>and optionally, criteria the input's data must satisfy (e.g. age must be between 18 and 99)
</ul>

<pre class="code">
public class UserProfileForm extends HtmlForm {
    private static final String PHONE = "\\d{3}+-\\d{3}+-\\d{4}+";
    
    public UserProfileForm() {

        // A text input that must be between 5 and 32 characters long
        <b>input(stringType(), "fullName", length(5, 32));</b>
        
        // An integer field with a value between 18 and 99 inclusive
        <b>input(intType(), "age", range(18, 99));</b>
        
        // A character field that accepts 'M' or 'F' only
        <b>input(charType(), "gender", accept('M', 'F'));</b>
        
        // Optional phnoe number field with the following format: 123-456-7777
        // The length of the string must be exactly 12 characters
        <b>input(stringType(), "phone", exactLength(12), regex(PHONE)).optional();</b>
    }
    
    //
    // Convenience methods to get input values.
    //

    public Input<String> getFullName() {
        return getInput("fullName");
    }
    
    public Input<Integer> getAge() {
        return getInput("age");
    }
    
    public Input<Character> getGender() {
        return getInput("gender");
    }
    
    public Input<String> getPhone() {
        return getInput("phone");
    }
}
</pre>


<h3>The JSP Page</h3>

<p>The JSP page is very straightforward. It simply contains a form with all the input fields listed above.</p>


<pre class="code">
&lt;jsp:useBean id="form" class="org.jreform.HtmlForm"/&gt;

&lt;form action="UserProfile" method="post"&gt;

Full name:
&lt;div class="error"&gt;<b>${form.fullName.messageOnError}</b>&lt;/div&gt;
&lt;input type="text" name="<b>${form.fullName.inputName}</b>" value="<b>${form.fullName}</b>"&gt;&lt;br&gt;&lt;br&gt;

Age:
&lt;div class="error"&gt;<b>${form.age.messageOnError}</b>&lt;/div&gt;
&lt;input type="text" name="<b>${form.age.inputName}</b>" value="<b>${form.age}</b>"&gt;&lt;br&gt;&lt;br&gt;

Gender (M / F):
&lt;div class="error"&gt;<b>${form.gender.messageOnError}</b>&lt;/div&gt;
&lt;input type="text" name="<b>${form.gender.inputName}</b>" value="<b>${form.gender}</b>"&gt;&lt;br&gt;&lt;br&gt;

Phone number (123-456-7890)
&lt;div class="error"&gt;<b>${form.phone.messageOnError}</b>&lt;/div&gt;
&lt;input type="text" name="<b>${form.phone.inputName}</b>" value="<b>${form.phone}</b>"&gt; (Optional)&lt;br&gt;&lt;br&gt;

&lt;input type="submit" name="submit" value="submit"&gt;
&lt;/form&gt;
</pre>


<h3>The Servlet</h3>
<p>Finally, we create a servlet to handle the form data. The validation takes place by passing
down the <code>HttpServletRequest</code> to the <code>validate(...)</code> method. When it's
invoked, each input field is tested against the criteria specified in the form class.</p>

<p>Once validated, input values can be retrieved as whatever type specified in the form class.</p>


<p>If validation fails, the form is passed back to the JSP page, where specific error messages
will be displayed to the user.</p>


<pre class="code">
protected void doPost(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    UserProfileForm form = new UserProfileForm();

    if(req.getParameter("submit") != null) {

       // Validate form data
       if(form.validate(req)) {
           String fullName = form.getFullName().getValue();
           String phone = form.getPhone().getValue();
           int age = form.getAge().getValue();
           char gender = form.getGender().getValue();

           // process data..
       }
    }

    req.setAttribute("form", form);
    getServletContext().getRequestDispatcher("/UserProfile.jsp")
	.forward(req, res);
}
</pre>

<h3>Download Example</h3>

<p>The source code for this example is included in source archives
available from the <a href="download.php">downloads page</a> or from
<a href="http://sourceforge.net/project/platformdownload.php?group_id=198446">Sourceforge</a>. Use the <code>war</code> target to generate a web archive
using Ant.
</p>

</div>
<div id="footer"><?php include("inc/footer.php");?></div>
<?php include("inc/ga.php");?>
</body>
</html>
