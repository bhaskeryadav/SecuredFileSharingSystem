
<html>
<head>
<!-- <link rel="stylesheet" type="text/css" href="Look.css"> -->
<title>Home Page</title>
<link rel="stylesheet" type="text/css" href="genral.css" >
</head>
<body>
	<br>
	<br>
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
		
		



		<%
			String msg = (String) session.getAttribute("filename");
			String key = (String) session.getAttribute("key");
		%>

		<div id="container_body" style="padding: 20px;">
			<div style="margin-left: 10px;">
				<h2 class="form_title" style="margin-left: 0px; padding: 0 0 0 0 ;">
					<u>Upload file</u>
				</h2>
			</div>

			<!--Form  start-->
			<form name="form" action="UploadServlet" method="post"  enctype="multipart/form-data" >
				<div id="form_name" style="padding: 10px 0 0 0;">
					<div class="firstnameorlastname">

						<div id="errorBox"></div>
					</div>

					<div id="Re_email_form" style="padding: 10px 0 0 0;">
						<input type="file" name="bfile" id="bfile" placeholder="Select File">
					</div>
					<div id="email_form" style="padding: 10px 0 0 0;">
						<input type="hidden" name="key" value=<%=request.getAttribute("sharedKey")%>
							placeholder="Key for encryption" class="input_email">
					</div>
					<div id="password_form" style="padding: 10px 0 0 0;">
						<!-- <input type="password" name="Password" value=""
							placeholder="New Password" class="input_password"> -->
							<textarea rows="3" cols="52" name="desc" placeholder="Description for this file."></textarea>
					</div>
					
					<div>
						<h3 class="birthday_title" style="padding: 0 0 0 0;">Select Encryption Mode</h3>
					</div>
					
					<!-- <div id="radio_button" style="padding: 10px 0 0 0;">
						<input type="radio" name="enc_type" value="AES"> <label>AES
							only</label> &nbsp;&nbsp;&nbsp; <input type="radio" name="enc_type"
							value="AESandBlowfish"> <label>AES and Blowfish</label>
					</div> -->
					<div id="radio_button">
					Encryption using advance 256 bit AES and blowfish.
					</div>
					<input type="hidden" name="enc_type"
							value="AESandBlowfish">
					<div>
						<!-- <p id="sign_user" onClick="Submit()">Sign Up</p> -->
						<button id="sign_user" type="submit">
							Save File
						</button>
					</div>

				</div>
			</form>
		</div>

		<!--form ends-->
			</div>









		<%
			if (key != null && msg != null) {
		%>
		<a href="Client.jsp">Start Encryption</a>
		<%
		}
	%>
	
	<form action="ResetServlet">
	<table>
	<%
				if (!(msg == null || msg.trim().equals(""))&&!(key == null || key.trim().equals("")))
				{
				%>
				<tr>
				<td colspan="2"><input type="submit" value="To Upload another file click Here!!"> </td>
				</tr>
				<%
				}
				%>
	<%
			}
			
			else
			{
	%>

	</table>
	</form>
		<h3><span style="color: #8ca379; text-align: center;">Please login first to upload any file.</span></h3>
	<%} %>
	<br><br>
	</div>
</body>
</html>