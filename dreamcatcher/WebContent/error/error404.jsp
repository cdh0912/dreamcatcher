<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp" %>    
<style>
body{
	background:#eaeaea !important;
}	
.wrap{
	margin:0 auto !important;
	width:100% !important;
}
.logo{
	text-align:center !important;
	margin-top:10% !important;
	margin-bottom:10% !important;
}
.logo img{
	width:350px !important;
}
.logo p{
	color:#272727 !important;
	font-size:40px !important;
	margin-top:1px !important;
}	
.logo p span{
	color:lightgreen !important;
}	
.sub a{
	color:#fff !important;
	background:#272727 !important;
	text-decoration:none !important;
	padding:10px 20px !important;
	font-size:13px !important;
	font-family: arial, serif !important;
	font-weight:bold !important;
	-webkit-border-radius:.5em !important;
	-moz-border-radius:.5em !important;
	-border-radius:.5em !important;
}	
</style>
 <div class="wrap">
	<div class="logo">
			<p>OOPS! - Could not Find it</p>
			<img src="${root }/assets/img/404.png"/>
			<div class="sub">
			  <p><a href="${root }">Back </a></p>
			</div>
	</div>
 </div>	
