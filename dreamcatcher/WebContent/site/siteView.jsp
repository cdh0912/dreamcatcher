
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<link href="${root}/assets/css/planner/schedulestyle.css" media="all" rel="stylesheet" type="text/css">
<script type="text/javascript">
function viewOk(){
		
	document.siteViewForm.action=root+"/route?act=routeList";
	document.siteViewForm.submit();
}

function moveSiteModify(){
	$('#siteViewForm #act').val('moveSiteModify');
	$('#siteViewForm #page').val(page);
	$('#siteViewForm #viewMode').val(viewMode);
	$('#siteViewForm #searchWord').val(searchWord);
	$('#siteViewForm #searchMode').val(searchMode);	
	$('#siteViewForm #categoryMode').val(categoryMode);
	
	document.siteViewForm.action = root+"/site";
	document.siteViewForm.submit();
	
	
}

function moveSiteList(){
	$('#boardForm').append('<input type="hidden" name="site_id" id="site_id" value="'+site_id+'">');
	$('#boardForm #searchMode').val(searchMode);	
	$('#boardForm #act').val('siteArticleList');
	$('#boardForm #page').val(page);
	$('#boardForm #viewMode').val(viewMode);
	$('#boardForm #searchWord').val(searchWord);
	$('#boardForm #categoryMode').val(categoryMode);
	document.boardForm.action = root+"/site";
	document.boardForm.submit();
	//document.location.href=root+'/site?act=siteView&site_id='+site_id;
}

function like() {
	var site_id = ${siteInfo.site_id };

	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/site",
		data : {'act': 'likeSite',
				'site_id': site_id},
		success : function(data) {
			var output='';
			output+= data.likeCnt;
			$('#likecnt').empty();
			$('#likecnt').append("<img class='logout-remove-element' src='${root}/assets/img/planner/heart1.png'>");
			$('#likecnt').append(output);
		}
	});
	$('#btnLike').hide();
	$('#btnDislike').show();
}
function dislike() {
	var site_id = ${siteInfo.site_id };
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/site",
		data : {'act': 'disLikeSite',
				'site_id': site_id},
		success : function(data) {
			var output='';
			output+= data.likeCnt;
			$('#likecnt').empty();
			$('#likecnt').append("<img class='logout-remove-element' src='${root}/assets/img/planner/heart1.png'>");
			$('#likecnt').append(output);
		}
	});
	$('#btnLike').show();
	$('#btnDislike').hide();
}

</script>

        <div class="page-title-container ">
            <div class="container">
                <div class="row">
                    <div class="col-sm-7 wow fadeIn">
                        <i class="fa fa-camera"></i>
                        <h1>여행지 상세 정보 /</h1>
                        <p>이곳으로 여행을 떠나는건 어떠세요?</p>
                    </div>
                    
                    <div id="likebtn" class="col-sm-2" style="margin-top:1%; float:right;">
					<c:if test="${likeCheck == 0 && memberInfo != null}">
						<img class="logout-remove-element" src="${root}/assets/img/planner/like.png" onclick="javascript:like();" id="btnLike">
						<img class="logout-remove-element" src="${root}/assets/img/planner/cancel.png" onclick="javascript:dislike();" id="btnDislike" style="display:none;">
					</c:if><c:if test="${likeCheck != 0 && memberInfo != null}">	
						<img class="logout-remove-element" src="${root}/assets/img/planner/like.png" onclick="javascript:like();" id="btnLike" style="display:none;">
						<img class="logout-remove-element" src="${root}/assets/img/planner/cancel.png" onclick="javascript:dislike();" id="btnDislike">
					</c:if>	
					</div>
                   
					<div id="likecnt" class="col-sm-3" style="margin-top:1%; text-align:right;">
						<img src="${root}/assets/img/planner/heart1.png">${siteInfo.recommend}
					</div>
                </div>
            </div>
        </div>

<div class="portfolio-container">
	<form name="siteViewForm" id="siteViewForm" method="post" action="">
		<input type="hidden" name="act" id="act" value=""> 
		<input type="hidden" name="site_id" id="site_id" value="${siteInfo.site_id }">
		<input type="hidden" id="page" name="page" value="">
		<input type="hidden" id="viewMode" name="viewMode" value="">
		<input type="hidden" id="searchMode" name="searchMode" value="">
		<input type="hidden" id="searchWord" name="searchWord" value="">
		<input type="hidden" id="categoryMode" name="categoryMode" value="">
		<div class="container">
			<div class="row article-container">
				<h3><span class="article-title">${siteInfo.site_name } </span></h3>
				<h4><span class="article-info">${siteInfo.brief_info }</span></h4>
				
			<div class="col-sm-12"><hr></div>
			<div class="col-sm-12 roundBorder imageArea">
				<div class="image-container" style="background: url('${root }/upload/${siteInfo.savefolder }/${siteInfo.saved_picture }');">
		<!-- 			<div class="image-caption"><div>안녕하세요</div></div> -->
				</div>
			
			</div>
			<div class="col-sm-12 article-logtime"><h4>${siteInfo.logtime } 포스팅</h4></div>

			<div class="col-sm-12 work-title wow fadeIn animated">
				<h2>떠나보세요 !!</h2>
			</div>
			<div class="col-sm-12 squareBorder contentArea">

			${siteInfo.detail_info }


			</div>
			<div class="col-sm-12"><hr></div>
			<div class="col-sm-12 buttonArea">
			<c:if test="${memberInfo.id == siteInfo.id || memberInfo.m_level == 1}">
			<button type="button" class="btn btn-default logout-remove-element" onclick="javascript:moveSiteModify();">수정하기</button>
			</c:if>
			<button type="button" class="btn btn-default" onclick="javascript:moveSiteList();">목록으로</button>		
			</div>
			</div>
		</div>
	</form>
</div>

		<div id="comments">
			<%@include file="/site/siteReplyView.jsp"%>
		</div>
