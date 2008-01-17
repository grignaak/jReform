<jsp:useBean id="form" class="org.jreform.HtmlForm" scope="request" />

<html>
<head>
<style type="text/css">div.error {color: red;}</style>
</head>
<body>
<h3>User Profile Page</h3>

<form action="UserProfile" method="post">

Full name:
<div class="error">${form.fullName.onError}</div>
<input type="text" name="${form.fullName.inputName}" value="${form.fullName}"><br><br>

Age:
<div class="error">${form.age.onError}</div>
<input type="text" name="${form.age.inputName}" value="${form.age}"><br><br>

Gender (M / F):
<div class="error">${form.gender.onError}</div>
<input type="text" name="${form.gender.inputName}" value="${form.gender}"><br><br>

Phone number (123-456-7890)
<div class="error">${form.phone.onError}</div>
<input type="text" name="${form.phone.inputName}" value="${form.phone}"> (Optional)<br><br>

<input type="submit" name="submit" value="submit">

</form>

</body>
</html>
