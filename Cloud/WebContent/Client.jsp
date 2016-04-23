<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="genral.css">
<script>
	function loadXMLDoc() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("myDiv").innerHTML = xmlhttp.responseText;
			}
		}
		xmlhttp.open("GET", "EncServerServlet", true);
		xmlhttp.send();
	}
</script>


</head>
<body>
<br><br>

<div style="background-color: #E1E8D5; padding-left: 40px;" id="container">
<br><br>
	<%
	String msg=(String)request.getAttribute("msg");
	if(msg!=null)
	{
		out.print(msg);
	}
		String isStarted = (String) session.getAttribute("isStarted");
		if (isStarted == null) {
	%>
	<form action="EncServerServlet" method="get">
	<input type="hidden" name="op" value="1">
		<button  type="submit" value="Start Encryption" id="sign_user">Start Encryption</button>
	</form>
	<%
		} else {
	%>

	<table style="width: 60%;" >
		<td>
		<tr>
			<button type="button" onclick="loadXMLDoc()">Change Content</button>
		</tr>
		<tr>
			<div id="myDiv" style="padding-left: 70px">
				<h2>Console output will be displayed here</h2>
			</div>
		</tr>
		</td>
	</table>



	<%} %>
	<br><br>
</div>
</body>
</html>