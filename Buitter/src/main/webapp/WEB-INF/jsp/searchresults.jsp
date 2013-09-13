<%@ include file="header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-7 col-md-offset-1">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>

			<div class="well well-lg">

				<div class="well-header">
					People results for <span class="text-bold"><c:out
							value="${query}" /></span>
				</div>

				<c:choose>
					<c:when test="${empty results}">
						<div class="text-center">
						<img src="img/logo.png" class="logo-alt"/>
						<br/>No people results for <span
							class="text-bold"><c:out value="${query}" />.</span>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${results}" var="user">
							<div class="media buit">
								<a class="pull-left" href="#"> <img
									class="media-object buit-profile-pic" src="img/nopicture.png">
								</a>
								<div class="media-body">
									<div class="media-heading">
										<span class="text-bold"><a
											href="<c:url value="profile"><c:param name="name" value="${user.username}" /></c:url>"><c:out
													value="${user.name}" /> <c:out value="${user.surname}" /></a></span>
										<c:out value="${user.username}" />
									</div>
									<p>
									<div class="pull-left">
										Date registered:
										<c:out value="${user.date}" />
									</div>
									</p>
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