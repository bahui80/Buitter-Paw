<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-8 col-md-offset-2">

			<div class="well well-lg">
				<span style="font-size: 25.5px">Favorites</span>
				<c:choose>
					<c:when test="${empty favorites}">
						<div class="alert-box">	
							<img src="../../img/logo.png" class="logo-alt" /> <br />No
							favorites so far.
						</div>
					</c:when>
					<c:otherwise>


						<c:forEach items="${favorites}" var="favorite">
							<div class="media buit">
								<c:if test="${not empty favorite.buitter.photo}">
									<img class="pull-left media-object buit-profile-pic"
										src="../image?name=<c:out value="${favorite.buitter.username}"/>" />
								</c:if>
								<c:if test="${empty favorite.buitter.photo}">
									<img class="pull-left media-object buit-profile-pic"
										src="../../img/nopicture.png" />
								</c:if>
								<div class="media-body">
									<div class="media-heading">
										<div class="row" style="margin-left: 0px; margin-right: 0px;">
											<span class="pull-left text-bold" style="font-size: 15px;">@<c:out
													value="${favorite.buitter.username}" /></span> <span
												class="pull-right text-muted" style="font-size: 15px"><fmt:formatDate
													type="both" value="${favorite.buitter.date}" /></span>
										</div>
										<div class="row" style="margin-left: 0px; margin-right: 0px;">
											<p style="word-wrap: break-word; font-size: 15px">${favorite.message}</p>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>

					</c:otherwise>
				</c:choose>

			</div>

		</div>

	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>