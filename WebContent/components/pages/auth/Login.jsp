<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<jsp:include page="../HeaderData.jsp"></jsp:include>
<script type="text/javascript"
	src="components/pages/auth/js/validation/validaLogin.js"></script>


</head>
<body>
	<%
		Boolean admin = (Boolean) session.getAttribute("isAdmin");
		if ((admin != null) && (admin == true)) {
			String redirectURL = request.getContextPath() + "/admin/dashboard";
			response.sendRedirect(redirectURL);
		}
	%>
			<jsp:include page="../shop/Header.jsp" />
	
	<div class="section"></div>

	
	<div style="display:flex; justify-content:center; align-items:center; flex-direction:column;">

		<div style="margin-top:50px;"></div>


		<h5 class="indigo-text">Effettua il login!</h5>
		<div class="section"></div>

		<div class="container" style="display:flex; justify-content:center; align-items:center; flex-direction:column;">

			<div class="z-depth-1 grey lighten-4 row"
				style="display: inline-block; padding: 0px 48px 0px 48px; border: 1px solid #EEE;">

				<form class="col s12" method="get" name="invio">
					<div class='row'>
						<div class='col s12'></div>
					</div>

					<div class='row'>
						<div class='input-field col s12'>
							<input class='validate' type='email' name='email' id='email' />
							<label for='email'>Enter your email</label>
						</div>
					</div>

					<div class='row'>
						<div class='input-field col s12'>
							<input class='validate' type='password' name='password'
								id='password' /> <label for='password'>Enter your
								password</label>
						</div>
					</div>

					<br />
					
				</form>
				
				<div class='row'>
				<p id="val" style="color: red"></p>
						<button type='submit' name='btn_login'
							class='col s12 btn btn-large waves-effect indigo'
							onclick="valida()">Login</button>
					</div>
			</div>
		</div>
					Non hai un account? <a href="${pageContext.request.contextPath}/signup">Iscriviti</a>
		

	</div>

</body>
</html>