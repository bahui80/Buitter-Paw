<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="container">
<div class="row row-offcanvas row-offcanvas-right">
<div class="col-md-6 col-md-offset-3">
	<div class="well">
		<c:if test="${not empty error_logged_in}">
			<form class="bs-example form-horizontal" method="post" action="login">
			<fieldset>
				<legend>Logout</legend>
                <div class="form-group text-center	">
                	<label class="control-label">Do you want to logout?</label>
                </div>
                <div class="form-group" style="margin-left: -27px;">
                    <span class="col-md-offset-4">
                      <input type="submit" value="Logout" name="logout" class="btn btn-danger"></input>
                      <input type="submit" value="Keep buitting" name="continue" class="btn btn-success"></input>
                    </span>	
                    
                </div>
			</fieldset>
		</form>
		</c:if>
		<c:if test="${empty error_logged_in}">
		<form class="bs-example form-horizontal" method="post" action="login">
			<fieldset>
				<legend>Login</legend>
				    <c:if test="${not empty error_login}">
				    	<p class="text-danger" style="margin-bottom: 10px"><c:out value="${error_login}"/></p>
				    </c:if>
                <div class="form-group">
                	<label for="username" class="col-md-2 control-label">Username</label>
                    <div class="col-md-8">
                    	<input type="text" class="form-control3" placeholder="Username" name="username" value="<c:out value="${user_username}"/>"/>
                    </div>
                </div>
                <div class="form-group">
                	<label for="password" class="col-md-2 control-label">Password</label>
                    <div class="col-md-8">
                    	<input type="password" class="form-control3" placeholder="Password" name="password"/>
                    </div>
                </div>	
                <div class="form-group">
                	<a href="../user/forgotpassword" style="margin-left:107px">Forgot password?</a>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                      <button type="submit" class="btn btn-primary">Sign in</button>
                    </div>
                </div>
			</fieldset>
		</form>
		</c:if>
	</div>
</div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>