<%@page import="java.util.List"%>
<%@page import="com.dreamcatcher.plan.model.dao.PlanDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.dreamcatcher.plan.model.PlanDto, com.dreamcatcher.util.StringCheck, java.util.HashMap"%>
<%@include file="/common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%
List<PlanDto> pList=(List<PlanDto>)request.getAttribute("pList");
if(pList.size() == 0) {
%>
<script type="text/javascript">
alert('잘못된 접근입니다.');
document.location.href="/dreamcatcher/";
document.body.innerHTML = '';
</script>
<%		
} else {
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="${root}/assets/css/planner/schedulestyle.css" media="all" rel="stylesheet" type="text/css">

<link href='${root}/assets/css/fullcalendar.min.css' rel='stylesheet' />	
<link href='${root}/assets/css/jquery.qtip.min.css' rel='stylesheet' />	

<script type="text/javascript">
var root = "/dreamcatcher";
var route_id = ${pList.get(0).getRoute_id()};

function modifyRoute(){
	if(confirm("경로를 수정 하시겠습니까?") == true) {
		$('#scheduleForm #act').val('modifyMapStart');
		$('#scheduleForm #page').val(page);
		$('#scheduleForm #viewMode').val(viewMode);
		$('#scheduleForm #searchWord').val(searchWord);
		$('#scheduleForm #searchMode').val(searchMode);	
		$('#scheduleForm #categoryMode').val(categoryMode);
		document.scheduleForm.action = root+"/plan";
		document.scheduleForm.submit();
	} else {
		return;
	}
}
function modifyPlan(){
	if(confirm("일정을 수정 하시겠습니까?") == true) {
		$('#scheduleForm #act').val('viewModify');
		$('#scheduleForm #page').val(page);
		$('#scheduleForm #viewMode').val(viewMode);
		$('#scheduleForm #searchWord').val(searchWord);
		$('#scheduleForm #searchMode').val(searchMode);	
		$('#scheduleForm #categoryMode').val(categoryMode);	
		document.scheduleForm.action = root+"/plan";
		document.scheduleForm.submit();
	} else {
		return;
	}
}

function deleteRoutePlan() {
	if(confirm("경로와 일정을 모두 삭제하시겠습니까?") == true) {
		$('#scheduleForm #act').val('deletePlan');
		$('#scheduleForm #page').val(page);
		$('#scheduleForm #viewMode').val(viewMode);
		$('#scheduleForm #searchWord').val(searchWord);
		$('#scheduleForm #searchMode').val(searchMode);	
		$('#scheduleForm #categoryMode').val(categoryMode);	
		document.scheduleForm.action = root+"/plan";
		document.scheduleForm.submit();
	} else {
		return;
	}
}

function moveRouteList(){
	$('#boardForm').append('<input type="hidden" name="route_id" id="route_id" value="'+route_id+'">');
	$('#boardForm #act').val('routeArticleList');
	$('#boardForm #page').val(page);
	$('#boardForm #viewMode').val(viewMode);
	$('#boardForm #searchWord').val(searchWord);
	$('#boardForm #searchMode').val(searchMode);	
	$('#boardForm #categoryMode').val(categoryMode);
	document.boardForm.action = root+"/route";
	document.boardForm.submit();
	//document.location.href=root+'/site?act=siteView&site_id='+site_id;
}

function like() {
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/plan",
		data : {'act': 'likePlan',
				'route_id': route_id},
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
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/plan",
		data : {'act': 'disLikePlan',
				'route_id': route_id},
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




$(function() {
	
	var eventsArray = new Array(); 

	var tooltip = $('<div/>').qtip({
		id: 'calendar',
		prerender: true,
		content: {
			text: ' ',
		},
		position: {
			my: 'bottom center',
			at: 'top center',
			target: 'mouse',
			viewport: $('#fullcalendar'),
			adjust: {
				mouse: false,
				scroll: false
			}
		},
		show: false,
		hide: false,
		style: 'qtip-light',
	}).qtip('api');
	
 	
	//달력에 입력될 데이터를 event라는 Array에 주입
	<c:forEach var="pl" items="${pList}">
		var site_name = "${pl.getSite_name()}";
		var stay_date = "${pl.getStay_date()}";
		var stay_date = "${pl.getStay_date()}";
		eventObj = new Object(); 
		eventObj.title = site_name; 
		eventObj.start = stay_date;
		eventObj.color = "#9B4068";
		eventObj.allDay = false;
	 	eventsArray.push(eventObj); 
 	</c:forEach>

 	
 	//달력옵션
	$('#calendar').fullCalendar({
		header : {  left : 'prev,next today',
					center : 'title',
					right : 'month,agendaWeek'
		},
		defaultDate: "${pList.get(0).stay_date}",
		editable: false,
		eventLimit: true, // allow "more" link when too many events
		events: eventsArray,
		eventClick: function(data, event, view) {
			var content = "<font style='font-size:small; font-weight:bold;'>"+data.title+'</font><br>'+data.start.format('YYYY-MM-DD');
			tooltip.set({
				'content.text': content
			})
			.reposition(event).show(event);
		},
        dayClick: function() { tooltip.hide() }
	});
	
	//달력 높이와 테이블 높이 맞추기.
	$("#tableContainer").height(($("#calendarContainer").height())*1.06); 

	
	
	
	
	
	
	//지도 좌표받아오기
	$.ajax({  
		url: root+'/plan',  
		data : {'act': 'getPlanLatlng',
			'route_id': route_id},
		type:'post',
		dataType:'json',
		success: siteListResult,
		error : function(e){}
	}); 
	
});


function siteListResult(data){
	routeDetailList = data;
	LoadMap();
}



function LoadMap() {
	var mapOptions = {
		streetViewControl : false,
		mapTypeControl : false,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

	
	latlngs = new Array();
	var flightPlanCoordinates = [];
	bound = new google.maps.LatLngBounds();
	for (var i = 0; i < routeDetailList.length; i++) {	
		latlngs[i] = new google.maps.LatLng (routeDetailList[i].latitude,routeDetailList[i].longitude);
		bound.extend(latlngs[i]);
		flightPlanCoordinates.push(latlngs[i]);
	}
	map.fitBounds(bound);
	
	
	var flightPath = new google.maps.Polyline({
		path: flightPlanCoordinates,
		strokeColor: '#c15587',
		strokeOpacity: 1.0,
		strokeWeight: 4
	});	
	flightPath.setMap(map);
	
	
	
	var infoWindow = new google.maps.InfoWindow();
	for (var i = 0; i < routeDetailList.length; i++) {
		var myLatlng = new google.maps.LatLng(routeDetailList[i].latitude, routeDetailList[i].longitude);
			var yellowMarker = new google.maps.MarkerImage(root+"/assets/img/planner/dot_yellow.png",
			        new google.maps.Size(20, 20),
			        new google.maps.Point(0, 0),
			        new google.maps.Point(7, 7));
			var marker = new google.maps.Marker({
				position : myLatlng,
				icon : yellowMarker,
				map : map,
				title : routeDetailList[i].site_name
			});

		var site = routeDetailList[i];	
		(function(marker, site) {
			google.maps.event.addListener(map, "click", function(){
				infoWindow.close();
				});
			google.maps.event.addListener(marker, "click", function(e) {
					infoWindow.setContent(
						"<div class='infowindow'>													" +
						"	<div class='info_sitemenu'>												" +
						"		<div><font size='4px'>" + site.site_name + "</font></div>	" +	
						"	</div>																	" +
						"</div>																		"
					);
					infoWindow.open(map, marker);
			});
		})(marker, site);
		
	}	
}


</script>

</head>
<body>

      <!-- Page Title -->
        <div class="page-title-container">
            <div class="container">
                <div class="row">
                    <div class="col-sm-7 wow fadeIn">
                        <i class="fa fa-comments"></i>
                        <h1>${pList[0].getTitle()}</h1><br>
                        <div>${pList[0].getId()}(${pList[0].getName()})</div>
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
						<img src="${root}/assets/img/planner/heart1.png">${pList[0].getRecommend()}
					</div>
                </div>
            </div>
        </div>



		
	<form id="scheduleForm" name="scheduleForm" class="container" method="post" action="">
	<div class="foundation" >
		<input type="hidden" id="index" name="index" value=""> 
		<input type="hidden" id="act" name="act" value=""> 
		<input type="hidden" id="route_id" name="route_id" value="${pList[0].getRoute_id()}"> 
		<input type="hidden" id="page" name="page" value="">
		<input type="hidden" id="viewMode" name="viewMode" value="">
		<input type="hidden" id="searchMode" name="searchMode" value="">
		<input type="hidden" id="searchWord" name="searchWord" value="">
		<input type="hidden" id="categoryMode" name="categoryMode" value="">
				
		<div id="top" class="map_container col-sm-12">
			<div id="map_canvas"></div>
		</div>
		<div id="center" class="row">
			<div class="col-sm-6 leftCol">
				<div class='fatherBorder' id="calendarContainer">
					<div id='calendar'></div>
				</div>
			</div>
			<div class="col-sm-6 rightCol">
				<div class="fatherBorder " id="tableContainer">
					<table id="scheduleTable" class="table table-hover">
						<tr class="tableTitle">
							<td width="80px">날짜</td>
							<td width="100px">장소</td>
							<td>일정</td>
							<td width="90px">비용</td>
						</tr>
						
						<c:forEach var="pl" items="${pList}" varStatus="status">
						<tr>
							<td>${pl.stay_date}</td>
							<td>${pl.site_name}</td>
							<td>${pl.content}</td>
							<td>${pl.budget}${pl.currency}</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>	
		</div>
			<div id="bottom">
				<c:if test="${memberInfo.id == pList[0].getId() || memberInfo.m_level == 1}" >
					<button id="btnModifyRoute" class="btn btn-default btn-lg logout-remove-element" onclick="javascript:modifyRoute();">경로수정</button>
					<button id="btnModifyPlan" class="btn btn-default btn-lg logout-remove-element" onclick="javascript:modifyPlan();">일정수정</button>
					<button id="btnDelete" class="btn btn-default btn-lg logout-remove-element" onclick="javascript:deleteRoutePlan();">삭제</button>
				</c:if>
					<button type="button" class="btn btn-default" onclick="javascript:moveRouteList();">목록으로</button>		
			</div>
		</div>		
	</form>
		
	 	<div id="comments">
			<%@include file="/plan/planReplyView.jsp"%>
		</div> 
		

</body>
</html>

<%}%>