<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>    

<script type="text/javascript">

$(document).ready(function() {
	$('#sitePage').val(1);
	$('#routePage').val(1);
	
	mySiteArticle(1);
	
});

var StringBuffer = function() { 
    this.buffer = new Array(); 
} 
StringBuffer.prototype.append = function(obj) {
     this.buffer.push(obj); 
} 
StringBuffer.prototype.toString = function(){ 
     return this.buffer.join(""); 
} 



function mySiteArticle(page){
	$('#sitePage').val(page);
	
//	document.myInfoForm.action = root+"/member";
//	document.myInfoForm.submit();
	myInfoAjax();
}

function myRouteArticle(page){
	$('#routePage').val(page);
	myInfoAjax();
}

function myInfoAjax(){
	$.ajax({
		url : root+"/member",
		type : "post",
		dataType : "json",
		data: $('#myInfoForm').serialize(),				
		success: 
		function( data ) {
			var siteArticleList = data.siteArticleList;
			if(siteArticleList.length>0){
				$('#siteArticleArea').html('');

				for(var i=0; i < siteArticleList.length; i++){
					var sb = new StringBuffer();
					sb.append(' <div class="portfolio-box"> \n');
					sb.append(' 	<div class="portfolio-box-container " onClick="javascript:moveSiteView(\''+siteArticleList[i].site_id+'\');"> \n');
					sb.append('			<div class="site-image" style="background-image:url(\''+root+'/upload/'+siteArticleList[i].savefolder+'/thumb/'+siteArticleList[i].saved_picture+'\');"></div>');
					sb.append(' 		<div class="portfolio-box-text"> \n');
					sb.append(' 			<h3>'+siteArticleList[i].site_name+'</h3> \n');
					sb.append(' 			<p><marquee>'+siteArticleList[i].brief_info+'</marquee></p> \n');
					sb.append(' 		</div> \n');
					sb.append(' 	</div> \n');
					sb.append(' 	<div class="best-item-vote"> \n');
					sb.append(' 		<div class="best-content"> \n');
					sb.append(' 			<ul class="progess-bars"> \n');
					sb.append(' 				<li> \n');
					sb.append(' 					<div class="progress"> \n');
					sb.append(' 						<div class="progress-bar comments" role="progressbar" \n');
					sb.append(' 							aria-valuenow="'+siteArticleList[i].rec_percent+'" aria-valuemin="0" aria-valuemax="100" \n');
					sb.append(' 							style="width: '+siteArticleList[i].rec_percent+'%"> \n');
					sb.append(' 						</div> \n');
					sb.append(' 						<span class="comments">'+siteArticleList[i].recommend+'<i class="fa fa-heart"></i></span> \n');
					sb.append(' 					</div> \n');
					sb.append(' 				</li> \n');
					sb.append(' 			</ul> \n');
					sb.append(' 		</div> \n');
					sb.append(' 	</div> \n');
					sb.append(' </div> \n');
					$('#siteArticleArea').append(sb.toString());
				}
				$('#siteArticleArea').masonry('reloadItems');
				$('#siteArticleArea').masonry('layout');
				$('#siteNavigatorArea').html(data.siteNavigator.navigator);

			}else{
				var sb = new StringBuffer();
				sb.append('<div style="padding-top:0; padding-bottom:10%; margin-top:0; margin-bottom:10%"> \n');
				sb.append('<h4>등록하신 여행지가 없습니다.</h4> \n');
				sb.append('</div> \n');
				$('#siteArticleArea').html(sb.toString()).css( {'padding-top':'10%', 'padding-bottom':'10%'});
			}
			
			var routeArticleList = data.routeArticleList;
			if(routeArticleList.length>0){
				$('#routeArticleArea').html('');

				for(var i=0; i < routeArticleList.length; i++){
					var sb = new StringBuffer();
					sb.append('<div class="portfolio-box"> \n');
					sb.append('	<div class="best-item-vote"> \n');
					sb.append('		<div class="work wow fadeInUp"> \n');
					sb.append('			<img src="'+routeArticleList[i].route_url+'" alt="" onClick="javascript:moveRouteView(\''+routeArticleList[i].route_id+'\');"> \n');
					sb.append('			<a href="'+routeArticleList[i].route_url+'">');
					sb.append('			<i class="portfolio-box-icon fa fa-search"></i></a>');		
					sb.append('		</div> \n');
					sb.append('		<div class="best-content portfolio-box-text"> \n');
					sb.append('			<h5><a onClick="javascript:moveRouteView(\''+routeArticleList[i].route_id+'\');">'+routeArticleList[i].title+'</a></h5> \n');
					sb.append('			<ul class="progess-bars"> \n');
					sb.append('				<li> \n');
					sb.append('					<div class="progress"> \n');
					sb.append('						<div class="progress-bar" role="progressbar" \n');
					sb.append('							aria-valuenow="'+routeArticleList[i].rep_percent+'" aria-valuemin="0" aria-valuemax="100" \n');
					sb.append('							style="width: '+routeArticleList[i].rep_percent+'%;"></div> \n');
					sb.append('						<span>'+routeArticleList[i].reply_count+'<i class="fa fa-heart"></i></span> \n');
					sb.append('					</div> \n');
					sb.append('				</li> \n');
					sb.append('				<li> \n');
					sb.append('					<div class="progress"> \n');
					sb.append('						<div class="progress-bar comments" role="progressbar" \n');
					sb.append('							aria-valuenow="'+routeArticleList[i].rec_percent+'" aria-valuemin="0" aria-valuemax="100" \n');
					sb.append('							style="width: '+routeArticleList[i].rec_percent+'%;"></div> \n');
					sb.append('						<span class="comments">'+routeArticleList[i].recommend+'<i class="fa fa-heart"></i></span> \n');
					sb.append('					</div> \n');
					sb.append('				</li> \n');
					sb.append('			</ul> \n');
					sb.append('		</div> \n');
					sb.append('	</div> \n');
					sb.append('</div> \n');
					$('#routeArticleArea').append(sb.toString());
				}
				$('#routeArticleArea').masonry('reloadItems');
				$('#routeArticleArea').masonry('layout');
				$('#routeNavigatorArea').html(data.routeNavigator.navigator);
				
			}else{
				var sb = new StringBuffer();
				sb.append('<div style="padding-top:0; padding-bottom:10%; margin-top:0; margin-bottom:10%"> \n');
				sb.append('<h4>등록하신 경로가 없습니다.</h4> \n');
				sb.append('</div> \n');
				$('#routeArticleArea').html(sb.toString()).css( {'padding-top':'10%', 'padding-bottom':'10%'});	
				
			}
			
			$('#sitePage').val(data.sitePage);
			$('#routePage').val(data.routePage);
			addMagnificPopupEvent();
	    },
		error : function(data) {
			alert("로그인 이후 사용 가능한 서비스입니다.\n메인 페이지로 이동합니다.");
			document.location.href="/dreamcatcher/";
		}
	});
	
}

