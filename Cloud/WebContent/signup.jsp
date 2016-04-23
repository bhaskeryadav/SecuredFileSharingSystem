<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="genral.css">
<script>
function Submit(){
	var emailRegex = /^[A-Za-z0-9._]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
	var fname = document.form.Name.value,
		lname = document.form.LastName.value,
		femail = document.form.Email.value,
		funame = document.form.username.value,
		fpassword = document.form.Password.value,
		fmonth = document.form.birthday_month.value,
		fday = document.form.birthday_day.value,
		fyear = document.form.birthday_year.value;
		
	
	
	if( fname == "" )
   {
     document.form.Name.focus() ;
	 document.getElementById("errorBox").innerHTML = "enter the first name";
     return false;
   }
	if( lname == "" )
   {
     document.form.LastName.focus() ;
	  document.getElementById("errorBox").innerHTML = "enter the last name";
     return false;
   }
   
   if (femail == "" )
	{
		document.form.Email.focus();
		document.getElementById("errorBox").innerHTML = "enter the email";
		return false;
	 }else if(!emailRegex.test(femail)){
		document.form.Email.focus();
		document.getElementById("errorBox").innerHTML = "enter the valid email";
		return false;
	 }
	 
	  if (funame == "" )
	{
		document.form.enterEmail.focus();
		document.getElementById("errorBox").innerHTML = "Re-enter the email";
		return false;
	 }
	 
	 
	if(fpassword == "")
	 {
		 document.form.Password.focus();
		 document.getElementById("errorBox").innerHTML = "enter the password";
		 return false;
	 }
	 
		 if (fmonth == "") {
        document.form.birthday_month.focus();
		document.getElementById("errorBox").innerHTML = "select the birthday month";
        return false;
     }
	 if (fday == "") {
        document.form.birthday_day.focus();
		document.getElementById("errorBox").innerHTML = "select the birthday day";
        return false;
     }
	 if (fyear == "") {
        document.form.birthday_year.focus();
		document.getElementById("errorBox").innerHTML = "select the birthday year";
        return false;
     }
		if(document.form.radiobutton[0].checked == false && document.form.radiobutton[1].checked == false){
				document.getElementById("errorBox").innerHTML = "select your gender";
			 return false;
			}
		if(fname != '' && lname != '' && femail != '' && funame != '' && fpassword != '' && fmonth != '' && fday != '' && fyear != ''){
			document.getElementById("errorBox").innerHTML = "form submitted successfully";
			return true;
			}
		  
}

</script>
</head>

<body>
	
		<!-- <div id="emptyDiv">
<h2><a href="http://www.webcodehelpers.com" target="_blank">WebCodeHelpers.com</a></h2>
Tutorial Link : <a href=" http://www.webcodehelpers.com/2013/11/registration-form-validation-using-javascript-with-example.html" target="_blank">http://www.webcodehelpers.com/2013/11/registration-form-validation-using-javascript-with-example.html</a>
</div> -->
		<br>
		<br>

		<div id="description"></div>
		<!--container start-->
		
		<div id="container">
		
		
			<div id="container_body">
			<div style=" margin-left: 10px;">
				<h2 class="form_title" style="margin-left: 0px;">
					<u>User Registration Form Demo</u></h2>
			</div>

			<!--Form  start-->
				<div id="form_name">
					<div class="firstnameorlastname">
						<form name="form" action="RegisterServlet" method="post" onsubmit="return Submit()">
							<div id="errorBox"></div>
							<input type="text" name="Name" value="" placeholder="First Name"
								class="input_name"> <input type="text" name="LastName"
								value="" placeholder="Last Name" class="input_name">
					</div>
					<div id="email_form">
						<input type="text" name="Email" value="" placeholder="Your Email"
							class="input_email">
					</div>
					<div id="username">
						<input type="text" name="username" value=""
							placeholder="Enter Username" class="input_Re_email">
					</div>
					<div id="password_form">
						<input type="password" name="Password" value=""
							placeholder="New Password" class="input_password">
					</div>
					<!--birthday details start-->
					<div>
						<h3 class="birthday_title">Birthday</h3>
					</div>
					<div>
						<select name="birthday_month">
							<option value="" selected>Month</option>
							<option value="1">Jan</option>
							<option value="2">Feb</option>
							<option value="3">Mar</option>
							<option value="4">Apr</option>
							<option value="5">May</option>
							<option value="6">Jun</option>
							<option value="7">Jul</option>
							<option value="8">Aug</option>
							<option value="9">Sep</option>
							<option value="10">Oct</option>
							<option value="11">Nov</option>
							<option value="12">Dec</option>
						</select> &nbsp;&nbsp; <select name="birthday_day">
							<option value="" selected>Day</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
						</select> &nbsp;&nbsp; <select name="birthday_year">
							<option value="" selected>Year</option>
							<option value="2013">2013</option>
							<option value="2012">2012</option>
							<option value="2011">2011</option>
							<option value="2010">2010</option>
							<option value="2009">2009</option>
							<option value="2008">2008</option>
							<option value="2007">2007</option>
							<option value="2006">2006</option>
							<option value="2005">2005</option>
							<option value="2004">2004</option>
							<option value="2003">2003</option>
							<option value="2002">2002</option>
							<option value="2001">2001</option>
							<option value="2000">2000</option>
							<option value="1999">1999</option>
							<option value="1998">1998</option>
							<option value="1997">1997</option>
							<option value="1996">1996</option>
							<option value="1995">1995</option>
							<option value="1994">1994</option>
							<option value="1993">1993</option>
							<option value="1992">1992</option>
							<option value="1991">1991</option>
							<option value="1990">1990</option>
						</select>
					</div>
					<!--birthday details ends-->
					<div id="radio_button">
						<input type="radio" name="gender" value="Female"> <label>Female</label>
						&nbsp;&nbsp;&nbsp; <input type="radio" name="gender"
							value="Male"> <label>Male</label>
					</div>
					<div>
						<!-- <p id="sign_user" onClick="Submit()">Sign Up</p> -->
						<button id="sign_user" type="submit">Register</button>
					</div>
					</form>
					</div>
				</div>
				<!--form ends-->
			</div>
		</div>
		<!--container ends-->

</body>
</html>
