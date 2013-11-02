<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-8 col-md-offset-2">

			<div class="well well-lg">
				<span style="font-size: 25.5px">Interactions</span>
				<c:choose>
					<c:when test="${empty events}">
						<div class="alert-box">
							<img src="../../img/logo.png" class="logo-alt" /> <br />No
							interactions so far.
						</div>
					</c:when>
					<c:otherwise>


						<c:forEach items="${events}" var="event">
							<div class="media buit">
								<a class="pull-left"> <c:if
										test="${not empty event.firer.photo}">
										<img class="pull-left media-object buit-profile-pic"
											src="../image?name=<c:out value="${event.firer.username}"/>" />
									</c:if> <c:if test="${empty event.firer.photo}">
										<img class="pull-left media-object buit-profile-pic"
											src="../../img/nopicture.png" />
									</c:if>
								</a>
								<div class="media-body">
									<div class="media-heading">
										<span class="pull-left"> <strong>@<c:out
													value="${event.firer.username}" /></strong> 

										</span> <span class="pull-right text-muted"><fmt:formatDate	type="both" value="${event.date}" /></span>
									</div>
									<br />
									<p><c:out
												value="${event.message}" /></p>
								</div>
							</div>
						</c:forEach>

					</c:otherwise>
				</c:choose>

			</div>

		</div>

	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>