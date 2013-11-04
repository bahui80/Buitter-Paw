<%@ include file="/WEB-INF/jsp/header.jsp"%>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>

<c:if test="${not empty labels}">
	<c:if test="${not empty values}">
					<script type="text/javascript">
$(function () {
        $('#container').highcharts({
            chart: {
                type: 'column',
                margin: [ 50, 50, 100, 80]
            },
            title: {
                text: 'Statistics'
            },
            xAxis: {
                categories: [
                    <c:forEach items="${labels}" var="label">
							'<c:out value="${label}" />',	
					</c:forEach>
                ],
                labels: {
                    rotation: -45,
                    align: 'right',
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Cantidad de buits'
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: 'Buits entre <c:out value="${toDate}"/> y <c:out value="${toDate}"/>: <b>{point.y:.1f}</b>',
            },
            series: [{
                name: 'Population',
                data: [<c:forEach items="${values}" var="value">
							<c:out value="${value}" />,	
					</c:forEach>],
                dataLabels: {
                    enabled: true,
                    rotation: -90,
                    color: '#FFFFFF',
                    align: 'right',
                    x: 4,
                    y: 10,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif',
                        textShadow: '0 0 3px black'
                    }
                }
            }]
        });
    });
    
</script>						
	</c:if>								
</c:if>


    <script src="${pageContext.request.contextPath}/js/Highcharts/js/highcharts.js"></script>
    <script src="${pageContext.request.contextPath}/js/Highcharts/js/modules/exporting.js"></script>

<div class="container">

	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-md-8 col-md-offset-2">

			<div class="well well-lg">
				<span cla	ss="custom-header">Statistics</span>

				<c:choose>
					<c:when test="${buit_count == 0}">
							<div class="alert-box">
							<img src="../../img/logo.png" class="logo-alt" /> <br />No
							buits to analyze.
						</div>
					</c:when>
					<c:otherwise>
					
						<strong>Analyzing ${buit_count} buits from your account.</strong><br/><br />
							<c:if test="${not empty labels}">
								<c:if test="${not empty values}">
							<div id="container" style="min-width: 500px; height: 400px; margin: 0 auto"></div>
								</c:if>								
							</c:if>
						<br /><br /><br />
					</c:otherwise>
				</c:choose>


				<form method="post" action="showstats">
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