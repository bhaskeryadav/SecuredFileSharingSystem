<%@page import="utils.JavaPropertyFileOperations"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="genral.css" >
</head>
<body>

	<%
	File f= new File(JavaPropertyFileOperations.homeDir+File.separator+"BLOWFISH.DAT");
	File f2=new File("/resource/BLOWFISH.DAT");
	System.out.print(f2.getAbsolutePath());
	%>

		<br> <br>

		<div style="background-color: #E1E8D5;"  id="container">
			<form action="LoginServlet" method="post" >

	<%
			String uname = (String) session.getAttribute("uname");

			if (uname != null && !uname.trim().equals("")) {
		%>
		<h3 style="color: #8ca379; text-align: right;">
			Welcome
			<%
			out.print(uname);
		%>,<br>You have already logged in.<br>You can login using different account. 
		</h3>
		<%
			}
		%>

				<br> <br>

				
			<div id="container_body" style="padding: 20px;">			
						<div style="margin-left: 10px;">
				<h2 class="form_title" style="margin-left: 0px; padding: 0 0 0 0 ;">
					Login
				</h2>
			</div>
					
					
						
					
					
						<!-- <td>User Name :</td>
						<td><input type="text" name="u_name"></td> -->
						<div id="Re_email_form" style="padding: 10px 0 0 0;">
						<input type="text" name="u_name" class="input_email" placeholder="Enter Username">
					</div>
					
						<!-- <td>Password :</td>
						<td><input type="password" name="password"></td> -->
						<div id="Re_email_form" style="padding: 10px 0 0 0;">
						<input type="password" name="password" class="input_email" placeholder="Enter Password">
					</div>
					
					
						<button id="sign_user" type="submit"><!-- <input type="submit" value="Login"> -->LogIn</button>
					</div>
			</form>
			<br> <br> <br> <br>
		</div>
	
</body>
</html>