<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!doctype html>
<html>
<head>  
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,400">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Droid+Sans">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Lobster">
<link rel="stylesheet" href="${root}/assets/plugin/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${root}/assets/plugin/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${root}/assets/css/animate.css">
<link rel="stylesheet" href="${root}/assets/css/magnific-popup.css">
<link rel="stylesheet" href="${root}/assets/plugin/flexslider/flexslider.css">
<link rel="stylesheet" href="${root}/assets/css/form-elements.css">
<link rel="stylesheet" href="${root}/assets/css/style.css">
<link rel="stylesheet" href="${root}/assets/css/media-queries.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

 -->
     
<!-- Favicon and touch icons -->
<link rel="shortcut icon" href="${root}/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="${root}/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="${root}/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="${root}/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="${root}/assets/ico/apple-touch-icon-57-precomposed.png">


<!-- Javascript -->
<script src="${root}/assets/js/jquery-1.9.1.js"></script>
<script src="${root}/assets/js/jquery-1.9.1.min.js"></script>
<script src='${root}/assets/js/moment.js'></script>
<script src='${root}/assets/js/fullcalendar.min.js'></script>
<script src='${root}/assets/js/jquery.qtip.min.js'></script>
<script src="${root}/assets/plugin/bootstrap/js/bootstrap.min.js"></script>
<script src="${root}/assets/js/jquery.validate.min.js"></script>
<script src="${root}/assets/js/bootstrap-hover-dropdown.min.js"></script>
<script src="${root}/assets/js/jquery.backstretch.min.js"></script>
<script src="${root}/assets/js/wow.min.js"></script>
<script src="${root}/assets/js/jquery.magnific-popup.min.js"></script>
<script src="${root}/assets/plugin/flexslider/jquery.flexslider-min.js"></script>
<script src="${root}/assets/js/jflickrfeed.min.js"></script>
<script src="${root}/assets/js/masonry.pkgd.min.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script src="${root}/assets/js/jquery.ui.map.min.js"></script>
<script src="${root}/assets/js/scripts.js"></script>
<link href="${root }/assets/plugin/bootstrap/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="${root }/assets/plugin/bootstrap/js/bootstrap-toggle.min.js"></script>
<link href="${root }/assets/plugin/jquery/css/jquery-ui.css" rel="stylesheet">
<script src="${root }/assets/plugin/jquery/js/jquery-ui.min.js"></script>

<script type="text/javascript">

</script>
</head>
<body>
	<header>
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</header>
	<section>
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</section>
	<footer>
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</footer>
</body>
</html>

