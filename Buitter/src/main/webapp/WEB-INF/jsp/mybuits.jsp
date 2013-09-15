<%@ include file="header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-7 col-md-offset-1">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>

			<div class="well well-lg text-center">
				<img class="profile-pic" src="img/nopicture.png" />
				<h2 class="profile-name"><c:out value="${user_info.name}"/> <c:out value="${user_info.surname}"/></h2>
				<h4 class="profile-user">@<c:out value="${user_info.username}"/></h4>
				<p class="profile-desc"><c:out value="${user_info.description}"/></p>
			</div>
			

			<div class="well well-lg">
				<c:if test="${user == user_info.username }">
					<div class="input-group buit-compose-form">
						<form method="post" action="profile">
							<fieldset>
								<textarea id="buit-field" class="form-control" rows="3" name="buit"
									placeholder="Compose new Buit..." maxlength="140"></textarea>
							<span  id='remainingC'>140</span>
								<button type="submit"
									class="btn btn-primary pull-right buit-compose-button">Buit</button>
							</fieldset>
						</form>
					</div>
				</c:if>
				
				<script>
					var txtBoxRef = document.getElementById("buit-field");
 					var counterRef = document.getElementById("remainingC");
 					txtBoxRef.addEventListener("keydown",function(){
 				
  					var remLength = 0;
  					remLength = 140 - parseInt(txtBoxRef.value.length);
  					if(remLength >= 0){
  						counterRef.innerHTML = remLength;
  					}
 					},true);
				</script>

				<c:choose>
					<c:when test="${empty buits}">
						<div class="alert-box">
							<img src="img/logo.png" class="logo-alt" /> <br />@<c:out
									value="${user_info.username}" /> hasn't buited yet.
						</div>
					</c:when>
					<c:otherwise>


						<c:forEach items="${buits}" var="buit">
							<div class="media buit">
								<a class="pull-left" href="#"> <img
									class="media-object buit-profile-pic" src="img/nopicture.png">
								</a>
								<div class="media-body">
									<div class="media-heading">
										<span class="pull-left text-bold">@<c:out
												value="${buit.user.username}" /></span> <span class="pull-right"><c:out
												value="${buit.date}" /></span>
									</div>
									<br />
									<p>${buit.message}</p>
									<!-- agregar un form oculto con el id para el borrado (buit.id)-->
								</div>
							</div>
						</c:forEach>

					</c:otherwise>
				</c:choose>

			</div>

		</div>
		<%@ include file="sidebar.jsp"%>

	</div>
	<!--/cerrar el div si la sidebar esta presente-->

	<%@ include file="footer.jsp"%>