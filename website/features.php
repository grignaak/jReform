<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>jReform - Features</title>
  <link rel="stylesheet" href="style.css" type="text/css">
</head>
<body>

<div class="header" id="header"><?php include("inc/header.php");?></div>
<div class="menu" id="menu"><?php include("inc/menu.php");?></div>
<div class="main" id="main">

<h3>jReform Features</h3>
<ul>
  <li><a href="#inputTypes">Input types</a>
  <li><a href="#dataTypes">Input data types</a>
  <li><a href="#criteria">Criteria</a>
</ul>

<h3><a name="inputTypes">Input Types</a></h3>

<p>jReform supports all types of HTML input controls.
Each input class has its own validation rules and slightly different
behaviour. All input types with the exception of a single value checkbox
can be specified as either required or optional. A single value checkbox
cannot be specified as a required input because when unchecked, nothing is
submitted with the request and its value is <code>null</code>.
</p>

<p>Checkbox, select and radio inputs have a state associated with them that
indicates whether the input is <code>checked</code> or <code>selected</code>.
This allows the form to maintain its state in cases when validation fails.
</p>

<p>Inputs also have support for error messages that can be displayed
to the user when submitted values are not valid. Default error messages
can be overridden with custom messages where necessary.
</p>

<table width="400">
  <tr class="header">
    <td>Input Type</td>
    <td>jReform Type</td>
  </tr>
  <tr>
    <td><tt>text/textarea/hidden</tt></td>
    <td><tt>org.jreform.Input</tt></td>
  </tr>
  <tr>
    <td><tt>select</tt></td>
    <td><tt>org.jreform.Select</tt></td>
  </tr>
  <tr>
    <td><tt>multi value select</tt></td>
    <td><tt>org.jreform.MultiSelect</tt></td>
  </tr>
  <tr>
    <td><tt>checkbox</tt></td>
    <td><tt>org.jreform.Checkbox</tt></td>
  </tr>
  <tr>
    <td><tt>multi value checkbox</tt></td>
    <td><tt>org.jreform.MultiCheckbox</tt></td>
  </tr>
  <tr>
    <td><tt>radio button</tt></td>
    <td><tt>org.jreform.Radio</tt></td>
  </tr>

</table>

<h3><a name="dataTypes">Input Data Types</a></h3>

<p><code>InputDataType</code> is the Java type of an input's value and it is specified
when the input is created. All values submitted through the form are automatically converted
into specified data types. When conversion fails the form is deemed invalid.
</p>

<p>A few common types are supported out of the box, although the framework can be easily extended
to define new types as required.
</p>

<table width="400">
  <tr class="header">
    <td>Input Data Type</td>
    <td>Java Type</td>
  </tr>
  <tr>
    <td><tt>StringType</tt></td>
    <td><tt>java.lang.String</tt></td>
  </tr>
  <tr>
    <td><tt>IntType</tt></td>
    <td><tt>java.lang.Integer</tt></td>
  </tr>
  <tr>
    <td><tt>DoubleType</tt></td>
    <td><tt>java.lang.Double</tt></td>
  </tr>
  <tr>
    <td><tt>FloatType</tt></td>
    <td><tt>java.lang.Float</tt></td>
  </tr>
  <tr>
    <td><tt>LongType</tt></td>
    <td><tt>java.lang.Long</tt></td>
  </tr>
  <tr>
    <td><tt>ShortType</tt></td>
    <td><tt>java.lang.Short</tt></td>
  </tr>
  <tr>
    <td><tt>CharType</tt></td>
    <td><tt>java.lang.Character</tt></td>
  </tr>
  <tr>
    <td><tt>DateType</tt></td>
    <td><tt>java.util.Date</tt></td>
  </tr>
</table>


<h3><a name="criteria">Criteria</a></h3>
<p>Criteria provide a simple way to place certain restrictions on input values.
If an input does not meet all of its criteria, it is deemed invalid and form validation fails.
An example of a typical criterion is a string length requirement, or a range of acceptable values
for a number. Note that not all criteria are applicable to all data types. For example,
if a numeric range restriction is placed on a string input, an exception will be thrown at runtime.
</p>

<p>The following table provides a brief summary of main criteria types.
It is also possible to define custom criteria.
</p>

<table width="100%">
  <tr class="header">
    <td>Criterion</td>
    <td>Description</td>
  </tr>
  <tr>
    <td><tt>And, Or</tt></td>
    <td>Allows perform logical operations on two or more criteria.</td>
  </tr>
  <tr>
    <td><tt>Email</tt></td>
    <td>String conforms to email address format.</td>
  </tr>
  <tr>
    <td><tt>Min, Max</tt></td>
    <td>Input is not below/above the Min/Max value.</td>
  </tr>
  <tr>
    <td><tt>ExactLength</tt></td>
    <td>String length must equal given length value.</td>
  </tr>
  <tr>
    <td><tt>Length(min, max)</tt></td>
    <td>String length must be within the given range.</td>
  </tr>
  <tr>
    <td><tt>MinLength / MaxLength</tt></td>
    <td>String length must not be less/greater than MinLength/MaxLength.</td>
  </tr>
  <tr>
    <td><tt>Range(min, max)</tt></td>
    <td>Input value must be within the given range. Equivalent to <tt>Min(min) && Max(max).</tt></td>
  </tr>
  <tr>
    <td><tt>Regex</tt></td>
    <td>String must match the specified regular expression.</td>
  </tr>
  <tr>
    <td><tt>StartsWith</tt></td>
    <td>String must start with given value.</td>
  </tr>
  <tr>
    <td><tt>StringValues</tt></td>
    <td>String must equal one of the specified values (allows <code>ignoreCase</code>)</td>
  </tr>
  <tr>
    <td><tt>Accept</tt></td>
    <td>Same as <tt>AcceptString</tt> but applicable to any <tt>java.lang.Comparable</tt></td>
  </tr>
</table>

<br>
<p><b>A few examples of using criteria</b></p>

<p>Password field must be at least 6 characters long</p>
<pre class="code">input(stringType(), "password", minLength(6));</pre>

<p>Quantity is an integer whose value must be greater than or equal to 1</p>
<pre class="code">input(intType(), "quantity", min(1));</pre>

<p>Call-back date must be of the format dd-mm-yyyy and must not be a past date</p>
<pre class="code">input(dateType("dd-MM-yyyy"), "callBack", min(new Date()));</pre>

<p><b>Custom criteria</b></p>
<p>A custom criterion can be implemented by extending the
<a class="api_link" href="docs/org/jreform/criteria/AbstractCriterion.html">AbstractCriterion</a> class.
This class contains a method that verifies the passed in value and provides a default error message
that can be displayed to the user if the value does not satisfy the criterion.</p>

</div>
<div id="footer"><?php include("inc/footer.php");?></div>
<?php include("inc/ga.php");?>
</body>
</html>
