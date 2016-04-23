<%@page import="java.util.Iterator"%>
<%@page import="pojos.FilesInDB"%>
<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="Look2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="genral.css">
<script>

	function generateKey() {
		var key=Math.floor(Math.random() * 10000);
		document.getElementById("key").innerHTML =key;
		document.getElementById("B").value=key;
		document.getElementById("share").innerHTML = "<button onclick=\"compute()\">Generate and Share public key with server.</button>";
	}

	function discrete_exp(t, u, n) { // args are base, exponent, modulus
		// computes s = (t ^ u) mod n
		// (see Bruce Schneier's book, _Applied Cryptography_ p. 244)
		var s = 1;
		while (u) {
			if (u & 1) {
				s = (s * t) % n
			}
			;
			u >>= 1;
			t = (t * t) % n;
		}
		;
		return s;
	}

	function compute() {
		//alert("compute");
		var base = document.getElementById("publicKey").value;
		var modulus = document.getElementById("N").value;
		///alert("base 1  "+base);
		//alert("modulus  "+modulus);
		var private_B =document.getElementById("key").value;
		//alert("private_b  "+private_B);
		var public_B = discrete_exp(base, private_B, modulus);
		//alert("public b   "+public_B);
		document.getElementById("B").value=public_B;
		// alert("finished");
		return public_B;
	}

</script>
</head>
<body>
<br><br>
<div style="background-color: #E1E8D5;  width: 100%" id="container">

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
		<%-- <%
			}
		%>
		 --%>


	<%
		Set<FilesInDB> set = (Set<FilesInDB>) request.getAttribute("list");
		Iterator<FilesInDB> ite = set.iterator();
	%>

	
	<form action="DownloadServlet" method="get" onsubmit="return compute()">
		<table class="reference" style="width: 80%;">
		
			<caption><br>Select the file to download</caption>
			<tr>

				<th style="text-align: center; color :#3F413B; font-size: 16px; font-weight: normal;">Select</th>
				<th style="text-align: center; color :#3F413B; font-size: 16px; font-weight: normal;" >File Name</th>
				<th style="text-align: center; color :#3F413B; font-size: 16px; font-weight: normal;">Size in bytes</th>
				<th style="text-align: center; color :#3F413B; font-size: 16px; font-weight: normal;">Uploaded on</th>

			</tr>
			<%
				FilesInDB tempFile = null;
				while (ite.hasNext()) {
					tempFile = ite.next();
			%>
			<tr>
				<td width="10%" style="text-align: center;"><input type="radio"
					name="select" value=<%out.print(tempFile.getFileName());%>></td>
				<td width="30%">
					<%
						out.print(tempFile.getFileName());
					%>
				</td>
				<td width="25%">
					<%
						out.print(tempFile.getSize());
					%>
				</td>
				<td width="35%">
					<%
						out.print(tempFile.getUploadedOn());
					%>
				</td>
			</tr>
			<%
				}
			%>
			<tr align="center">
				<td colspan="4">Enter Key :&nbsp;&nbsp;<input type="text" name="key" id="key"></td>
			</tr>
			<tr style="background-color: #cbd7b7;">
				<td colspan="4" align="center"><input type="submit" >
				<input type="hidden" id="publicKey" name="publicKey" value=<%=request.getAttribute("publicKey") %>>
				<input type="hidden" name="G" id="G" value="<%=request.getAttribute("G")%>" >
				<input type="hidden" name="N" id="N" value="<%=request.getAttribute("N")%>">  
				<input type="hidden" name="B" id="B">
				</td>
				
			</tr>
		</table>
	</form>
	<div id="myDiv"></div>
	
	
		<%
			}
			
			else
			{
	%>
	<h3 style="text-align: center;"><span style="color: #8ca379;text-align: center;">Please login first to upload any file.</span></h3>
	<%} %>
	<br>
	<br>
	</div>
</body>
</html>