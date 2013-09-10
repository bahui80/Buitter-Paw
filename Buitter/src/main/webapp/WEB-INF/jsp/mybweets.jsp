<%@ include file="WEB-INF/jsp/header.jsp"%>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-9">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas">Toggle nav</button>
			</p>

			<div class="well well-lg text-center">
				<img src="img/nopicture.png" width="73px"
					style="border: 4px solid #fff; border-radius: 5px; box-shadow: 0 1px 1px; -webkit-box-shadow: 0 1px 1px" />
				<h2 style="margin: 8px">Nombre Apellido</h2>
				<h4 style="margin: 5px">@username</h4>
				<p style="margin-top: 10px; margin-left: 20%; margin-right: 20%">This
					is my description. Find out what's happening, right now, with the
					people and organizations you care about.</p>
			</div>

			<div class="well well-lg">
				<div class="row">
					<div class="col-md-5 col-md-offset-2">
						<form class="bs-example form-horizontal">
							<fieldset>
								<div class="form-group">

									<textarea class="form-control" rows="3" id="textArea"
										placeholder="Compose new Buit..." maxlength="140"></textarea>

									<button type="submit" class="btn btn-primary pull-right">Bweet</button>

								</div>
							</fieldset>
						</form>
					</div>

					<div class="row" style="margin-bottom: 3em">
						<div class="col-md-8 col-md-offset-2">
							<div class="row">
								<div class="col-md-1">
									<img src="img/nopicture.png" width="40px"
										style="border: 4px solid #fff; border-radius: 5px; box-shadow: 0 1px 1px; -webkit-box-shadow: 0 1px 1px" />
								</div>
								<div class="col-md-11">
									<div class="pull-left">@username</div>
									<div class="pull-right">fecha hora</div>
									<br />
									<div>Donec id elit non mi porta gravida at eget metus.
										Fusce dapibus, tellus ac cursus commodo, tortor mauris
										condimentum nibh, ut fermentum mass</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-10 col-md-offset-1">
							<div class="row">
								<div class="col-md-2">
									<img src="img/nopicture.png" width="48px" />
								</div>
								<div class="col-md-10">
									<div class="pull-left">@username</div>
									<div class="pull-right">fecha hora</div>
									<br />
									<div>Donec id elit non mi porta gravida at eget metus.
										Fusce dapibus, tellus ac cursus commodo, tortor mauris
										condimentum nibh, ut fermentum mass</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!--/row-->
			</div>
			<!--/span-->
		</div>
		<%@ include file="WEB-INF/jsp/sidebar.jsp"%>

	</div>
	<!--/row-->

	<%@ include file="WEB-INF/jsp/footer.jsp"%>