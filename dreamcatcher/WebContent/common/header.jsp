<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/board.jsp"%>

<script type="text/javascript">


$(function() {

	/*
		$('a.modalButton').on('click', function(e) {
			var src = $(this).attr('data-src');

			//var height = $(this).attr('data-height');
			//var width = $(this).attr('data-width');
			
			//$("#memberModal .modal-dialog").css('height', height);
			//$("#memberModal .modal-dialog").css('width', width);
			//$("#memberModal .modal-content").css('height', height);
			//$("#memberModal .modal-content").css('width', width);
			//$("#memberModal .modal-body").css('height', '85%');
			
			 $(".modal-content").html('<iframe width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency="true" src="'+src+'"></iframe>');

			 
			$("#memberModal iframe").attr({'src':src,
                'height': 600,
                'width': '100%',
            	'overflow' : hidden    
			});

		});
*/
//$('a.modalButton').on('click', function(e) {

    //var url = $(this).attr('data-src');
    //$(".modal-body").html('<iframe style="overflow:auto" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency="true" src="'+url+'"></iframe>');

  //  $('#memberModal').modal('show');



//});


$('a.modalButton').on('click', function (e) {
	e.preventDefault();
	var url = $(this).data('src');
	var target = $(this).data('target');
	$(target).removeData('bs.modal');
	$(target).modal({remote: url });
	$(target).modal('show');
});


/* $('body').on('hidden.bs.modal', '#memberModal', function () {
	$(this).removeData('bs.modal');
	$(this).find('.modal-body').html('');
	window.location.reload();
}); */


	//$('#memberModal').on('show.bs.modal', function(e) {
		 
	//    var link = $(e.relatedTarget);
	 //  $(this).find(".modal-content").load(link.attr("data-src"));
							
						//	$(this).find('.modal-body').css({
							//	'height':720,
							//	'border-radius' : '0',
							//	'padding' : '0',
							//	'overflow' : 'auto'
							//});
			
	//})
	
/* 	$('#logoutButton').on('click', function (e) {
		alert('로그아웃');
		jQuery.ajax({
			url: root+'/member',
			type : 'post',
			data : {'act':'logout'},
			dataType : 'text',
			success : function(data){},
			error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
		});
		
	}); */
});

