<%@ include file="/WEB-INF/jsp/header.jsp" %>
<div class="container">
<c:if test="${not empty user || user_info.privacy=='false'}">
	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-8 col-md-offset-2">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
			</p>
<%@ include file="user_header.jsp" %>	
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
										<span class="pull-left text-bold">@<c:out value="${buit.buitter.username}" /></span> <span class="pull-right text-muted" style="font-size:15px "><c:out value="${buit.date}" /></span>
									</div>
									<br />
									<p style="word-wrap:break-word">${buit.message}</p>
									<c:if test="${not empty user}">
										<c:if test="${user == user_info.username }">
											<button id="<c:out value="${buit.id}"/>" type="button" onclick="proceed(this.id);" class="pull-right btn btn-link btn-xs"><i class="icon-trash"> Delete</i></button>
										</c:if>
										<!-- Cambiar los valores para el favorito y hacer la funcion javascript. Poner ifs-->
										<button id="<c:out value="${buit.id}"/>" type="button" onclick="favorite(this.id);" class="pull-right btn btn-link btn-xs"><i class="icon-star-o"> Favorite</i></button>
										<!-- cambiar lso valores para rebuiteo y hacer la funcion javscript. Poner los ifs-->
										<c:if test="${user != user_info.username }">
											<button id="<c:out value="${buit.id}"/>" type="button" onclick="rebuit(this.id);" class="pull-right btn btn-link btn-xs"><i class="icon-retweet"> Rebuit</i></button>
										</c:if>
									</c:if>	
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${empty user && user_info.privacy=='true'}">
	<div class="bs-example">
        <div class="jumbotron">
        	<img style="display: block; margin-left: auto;  margin-right: auto;" src="../../img/stop_vulture.png">
          	<h2 style="text-align:center">Sorry, user @<c:out value="${user_info.username}"/> is private</h1>
          	<p style="text-align:center">You must be logged in to see this profile.</p>
        </div>
   	</div>
</c:if>

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
<script>
	function favorite (id) {
		var form = document.createElement('form');
		form.setAttribute('method', 'post');
		form.setAttribute('action', 'favorite');
		form.style.display = 'hidden';
		var input = document.createElement('input');
	    input.setAttribute('type','text');
		input.setAttribute('name','buitid');
		input.setAttribute('value', id);
		input.style.display = 'hidden';
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	}
	
	function rebuit (id) {
		var form = document.createElement('form');
		form.setAttribute('method', 'post');
		form.setAttribute('action', 'rebuit');
		form.style.display = 'hidden';
		var input = document.createElement('input');
	    input.setAttribute('type','text');
		input.setAttribute('name','buitid');
		input.setAttribute('value', id);
		input.style.display = 'hidden';
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	}
</script>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>