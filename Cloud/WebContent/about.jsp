<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="genral.css" >
</head>
<body>
<br><br>
<div style="background-color: #E1E8D5;  width: 100%; height: 78%;"  id="container" >
<br>
			<%
			String uname = (String) session.getAttribute("uname");

			if (uname != null && !uname.trim().equals("")) {
		%>
		<h3 style="color: #8ca379; text-align: right;">
			Welcome
			<%
			out.print(uname);
		%>,
		</h3>
		<%
			}
		%>
	<br>	
About's content
</div>
</body>
</html>