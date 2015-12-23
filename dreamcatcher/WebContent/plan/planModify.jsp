<%@page import="java.util.List"%>
<%@page import="com.dreamcatcher.plan.model.dao.PlanDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.dreamcatcher.plan.model.PlanDto"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/loginCheck.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%
String root = request.getContextPath();
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
</head>
<script src="${root}/assets/js/jquery-1.9.1.js"></script>
<script src="${root}/assets/js/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel='stylesheet' href='//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css'>
<link href="${root}/assets/css/planner/schedulestyle.css" media="all" rel="stylesheet" type="text/css">

<script type="text/javascript">

var root = "/dreamcatcher";

//오늘 날짜 구하기//
var time = new Date();
year = time.getFullYear();
month = time.getMonth()+1;
if(month<10) month="0"+month;
date = time.getDate();
if(date<10) date="0"+date;
var today = year + "-" + month + "-" + date;
//////////////

var sRowID = 0;
var cRowID = 0;
function addSchedule() {
	scheduleTable = document.getElementById("scheduleTable");

	row = scheduleTable.insertRow(scheduleTable.rows.length);
	sRowID++;
	row.setAttribute('id', 'tr' + sRowID);

	cell1 = row.insertCell(0);
	cell2 = row.insertCell(1);
	cell3 = row.insertCell(2);
	cell4 = row.insertCell(3);
	cell5 = row.insertCell(4);
	cell6 = row.insertCell(5);
	cell1.innerHTML = "<input type='text' name='date' class='datePicker form-control' placeholder='"+ today +"'>";
	cell2.innerHTML = "<input type='text' name='location' class='form-control'>";
	cell3.innerHTML = "<input type='text' name='schedule' class='form-control'>";
	cell4.innerHTML = "<input type='text' name='cost' class='form-control'>";
	cell5.innerHTML = "<input type='text' name='won' class='form-control'>";
	cell6.innerHTML = "<input type='button' value='삭제' class='btn btn-default btn-sm' onclick='javascript:deleteSchedule("
			+ row.getAttribute('id') + ")'>";

	//날짜 입력 플러그인
	datePicker();
}

function datePicker(){
	//날짜 입력 플러그인
	$(".datePicker").datepicker({ 
		              inline: true, 
		              dateFormat: "yy-mm-dd",    /* 날짜 포맷 */ 
		              prevText: 'prev', 
		              nextText: 'next', 
		              changeMonth: true,        /* 월 선택박스 사용 */ 
		              changeYear: true,        /* 년 선택박스 사용 */ 
		              showOtherMonths: true,    /* 이전/다음 달 일수 보이기 */ 
		              selectOtherMonths: true,    /* 이전/다음 달 일 선택하기 */ 
		              minDate: '-30y', 
		              currentText: '오늘', 
		              showMonthAfterYear: true,        /* 년과 달의 위치 바꾸기 */ 
		              /* 한글화 */ 
		              monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], 
		              monthNamesShort : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], 
		              dayNames : ['일', '월', '화', '수', '목', '금', '토'],
		              dayNamesShort : ['일', '월', '화', '수', '목', '금', '토'],
		              dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'],
		              showAnim: 'slideDown', 
		              /* 날짜 유효성 체크 */ 
		              onClose: function( selectedDate ) { 
		                  $('#fromDate').datepicker("option","minDate", selectedDate); 
		              }
	});
}
function deleteSchedule(dRow) {
	dRow && dRow.parentNode.removeChild(dRow);
	removeElement(document.getElementById(dRow));
}

function deleteTableSchedule(trID) {
	row=document.getElementById(trID);
	row.parentNode.removeChild(row);
}

