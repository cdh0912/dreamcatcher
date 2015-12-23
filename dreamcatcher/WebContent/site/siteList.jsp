<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	$('#keyword').val(searchWord);
	
	$("#keyword").autocomplete({
		matchContains: true,
		source : function(request, response) {
			$.ajax({
				url : root+"/common?act=autoComplete",
				type : "post",
				dataType : "json",
				data: request,
					
				success: function( result ) {
			           	//return 된놈을 response() 함수내에 다음과 같이 정의해서 뽑아온다.
					response( 
					 	$.map( result, function( item ) {
					 			return {
					 			//label : 화면에 보여지는 텍스트
					 			//value : 실제 text태그에 들어갈 값
					 			//본인은 둘다 똑같이 줬음
					 			//화면에 보여지는 text가 즉, value가 되기때문 
					   				label: item.keyword,
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

	
	// 자동완성 검색 목록 색상 Highlight
	$.ui.autocomplete.prototype._renderItem = function (ul, item) {
        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span class='selected-keyword'>$1</span>");
        return $("<li></li>")
                .data("item.autocomplete", item)
                .append('<a style="background-color:white">' + item.label + '</a>')
                .appendTo(ul);
    };
    
    $('#nationSelectbox').change(function() {
    	$('#act').val('locationCategory');
    	$('#searchMode').val(searchMode);	
    	$('#page').val(page);
    	$('#viewMode').val(viewMode);
    	$('#searchWord').val(searchWord);
    	$('#categoryMode').val(categoryMode);

    	$("#nationSelectbox option:first").attr('disabled',false);
    	var nationCode = $(this).val();
		//alert(nationCode);
    	if( $('#boardForm #nation_code').length == '0' ){
    		$('#boardForm').append('<input type="hidden" id="nation_code" name="nation_code" value="">');
    	}
    	$('#boardForm #nation_code').val(nationCode);
    	
		
    	$.ajax({
			url : root+"/common",
			type : "post",
			dataType : "json",
			data: $('#boardForm').serialize(),	
			success: 
			function( data ) {
				if(data.length > 0){
					$('.locationCategory').html('<a href="#" class="filter-all active" onClick="javascript:filterSort(this);">전체보기</a>');
					
					for(var i = 0; i < data.length; i++ ){
						$('.locationCategory').append(' / <a href="#" class="filter-'+data[i].loc_name+'" onClick="javascript:filterSort(this);">'+data[i].loc_name+'</a>');
					}
				}
		    },
			error : function(data) {
				alert("에러가 발생하였습니다.")
			}
		});

	});
    
 	$('#nationSelectbox').on('click',function() {
 		$("#nationSelectbox option:first").attr('disabled',true);
 		
 	});
 	
 	$('#searchCheckBox').change(function() {
 		  if ($(this).is(':checked')) {
 		    $(this).attr('checked', true);
 		  } else {
 		    $(this).attr('checked', false);
 		  }
 	});

});
    


function filterSort(element){
	if(!$(element).hasClass('active')) {
    	$('.portfolio-filters a').removeClass('active');
    	var clicked_filter = $(element).attr('class').replace('filter-', '');
    	$(element).addClass('active');
    	if(clicked_filter != 'all') {
    		$('.portfolio-box:not(.' + clicked_filter + ')').css('display', 'none');
    		$('.portfolio-box:not(.' + clicked_filter + ')').removeClass('portfolio-box');
    		$('.' + clicked_filter).addClass('portfolio-box');
    		$('.' + clicked_filter).css('display', 'block');
    		$('.portfolio-masonry').masonry();
    	}
    	else {
    		$('.portfolio-masonry > div').addClass('portfolio-box');
    		$('.portfolio-masonry > div').css('display', 'block');
    		$('.portfolio-masonry').masonry();
    	}
	}
}

function siteArticleSearch(){
	//alert($('#searchCheckBox').prop('checked'));
	// $('#searchCheckBox').prop('checked') == true : 등록일순 || false : 추천순
	if(!$('#searchCheckBox').prop('checked')){
		$('#searchMode').val('recommend');	
	}else{
		$('#searchMode').val('date');	
	}
	$('#act').val('siteArticleList');
	$('#page').val('1');
	$('#viewMode').val(viewMode);
	$('#searchWord').val($('#keyword').val());
	$('#categoryMode').val(categoryMode);
	document.boardForm.action = root+"/site";
	document.boardForm.submit();
}

function siteMoreArticle(){
	$('#searchMode').val(searchMode);	
	$('#act').val('siteMoreArticle');
	$('#page').val(++page);
	$('#viewMode').val('moreView');
	$('#searchWord').val(searchWord);
	$('#categoryMode').val(categoryMode);
	//alert(page);
	$.ajax({
		url : root+"/site",
		type : "post",
		dataType : "json",
		data: $('#boardForm').serialize(),				
		success: 
		function( data ) {
			//$('#articleListArea').html('');
			var siteArticleList = data.siteArticleList;
			var nationList = data.nationList;
			var locationList = data.locationList;
			for(var i=0; i < siteArticleList.length; i++){
				var sb = new StringBuffer();

				sb.append('<div class="portfolio-box '+siteArticleList[i].loc_name+'">');
            	sb.append('<div class="portfolio-box-container" onClick="javascript:moveSiteView('+siteArticleList[i].site_id+');">');
            	sb.append('<div class="site-image" style="background-image:url(\''+root+'/upload/'+siteArticleList[i].savefolder+'/thumb/'+siteArticleList[i].saved_picture+'\');"></div>');
            	sb.append('<div class="portfolio-box-text">');
            	sb.append('<h3>'+siteArticleList[i].site_name+'</h3>');
            	sb.append('<p><marquee>'+siteArticleList[i].brief_info+'</marquee></p>');
            	sb.append('</div>');
            	sb.append('</div>');
            	sb.append('<div class="best-item-vote">');
            	sb.append('<div class="best-content">');
            	sb.append('<ul class="progess-bars">');
            	sb.append('<li>');
            	sb.append('<div class="progress">');
            	sb.append('<div class="progress-bar comments" role="progressbar" ');
            	sb.append('aria-valuenow="'+siteArticleList[i].rec_percent+'" aria-valuemin="0" aria-valuemax="100" ');
            	sb.append('style="width: '+siteArticleList[i].rec_percent+'%"></div>');
            	sb.append('<span class="comments">'+siteArticleList[i].recommend+'<i class="fa fa-heart"></i></span>');
            	sb.append('</div>');
            	sb.append('</li>');
            	sb.append('</ul>');
            	sb.append('</div>');
            	sb.append('</div>');
            	sb.append('</div>');
				$('#articleListArea').append(sb.toString());
				$('#articleListArea').masonry('reloadItems');
				$('#articleListArea').masonry('layout');
			}
			
			$('#nationSelectbox').html('<option value="default">국가를 선택하세요</option>');
			for(var i=0; i < nationList.length; i++){
				$('#nationSelectbox').append('<option value="'+nationList[i].nation_code+'">'+nationList[i].kor_name+'</option>');
			}
			$('.locationCategory').html('');
			if(data.isLastPage){
				$('#moreButtonArea').html('');				
			}
	    },
		error : function(data) {
			alert("에러가 발생하였습니다.")
		}
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

function moveSiteView(site_id){
	$('#boardForm').append('<input type="hidden" name="site_id" id="site_id" value="'+site_id+'">');
	$('#searchMode').val(searchMode);	
	$('#act').val('siteView');
	$('#page').val(page);
	$('#viewMode').val(viewMode);
	$('#searchWord').val(searchWord);
	$('#categoryMode').val(categoryMode);
	document.boardForm.action = root+"/site";
	document.boardForm.submit();
	//document.location.href=root+'/site?act=siteView&site_id='+site_id;
	
}
</script>

	
        <!-- Page Title -->	
        <div class="page-title-container">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 wow fadeIn">
                        <i class="fa fa-camera"></i>
                        <h1>여행지 정보 /</h1>
                        <p>마음껏 둘러보고 의견을 나누세요!</p>
                    </div>
                </div>
            </div>
        </div>
	
        <!-- Portfolio -->
        <div class="portfolio-container">
	        <div class="container">
	        	<div class="row">
			        <div class="col-md-12">
			            <form id="siteSearchForm" method="get" action="" onsubmit="return false;">
			                <div class="input-group">
			                    <span class="input-group-btn">
			                    <c:if test="${'date' eq param.searchMode || '' eq param.searchMode}">
									<c:set var="checked" value="checked"/>			                    
			                    </c:if>
			                    		<input ${checked } type="checkbox" id="searchCheckBox" name="searchCheckBox" data-toggle="toggle" data-on="등록일순" data-off="추천순" data-onstyle="toggle1" data-offstyle="toggle2">
			                    </span>
			                    
			                    <input class="form-control" id="keyword" name="keyword" placeholder="여행지를 검색해보세요" value="">
			                    

			                    <span class="input-group-btn" style="">
			                        <button type="submit" class="btn btn-default" onClick="javascript:siteArticleSearch()"><i class="glyphicon glyphicon-search"></i></button>
			                    </span>
                			</div>
            			</form>
        			</div>
        		</div>

		           

	            <div class="row">
	             	<div id="areaCategory" class="col-lg-6 portfolio-filters wow form-group"> 
	                  <label class="control-label templatemo-block">여행지 정보를 편하게 정렬하세요!</label>                 
	                  <select id="nationSelectbox" class="form-control">

	                  	<option value="default">국가를 선택하세요</option>
	                  	<c:forEach var="nation" items="${nationList}">
	                    <option value="${nation.nation_code}">${nation.kor_name }</option>
	                    </c:forEach>                   
	                  </select>
	     
	                </div>
	            	<div class="col-sm-12 portfolio-filters wow fadeInLeft locationCategory">

	            	</div>
	            	
	            </div>
	            
	            <div class="row">
	            <c:if test="${siteArticleList.size() > 0}">
	            	<div id="articleListArea" class="col-sm-12 portfolio-masonry">
	            	
	            		<c:forEach var="siteArticle" varStatus="status" items="${siteArticleList}">
		                <div class="portfolio-box ${siteArticle.loc_name}">
		                	<div class="portfolio-box-container" onClick="javascript:moveSiteView('${siteArticle.site_id}');">
<%-- 			                	<img src="${root}/assets/img/portfolio/work1.jpg" alt="" data-at2x="${root}/assets/img/portfolio/work1.jpg">
 --%>
 									<div class="site-image" style="background-image:url('${root }/upload/${siteArticle.savefolder}/thumb/${siteArticle.saved_picture}');"></div>

 				                	<div class="portfolio-box-text">
			                		<h3>${siteArticle.site_name}</h3>
			                		<p><marquee>${siteArticle.brief_info }</marquee></p>
			                	</div>
			                </div>
			                <div class="best-item-vote">
								<div class="best-content">
									<ul class="progess-bars">
										<li>
											<div class="progress">
												<div class="progress-bar comments" role="progressbar"
													aria-valuenow="${siteArticle.rec_percent}" aria-valuemin="0" aria-valuemax="100"
													style="width: ${siteArticle.rec_percent}%"></div>
												<span class="comments">${siteArticle.recommend}<i class="fa fa-heart"></i></span>
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
	                	</c:if>
					<c:if test="${siteArticleList.size() == 0}">
					
					<div class="col-sm-12" style="margin-top:10%; margin-bottom:20%">
						<h4>검색결과가 없습니다.</h4>
					</div>
					</c:if>	
	            </div>
	     		<div id="moreButtonArea">
	            <c:if test="${!isLastPage}">
	            <div class="row">
	            	<div class="col-sm-12 portfolio-masonry">
						<button type="button" class="btn" onclick="javascript:siteMoreArticle()">더 보기</button>
	                </div>
	            </div>
	            </c:if>
	            </div>
	        </div>
        </div>

