<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-8 col-md-offset-2">

			<div class="well well-lg text-center">
				<img class="profile-pic" src="../../img/hashtag.jpeg" />
				<h2 class="profile-name" style="word-wrap:break-word">#<c:out value="${hashtag.hashtag}" /></h2>
				<h4 class="profile-user">Created by @<c:out value="${hashtag.user.username}" /></h4>
				<p class="profile-desc text-muted">First used: <fmt:formatDate type="both" value="${hashtag.date}" /></p>
			</div>

			<div class="well well-lg">
				Results for <span class="text-bold">#<c:out value="${hashtag.hashtag}" /></span>
				<c:choose>
					<c:when test="${empty buits}">
						<div class="alert-box">
							<img src="../../img/logo.png" class="logo-alt" /> <br />No Buit
							results for #<c:out value="${hashtagName}" />.
						</div>
					</c:when>
					<c:otherwise>


						<c:forEach items="${buits}" var="buit">
							<c:if test="${not empty user || buit.buitter.privacy == false}">
								<div class="media buit">
									<a class="pull-left"> 
										<c:if test="${not empty buit.buitter.photo}">
											<img class="pull-left media-object buit-profile-pic" src="../image?name=<c:out value="${buit.buitter.username}"/>"/>
										</c:if>
										<c:if test="${empty buit.buitter.photo}">
											<img class="pull-left media-object buit-profile-pic" src="../../img/nopicture.png"/>
										</c:if>
									</a>
									<div class="media-body">
										<div class="media-heading">
											<span class="pull-left text-bold">@<c:out value="${buit.buitter.username}" /></span> <span class="pull-right text-muted"><fmt:formatDate type="both" value="${buit.date}" /></span>
										</div>
										<br />
										<p>${buit.message}</p>
										<!-- agregar un form oculto con el id para el borrado (buit.id)-->
									</div>
								</div>
							</c:if>
						</c:forEach>

					</c:otherwise>
				</c:choose>

			</div>

		</div>

	</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>