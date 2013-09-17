<%@ include file="WEB-INF/jsp/header.jsp"%>
<script>
		function refresh(){
			var e = document.getElementById("select");
			var strUser = e.options[e.selectedIndex];
			var str1 = "/Buitter/home?time=";
			var str2 = str1.concat(strUser.id);
			window.location = str2;
		}
	</script>
<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-xs-12 col-sm-9">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>
			<div class="jumbotron">
				<h1 id="main-name">Buitter.</h1>
				<p id="no-more-tweets">#NoMoreTweets. #WeBuitNow.</p>
			</div>


			<div class="row">
				<c:forEach items="${userlist}" var="us">
					<div class="col-6 col-sm-6 col-lg-4 text-center"
						style="margin-bottom: 40px">
						<c:if test="${not empty us.photo}">
							<img class="img-circle profile-pic-home"
								src="image?name=<c:out value="${us.username}"/>" />
						</c:if>
						<c:if test="${empty us.photo}">
							<img class="img-circle profile-pic-home" src="img/nopicture.png"/>
						</c:if>
						<h3 class="username-home">
							<c:out value="${us.username}" />
						</h3>
						<p class="text-muted">
							"
							<c:out value="${us.description}" />
							"
						</p>
						<a
							href="<c:url value="profile"><c:param name="name" value="${us.username}"/></c:url>"
							class="btn btn-link btn-xs">View profile</a>
					</div>
				</c:forEach>
			</div>


			<!-- <div class="row">
					<div class="col-md-offset-1 col-md-10">
						<h4>Heading</h4>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" href="#">View details &raquo;</a>
						</p> -->
			<!-- </div> -->
			<!--/span-->
			<!-- </div> -->
			<!--/row-->
		</div>
		<!--/span-->

		<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar"
			role="navigation">
			<div class="well sidebar-nav">
				<span> Trending Topics </span> <span><select
					class="form-control" id="select" style="width: 100%">
						<option id="7">1 week</option>
						<option id="1">1 day</option>
						<option id="30">1 month</option>
				</select></span>
				<ul class="nav">
					<c:forEach items="${trending}" var="trend">
						<li><a
							href="<c:url value="hashtag"><c:param name="name" value="${trend.hashtag}"/></c:url>">
								#<c:out value="${trend.hashtag}" />
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

	<%@ include file="WEB-INF/jsp/footer.jsp"%>