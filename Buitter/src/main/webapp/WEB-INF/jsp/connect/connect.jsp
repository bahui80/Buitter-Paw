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
										test="${not empty event.user.photo}">
										<img class="pull-left media-object buit-profile-pic"
											src="../image?name=<c:out value="${event.user.username}"/>" />
									</c:if> <c:if test="${empty event.user.photo}">
										<img class="pull-left media-object buit-profile-pic"
											src="../../img/nopicture.png" />
									</c:if>
								</a>
								<div class="media-body">
									<div class="media-heading">
										<span class="pull-left"> <strong>@<c:out
													value="${event.user.username}" /></strong> <c:if
												test="${event.kind == 'followed'}"> followed you</c:if> <c:if
												test="${event.kind == 'mentioned'}"> mentioned you</c:if> <c:if
												test="${event.kind == 'retweeted'}"> rebuited you</c:if>

										</span> <span class="pull-right text-muted"><c:out
												value="${event.date}" /></span>
									</div>
									<br />
									<p>${event.message}</p>
								</div>
							</div>
						</c:forEach>

					</c:otherwise>
				</c:choose>

			</div>

		</div>

	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>