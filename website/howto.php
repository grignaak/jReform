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

<h3>HOWTOs</h3>

<p class="howto_title">Create a required input</p>
<p>The following code snippet creates a required input of type string
whose length must be between 6 and 12 characters long. A required input
is valid when it has a value and its value satisfies all criteria.
</p>
<pre class="code">
input(stringType(), "username", length(6, 12));
</pre>

<p class="howto_title">Create an optional input</p>
<p>An input can be flagged as optional by invoking the
<code>optional()</code> method. An optional input is valid either
if it has no value, or if its value satisfies all criteria.
In this case, the input is valid if it is left blank or if its
length is at least 8 characters long.</p>
<pre class="code">
input(stringType(), "address", minLength(8)).optional();
</pre>

<p class="howto_title">Create an input with a int value</p>
<p>This creates a required input whose value must be an integer.
If the value provided is not an integer or if it is less than 0,
the input is considered invalid.
</p>
<pre class="code">
input(intType(), "positive", min(0));
</pre>

<p class="howto_title">Specify a custom error message</p>
<p>Each criterion has a default error message that describes
a validation error. The default message can be overridden
where necessary by invoking the <code>setOnError(...)</code>
method. The JSP page can display the error message by invoking the
<code>getOnError()</code> method. Note that if the input validates
successfully <code>getOnError()</code> returns an empty string,
eliminating the need for checking whether the input is valid.
</p>
<pre class="code">
input(stringType(), "url", startsWith("http"))
	.setOnError("URL must start with http");
</pre>

<p class="howto_title">Using criteria that represent logical operators</p>
<p>Criteria can be combined using <code>and(Criterion...c)</code>
or <code>or(Criteria...c)</code> criteria. Both expect at least two
arguments otherwise an exception will be thrown.
</p>
<p>In the first example, the input must either start with 'http' or
with 'www.' for the input to validate successfully. The second
specifies that the value must start with 'http' and be at least
10 characters long.
</p>
<pre class="code">
// using 'or'
input(stringType(), "url", or(startsWith("http"), startsWith("www.")));

// using 'and'
input(stringType(), "url", and(startsWith("http"), minLength(10)));
</pre>

<p class="howto_title">Create a single value checkbox</p>
<p>A single value checkbox has a single value that is either checked
or unchecked. Since an unchecked checkbox does not submit a value with
the request (that is, its value is <code>null</code>), it is always
treated as an optional input.
</p>
<pre class="code">
checkbox(stringType(), "receiveNewsletter");

// jsp
&lt;input type="checkbox" name="receiveNewsletter"&gt; Subscribe to newsletter
</pre>

<p class="howto_title">Create a multi value checkbox</p>
<p>Unlike a single value checkbox, a checkbox with multiple values
can be both, required or optional. The code snippet below creates a
checkbox named 'toppings' that will only validate successfully
if it has one of the values passed to the <code>accept(...)</code>
criterion. Since this is a required input, at least one checkbox must
be checked. If nothing is checked the input is deemed invalid.
</p>
<pre class="code">
multiCheckbox(stringType(), "toppings", accept("peppers", "olives", "ham"));

// jsp
&lt;input type="checkbox" name="toppings" value="peppers"&gt; Peppers
&lt;input type="checkbox" name="toppings" value="olives"&gt; Olives
&lt;input type="checkbox" name="toppings" value="ham"&gt; Ham
</pre>

<p class="howto_title">Create a select input</p>
<p>The following example creates a select input. This input supports only
a single selection. In this example, the default option 'Select a country'
has a value that is just an empty string. Therefore, if no selection is
made validation will fail since this is a required input.
</p>
<pre class="code">
select(stringType(), "country");

// jsp
&lt;select name="country"&gt;
  &lt;option value=""&gt;Select a country&lt;/option&gt;
  &lt;option value="Spain"&gt;&lt;/Spain&gt;
  &lt;option value="Italy"&gt;Italy&lt;/option&gt;
  &lt;option value="France"&gt;France&lt;/option&gt;
&lt;/select&gt;
</pre>

<p class="howto_title">Create a multiple select</p>
<p>A multiple select input allows several selections to be submitted at once.
This code snippet is an example of a required multiple select. It requires
at least one selection to validate successfully.
</p>
<pre class="code">
multiSelect(stringType(), "colours");

// jsp
&lt;select name="colours" multiple size="3"&gt;
  &lt;option value="Red"&gt;Red&lt;/option&gt;
  &lt;option value="Green"&gt;Green&lt;/option&gt;
  &lt;option value="Blue"&gt;Blue&lt;/option&gt;
&lt;/select&gt;
</pre>

<p class="howto_title">Create a radio button</p>
<p>This is an example of a radio button with two values. The
<code>accept(...)</code> criterion restricts the choices to 'yes' and 'no'.
If any other value (or no value at all) is submitted validation will fail.
</p>
<pre class="code">
radio(stringType(), "acceptTerms", accept("yes", "no"));

// jsp
&lt;input type="radio" name="acceptTerms" value="yes"&gt; Yes
&lt;input type="radio" name="acceptTerms" value="no"&gt; No
</pre>


<p class="howto_title">Create an input group</p>
<p>An input group provides a convenient way of grouping related inputs
together. This allows a large form to be split into several groups.
For examples, a long application form can be split into subsections
that group together contact information, bank account details, etc.
In the following example, both groups are required. Therefore, all
required inputs within the group must have valid values for the form
to validate successfully.
</p>
<pre class="code">
Group contactDetails = requiredGroup("contactDetails");
group.input(stringType(), "address");
group.input(stringType(), "phoneNumber");
group.input(stringType(), "emailAddress").optional();

Group accountInfo = requiredGroup("accountInfo");
group.input(stringType(), "bankName");
group.input(intType(), "accountNumber");
group.input(intType(), "branchCode");

// other subsections ...
</pre>



<p class="howto_title">Create an optional input group</p>
<p>An optional group has different validation rules from a required group.
An optional group is considered valid if all of its inputs are blank,
regardless of whether the inputs are optional or required. However,
if at least one input within the group has a value, then all inputs
are validated as usual. In other words, it follows a 'all or nothing'
principle.
</p>
<p>The following code snippet is an example of an optional group.
If the weight is entered then the group will only validate successfully
if the units are also selected, and vice versa. However the group will
also validate successfully if neither weight nor units are provided.
</p>
<pre class="code">
Group group = optionalGroup("personalInfo");
group.input(intType(), "weight");
group.select(stringType(), "units", accept("kg", "lb"));
</pre>

</div>
<div id="footer"><?php include("inc/footer.php");?></div>
<?php include("inc/ga.php");?>
</body>
</html>
