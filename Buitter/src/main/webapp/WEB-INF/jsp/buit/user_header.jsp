<div class="well well-lg text-center" style="margin-bottom: 0px">
				<c:if test="${not empty user_info.photo}">
					<img class="profile-pic" src="../image?name=<c:out value="${user_info.username}"/>"/>
				</c:if>
				<c:if test="${empty user_info.photo}">
					<img class="profile-pic" src="../../img/nopicture.png"/>
				</c:if>
				<h2 class="profile-name"><c:out value="${user_info.name}"/> <c:out value="${user_info.surname}"/></h2>
				<h4 class="profile-user">@<c:out value="${user_info.username}"/></h4>
				<p class="profile-desc text-muted" style="word-wrap:break-word">"<c:out value="${user_info.description}"/>"	</p>
			</div>
			<div class="bs-example table-responsive">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th><a href="<c:url value="../buit/profile"><c:param name="name" value="${user_info.username}"/></c:url>"><small><p style="margin-bottom: 0px;color: #000000;">${fn:length(user_info.mybuits)}</p></small><small><p style="margin-bottom: 0px;color: #999;">BUITS</p></small></a></th>
							<th><a href="<c:url value="../buit/following"><c:param name="name" value="${user_info.username}"/></c:url>"><small><p style="margin-bottom: 0px;color: #000000;";>${fn:length(user_info.following)}</p></small><small><p style="margin-bottom: 0px;color: #999;">FOLLOWING</p></small></a></th>
							<th><a href="<c:url value="../buit/followers"><c:param name="name" value="${user_info.username}"/></c:url>"><small><p style="margin-bottom: 0px;color: #000000;">${fn:length(user_info.followers)}</p></small><small><p style="margin-bottom: 0px;color: #999;">FOLLOWERS</p></small></a></th>
							<th><small><p style="margin-bottom: 0px;color: #000000;"><c:out value="${user_info.visits}"/></p></small><small><p style="margin-bottom: 0px; color: #999;">VISITORS</p></small></th>
							<c:if test="${not empty user}">
								<c:if test="${user != user_info.username}">
									<c:set var="found" value='false'/>
									<c:forEach items="${user_info.followers}" var="follower">
										<c:if test="${follower.username == user}"><c:set var="found" value='true'/></c:if>
									</c:forEach>
									<c:if test="${found == 'true'}"><c:set var="method" value='Unfollow'/></c:if>
									<c:if test="${found == 'false'}"><c:set var="method" value='Follow'/></c:if>
									<th><button id="<c:out value="${user_info.username}"/>" class="btn btn-follow btn-sm pull-right" style="margin-bottom: 3px" onclick="follow(this.id, '<c:out value="${method}"/>');"><img src="../../img/logo.png" class="logo" /><c:out value="${method}"/></img></button></th>
								</c:if>
								<c:if test="${user == user_info.username}">
									<th><button onclick="edit();" class="btn btn-follow btn-sm pull-right" style="margin-bottom: 3px"</button>Edit profile</th>
								</c:if>
							</c:if>
						</tr>
					</thead>
				</table> 
			</div>