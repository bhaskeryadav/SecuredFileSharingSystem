<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Key</title>
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
		var base = document.getElementById("G").value;
		var modulus = document.getElementById("N").value;
		//alert("base 1  "+base);
		//alert("modulus  "+modulus);
		var private_B =document.getElementById("B").value;
		//alert("private_b  "+private_B);
		var public_B = discrete_exp(base, private_B, modulus);
		//alert("public b   "+public_B);
		document.getElementById("B").value=public_B;
		 document.getElementById("sub").style.visibility = 'hidden';
		 //alert("finished");
		//return public_B;
	}

</script>

</head>
<body>
	<center>
		<br>
		<br>
		<div style="background-color: #E1E8D5;" id="container">

			<br>
			<br>
			<form action="KeyDistribution">
				<span style="color: #8ca379;">Registration was successful.<br>
					Below is your private key. Kindly remember it to download any file
					from cloud.
					<p id="key">
						<a onclick="generateKey()" href="#">Click Here</a>
					</p>
					<p id="share"></p>
				</span> 
				<input type="hidden" name="G" id="G" value="<%=request.getAttribute("G")%>" >
				<input type="hidden" name="N" id="N" value="<%=request.getAttribute("N")%>">  
				<input type="hidden" name="B" id="B">
				<button type="submit" id="sub" style="visibility: hidden">Submit</button>
			</form>
			<br>
			<br>
		</div>
	</center>
</body>
</html>