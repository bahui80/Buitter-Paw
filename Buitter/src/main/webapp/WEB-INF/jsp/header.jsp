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
<link rel="shortcut icon" href="img/logo.png">

<title>Buitter</title>

<!-- Bootstrap core CSS -->
<link href="css/style.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/custom.css" rel="stylesheet">

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
	<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a id="logo" class="navbar-brand" href="#"><img src="img/logo.png"
					class="logo" />Buitter</a>
			</div>
			<div class="collapse navbar-collapse">
				<form class="navbar-form navbar-left">
					<div class="form-group">
						<input type="text" style="margin-top: 4px" class="form-control" placeholder="Search">
					</div>
				</form>
				<ul class="nav navbar-nav" style="float:right">
					<li class="active"><a href="#">Home</a></li>
					<c:if test="${not empty user}">
						<li><a href="#myBuitts"><c:out value="${user.username}"/></a></li>
						<li id="dropdown" class="dropdown" onclick="toggle()">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                          <ul class="dropdown-menu">
                            <li><a href="/Buitter/logout">Logout</a></li>
                             <li class="divider"></li>
                            <li><a href="#">Edit profile</a></li>
                          </ul>
                        </li>
					</c:if>
					<c:if test="${empty user}">
						<li style="margin-top: 8px"><button type="button" class="btn btn-primary" onclick="location.assign('login');">Log in</button></li>
						<li style="margin-top: 8px; margin-left: 8px;"><button type="button" class="btn btn-success" onclick="location.assign('register');">Register</button></li>
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