<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../img/logo.png">

<title>Buitter</title>

<!-- Bootstrap core CSS -->
<link href="../../css/style.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../../css/custom.css" rel="stylesheet">

<!-- Font-awesome CSS -->
<link href="../../css/font-awesome.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->	


<script type=text/javascript>
function toggle(){
	var dropdown = document.getElementById("dropdown");
	if(dropdown.className == 'dropdown'){
		document.getElementById("dropdown").setAttribute("class", "dropdown open");
	}else{
		document.getElementById("dropdown").setAttribute("class", "dropdown");
	}
}
</script>
</head>

<body>
	<div class="navbar navbar-fixed-top navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				
				<a id="logo" class="navbar-brand" href="/Buitter/web/home/home"><img src="../../img/logo.png"
					class="logo" />Buitter</a>
			</div>
			<div class="collapse navbar-collapse">
				<form class="navbar-form navbar-left" method="get" action="searchresults">
					<div class="form-group">
						<input type="search" name="name" style="margin-top: 4px" class="form-control" placeholder="Search">
					</div>
				</form>
				<ul class="nav navbar-nav" style="float:right">
					<li class=""><a href="/Buitter/web/home/home"><i class='icon-home'></i> Home</a></li>
					<c:if test="${not empty user}">
						<li><a href="<c:url value="profile"><c:param name="name" value="${user}"/></c:url>">
							<c:out value="@${user}"/></a></li>
						<li id="dropdown" class="dropdown" onclick="toggle()">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class='icon-cog'></i>	 </a>
                          <ul class="dropdown-menu">
                           <li><a href="/Buitter/web/user/editprofile"><i class='icon-pencil'></i>  Edit profile</a></li>
                             <li class="divider"></li>
                             <li><a href="/Buitter/web/user/logout"><i class='icon-power-off'></i>  Logout</a></li>
                          </ul>
                        </li>
					</c:if>
					<c:if test="${empty user}">
						<li style="margin-top: 8px"><button type="button" class="btn btn-primary" onclick="location.assign('/Buitter/web/user/login');">Log in</button></li>
						<li style="margin-top: 8px; margin-left: 8px;"><button type="button" class="btn btn-success" onclick="location.assign('/Buitter/web/user/register');">Register</button></li>
					</c:if>
				</ul>
			<div>
				
				</div>
			</div>
			<!-- /.nav-collapse -->
		</div>
		<!-- /.container -->
	</div>
	<!-- /.navbar -->
