<%@ include file="WEB-INF/jsp/header.jsp" %>

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
					<div class="col-md-offset-1 col-md-10">
						<h4>Heading</h4>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" href="#">View details &raquo;</a>
						</p>
					</div>
					<!--/span-->
				</div>
				<!--/row-->
			</div>
			<!--/span-->

			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar"
				role="navigation">
				<div class="well sidebar-nav">
				<span> Trending Topics </span> <span><select class="form-control" id="select" style="width:100%">
                        <option>1 day</option>
                        <option>1 week</option>
                        <option>1 month</option>
                        
                      </select></span>
					<ul class="nav">
						<c:forEach items="${trending}" var="trend">
							<li><a href="<c:url value="hashtag"><c:param name="name" value="${trend.hashtag}"/></c:url>">  #<c:out value="${trend.hashtag}" /></a></li>
						</c:forEach>
						<li><button id="<c:out value="${buit.id}"/>" type="button" onclick="proceed(this.id);" class="pull-right btn btn-link btn-xs"><i class="icon-refresh"> Refresh</i></button></li>
					</ul>
					
				</div>
				<!--/.well -->
			</div>
			<!--/span-->
		</div>
		<!--/row-->

<%@ include file="WEB-INF/jsp/footer.jsp" %>
