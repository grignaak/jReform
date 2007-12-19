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

<h3>Introduction</h3>
<p>Handling data from HTML forms is a common task facing web developers.
Typically this results in a lot of boilerplate code checking for null values
and empty strings, for example</p>

<pre class="code">
String username = req.getParameter("username");

if(username != null && !username.trim().equals("")) {
    // do
}
</pre>

<p>Or if a value needs to be parsed into a number:</p>

<pre class="code">
int userId;

try {
   userId = Integer.parseInt(req.getParameter("userId"));
}
catch(NumberFormatException e) {
    // handle
}
</pre>

<p>jReform offers a simple solution for validating forms
with very few lines of code and without any XML files.
</p>

<h3>Creating a form</h3>
<p>A form is defined as an object extending the <a href="docs/org/jreform/HtmlForm.html" class="api_link">HtmlForm</a> class. This base class contains methods for adding inputs to the
form and validating its values. For example, a form with fields <code>username</code>
and <code>userId</code> will look as follows:</p>

<pre class="code">
public class SimpleForm extends HtmlForm {

    public UserProfileForm() {
        input(stringType(), "username");
        input(intType(), "userId");
    }
}
</pre>

<p>Input names refer to the name attribute of an input. The type variables, <code>stringType()</code>
and <code>intType()</code>, specify the type that input data should be converted to. jReform will validate
that user's input can be converted to the proper type.
</p>

<pre class="code">
&lt;input type="text" name="username"&gt;
&lt;input type="text" name="userId"&gt;
</pre>

<h3>Validating a form</h3>
<p>To validate the form we simply call the <code>validate(HttpServletRequest)</code> method.
This method will check that all required values have been given and will be convert those values into
specified type. If validation is successfull input values can be accessed as follows:</p>

<pre class="code">
SimpleForm form = new SimpleForm();

if(form.validate(req)) {
    String username = form.getStringValue("username");
    Integer userId = (Integer)form.getValue("userId");
}
</pre>

<p>To avoid casting and specifying input names each time a value needs to be accessed,
add get methods to the form class. The compete form class will therefore looks as follows:</p>


<pre class="code">
public class SimpleForm extends HtmlForm {

    public UserProfileForm() {
        input(stringType(), "username");
        input(intType(), "userId");
    }

    public Input<String> getUsername() {
        return getInput("username");
    }
    
    public Input<Integer> getUserId() {
        return getInput("userId");
    }
}
</pre>

<p>This makes the servlet code even more readable:</p>

<pre class="code">
String username = form.getUsername().getValue();
Integer userId = form.getUserId().getValue();
</pre>

<p>The getters can also help improve the JSP page. The hard-coded input
names can be removed which reduces the scope for errors.</p>

<pre class="code">
&lt;input type="text" name="${form.username.inputName}"&gt;
&lt;input type="text" name="${form.userId.inputName}"&gt;
</pre>

<h3>More information</h3>
<p>More detailed examples of how to use jReform (with downloadable source code) can be
found <a href="examples.php">here</a>. There is also a section describing main
<a href="features.php">features</a> of the framework.</p>


</div>
<div id="footer"><?php include("inc/footer.php");?></div>
</body>
</html>
