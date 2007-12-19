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

<h3>What is jReform?</h3>
<p><b>jReform</b> is a library for processing HTML forms that aims to simplify web
development by automating validation of form inputs. Its emphasis is on simplicity and
speed of development. No xml or configuration files are required.</p>

<ul>
 <li>Inputs are evaluated according to predefined criteria
 <li>Validation is performed behind the scenes, allowing developer to concentrate on application logic.
 <li>Additional manual validation can be be added if necessary.
</ul>

<h3>How does it work?</h3>
<p><b>jReform</b> allows the developer to specify data type of an input field and, optionally, criteria which the input's value must meet to be valid. Once validated, input values are converted into specified Java types. Alternatively, the form can be used to create or populate beans with properties that match form's input names.</p>

<h3>Best place to start...</h3>
<p>See the <a href="overview.php">overview page</a> for a description of main features of the framework with a simple example.</p>


</div>
<div id="footer"><?php include("inc/footer.php");?></div>
</body>
</html>
