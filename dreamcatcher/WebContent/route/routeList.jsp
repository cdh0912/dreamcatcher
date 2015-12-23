<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	$('#keyword').val(searchWord);
	
	$("#keyword").autocomplete({
		matchContains: true,
		source : function(request, response) {
			$.ajax({
				url : root+"/route?act=autoComplete",
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
	
	$.ui.autocomplete.prototype._renderItem = function (ul, item) {
        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span class='selected-keyword'>$1</span>");
        return $("<li></li>")
                .data("item.autocomplete", item)
                .append('<a style="background-color:white">' + item.label + '</a>')
                .appendTo(ul);
    };
    
 	$('#searchCheckBox').change(function() {
		  if ($(this).is(':checked')) {
		    $(this).attr('checked', true);
		  } else {
		    $(this).attr('checked', false);
		  }
	});
});

function routeArticleSearch(){
	//alert($('#searchCheckBox').prop('checked'));
	// $('#searchCheckBox').prop('checked') == true : 등록일순 || false : 추천순
	if(!$('#searchCheckBox').prop('checked')){
		$('#searchMode').val('recommend');	
	}else{
		$('#searchMode').val('date');	
	}
	$('#act').val('routeArticleList');
	$('#page').val('1');
	$('#viewMode').val(viewMode);
	$('#searchWord').val($('#keyword').val());
	$('#categoryMode').val(categoryMode);
	document.boardForm.action = root+"/route";
	document.boardForm.submit();
}

function moveRouteView(route_id){
	$('#boardForm').append('<input type="hidden" name="route_id" id="route_id" value="'+route_id+'">');
	$('#searchMode').val(searchMode);	
	$('#act').val('viewPlan');
	$('#page').val(page);
	$('#viewMode').val(viewMode);
	$('#searchWord').val(searchWord);
	$('#categoryMode').val(categoryMode);
	document.boardForm.action = root+"/plan";
	document.boardForm.submit();
	//alert(route_id);
	//document.location.href=root+'/plan?act=viewPlan&route_id='+route_id;
	
}

</script>
      <!-- Page Title -->
        <div class="page-title-container">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 wow fadeIn">
                        <i class="fa fa-comments"></i>
                        <h1>추천경로 /</h1>
                        <p>먼저 다녀온 사람들의 소중한 정보를 공유하세요 !</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Portfolio -->
        <div class="portfolio-container">
	        <div class="container">
	        	<div class="row">
			        <div class="col-md-12">
			            <form action="routeSearchForm" method="get" onsubmit="return false;">
			                <div class="input-group">
			               		<span class="input-group-btn">
			                    <c:if test="${'date' eq param.searchMode || '' eq param.searchMode}">
									<c:set var="checked" value="checked"/>			                    
			                    </c:if>
			                    		<input ${checked } type="checkbox" id="searchCheckBox" name="searchCheckBox" data-toggle="toggle" data-on="등록일순" data-off="추천순" data-onstyle="toggle1" data-offstyle="toggle2">
			                    </span>
			                    <input class="form-control" id="keyword" name="keyword" placeholder="경유 지역을 검색해보세요">
			                    

			                    <span class="input-group-btn"style="">
			                        <button type="submit" class="btn btn-default" onClick="javascript:routeArticleSearch()"><i class="glyphicon glyphicon-search"></i></button>
			                    </span>
                			</div>
            			</form>
        			</div>
        		</div>

	            <div class="row">
			<c:if test="${routeArticleList.size() > 0}">
	            	<div class="col-sm-12 portfolio-masonry">
				<c:forEach var="routeArticle" items="${routeArticleList}">
					<div class="portfolio-box">
					
						<div class="best-item-vote">
							<div class="work">
								<img src="${routeArticle.route_url}" alt="" onClick="javascript:moveRouteView('${routeArticle.route_id}');">
								<a href="${routeArticle.route_url}">
								<i class="portfolio-box-icon fa fa-search"></i></a>
							</div>
							
							<div class="best-content portfolio-box-text">

								<h5>
									<a onClick="javascript:moveRouteView('${routeArticle.route_id}');">${routeArticle.title }</a>
								</h5>
								<span class="tagline">By: ${routeArticle.name }</span>
								<ul class="progess-bars">
									<li>
										<div class="progress">
											<div class="progress-bar" role="progressbar"
												aria-valuenow="${routeArticle.rep_percent }" aria-valuemin="0" aria-valuemax="100"
												style="width: ${routeArticle.rep_percent }%;"></div>
											<span>${routeArticle.reply_count }<i class="fa fa-heart"></i></span>
										</div>
									</li>
									<li>
										<div class="progress">
											<div class="progress-bar comments" role="progressbar"
												aria-valuenow="${routeArticle.rec_percent }" aria-valuemin="0" aria-valuemax="100"
												style="width: ${routeArticle.rec_percent }%;"></div>
											<span class="comments">${routeArticle.recommend }<i class="fa fa-heart"></i></span>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</c:forEach>


	                </div>
	            </div>
	            <div class="row">
				${navigator.navigator }
				</div>
				</c:if>
				<c:if test="${routeArticleList.size() == 0}">
					
					<div class="col-sm-12" style="margin-top:15%; margin-bottom:20%">
						<h4>검색결과가 없습니다.</h4>
					</div>
				</c:if>	
	        </div>
        </div>
