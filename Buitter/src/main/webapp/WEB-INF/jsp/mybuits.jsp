<%@ include file="header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-7 col-md-offset-1">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>

			<div class="well well-lg text-center">
				<c:if test="${not empty user_info.photo}">
					<img class="profile-pic" src="img/photo.jpg"/>
				</c:if>
				<c:if test="${empty user_info.photo}">
					<img class="profile-pic" src="img/nopicture.png"/>
				</c:if>
				<h2 class="profile-name"><c:out value="${user_info.name}"/> <c:out value="${user_info.surname}"/></h2>
				<h4 class="profile-user">@<c:out value="${user_info.username}"/></h4>
				<p class="profile-desc text-muted">"<c:out value="${user_info.description}"/>"	</p>
			</div>

			<div class="well well-lg">
				<c:if test="${user == user_info.username }">
					<div class="input-group buit-compose-form">
						<form method="post" action="profile">
							<fieldset>
								<textarea id="buit-field" class="form-control" rows="3" name="buit"
									placeholder="Compose new Buit..." maxlength="140"></textarea>
									Remaining: <span  id='remainingC'>140</span>
                      				<c:if test="${not empty error_buit}">
                        				<p class="text-danger" style="margin-bottom: 0px"><small><em>Your buit is empty<c:out value="${error_buit}"/></em></small></p>
                      				</c:if>
								<button type="submit"
									class="btn btn-primary pull-right buit-compose-button">Buit</button>
							</fieldset>
						</form>
					</div>
				</c:if>

				<script>
					var txtBoxRef = document.getElementById("buit-field");
 					var counterRef = document.getElementById("remainingC");
 					txtBoxRef.addEventListener("input",function(){
  					var remLength = 0;
  					remLength = 140 - txtBoxRef.value.length;
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
									<c:if test="${not empty user_info.photo}">
										<img class="pull-left media-object buit-profile-pic" src="img/photo.jpg"/>
									</c:if>
									<c:if test="${empty user_info.photo}">
										<img class="pull-left media-object buit-profile-pic" src="img/nopicture.png"/>
									</c:if>
								<div class="media-body">
									<div class="media-heading">
										<span class="pull-left text-bold">@<c:out
												value="${buit.user.username}" /></span> <span class="pull-right text-muted" style="font-size:15px "><c:out
												value="${buit.date}" /></span>
									</div>
									<br />
									<p style="word-wrap:break-word">${buit.message}</p>
									<c:if test="${user == user_info.username }">
										<button id="<c:out value="${buit.id}"/>" type="button" onclick="proceed(this.id);" class="pull-right btn btn-link btn-xs"><i class="icon-trash"> Delete</i></button>
										<script>
											function proceed (clicked_id) {
										   		var form = document.createElement('form');
										   		form.setAttribute('method', 'post');
										   		form.setAttribute('action', 'deletebuit');
										    	form.style.display = 'hidden';
										    	var input = document.createElement('input');
										    	input.setAttribute('type','text');
										    	input.setAttribute('name','buitid');
										    	input.setAttribute('value', clicked_id);
										    	input.style.display = 'hidden';
										    	form.appendChild(input);
										    	document.body.appendChild(form);
										    	form.submit();
											}
										</script>
									</c:if>
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
