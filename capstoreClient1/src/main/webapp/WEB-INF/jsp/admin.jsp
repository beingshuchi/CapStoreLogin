<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Login</title>
<script type="text/javascript">
function validate(){
	var email=document.loginForm.email.value;
	var password=document.loginForm.pass.value;
	if(email==""){
		alert("Email cannot be left empty!");
		document.loginForm.email.focus();
		return false;
	}
	
	if(password==""){
		alert("Password cannot be left empty!");
		document.loginForm.pass.focus();
		return false;
	}
		return true;
	
	
}
</script>

</head>
<body>
<form name="form" action="/ahome" >
  
  Email: <input name='username' id="username" placeholder='Username' type="text"/>
   Password: <input type="password" name="password" id="pw"  placeholder="Password" onblur="showText()"  />
    <input type='submit' value='Sign in'/>
</form>
</body>
</html>