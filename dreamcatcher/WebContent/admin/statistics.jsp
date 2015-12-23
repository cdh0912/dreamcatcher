<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<script src="https://www.google.com/jsapi"></script>
<script src="${root}/assets/plugin/jquery/js/jquery.spinner.js"></script>
<link href="${root}/assets/plugin/bootstrap/css/bootstrap-spinner.css" rel="stylesheet">

<script>

/* Google Chart 
-------------------------------------------------------------------*/
// Load the Visualization API and the piechart package.
google.load('visualization', '1.0', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);
google.setOnLoadCallback(drawLocationChart);
google.setOnLoadCallback(drawSiteChart);
google.setOnLoadCallback(drawRouteChart);
// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.

var locationRange = 5;
var siteRange = 5;
var routeRange = 5;

function drawChart() {

	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/admin",
		data : {'act': 'statistics'},
		success : 
		function(data) {
    	      				
			var locationData = new google.visualization.DataTable();
			locationData.addColumn('string', 'location');
			locationData.addColumn('number', 'recommend');
			var locationStatistics = data.locationStatistics;
			for(var i=0; i<locationStatistics.length; i++){
				locationData.addRow([locationStatistics[i].item, locationStatistics[i].count]);	
			}

			var locationChartOptions = {'title':'지역 추천 순위'};

			var locationPieChart = new google.visualization.PieChart(document.getElementById('location_chart_div'));
			locationPieChart.draw(locationData, locationChartOptions);
  	            
			
			var siteData = new google.visualization.DataTable();
			siteData.addColumn('string', 'site');
			siteData.addColumn('number', 'recommend');
			var siteStatistics = data.siteStatistics;
			for(var i=0; i<siteStatistics.length; i++){
				siteData.addRow([siteStatistics[i].item, siteStatistics[i].count]);	
			}

			var siteChartOptions = {'title':'관광지 추천 순위'};
  	            
			var sitePieChart = new google.visualization.PieChart(document.getElementById('site_chart_div'));
			sitePieChart.draw(siteData, siteChartOptions);
  	            
			
			var routeData = new google.visualization.DataTable();
			routeData.addColumn('string', 'site');
			routeData.addColumn('number', 'recommend');
			var routeStatistics = data.routeStatistics;
			for(var i=0; i<routeStatistics.length; i++){
				routeData.addRow([routeStatistics[i].item, routeStatistics[i].count]);	
			}

			var routeChartOptions = {'title':'여행경로 포함 관광지 순위'};
  	            
			var routePieChart = new google.visualization.PieChart(document.getElementById('route_chart_div'));
			routePieChart.draw(routeData, routeChartOptions);
    
		}
	}); 
}

function drawLocationChart() {

	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/admin",
		data : {'act': 'locationRank','range':locationRange},
		success : 
		function(locationStatistics) {
    	      				
			var locationData = new google.visualization.DataTable();
			locationData.addColumn('string', 'location');
			locationData.addColumn('number', 'recommend');
			for(var i=0; i<locationStatistics.length; i++){
				locationData.addRow([locationStatistics[i].item, locationStatistics[i].count]);	
			}

			var locationChartOptions = {'title':'지역 추천 순위'};

			var locationPieChart = new google.visualization.PieChart(document.getElementById('location_chart_div'));
			locationPieChart.draw(locationData, locationChartOptions);
    
		}
	}); 
}

function drawSiteChart() {

	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/admin",
		data : {'act': 'siteRank','range':siteRange},
		success : 
		function(siteStatistics) {
    	     
			
			var siteData = new google.visualization.DataTable();
			siteData.addColumn('string', 'site');
			siteData.addColumn('number', 'recommend');
			for(var i=0; i<siteStatistics.length; i++){
				siteData.addRow([siteStatistics[i].item, siteStatistics[i].count]);	
			}

			var siteChartOptions = {'title':'관광지 추천 순위'};
  	            
			var sitePieChart = new google.visualization.PieChart(document.getElementById('site_chart_div'));
			sitePieChart.draw(siteData, siteChartOptions);
  	            
    
		}
	}); 
}


