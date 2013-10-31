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
				<c:choose>
					<c:when test="${empty user_info.following}">
						<div class="alert-box">
							<img src="../../img/logo.png" class="logo-alt" /> <br />@<c:out
									value="${user_info.username}" /> isn't following anyone yet.
						</div>
					</c:when>
					<c:otherwise>

						<h3 class="text-bold" style="margin-left:40px">Following</h3>
						<c:forEach items="${user_info.following}" var="following">
							<div class="media buit">
								<c:if test="${not empty following.photo}">
									<img class="pull-left media-object buit-profile-pic" src="../image?name=<c:out value="${following.username}"/>"/>
								</c:if>
								<c:if test="${empty following.photo}">
									<img class="pull-left media-object buit-profile-pic" src="../../img/nopicture.png"/>
								</c:if>
								<div class="media-body">
									<div class="media-heading">
										<span class="text-bold">
											<a href="<c:url value="../buit/profile"><c:param name="name" value="${following.username}" /></c:url>"><c:out value="${following.name}" /> <c:out value="${following.surname}" />
											</a>
										</span>
										@<c:out value="${following.username}" />
									</div>
									<p class="text-muted" style="word-wrap:break-word;">"${following.description}"</p>
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
<%@ include file="/WEB-INF/jsp/footer.jsp" %>