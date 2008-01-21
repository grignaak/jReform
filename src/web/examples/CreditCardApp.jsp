<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="web.examples.*" %>
<jsp:useBean id="form" class="org.jreform.HtmlForm" scope="request" />

<html>
<head>
  <title>jReform Example</title>
  <style type="text/css">
      div.error {color: red;}
      td.heading {margin-top: 10px; font-size: 15px; font-weight: bold;}
  </style>
</head>

<body>
<h3>Credit Card Application Form</h3>

<form action="CreditCardApp" method="post">

<table>
  <tr>
    <td>
      <div class="error">${form.title.onError}</div>
      Title:
    </td>
    <td>
      <input type="radio" name="${form.title.inputName}" value="Mr"
             ${form.title.state["Mr"]}> Mr
      <input type="radio" name="${form.title.inputName}" value="Ms"
             ${form.title.state["Ms"]}> Ms
      <input type="radio" name="${form.title.inputName}" value="Mrs"
             ${form.title.state["Mrs"]}> Mrs
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.firstName.onError}</div>
      First name:
    </td>
    <td><input type="text" name="${form.firstName.inputName}"
               value="${form.firstName}">
    </td>
  </tr>


  <tr>
    <td>
      <div class="error">${form.lastName.onError}</div>
      Last name:
    </td>
    <td><input type="text" name="${form.lastName.inputName}"
               value="${form.lastName}"><br><br>
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.dob.onError}</div>
      Date of birth (dd-mm-yyyy):
    </td>
    <td><input type="text" name="${form.dob.inputName}"
               value="${form.dob}">
    </td>
  </tr>

  <tr>
    <td colspan="2" class="heading">Contact Information</td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.address.onError}</div>
      Address:
    </td>
    <td><input type="text" name="${form.address.inputName}"
               value="${form.address}"><br><br>
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.city.onError}</div>
      City:
    </td>
    <td><input type="text" name="${form.city.inputName}"
               value="${form.city}">
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.ownOrRent.onError}</div>
      Do you own or rent a property?
    </td>
    <td>
      <input type="radio" name="${form.ownOrRent.inputName}"
             value="own" ${form.ownOrRent.state["own"]}> Own
      <input type="radio" name="${form.ownOrRent.inputName}"
             value="rent" ${form.ownOrRent.state["rent"]}> Rent
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.email.onError}</div>
      Email:
    </td>
    <td><input type="text" name="${form.email.inputName}"
               value="${form.email}">
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.phoneNumber.onError}</div>
      Phone number
    </td>
    <td><input type="text" name="${form.phoneNumber.inputName}"
               value="${form.phoneNumber}">
    </td>
  </tr>

  <tr>
    <td colspan="2" class="heading">Employment Information</td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.employmentStatus.onError}</div>
      Employment status
    </td>
    <td>
      <select name="${form.employmentStatus.inputName}">
        <option value="None">Select...</option>
        <c:forEach var="status" items="${form.employmentStatusValues}">
        <option value="${status.id}" ${form.employmentStatus.state[status.id]}>
            ${status.desc}
        </option>
        </c:forEach>
      </select>
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.company.onError}</div>
      Company:
    </td>
    <td><input type="text" name="${form.company.inputName}"
               value="${form.company}">
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.businessPhoneNumber.onError}</div>
      Business Phone number
    </td>
    <td><input type="text" name="${form.businessPhoneNumber.inputName}"
               value="${form.businessPhoneNumber}">
    </td>
  </tr>

  <tr>
    <td colspan="2" class="heading">Existing Clients</td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.hasAccountWithUs.onError}</div>
      Do you have an account with us?
    </td>
    <td><input type="radio" name="${form.hasAccountWithUs.inputName}"
               value="yes" ${form.hasAccountWithUs.state["yes"]}> yes
         <input type="radio" name="${form.hasAccountWithUs.inputName}"
                value="no" ${form.hasAccountWithUs.state["no"]}> no
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.accountType.onError}</div>
      Account Type:
    </td>
    <td><input type="text" name="${form.accountType.inputName}"
               value="${form.accountType}">
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.accountNumber.onError}</div>
      Account Number:
    </td>
    <td><input type="text" name="${form.accountNumber.inputName}"
               value="${form.accountNumber}">
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.branchNumber.onError}</div>
      Branch Number:
    </td>
    <td>
      <input type="text" name="${form.branchNumber.inputName}"
             value="${form.branchNumber}">
    </td>
  </tr>

  <tr>
    <td colspan="2" class="heading">Financial Information</td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.monthlyIncome.onError}</div>
      Monthly Income:
    </td>
    <td><input type="text" name="${form.monthlyIncome.inputName}"
               value="${form.monthlyIncome}">
    </td>
  </tr>

  <tr>
    <td>
      <div class="error">${form.monthlyExpenses.onError}</div>
      Monthly Expenses:
    </td>
    <td><input type="text" name="${form.monthlyExpenses.inputName}"
               value="${form.monthlyExpenses}">
    </td>
  </tr>

  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" name="submit" value="submit"></td>
  </tr>

</table>
</form>

</body>
</html>
