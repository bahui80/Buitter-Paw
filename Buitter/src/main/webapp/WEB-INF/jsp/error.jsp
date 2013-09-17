<%@ include file="header.jsp"%>
	<div class="bs-example">
              <div class="jumbotron">
                <h1><c:out value="${error}"></c:out></h1>
                <c:if test="${empty error}">
                	<h1>Unexpected error</h1>
                </c:if>
                <c:if test="${not empty error_log}">
                	<p><c:out value="${error_log}"	></c:out></p>
                </c:if>
                 <c:if test="${empty error_log}">
                	<p>We are working to solve your problems. Please report this.</p>
                </c:if>
                <img style="position:fixed; top:100px; right:60px;" src="img/tired_vulture.png">
           	  </div>
   	</div>
<%@ include file="footer.jsp"%>