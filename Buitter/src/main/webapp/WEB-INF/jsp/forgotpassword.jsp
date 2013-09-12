<%@ include file="header.jsp" %>

<div class="container">
	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-6 col-md-offset-3">
			<div class="well">
				<form class="bs-example form-horizontal" method="post" action="recoverpassword">
					<fieldset>
						<legend>Reset your password</legend>
						<c:if test="${empty correct_username}">
							<p style="margin-bottom:20px" class="text-success">To reset your password, please enter your username</p>
							<div class="form-group">
                				<label for="username" class="col-md-2 control-label">Username</label>
                    			<div class="col-md-8">
                    				<input type="hidden" name="recover" value="recover">
                    				<input type="text" class="form-control3" placeholder="Username" name="username">
                    				<c:if test="${not empty error_username}">
                        				<p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_username}"/></em></small></p>
                      				</c:if>
                    			</div>
                			</div>
                			<div class="form-group">
                   				<div class="col-md-offset-5">
                      				<button type="submit" class="btn btn-primary">Continue</button>
                    			</div>
                			</div>
                		</c:if>
                		<c:if test="${not empty correct_username}">
                			<p style="margin-bottom:20px" class="text-success">To reset your password, please enter a new password</p>
                			<div class="form-group">
                				<label for="password" class="col-md-2 control-label">Password</label>
                    			<div class="col-md-8">
                    				<input type="hidden" name="recover" value="reset">
                    				<input type="password" class="form-control3" placeholder="Password" name="password">
                    				<c:if test="${not empty error_password}">
                        				<p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_password}"/></em></small></p>
                      				</c:if>
                    			</div>
                			</div>
                			<div class="form-group">
                				<label for="password2" class="col-md-2 control-label">Re-type password</label>
                    			<div class="col-md-8">
                    				<input type="password2" class="form-control3" placeholder="Re-type password" name="password2">
                    				<c:if test="${not empty error_password2}">
                        				<p class="text-danger" style="margin-bottom: 0px"><small><em><c:out value="${error_password2}"/></em></small></p>
                      				</c:if>
                    			</div>
                			</div>
                		</c:if>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

<%@ include file="footer.jsp" %>