function save() {
	$('#scheduleForm #act').val('modifyPlan');
	$('#scheduleForm #page').val(page);
	$('#scheduleForm #viewMode').val(viewMode);
	$('#scheduleForm #searchWord').val(searchWord);
	$('#scheduleForm #searchMode').val(searchMode);	
	$('#scheduleForm #categoryMode').val(categoryMode);			
	document.scheduleForm.action = "<%=root%>/plan";
	document.scheduleForm.submit();
}
function cancel() {
	alert("리스트 경로 지정");
	history.back();
}
$(function printRouteDetailList() {
	
	<c:forEach var="rdlist" items="${routeDetailList}" varStatus="status">

	$("#routeList").append("<li>${rdlist.site_name}</li>");
    
    </c:forEach>
});
</script>
</head>
<body onload="javascript:datePicker();">

      <!-- Page Title -->
        <div class="page-title-container">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 wow fadeIn">
                        <i class="fa fa-comments"></i>
                        <h1>일정 수정</h1>
                    </div>
                </div>
            </div>
        </div>


	<form id="scheduleForm" name="scheduleForm" method="post" class="container" action="">
		<input type="hidden" id="act" name="act" value=""> 
		<input type="hidden" id="route_id" name="route_id" value="${pList[0].getRoute_id()}">
		<input type="hidden" id="page" name="page" value="">
		<input type="hidden" id="viewMode" name="viewMode" value="">
		<input type="hidden" id="searchMode" name="searchMode" value="">
		<input type="hidden" id="searchWord" name="searchWord" value="">
		<input type="hidden" id="categoryMode" name="categoryMode" value="">
	
	<div class="foundation" >	
		<div id="top" class="row grandFaRow">
			<div class="col-sm-6 fatherBorder leftCol">
				<img src="${route_url}" id="route_img" class="col-md-12">
			</div>
			<div class="col-md-5 col-md-offset-1 rightCol">
				<div class="row fatherBorder">
					<h3>경로에 추가한 여행지</h3>
					<ol id="routeList">
					</ol>
				</div>
				<div id="topOftop" class="row">
					<h4>제목</h4>
					<input type="text" id="title" class='form-control' name="title" value="${pList[0].getTitle()}" placeholder="일정의 제목을 입력하세요.">
					${pList[0].getId()}(${pList[0].getName()})
				</div>
			</div>
		</div>
		<hr>
		<div id="center grandFaRow">
			<input type="hidden" id="index" name="index" value=""> 

			<div id="center" width="100%">
				<table id="scheduleTable" class="table table-hover">
					<tr class="tableTitle">
						<td>날짜</td>
						<td>장소</td>
						<td>일정</td>
						<td>비용</td>
						<td>화폐단위</td>
						<td><input type="button" id="btnAddSchedule" class="btn btn-default btn-sm" value="일정 추가" onclick="javascript:addSchedule();"></td>
					</tr>
	
					<c:forEach var="pl" items="${pList}" varStatus="status">
						<tr id="trr${status.index}">
							<input type="hidden" id="planid" name="planid" value="${pl.plan_id}">
							<td><input type="text" id="date" name="date" class='datePicker form-control' value="${pl.stay_date}" placeholder=""></td>
							<td><input type="text" id="location" name="location" class='form-control' value="${pl.site_name}"></td>
							<td><input type="text" id="schedule" name="schedule" class='form-control' value="${pl.content}"></td>
							<td><input type="text" id="cost" name="cost" class='form-control' value="${pl.budget}"></td>
							<td><input type="text" id="won" name="won" class='form-control' value="${pl.currency}"></td>
							
							<td><input type="button" value="삭제" class='btn btn-default btn-sm' onclick="javascript:deleteTableSchedule('trr${status.index}')"></td>
						</tr>
					</c:forEach>
				</table>
			</div>
						
			<div id="bottom">	
				<button class="btn btn-default" id="btnSave" onclick="javascript:save();">저장</button>
				<button class="btn btn-default" id="btnCancel" onclick="javascript:cancel();" >취소</button>
			</div>
		</div>
	</div>
	</form>
</body>
</html>

<%}%>