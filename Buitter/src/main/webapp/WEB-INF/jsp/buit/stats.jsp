<%@ include file="/WEB-INF/jsp/header.jsp"%>
<script>
	function home() {
		var str1 = "/Buitter/web/home/home";
		window.location = str1;
	}
</script>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-8 col-md-offset-2">

			<div class="well well-lg">
				<span class="custom-header">Statistics</span>


				<c:choose>
					<c:when test="${buit_count == 0}">
						<div class="alert-box">
							<img src="../../img/logo.png" class="logo-alt" /> <br />No
							buits to analyze.
						</div>
					</c:when>
					<c:otherwise>
					
<!-- asi como esta hecho, si entra por primera vez (sin parametros) le tira todos los buits de su cuenta -->
<!-- en cambio si hay parametros, toma esos  -->
						<strong>Analyzing ${buit_count} buits from your account.</strong><br/><br />
						!!!grafico!!!

						<br /><br /><br />
					</c:otherwise>
				</c:choose>


				<form>
					Filter buits by date:<br /> <br />

					<div
						class="form-group <c:if test="${not empty error_fromdate}">has-error</c:if>">
						<label for="fromdate" class="col-md-2 control-label">From</label>
						<div class="col-md-9">
							<input type="date" name="fromdate"
								value="<c:out value="${fromdate}"/>" />
							<c:if test="${not empty error_fromdate}">
								<p class="text-danger" style="margin-bottom: 0px">
									<small><em><c:out value="${error_fromdate}" /></em></small>
								</p>
							</c:if>
						</div>
					</div>
					<div
						class="form-group <c:if test="${not empty error_todate}">has-error</c:if>">
						<label for="todate" class="col-md-2 control-label">To</label>
						<div class="col-md-9">
							<input type="date" name="todate"
								value="<c:out value="${todate}"/>" />
							<c:if test="${not empty error_todate}">
								<p class="text-danger" style="margin-bottom: 0px">
									<small><em><c:out value="${error_todate}" /></em></small>
								</p>
							</c:if>
						</div>
					</div>


					<br /> <br /> <br />
					<p>Group data by:
					<p />
					<c:if test="${not empty error_groupby}">
						<p class="text-danger" style="margin-bottom: 0px">
						<div class="has-error">
							<small><em><c:out value="${error_groupby}" /></em></small>
						</div>
						</p>
					</c:if>
					<div class="col-md-2">
						<label for="month">Month <input type="radio"
							name="groupby" value="month"
							<c:if test="${groupby=='month'}">checked</c:if> />
						</label>
					</div>
					<div class="col-md-2">
						<label for="day">Day <input type="radio" name="groupby"
							value="day" <c:if test="${groupby=='day'}">checked</c:if> />
						</label>
					</div>
					<div class="col-md-7">
						<label for="hour">Hour <input type="radio" name="groupby"
							value="hour" <c:if test="${groupby=='hour'}">checked</c:if> />
						</label>
					</div>
					<br /> <br /> <br />
					<div class="form-group">
						<div class="col-md-offset-2">
							<button type="button" class="btn btn-default" onclick="home();">Cancel</button>
							<button type="submit" class="btn btn-primary">Confirm</button>
						</div>
					</div>
				</form>


			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>