function moveSiteView(site_id){
	document.location.href=root+'/site?act=siteView&site_id='+site_id;
	
}

function moveRouteView(route_id){
	//alert(route_id);
	document.location.href=root+'/plan?act=viewPlan&route_id='+route_id;
	
}
</script>
<form id="myInfoForm" name="myInfoForm" method="get" action="">
	<input type="hidden" id="act" name="act" value="myInfo">
<!-- 	
	<input type="hidden" id="sitePage" name="sitePage" value="${param.sitePage}">
	<input type="hidden" id="siteSearchMode" name="siteSearchMode" value="${param.siteSearchMode}">
	<input type="hidden" id="routePage" name="routePage" value="${param.routePage}">
	<input type="hidden" id="routeSearchMode" name="routeSearchMode" value="${param.routeSearchMode}">
 -->
	<input type="hidden" id="sitePage" name="sitePage" value="">

	<input type="hidden" id="routePage" name="routePage" value="">


</form>    



        <div class="page-title-container">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 wow fadeIn">
                        <i class="fa fa-camera"></i>
                        <h1>내 정보 /</h1>
                        <p>직접 등록한 여행지와 경로 정보를 확인하세요!</p>
                    </div>
                </div>
            </div>
        </div>
	<!--  -->
<div class="content-section">
	<div class="container">
		<div class="row">
			<div class="col-sm-12 work-title wow fadeIn">
				<h2>내가 등록한 여행지</h2>
			</div>

		</div>
		<!-- /.row -->
		<div class="row">

			<div id="siteArticleArea" class="col-sm-12 portfolio-masonry">
	            	
                
	        </div>
	     	<div class="row" id="siteNavigatorArea">
	
			</div>			
		</div>
	</div>
</div>

<div class="content-section">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 work-title wow fadeIn">
					<h2>내 여행경로</h2>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
		
			
				<div id="routeArticleArea" class="col-sm-12 portfolio-masonry">
		
				</div>
			</div>
	
			<div class="row" id="routeNavigatorArea">
			</div>			
				
		</div>
		<!-- /.container -->
	</div>