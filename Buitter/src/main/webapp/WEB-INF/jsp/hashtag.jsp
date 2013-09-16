<%@ include file="header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-7 col-md-offset-1">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>

			<div class="well well-lg text-center">
				<img class="profile-pic" src="img/hashtag.jpeg" />
				<h2 class="profile-name">#<c:out value="${hashtag.hashtag}" /></h2>
				<h4 class="profile-user">Created by @<c:out value="${hashtag.user.username}" /></h4>
				<p class="profile-desc">First used: NO ME LEE EL DATE<c:out value="${}" /></p>
			</div>

			<div class="well well-lg">
				Results for <span class="text-bold">#<c:out value="${hashtag.hashtag}" /></span>
				<c:choose>
					<c:when test="${empty buits}">
						<div class="alert-box">
							<img src="img/logo.png" class="logo-alt" /> <br />No Buit
							results for #myhashtag.
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
										<span class="pull-left text-bold"><c:out
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