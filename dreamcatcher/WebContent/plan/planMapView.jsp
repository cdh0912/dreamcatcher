<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ArrayList, com.dreamcatcher.site.model.SiteDto"%>
<%@include file="/common/common.jsp"%>

<!doctype html>
<html>
<head>  
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Dream Catcher</title>

<script src="${root}/assets/js/jquery-1.9.1.js"></script>
<script src="${root}/assets/js/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.1.1.min.js"></script>
<link rel='stylesheet' href='//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css'>
<link rel="stylesheet" href="${root}/assets/plugin/bootstrap/css/bootstrap.min.css">
<script src="${root}/assets/plugin/bootstrap/js/bootstrap.min.js"></script>

<link rel="shortcut icon" href="${root}/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="${root}/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="${root}/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="${root}/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="${root}/assets/ico/apple-touch-icon-57-precomposed.png">

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&region=KR"></script>
<script src="${root}/assets/js/planner/RegisterMapFunction.js"></script>
<link href="${root}/assets/css/planner/plannerstyle.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
var realRoot = "${root}";

$(function(){
	// planMapViewAction에서 siteList의 jsonString을 받아온다
	$.ajax({  
		url: root+'/plan',  
		data: {'act':'getSitesForMap'}, 
		type:'post',
		dataType:'json',
		success: siteListResult,
		error : function(e){}
	}); 
	
	// modalButton이 클릭됐을때 #siteAddModal 디비전에 url을 띄운다.
	$('a.modalButton').on('click', function (e) {
		e.preventDefault();
		var url = $(this).data('src');
		var target = $(this).data('target');
		$(target).removeData('bs.modal');
		$(target).modal({remote: url });
		$(target).modal('show');
	});

	/*	$('a.modalButton').on('click', function(e) {
			e.preventDefault();
	    	var url = $(this).data('src');
	    	var target = $(this).data('target');
	    	//$(".modal-body").html('<iframe style="overflow:auto" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency="true" src="'+url+'"></iframe>');
	    	var frame = $(window.parent.document).find(target+ " iframe");	
	    	$(frame).attr({
	    		'src':url,
	    		width:'100%',
	    		height:$(document).find(target+ " .modal-content").height()+'px',
	    		scrolling:'no'
	    		
	    	});
	    	$(target).modal('show');

		});
		*/
	
	// dreamcatcher로고에 마우스오버시 모달버튼이 나타난다
	$('.addSiteImgUnPushed').on('hover', function () {
		$(".addSiteImgUnPushed").hide();
		$(".addSiteImgPushed").show();
		$(".addSiteImgText").fadeIn();
	});
	// 맵에 마우스오버시 모달버튼이 사라진다
	$('#map_canvas').on('hover', function () {
		$(".addSiteImgText").fadeOut();
		$(".addSiteImgPushed").hide();
		$(".addSiteImgUnPushed").show();
	});
	
	
	// 검색창 자동완성
	$("#keyword").autocomplete({
		matchContains: true,
		source : function(request, response) {

			$.ajax({
				url : root+"/plan?act=autoCompleteInMap",
				type : "post",
				dataType : "json",
				data: request,
					
				success: function( result ) {
			           	//return 된 녀석을 response() 함수내에 다음과 같이 정의해서 뽑아온다.
					response( 
					 	$.map( result, function( item ) {
					 			return {
					 			//label : 화면에 보여지는 텍스트
					 			//value : 실제 text태그에 들어갈 값
					 			//본인은 둘다 똑같이 줬음
					 			//화면에 보여지는 text가 즉, value가 되기때문 
					   				label: "<div style='width:100%;' onclick='markerClick(" + item.site_id + ")'>" + item.keyword + " &nbsp;  &nbsp; <span class='searchedAddress'>(" 
					   						+ item.address + ")</span></div>",
					   				value: item.keyword
					 			}
						})
					);
			           },
				error : function(data) {
					alert("에러가 발생하였습니다.")
				}
			});
		}
	});	
	
	$.ui.autocomplete.prototype._renderItem = function (ul, item) {
        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span class='selected-keyword'>$1</span>");
        return $("<li></li>")
                .data("item.autocomplete", item)
                .append('<a style="background-color:white">' + item.label + '</a>')
                .appendTo(ul);
        };	
 
    //드롭다운 버튼 클릭 시 메뉴 나타남
   	$('#dropButton').click(
   			function() { if ( $('#dropMenu').is(':visible') ) {
   							$('#dropMenu').slideUp();
   						 } else {
   							$('#dropMenu').slideDown(); 
   	}});
   	
   	
   	//검색창에 마우스오버 시 투명도 1
   	$('#searchWindow, .ui-autocomplete').hover(
  			function() {
  				$('#searchWindow, .ui-autocomplete').css({'-webkit-transition':'all 0.5s ease','opacity': '1'})
  			},
  			function() {
  				$('#searchWindow, .ui-autocomplete').css({'-webkit-transition':'all 0.5s ease','opacity': '0.3'})
  			}
   	);
   	
   	


});



