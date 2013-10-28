<%@ include file="/WEB-INF/jsp/header.jsp" %>
<script>
		function edit() {
			var str1 = "../user/editprofile";
			window.location = str1;
		}
</script>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-8 col-md-offset-2">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>

			<div class="well well-lg text-center" style="margin-bottom: 0px">

				<c:if test="${not empty user_info.photo}">
					<img class="profile-pic" src="../image?name=<c:out value="${user_info.username}"/>"/>
				</c:if>
				<c:if test="${empty user_info.photo}">
					<img class="profile-pic" src="../../img/nopicture.png"/>
				</c:if>
				<h2 class="profile-name"><c:out value="${user_info.name}"/> <c:out value="${user_info.surname}"/></h2>
				<h4 class="profile-user">@<c:out value="${user_info.username}"/></h4>
				<p class="profile-desc text-muted" style="word-wrap:break-word">"<c:out value="${user_info.description}"/>"	</p>
				<c:if test="${user == user_info.username }">
					<button onclick="edit();" type="button" class="pull-right btn btn-link btn-xs"><i class="icon-pencil"> Edit profile</i></button>
				</c:if>
			</div>
			<div class="bs-example table-responsive">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th><a href="#"><small><p style="margin-bottom: 0px;">User</p></small><small><p style="margin-bottom: 0px;">Buits</p></small></a></th>
							<th><a href="#"><small><p style="margin-bottom: 0px">4</p></small><small><p style="margin-bottom: 0px;">Following</p></small></a></th>
							<th><a href="#"><small><p style="margin-bottom: 0px">1237</p></small><small><p style="margin-bottom: 0px;">Followers</p></small></a></th>
							<th><small><p style="margin-bottom: 0px;"><c:out value="${user_info.visits}"/></p></small><small><p style="margin-bottom: 0px;">Visitors</p></small></th>
							<c:if test="${not empty user}">
								<c:set var="found" value='false'/>
								<c:forEach items="${user_info.followers}" var="follower">
									<c:if test="${follower.username == user}"><c:set var="found" value='true'/></c:if>
								</c:forEach>
								<c:if test="${found == 'true'}"><c:set var="method" value='Unfollow'/></c:if>
								<c:if test="${found == 'false'}"><c:set var="method" value='Follow'/></c:if>
								<th><button id="<c:out value="${user_info.username}"/>" class="btn btn-follow btn-sm pull-right" style="margin-bottom: 3px" onclick="follow(this.id, '<c:out value="${method}"/>');"><img src="../../img/logo.png" class="logo" /><c:out value="${method}"/></img></button></th>
								<script>
									function follow (username, method) {
										var form = document.createElement('form');
										form.setAttribute('method', 'post');
										form.setAttribute('action', method.toLowerCase());
										form.style.display = 'hidden';
									    var input = document.createElement('input');
									    input.setAttribute('type','text');
									    input.setAttribute('name','username');
									    input.setAttribute('value', username);
									    input.style.display = 'hidden';
									    form.appendChild(input);
									    document.body.appendChild(form);
									    form.submit();
									}
								</script>
							</c:if>
						</tr>
					</thead>
				</table> 
			</div>
			<div class="well well-lg">
				<c:if test="${user == user_info.username }">
					<div class="input-group buit-compose-form">
						<form method="post" action="buit">
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
							<img src="../../img/logo.png" class="logo-alt" /> <br />@<c:out
									value="${user_info.username}" /> hasn't buited yet.
						</div>
					</c:when>
					<c:otherwise>


						<c:forEach items="${buits}" var="buit">
							<div class="media buit">
									<c:if test="${not empty user_info.photo}">
										<img class="pull-left media-object buit-profile-pic" src="../image?name=<c:out value="${user_info.username}"/>"/>
									</c:if>
									<c:if test="${empty user_info.photo}">
										<img class="pull-left media-object buit-profile-pic" src="../../img/nopicture.png"/>
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
											function proceed(clicked_id) {
										   		var form = document.createElement('form');
										   		form.setAttribute('method', 'post');
										   		form.setAttribute('action', 'delete');
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
									<!-- Cambiar los valores para el favorito y hacer la funcion javascript. Poner ifs-->
									<button id="<c:out value="${buit.id}"/>" type="button" onclick="favorite(this.id);" class="pull-right btn btn-link btn-xs"><i class="icon-star"> Favorite</i></button>
									<!-- cambiar lso valores para rebuiteo y hacer la funcion javscript. Poner los ifs-->
									<button id="<c:out value="${buit.id}"/>" type="button" onclick="rebuit(this.id);" class="pull-right btn btn-link btn-xs"><i class="icon-retweet"> Rebuit</i></button>
									
								</div>
							</div>
						</c:forEach>

					</c:otherwise>
				</c:choose>

			</div>

		</div>

	</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>