function drawRouteChart() {

	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : root+"/admin",
		data : {'act': 'routeRank','range':routeRange},
		success : 
		function(routeStatistics) {
			
			var routeData = new google.visualization.DataTable();
			routeData.addColumn('string', 'site');
			routeData.addColumn('number', 'recommend');
			for(var i=0; i<routeStatistics.length; i++){
				routeData.addRow([routeStatistics[i].item, routeStatistics[i].count]);	
			}

			var routeChartOptions = {'title':'여행경로 포함 관광지 순위'};
  	            
			var routePieChart = new google.visualization.PieChart(document.getElementById('route_chart_div'));
			routePieChart.draw(routeData, routeChartOptions);
    
		}
	}); 
}
    	        


      $(document).ready(function(){
    	  	drawChart();
          $(window).resize(function(){
        	drawLocationChart();
        	drawSiteChart();
        	drawRouteChart();

          });  
          
          $("#locationRange").spinner('changing', function(e, newVal, oldVal){
        		locationRange = newVal;
        		drawLocationChart();
          });  
          
          $("#siteRange").spinner('changing', function(e, newVal, oldVal){
        	  siteRange = newVal;
        	  drawSiteChart();
        });  
          
          $("#routeRange").spinner('changing', function(e, newVal, oldVal){
        	  routeRange = newVal;
        	  drawRouteChart();
        });  
      });


function siteRangeChange(){
	siteRange = $('#siteRange').val();
	drawSiteChart();
}

function routeRangeChange(){
	routeRange = $('#routeRange').val();
	drawRouteChart();
}

</script>
<div class="page-title-container ">
	<div class="container">
		<div class="row">
			<div class="col-sm-7 wow fadeIn">
				<i class="fa fa-camera"></i>
				<h1>추천 순위 통계 /</h1>
				<p>드림캐처 회원들의 관심 여행지</p>
			</div>
		</div>
	</div>
</div>



<div class="portfolio-container container">
	<div class="article-container">
		<div class="row roundBorder pie-chart-area">
			<div class="col-sm-8 col-sm-offset-2 input-column">
			    <div class="input-group spinner" data-trigger="spinner" id="locationRange">
			            <div class="input-group-addon">
			            <span>지역 순위 갯수</span>
			            </div>
			       	<input type="text" class="form-control" value="5" data-max="15" data-min="3" data-step="1" readonly>
			        <div class="input-group-addon">
			            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
			            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
			          </div>
			     </div>
			</div>
			<div class="col-sm-12">
			
				<div id="location_chart_div" class="pie-chart"></div>
			</div>
		</div>

		<div class="row roundBorder pie-chart-area">
			<div class="col-sm-8 col-sm-offset-2 input-column">
			    <div class="input-group spinner" data-trigger="spinner" id="siteRange">
			            <div class="input-group-addon">
			            <span>지역 순위 갯수</span>
			            </div>
			       	<input type="text" class="form-control" value="5" data-max="15" data-min="3" data-step="1" readonly>
			        <div class="input-group-addon">
			            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
			            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
			          </div>
			     </div>
			</div>
			<div class="col-sm-12">
				<div id="site_chart_div" class="pie-chart"></div>
			</div>
		</div>
		<div class="row roundBorder pie-chart-area">
			<div class="col-sm-8 col-sm-offset-2 input-column">
			    <div class="input-group spinner" data-trigger="spinner" id="routeRange">
			            <div class="input-group-addon">
			            <span>지역 순위 갯수</span>
			            </div>
			       	<input type="text" class="form-control" value="5" data-max="15" data-min="3" data-step="1" readonly>
			        <div class="input-group-addon">
			            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
			            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
			          </div>
			     </div>
			</div>		
			<div class="col-sm-12">
				<div id="route_chart_div" class="pie-chart"></div>
			</div>
		</div>
	</div>
</div>