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
					<c:when test="${empty user_info.followers}">
						<div class="alert-box">
							<img src="../../img/logo.png" class="logo-alt" /> <br />Nobody is following @<c:out
									value="${user_info.username}" /> yet.
						</div>
					</c:when>
					<c:otherwise>

						<h3 class="text-bold" style="margin-left:40px">Followers</h3>
						<c:forEach items="${user_info.followers}" var="follower">
							<div class="media buit">
								<c:if test="${not empty follower.photo}">
									<img class="pull-left media-object buit-profile-pic" src="../image?name=<c:out value="${follower.username}"/>"/>
								</c:if>
								<c:if test="${empty follower.photo}">
									<img class="pull-left media-object buit-profile-pic" src="../../img/nopicture.png"/>
								</c:if>
								<div class="media-body">
									<div class="media-heading">
										<span class="text-bold">
											<a href="<c:url value="../buit/profile"><c:param name="name" value="${follower.username}" /></c:url>"><c:out value="${follower.name}" /> <c:out value="${follower.surname}" />
											</a>
										</span>
										@<c:out value="${follower.username}" />
									</div>
									<p class="text-muted" style="word-wrap:break-word;">"${follower.description}"</p>
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