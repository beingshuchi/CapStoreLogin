<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up for CapStore - Merchant</title>
<script type="text/javascript">
	function validate() {

		var merchantname = document.myform.customerName.value;
		var address = document.myform.address.value;
		var mobileno = document.myform.mobileNo.value;
		var email = document.myform.email.value;
		var password = document.myform.password.value;
		var mob = /^[0-9]{10}$/;
		var name = /^[A-Za-z/s]{3,}$/;
		var em = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var pass = /[A-Z]{1,}[a-z0-9._%+-@a-z0-9.-a-z]{8,}$/;
		if (merchantname == "") {
			alert("Merchant name cannot be left empty!");
			return false;
		}
		if (name.test(merchantname) == false) {
			alert("Merchant name should contain only alphabets");
			return false;
		}
		if (mobileno == "") {
			alert("Mobile No cannot be left empty!");
			return false;
		}
		if (mob.test(mobileno) == false) {
			alert("Mobile No should be 10 digits only!");
			return false;
		}

		if (email == "") {
			alert("Email cannot be left empty!");
			return false;
		}
		if (em.test(email) == false) {
			alert("Invalid email");
			return false;
		}
		if (password == "") {
			alert("Password cannot be left empty!");
			return false;
		}
		if (pass.test(password) == false) {
			alert("Invalid password");
			return false;
		}
		if (address == "") {
			alert("Address cannot be left empty!");
			return false;
		}

		return true;
	}
</script>
</head>
<body>

		<form name="myform"  onsubmit="return validate();" action="/confirmsignup">
		<table align="center">
			<tr>
				<td>Name</td>
				<td><input type="text" name="customerName"></td>
			</tr>
		<tr>
			<td>Mobile Number</td>
			<td><input type="text" name="mobileNo"></td>
		</tr>
		<tr>
			<td>Email ID</td>
			<td><input type="text" name="email"></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td>Merchant Type</td>
			<td><select name="merchantType" required="required">
					<option value="default">Select</option>
					<option value="direct">Direct Merchant</option>
					<option value="thirdParty">Third party Merchant</option>
			</select>
		</tr>
		<tr>
			<td>
			</td>
			<td><input type="submit" value="Sign Up" name="submit"></td>
		</tr>

		
	</table>
	</form>
</body>
</html>