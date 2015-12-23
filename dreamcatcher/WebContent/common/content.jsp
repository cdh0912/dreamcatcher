<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>    

<script type="text/javascript">
$(document).ready(function() {
 	$('.searchCheckBox').change(function() {
		  if ($(this).is(':checked')) {
		    $(this).attr('checked', true);
		  } else {
		    $(this).attr('checked', false);
		  }
	});
 	
 	$('#siteSearchCheckBox').change(function() {
 		if(!$(this).prop('checked')){
 			$('#searchMode').val('recommend');	
 		}else{
 			$('#searchMode').val('date');	
 		}
 		$('#act').val('siteArticleSort');
 		$('#page').val('1');
 		$('#viewMode').val('mainView');
 		$('#searchWord').val('');		
 		$.ajax({
 			url : root+"/main",
 			type : "post",
 			dataType : "json",
 			data: $('#boardForm').serialize(),				
 			success: 
 			function( siteArticleList ) {
 				if(siteArticleList.length>0){
 					$('#siteArticleArea').html('');
	 				for(var i=0; i < siteArticleList.length; i++){
	 					var sb = new StringBuffer();
	 					sb.append(' <div class="col-sm-3"> ');
	 					sb.append(' 	<div class="best-item"> ');
	 					sb.append(' 		<div class="wow fadeInUp portfolio-item"> ');
	 					sb.append(' 			<div class="portfolio-image" style="background-image:url(\''+root+'/upload/'+siteArticleList[i].savefolder+'/thumb/'+siteArticleList[i].saved_picture+'\');"></div>');
	 					sb.append(' 			<div class="detail"> ');
	 					sb.append(' 				<h4>'+siteArticleList[i].site_name+'</h4> ');
	 					sb.append(' 				<p>'+siteArticleList[i].brief_info +'</p> ');
	 					sb.append(' 				<span class="btn" onClick="javascript:moveSiteView(\''+siteArticleList[i].site_id+'\');">상세보기</span> ');
	 					sb.append(' 			</div> ');
	 					sb.append(' 		</div> ');
	 					sb.append(' 		<div class="best-content"> ');
	 					sb.append(' 			<h4> ');
	 					sb.append(' 				<a onClick="javascript:moveSiteView(\''+siteArticleList[i].site_id+'\');">'+siteArticleList[i].site_name+'</a> ');
	 					sb.append(' 			</h4> ');
	 					sb.append(' 			<span class="reccomend">'+siteArticleList[i].recommend+' <i ');
	 					sb.append(' 				class="fa fa-heart"></i></span> ');
	 					sb.append(' 		</div> ');
	 					sb.append(' 	</div> ');
	 					sb.append(' </div> ');
	 					$('#siteArticleArea').append(sb.toString());
	 	
	 				}
 				}
 		    },
 			error : function(data) {
 				alert("에러가 발생하였습니다.")
 			}
 		});
	});
 	
 	$('#routeSearchCheckBox').change(function() {
 		if(!$(this).prop('checked')){
 			$('#searchMode').val('recommend');	
 		}else{
 			$('#searchMode').val('date');	
 		}
 		$('#act').val('routeArticleSort');
 		$('#page').val('1');
 		$('#viewMode').val('mainView');
 		$('#searchWord').val('');		
 		$.ajax({
 			url : root+"/main",
 			type : "post",
 			dataType : "json",
 			data: $('#boardForm').serialize(),				
 			success: 
 			function( routeArticleList ) {
 				if(routeArticleList.length>0){
 					$('#routeArticleArea').html('');
	 				for(var i=0; i < routeArticleList.length; i++){
	 					var sb = new StringBuffer();
	 					sb.append('<div class="col-sm-3"> \n');
	 					sb.append('	<div class="best-item-vote"> \n');
	 					sb.append('		<div class="work wow fadeInUp"> \n');
	 					sb.append('			<img src="'+routeArticleList[i].route_url+'" alt="" onClick="javascript:moveRouteView(\''+routeArticleList[i].route_id+'\');"> \n');
						sb.append('			<a href="'+routeArticleList[i].route_url+'">');
						sb.append('			<i style="right:25px;" class="portfolio-box-icon fa fa-search"></i></a>');			 					
	 					sb.append('		</div> \n');
	 					sb.append('		<div class="best-content portfolio-box-text"> \n');
	 					sb.append('			<h5> \n');
	 					sb.append('				<a onClick="javascript:moveRouteView(\''+routeArticleList[i].route_id+'\');">'+routeArticleList[i].title+'</a> \n');
	 					sb.append('			</h5> \n');
	 					sb.append('			<span class="tagline">By: '+routeArticleList[i].name+'</span> \n');
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
	 				addMagnificPopupEvent();
 				}
 				
 		    },
 			error : function(data) {
 				alert("에러가 발생하였습니다.")
 			}
 		});
	});
	
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

function moveSiteView(site_id){
	document.location.href=root+'/site?act=siteView&site_id='+site_id;
	
}

function moveRouteView(route_id){
	//alert(route_id);
	document.location.href=root+'/plan?act=viewPlan&route_id='+route_id;
	
}
</script>
	<!-- Main Slider -->
	<div class="slider-container">
		<div class="container">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1 slider">
					<div class="flexslider">
						<ul class="slides">
						<c:forEach var="siteAdminArticle"  varStatus="status" items="${siteAdminArticleList}">					
							<li data-thumb="${root }/upload/${siteAdminArticle.savefolder}/thumb/${siteAdminArticle.saved_picture}">
							<div class="thumbnail"><img src="${root }/upload/${siteAdminArticle.savefolder}/${siteAdminArticle.saved_picture}">
							<div class="flex-caption"><p style="font-size:20pt">${siteAdminArticle.site_name }</p>${siteAdminArticle.brief_info }</div></div></li>				
						</c:forEach>								
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div class="presentation-container">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 wow fadeInLeftBig animated"
					style="visibility: visible;">
					<h1>
						지금 <span class="violet">Dream Catcher</span>와 함께 여행하세요.
					</h1>
					<p>당신의 꿈이 현실로 실현됩니다.</p>
				</div>
			</div>
		</div>
	</div>

	<!--  -->
<div class="content-section">
	<div class="container">
		<div class="row">
			<div class="col-sm-12 work-title wow fadeIn">
				<h2>떠나보세요 !!</h2>
			</div>
			<div class="row">
			<div class="col-sm-2 wow fadeInDown animated">
				<br />
				<br /> <input checked type="checkbox" class="searchCheckBox"
					id="siteSearchCheckBox" name="siteSearchCheckBox"
					data-toggle="toggle" data-on="등록일순" data-off="추천순"
					data-onstyle="toggle1" data-offstyle="toggle2">
			</div>
			<div class="col-sm-2 col-sm-offset-8 wow fadeInDown animated">
				<br />
				<br />
				<button class="more-btn btn-default"
					onclick="javascript:moveFirstSiteArticleList();">더 보기</button>
			</div>
			</div>
		</div>
		<!-- /.row -->
		<div class="row">
			<div id="siteArticleArea">
				<c:forEach var="siteArticle" varStatus="status" items="${siteArticleList}">	
					<div class="col-sm-3">
						<div class="best-item">
							<!-- <div class="service wow fadeInUp"> -->
							<div class="wow fadeInUp portfolio-item">
							<div class="portfolio-image" style="background-image:url('${root }/upload/${siteArticle.savefolder}/thumb/${siteArticle.saved_picture}');"></div>
								<div class="detail">
									
									<h4>${siteArticle.site_name}</h4>
									<p>${siteArticle.brief_info }</p>
									<div onClick="javascript:moveSiteView('${siteArticle.site_id}');">상세보기</div>

								</div>
							</div>
							
							<!-- /.best-thum -->
							<div class="best-content">
								<h4>
									<a onClick="javascript:moveSiteView('${siteArticle.site_id}');">${siteArticle.site_name}</a>
								</h4>
								<span class="reccomend">${siteArticle.recommend} <i
									class="fa fa-heart"></i></span>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<div class="content-section">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 work-title wow fadeIn">
					<h2>따라가기</h2>
				</div>
				<div class="row">
				<div class="col-sm-2 wow fadeInDown animated"><br/><br/>
					<input checked type="checkbox" class="searchCheckBox" id="routeSearchCheckBox" name="routeSearchCheckBox" data-toggle="toggle" data-on="등록일순" data-off="추천순" data-onstyle="toggle1" data-offstyle="toggle2">
				</div>
				<div class="col-sm-2 col-sm-offset-8 wow fadeInDown animated"><br/><br/>
					<button class="more-btn btn-default" onclick="javascript:moveFirstRouteArticleList();">더 보기</button>
				</div>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
			<div id="routeArticleArea">
			<c:forEach var="routeArticle" varStatus="status" items="${routeArticleList}">
				<div class="col-sm-3">
					<div class="best-item-vote">
						<div class="work wow fadeInUp">
						
							<img src="${routeArticle.route_url}" alt="" onClick="javascript:moveRouteView('${routeArticle.route_id}');">
							<a href="${routeArticle.route_url}">
							<i style="top:10px; right:10px;" class="portfolio-box-icon fa fa-search"></i></a>
						</div>
						<!-- /.best-thum -->
						<div class="best-content portfolio-box-text">

							<h5>
								<a onClick="javascript:moveRouteView('${routeArticle.route_id}');">${routeArticle.title }</a>
							</h5>
							<span class="tagline">By: ${routeArticle.name}</span>
							<ul class="progess-bars">
								<li>
									<div class="progress">
										<div class="progress-bar" role="progressbar"
											aria-valuenow="${routeArticle.rep_percent}" aria-valuemin="0" aria-valuemax="100"
											style="width: ${routeArticle.rep_percent}%;"></div>
										<span>${routeArticle.reply_count }<i class="fa fa-heart"></i></span>
									</div>
								</li>
								<li>
									<div class="progress">
										<div class="progress-bar comments" role="progressbar"
											aria-valuenow="${routeArticle.rec_percent}" aria-valuemin="0" aria-valuemax="100"
											style="width: ${routeArticle.rec_percent}%;"></div>
										<span class="comments">${routeArticle.recommend}<i class="fa fa-heart"></i></span>
									</div>
								</li>
							</ul>
						</div>
						<!-- /.best-content -->
					</div>
					<!-- /.best-item-vote -->
				</div>
			</c:forEach>
			</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /.container -->
	</div>