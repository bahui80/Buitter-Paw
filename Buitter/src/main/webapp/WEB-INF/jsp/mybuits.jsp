<%@ include file="WEB-INF/jsp/header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-7 col-md-offset-1">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>

			<div class="well well-lg text-center">
				<img class="profile-pic" src="img/nopicture.png" />
				<h2 class="profile-name">Nombre Apellido</h2>
				<h4 class="profile-user">@username</h4>
				<p class="profile-desc">This
					is my description. Find out what's happening, right now, with the
					people and organizations you care about.</p>
			</div>

			<div class="well well-lg">
				<div class="input-group buit-compose-form">
					<form>
						<fieldset>
							<textarea class="form-control" rows="3" id="textArea"
								placeholder="Compose new Buit..." maxlength="140"></textarea>

							<button type="submit"
								class="btn btn-primary pull-right buit-compose-button">Buit</button>
						</fieldset>
					</form>
				</div>

				<div class="media buit">
					<a class="pull-left" href="#"> <img
						class="media-object buit-profile-pic" src="img/nopicture.png">
					</a>
					<div class="media-body">
						<div class="media-heading">
							<span class="pull-left text-bold">@username</span> <span
								class="pull-right">fecha hora</span>
						</div>
						<br /> Donec id elit non mi porta gravida at eget metus. Fusce
						dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
						ut fermentum mass
					</div>
				</div>

				<div class="media buit">
					<a class="pull-left" href="#"> <img
						class="media-object buit-profile-pic" src="img/nopicture.png">
					</a>
					<div class="media-body">
						<div class="media-heading">
							<span class="pull-left text-bold">@username</span> <span
								class="pull-right">fecha hora</span>
						</div>
						<br /> Donec id elit non mi porta gravida at eget metus. Fusce
						dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
						ut fermentum mass
					</div>
				</div>

			</div>

		</div>
		<%@ include file="WEB-INF/jsp/sidebar.jsp"%>

	</div>
	<!--/cerrar el div si la sidebar esta presente-->

	<%@ include file="WEB-INF/jsp/footer.jsp"%>