//jQueryajax의 결과값data를 siteList로 정의
function siteListResult(data){
	siteList = data;
	LoadMap();
}



//일정관리 버튼 눌렀을 때 추가한 루트가 0개라면 submit 막음.
function isPolylistCheck() {
	if(polylist) {
		 mapSave();
	} else {
		alert("지도에서 경로를 추가하세요.");
		return false;
	}
}

//일정등록 버튼 눌렀을때, 지도 끝
function mapSave() {

	makeRouteImageURL(); //루트의 위도 경로로 google static map 이미지를 가져올 URL을 만든다.

	var jsonObject = {"polylist":polylist, "route_url":route_url};
								//	console.log(jsonObject);	//	alert(jsonObject.route_url);		//	alert(jsonObject.polylist[0].site_name);		//	console.log(jsonObject);
	var jsonString = JSON.stringify(jsonObject);
								//	alert(jsonString);
	$("#act").val("moveToPlanPage");
	$("#jsonString").val(jsonString);
	document.plannerForm.action = root + "/plan";
	document.plannerForm.submit();
}


function moveSiteView(site_id){
	document.location.href = root+'/site?act=siteView&site_id='+site_id;
}

function logout(){
	jQuery.ajax({
		url: root+'/member',
		type : 'post',
		data : {'act':'logout'},
		dataType : 'json',
		success : memberLogoutSuccess,
		error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
	});
}

function memberLogoutSuccess(data){
	var result = data.result;
	if(result == 'logoutSuccess'){
		document.location.href = root+"/";
	}
}

</script>



</head>
<!-- <body oncontextmenu="return false" ondragstart="return false" onselectstart="return false"> <!-- 우클릭&드래그 막기 프로젝트완성후 주석풀기 -->
<body>
	<!-- 모달창 -->
	<div class="modal fade" id="siteAddModal" tabindex="-1" role="dialog" aria-labelledby="siteAddModalLabel" aria-hidden="true">
		<div class="modal-content">
		</div>
	</div>
	
	<div class="mapHeader">
		<a href="/dreamcatcher/"><div class="mapLogo"></div></a>
					
		<div id="dropButton"></div>
		
		<div id="dropMenu" style="display:none;">
			<div class="dropMenuItem" onclick="javascript:history.back();">돌아가기</div>			
			<a href="/dreamcatcher/plan?act=registerMap">
				<div class="dropMenuItem">새로 만들기</div></a>
			<hr class="dropMenuLine">
			<a href="/dreamcatcher/site?act=siteArticleList&page=1">
				<div class="dropMenuItem">메인</div></a>
			<a href="/dreamcatcher/">
				<div class="dropMenuItem">여행지정보</div></a>
			<a href="/dreamcatcher/route?act=routeArticleList&page=1">
				<div class="dropMenuItem">추천경로</div></a>
			<a href="/dreamcatcher/myInfo.tiles">
				<div class="dropMenuItem">마이페이지</div></a>
			<hr class="dropMenuLine">
			<div class="dropMenuItem" onclick="javascript:logout();">로그아웃</div>
		</div>
	</div>
	
 	
	<div id="base">
		
		<!-- 모달띄우는 버튼 -->
		<img src="${root}/assets/img/planner/addsiteimgUnPushed.png" class="addSiteImgUnPushed">
		<a class="modalButton" data-src="/dreamcatcher/site?act=moveSiteMake" data-target="#siteAddModal">
		<img src="${root}/assets/img/planner/addsiteimgPushed.png" class="addSiteImgPushed" style="display:none;">
		<img src="${root}/assets/img/planner/addsiteimgText.png" class="addSiteImgText" style="display:none;">
		</a>
	
		
		<form id="siteSearchForm" method="get" action="" onsubmit="return false;">
			<input type="hidden" id="searchWord" name="searchWord" value="">
			<!-- 검색창 -->
			<div class="searchWindow" id="searchWindow">
				<!-- 검색텍스트 -->
				<div class="searchImg"><img src="${root}/assets/img/planner/search_icon.png"></div>
				<!-- 검색텍스트필드 -->
				<input class="searchTextField" id="keyword" name="keyword" placeholder="찾으시는 지역을 검색해보세요" value="">
			</div>
		</form>
		
		
		<!-- 지도 -->
		<div id="map_canvas"></div>
		
		
		<!-- 루트영역 -->
		<div id="stopArea"></div>
		
	
		<!-- 지도 끝 버튼 -->
 		<form name="plannerForm" id="plannerForm" method="post" action="" onsubmit="return false;">
			<input type="hidden" id="act" name="act" value="" />
			<input type="hidden" id="jsonString" name="jsonString" value="" />
			<div class="mapSaveBtn" onclick="javascript:isPolylistCheck();"><span class="mapSaveText">일정등록</span></div>
		</form>
				
	</div>
</body>
</html>