function modalPopup(element){

    var url = $(element).data('src');
    var target = $(element).data('target');
    $(window.parent.document).find('.modal-backdrop').remove();
    $(window.parent.document).find(target).removeData('bs.modal');
    $(window.parent.document).find(target).modal({remote: url });		
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


var StringBuffer = function() { 
    this.buffer = new Array(); 
} 
StringBuffer.prototype.append = function(obj) {
     this.buffer.push(obj); 
} 
StringBuffer.prototype.toString = function(){ 
     return this.buffer.join(""); 
}

function memberLogoutSuccess(data){
	var result = data.result;
	//alert(result);
	if(result == 'logoutSuccess'){
		var newMenu = new StringBuffer;
		newMenu.append('<div style="text-align: right;">');
		newMenu.append('<h5>|&nbsp;&nbsp;');
		newMenu.append('<a class="modalButton" data-src="'+root+'/member?act=moveLogin" data-target="#memberModal" onClick="javascript:modalPopup(this)">');
		newMenu.append('로그인</a>&nbsp;&nbsp;|');
		newMenu.append('<a class="modalButton" data-src="'+root+'/member?act=moveRegister" data-target="#memberModal" onClick="javascript:modalPopup(this)">회원가입</a>');
		newMenu.append('&nbsp;&nbsp;|</h5></div>');
		
		$('#memberMenu').html(newMenu.toString());
		if(typeof returnMainPage != "undefined"){
			returnMainPage();
		}
		
		$('.logout-remove-element').remove();
	}
}

function moveFirstSiteArticleList(){
	$('#act').val('siteArticleList');
	$('#page').val('1');
	$('#viewMode').val('');
	$('#searchMode').val('date');
	$('#searchWord').val('');
	$('#categoryMode').val('');
	document.boardForm.action = root+"/site";
	document.boardForm.submit();
}

function moveSiteArticleList(page){
	$('#act').val('siteArticleList');
	$('#page').val(page);
	$('#viewMode').val(viewMode);
	$('#searchMode').val(searchMode);
	$('#searchWord').val(searchWord);
	$('#categoryMode').val(categoryMode);
	document.boardForm.action = root+"/site";
	document.boardForm.submit();
}

function moveFirstRouteArticleList(){
	$('#act').val('routeArticleList');
	$('#page').val('1');
	$('#viewMode').val('');
	$('#searchMode').val('date');
	$('#searchWord').val('');
	$('#categoryMode').val('');
	document.boardForm.action = root+"/route";
	document.boardForm.submit();
}

function moveRouteArticleList(page){
	$('#act').val('routeArticleList');
	$('#page').val(page);
	$('#viewMode').val(viewMode);
	$('#searchMode').val(searchMode);
	$('#searchWord').val(searchWord);
	$('#categoryMode').val(categoryMode);
	document.boardForm.action = root+"/route";
	document.boardForm.submit();
}

</script>    

    
	<div class="modal fade" id="memberModal" tabindex="-1" role="dialog"
		aria-labelledby="memberModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
    				<iframe frameborder="0"></iframe>
    				</div>
			</div>
		</div>
	</div>

	<!-- 상단 메인 메뉴 -->
	<nav class="navbar" role="navigation">
	<div class="container">
		<div class="navbar-header">

			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#top-navbar-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${root}">Dream Catcher - a best travel planner</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="top-navbar-1">
			<ul class="nav navbar-nav navbar-right">
			<!-- 드랍다운 메뉴 
				<li class="dropdown active"><a href="#" class="dropdo	wn-toggle"
					data-toggle="dropdown" data-hover="dropdown" data-delay="1000">
						<i class="fa fa-home"></i><br>Home <span class="caret"></span>
				</a>
					<ul class="dropdown-menu dropdown-menu-left" role="menu">
						<li class="active"><a href="index.html">Home</a></li>
						<li><a href="index-2.html">Home 2</a></li>
					</ul></li>
					
			 -->
			 	<li><a href="${root}"><i class="fa fa-home"></i><br>홈</a>
				</li>
				<li>
				<c:if test="${memberInfo != null}">
					<a href="${root}/plan?act=registerMap">
				</c:if>
				<c:if test="${memberInfo == null}">
					<a data-src="${root }/member?act=moveLogin" data-target="#memberModal" onClick="javascript:alert('로그인 후 이용 가능한 서비스입니다.'); modalPopup(this);">						
				</c:if>				
				<i class="fa fa-tasks"></i><br>플래너
				</a>
				</li>
				<li><a href="javascript:moveFirstSiteArticleList();"><i class="fa fa-camera"></i><br>여행지</a>
				</li>
				<li><a href="javascript:moveFirstRouteArticleList();"><i class="fa fa-comments"></i><br>추천경로</a>
				</li>
<!-- 
				<li><a href="about.html"><i class="fa fa-user"></i><br>드림캐처</a>
				</li> -->
				<li><a href="${root}/member?act=moveContact"><i class="fa fa-envelope"></i><br>문의하기</a>
				</li>
				<c:if test="${memberInfo.m_level == 1}">
					<li class="logout-remove-element"><a href="${root}/admin?act=moveStatistics"><i class="fa fa-pie-chart"></i><br>통계차트</a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
	<div id="memberMenu" class="container">
	<c:if test="${memberInfo == null }">
		<div style="text-align: right;">
			<h5>
				|&nbsp;&nbsp;
				<a class="modalButton"
					data-src="${root }/member?act=moveLogin" data-target="#memberModal">로그인</a>&nbsp;&nbsp;|
				<a class="modalButton"
					data-src="${root }/member?act=moveRegister" data-target="#memberModal">회원가입</a>&nbsp;&nbsp;|	
			</h5>
		</div>
	</c:if>
	<c:if test="${memberInfo != null }">
		<div style="text-align: right;">
			<h5>|&nbsp;&nbsp;
				${memberInfo.name }&nbsp;님
				<c:if test='${loginMode != null}'>
					&nbsp;(&nbsp;${loginMode }&nbsp; 계정으로 로그인 중)
				</c:if>				
				&nbsp;&nbsp;|&nbsp;&nbsp;
				<a id="logoutButton" onClick="javascript:logout()">로그아웃</a>&nbsp;&nbsp;|
				<c:if test='${loginMode == null }'>
				<a class="modalButton"
					data-src="${root }/member?act=moveModify" data-target="#memberModal">정보수정</a>&nbsp;&nbsp;|
				</c:if>	
					&nbsp;&nbsp;<a href="${root }/member?act=moveMyInfo">마이페이지</a>&nbsp;&nbsp;|	
			</h5>
		</div>
	
	</c:if>
	</div>
	</nav>
