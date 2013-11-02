<%@ include file="/WEB-INF/jsp/header.jsp"%>
<script>
	function refresh() {
		var e = document.getElementById("select");
		var strUser = e.options[e.selectedIndex];
		var str1 = "../home/home?time=";
		var str2 = str1.concat(strUser.id);
		window.location = str2;
	}
</script>
<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-xs-12 col-sm-9">
			<div class="well well-lg"
				style="padding-left: 0px; padding-right: 0px;">
				<h1 id="no-more-tweets"
					style="text-align: center; font-size: 25.5px">#NoMoreTweets.
					#WeBuitNow.</h1>
			</div>

			<c:if test="${not empty user}">
				<div class="well well-lg">
					<div class="row">
						<div class="col-6 col-sm-6 col-lg-6">
							<h3 style="text-align: center; margin-top: 12px;">Buits</h3>
							<c:forEach items="${user_info.buits}" var="buit">
								<div class="media buit">
									<c:if test="${not empty user_info.photo}">
										<img class="pull-left media-object buit-profile-pic"
											src="../image?name=<c:out value="${user_info.username}"/>" />
									</c:if>
									<c:if test="${empty user_info.photo}">
										<img class="pull-left media-object buit-profile-pic"
											src="../../img/nopicture.png" />
									</c:if>
									<div class="media-body">
										<div class="media-heading">
											<div class="row" style="margin-left: 0px; margin-right: 0px;">
												<span class="pull-left text-bold" style="font-size: 15px;">@<c:out
														value="${user_info.username}" /></span> <span
													class="pull-right text-muted" style="font-size: 15px"><fmt:formatDate
														type="both" value="${buit.date}" /></span>
											</div>
											<div class="row" style="margin-left: 0px; margin-right: 0px;">
												<p style="word-wrap: break-word; font-size: 15px">${buit.message}</p>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="col-6 col-sm-6 col-lg-6">
							<h3 style="text-align: center; margin-top: 12px;">Following</h3>
							<c:forEach items="${user_info.following}" var="following">
								<c:forEach items="${following.buits}" var="buit">
									<div class="media buit">
										<c:if test="${not empty following.photo}">
											<img class="pull-left media-object buit-profile-pic"
												src="../image?name=<c:out value="${following.username}"/>" />
										</c:if>
										<c:if test="${empty following.photo}">
											<img class="pull-left media-object buit-profile-pic"
												src="../../img/nopicture.png" />
										</c:if>
										<div class="media-body">
											<div class="media-heading">
												<div class="row"
													style="margin-left: 0px; margin-right: 0px;">
													<span class="pull-left text-bold" style="font-size: 15px">@<c:out
															value="${following.username}" /></span> <span
														class="pull-right text-muted" style="font-size: 15px"><c:out
															value="${buit.date}" /></span>
												</div>
												<div class="row"
													style="margin-left: 0px; margin-right: 0px;">
													<p style="word-wrap: break-word; font-size: 15px">${buit.message}</p>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:if>

			<c:if test="${empty user && not empty userlist}">
				<div class="well well-lg">
					<div class="row">
						<c:forEach items="${userlist}" var="us">
							<div class="col-6 col-sm-6 col-lg-4 text-center"
								style="height: 400px">
								<c:if test="${not empty us.photo}">
									<img class="img-circle profile-pic-home"
										src="../image?name=<c:out value="${us.username}"/>" />
								</c:if>
								<c:if test="${empty us.photo}">
									<img class="img-circle profile-pic-home"
										src="../../img/nopicture.png" />
								</c:if>
								<h3 class="username-home" style="word-wrap: break-word">
									<c:out value="${us.username}" />
								</h3>
								<p class="text-muted" style="word-wrap: break-word">
									"
									<c:out value="${us.description}" />
									"
								</p>
								<a href="../buit/profile/${us.username}"
									class="btn btn-link btn-xs">View profile</a>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>


		</div>

		<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
			<div class="well sidebar-nav">
				<span> Trending Topics </span> <span><select
					class="form-control" id="select" style="width: 100%">
						<option id="7"
							<c:if test="${selectedTime == '7'}">selected="selected"</c:if>>1
							week</option>
						<option id="1"
							<c:if test="${selectedTime == '1'}">selected="selected"</c:if>>1
							day</option>
						<option id="30"
							<c:if test="${selectedTime == '30'}">selected="selected"</c:if>>1
							month</option>
				</select></span>
				<ul class="nav">
					<c:if test="${empty trending}">
						<br />
						<p class="text-muted">There are no hashtags for this period of
							time.</p>
					</c:if>
					<c:forEach items="${trending}" var="trend">
						<li><a
							href="../buit/<c:url value="hashtag"><c:param name="name" value="${trend.hashtag}"/></c:url>"
							style="word-wrap: break-word"> #<c:out
									value="${trend.hashtag}" /> <c:out value="( ${trend.count} )" />
						</a></li>
					</c:forEach>
					<li><button id="<c:out value="${buit.id}"/>" type="button"
							onclick="refresh();" class="pull-right btn btn-link btn-xs">
							<i class="icon-refresh"> Refresh</i>
						</button></li>
				</ul>

			</div>
			<!--/.well -->
		</div>
		<!--/span-->
	</div>
	<!--/row-->

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>