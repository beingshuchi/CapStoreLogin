<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Profile</title>
</head>
<body>
<h1  align="center" style="background-color:Red">Customer Profile</h1>
<form>
<div align="center">
<h2>Customer Details</h2>
<table border="1" >

<c:forEach items="${users}" var="user">
<tr>
<td>Customer Name</td>
<td><c:out value="${CustomerBean.customerName}" /></td>
</tr>

<tr>
<td>Email Address</td>
    <td><c:out value="${CustomerBean.email}" /></td>
</tr>
<tr>
<td>Address</td>
<td><c:out value="${CustomerBean.address}" /></td>
</tr>
<tr>
<td>MobileNumber</td>
<td><c:out value="${CustomerBean.mobileNo}" /></td>
</tr>
</c:forEach>
            
</table>
</div>
<a href="/editprofile" id="update">Edit Profile</a>
<a href="#" id="orderlist"> Order List</a>
<a href="#" id="wishlist">WishList</a>
<a href="#" id="changepwd">Change Password</a>
<a href="#">Logout</a> 
</form>
</body>
</html>