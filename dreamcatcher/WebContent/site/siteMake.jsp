<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/common/common.jsp"%>

<style type="text/css">
.map_container {
	position: relative;
	width: 100%;
	height:100%;
	padding-bottom: 56.25%; /* Ratio 16:9 ( 100%/16*9 = 56.25% ) */
}

.map_container #site_canvas {

	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	margin: 0;
	padding: 0;
}
.container{
	position: relative;
	width: 100%;
	padding: 15px;
	margin-bottom:15px;
}

.input-column{
	margin-bottom:15px;
}
.modal-content {
top:50px;
right:20%;
left:20%;
  height: auto;
  min-width:60%;
  padding-bottom:0;
}

.btn {
  background-color: hsl(315, 39%, 25%) !important;
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#bc66a6", endColorstr="#58264c");
  background-image: -khtml-gradient(linear, left top, left bottom, from(#bc66a6), to(#58264c));
  background-image: -moz-linear-gradient(top, #bc66a6, #58264c);
  background-image: -ms-linear-gradient(top, #bc66a6, #58264c);
  background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #bc66a6), color-stop(100%, #58264c));
  background-image: -webkit-linear-gradient(top, #bc66a6, #58264c);
  background-image: -o-linear-gradient(top, #bc66a6, #58264c);
  background-image: linear-gradient(#bc66a6, #58264c);
  border-color: #58264c #58264c hsl(315, 39%, 17%);
  color: #fff !important;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.52);
  -webkit-font-smoothing: antialiased;
}

#textareaError{
	margin: 0 0 5px 0;

	text-align:left;
}
.alert-area{
	color:red;

}

</style>
<script type="text/javascript" src="${root}/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="${root}/assets/plugin/bootstrap/js/bootstrap-filestyle.js" charset="utf-8"></script>
<script src="${root}/assets/js/jquery.validate.min.js"></script>
<script src="${root}/assets/js/additional-methods.min.js"></script>
<script type="text/javascript"> 
var StreetViewPanorama = new function(){}
var step_flag = false;

var globalMap;
var globalMarker;
var globalGeocoder;
google.maps.event.addDomListener(window, 'load', initialize(0,0));
// 맵 초기화
function initialize(x, y) { 
     // 입력된 좌표가 없으면 기본좌표를 역삼동으로 설정.
    if(x==0){ x=37.50075507977441; }
    if(y==0){ y=127.03690767288208; }
    document.getElementById("searchAddress").focus();

    globalGeocoder = new google.maps.Geocoder();

    var latlng = new google.	maps.LatLng(x, y);

    var myOptions = { 
        zoom: 16, 

      	
        center: latlng, 
        
        navigationControl: false,    // 눈금자 형태로 스케일 조절하는 컨트롤 활성화 선택.
        navigationControlOptions: { 
            position: google.maps.ControlPosition.TOP_RIGHT,
            style: google.maps.NavigationControlStyle.DEFAULT // ANDROID, DEFAULT, SMALL, ZOOM_PAN
        },
        
        streetViewControl: false,

        scaleControl: false,    // 지도 축적 보여줄 것인지.
      
        
        mapTypeControl: false, // 지도,위성,하이브리드 등등 선택 컨트롤 보여줄 것인지
        mapTypeId: google.maps.MapTypeId.ROADMAP  // HYBRID, ROADMAP, SATELLITE, TERRAIN
    }; 

    globalMap = new google.maps.Map(document.getElementById("site_canvas"), myOptions); 

    
    google.maps.event.addListener(globalMap, 'dragend', function(){    // 드래그시 이벤트 추가
        showMapPos();
        showMapAddr();
    });
    google.maps.event.addListener(globalMap, 'click', function(event){        // 지도클릭시 마커이동
        moveMarker(event.latLng); 
    });
    
	
}
 
// 맵 드래그할 때 맵 중앙 좌표 보여주기
function showMapPos(){
    var pos=globalMap.getCenter();

    document.getElementById("centerX").value = pos.lat();
    document.getElementById("centerY").value = pos.lng();
}

// 드래그할 때 맵 중앙 좌표의 주소
function showMapAddr(){
    globalGeocoder.geocode( { 'location': globalMap.getCenter()}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
   
            
            var str="";
            for(var i=3; i>=0; i--){
                str += " "+results[0].address_components[i].short_name;
            }
            document.getElementById("txtAddress").innerHTML=str;
           
            

        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

// 맵 중앙에 마크찍기
function setMark(){
    var myOptions = {
        position: globalMap.getCenter(),
        draggable: true,
        map: globalMap,
       
        visible: true
    };

    globalMarker = new google.maps.Marker(myOptions);
}

// 마크 삭제하기
function removeMark(){
    globalMarker.setOptions({
        map: null,
        visible: false
    });
    globalMarker = null;
}

// 마크좌표 가져오기
function getMarkPos(){
    var pos=globalMarker.getPosition();

	$('#latitude').val(pos.lat());
	$('#longitude').val(pos.lng());
	
	step_flag = true;
}

// 특정좌표로 이동하기
function setMapByCoord(x, y){
    var loc = new google.maps.LatLng(x, y);

    globalMap.setCenter(loc);
}

function siteDetailWrite(){

    if(step_flag == false){
        $('.alert-searchAddress').text('추가하실 지역을 검색해주세요');
    }
    if($.trim($('#site_name').val()) == ''){
        $('.alert-siteName').text('지역 이름을 입력해주세요');
    }
    if(step_flag == true || $.trim($('#site_name').val()) != ''){
		$('#step1-area').hide();
		$('#step2-area').show();
    }
}


// 주소값으로 찾기
function codeAddress() {
	//globalMarker.setMap(null);
	$('.alert-searchAddress').text('');
    if($.trim($('#searchAddress').val()) == ''){
       $('.alert-searchAddress').text('검색하실 지역를 입력해주세요');
        return;
    }
   
    globalGeocoder.geocode( { 'address': $('#searchAddress').val() }, 
    		function(results, status) {
	        if (status == google.maps.GeocoderStatus.OK) {
	            globalMap.setCenter(results[0].geometry.location);
	            globalMarker = new google.maps.Marker({
	                map: globalMap, 
	                position: results[0].geometry.location,
	                draggable: true
	            });
	           
	            //geocoder를 사용하기 위해 변수를 선언하고 구글 맵 api에서 객체를 얻어옴.
	         	 console.log(results);
	         	 setLocationCode(results[0]);
				
	         /*var geocoder = new google.maps.Geocoder();
	          var pos=globalMarker.getPosition();
	          var latlng = new google.maps.LatLng(pos.lat(),pos.lng());
	          //위도와 경도를 구글 맵스의 geocoder에서 사용할 형식으로 변환합니다.
	          geocoder.geocode({'latLng' : latlng}, function(results, status) 
	          {
	          if (status == google.maps.GeocoderStatus.OK) {
	        	  //좌표를 주소로 변환시키는 geocoder를 실행
	        	  //반환된 값이 비어있찌 않으면 consol에 출력
	        	  //만약 성공적으로 변환이 되었따면, status라는 상태변수가 참이 되어 아래의 코드들이 실행됨.
	        	  if (results[1]) {
	         		

				
	          }
	          } else {
	        	  //geocoder 실패시 알림창 출력
	          	alert("Geocoder failed due to: " + status);
	          } 
          });*/
        } else {
            alert("지역 검색 중 오류가 발생하였습니다.\nReason : " + status);
            $('#searchAddress').val('');
        }
    });

}

function setLocationCode(results){
	//alert(results.formatted_address);
	/* location 정보를 받아온 후  
	   받아온 json 값에서 국가코드 및 도시 이름을 
	   가져오고, form 안에 값을 넣어준다.*/
	var addressComponents = results.address_components;
	var loc_name = '';
	var nation_code = '';
	for(var i = 0; i < addressComponents.length; i++){
		if(	addressComponents[i].types[0] == 'locality'){
			//alert(addressComponents[i].short_name);
			loc_name = addressComponents[i].short_name;
		}
		if(	addressComponents[i].types[0] == 'country'){
			//alert(addressComponents[i].short_name);
			nation_code = addressComponents[i].short_name;
		}
	}
	
	if(loc_name == ''){
		$('.alert-searchAddress').text('지역을 조금 더 상세히 입력해주세요');
		return;
	}
	if(nation_code == ''){
		$('.alert-searchAddress').text('국가가 지정되지 않은 지역은 추가하실 수 없습니다.');
		return; 
	}

	var address = results.formatted_address;
	//전체주소
/* 	var city = results[length-2].address_components[0].short_name;
	var gu = results[length-4].address_components[0].short_name;
	var dong = results[length-5].address_components[0].short_name;
	var street = results[length-6].address_components[0].short_name;
	var est = results[length-7].address_components[0].short_name;
	var cityName = city+gu+dong+street+est;
 */
    $('#loc_name').val(loc_name);
    $('#nation_code').val(nation_code);
    $('#address').val(address);

    getMarkPos();
}
// 정보창 마크 찍기
function setInfoMark(){
   

    var html = "";
    html += "<div>";
    html += "    <a href='http://www.findall.co.kr' target='_blank'>";
    html += "        <img src='http://image.findall.co.kr/FANew/Topmain/summer_logo.gif' border='0'>";
    html += "    </a>";
    html += "</div>";

    var infoWin = new google.maps.InfoWindow({content: html, maxWidth:1000});
    var loc = new google.maps.LatLng(37.500061375296184,127.03099206089973);
    
    var myOptions = {
        position: loc,
        draggable: false,
        map: globalMap,
      
        visible: true
    };

    // 마커 생성
    globalMarker = new google.maps.Marker(myOptions);

    // 마커에 이벤트리스너 추가
    google.maps.event.addListener(globalMarker, 'click', function(){
        infoWin.open(globalMap, globalMarker);
    });

    // 지도 중심좌표 이동
    globalMap.setCenter(loc);
}

// 지도 위의 마크 모두 삭제 - Refresh 말고 방법 없을까?
function clearMark(){
    var loc = globalMap.getCenter(); // 현재의 지도의 위치를 가져온다.

    globalMap = null;
    globalMarker = null;
    globalGeocoder = null;

    initialize(loc.lat(), loc.lng());
}

// 지도 클릭시 마커 이동
function moveMarker(loc){
  
    globalMarker.setPosition(loc);
}




	

	

//	google.maps.event.addDomListener(window, 'load', initialize);
	google.maps.event.addDomListener(window, "resize", resizingMap());


	function resizeMap() {
	   if(typeof globalMap =="undefined") return;
	   setTimeout( function(){resizingMap();} ,400);
	}

	function resizingMap() {
	   if(typeof globalMap =="undefined") return;
	   var center = globalMap.getCenter();
	   google.maps.event.trigger(globalMap, "resize");
	   globalMap.setCenter(center); 
	}
	
	var tempOriginFileName="";
	var oEditors = [];
	$(function(){
		
		initialize(0, 0);
		$('#step2-area').hide();
		resizeMap();
		
	      nhn.husky.EZCreator.createInIFrame({
	          oAppRef: oEditors,
	          elPlaceHolder: "content",
	          //SmartEditor2Skin.html 파일이 존재하는 경로
	          sSkinURI: "/dreamcatcher/SE2/SmartEditor2Skin.html",  
	          htParams : {
	              // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	              bUseToolbar : true,             
	              // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	              bUseVerticalResizer : true,     
	              // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	              bUseModeChanger : true,         
	              fOnBeforeUnload : function(){
	                   
	              }
	          }, 
	          fOnAppLoad : function(){
	        	  oEditors.getById["content"].exec("PASTE_HTML", [""]);
	              //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
	             // oEditors.getById["content"].exec("PASTE_HTML", ["기존 DB에 저장된 내용을 에디터에 적용할 문구"]);
	          },
	          fCreator: "createSEditor2"
	      });
	      
	      
	    $('#searchAddress').on('focus', function(){
	    	if($.trim($('#searchAddress').val()) == ''){
	    		$('#searchAddress').val('');
	    	}
	    	$('.alert-searchAddress').text('');
	    });
	    
	    $('#site_name').on('focus', function(){
	    	$('.alert-siteName').text('');	
	    });
	      
		// 엔터키 submit 막기
	    $(document).keypress(
	    	    function(event){
	    	     if (event.which == '13') {
	    	        event.preventDefault();
	    	      }


	    	});
	    
		$.validator.addMethod('filesize', function(value, element, param) {
		    // param = size (en bytes) 
		    // element = element to validate (<input>)
		    // value = value of the element (file name)
		    return this.optional(element) || (element.files[0].size <= param) 
		});
		
		jQuery("#siteMakeForm").validate({	        
	        submitHandler: siteMake,

	                rules: {
	                	brief_info: {
	                        required: true,
	                        maxlength: 300
 	                    },
	                    siteImageFile:{
	                    	required: true,
	                    	extension: 'png|jpe?g|gif', 
	                    	filesize: (10*1024*1024) 
	                    }
	                },
	                messages: {
	                	brief_info: {
	                        required: "간략 설명을 입력해주세요",
	                        maxlength: "간략 설명은 최대 300자를 초과할 수 없습니다"
 	                    },
	                    siteImageFile:{
	                    	required: '사진을 업로드해주세요',
	                    	extension: 'JPG, GIF, PNG 파일형식만 지원합니다', 
	                    	filesize: '첨부사진은 10MB를 초과할 수 없습니다.'
	                    }
	                },
	                errorElement: "div",
	                errorPlacement: function(error, element) {
	                    error.insertBefore(element);
	                    error.css({"margin": "0 0 0 0px", "color":"red", "text-align":"left"});
	                }

	            });  
		
		$('myform').submit(function() {
			  return false;
			});

	});
	

	function siteMake(){
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		var htmlString = $('#content').val();
		if($.trim($(htmlString).text()) == '' ){
			$('#textareaError').show();
			$('#textareaError').css({'color':'red'});
			oEditors.getById["content"].exec("PASTE_HTML", [""]);
			oEditors.getById["content"].exec("FOCUS"); 
		}else{
			$('#textareaError').hide();		
			//alert('success');
			document.siteMakeForm.action = "${root}/siteMake";
		 	document.siteMakeForm.submit(); 
		}
	} 

	function cancelSiteMake(){
		document.siteMakeForm.action = "${root}/plan/siteMapView.jsp";
	}



</script>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title">관광지 등록</h4>
</div>
<div class="modal-body">

	<form id="siteMakeForm" name="siteMakeForm" method="post" action="" enctype="multipart/form-data" onsubmit="return false;">
<!-- 		<input type="hidden" name="act" id="act" value="siteMake">
 -->		<!-- 위치찾기 버튼을 클릭 하면 구글맵에서 데이터를 가져온 후 value를 세팅 -->
		<input type="hidden" name="latitude" id="latitude" value=""> 
		<input type="hidden" name="longitude" id="longitude" value="">
		<input type="hidden" name="loc_name" id="loc_name" value=""> 
		<input type="hidden" name="nation_code" id="nation_code" value="">
		<input type="hidden" name="address" id="address" value="">
	<div id="step1-area">
		<div class="map_container">
			<div id="site_canvas"></div>
		</div>
		
		<div class="container">
			<div class="row">
				<div class="col-sm-12 input-column">
					<div class="alert-area alert-searchAddress"></div>
					<div class="input-group">
						<input type="text" class="form-control" name="searchAddress" id="searchAddress" value="" onClick="this.value='';" onkeypress="if(event.keyCode=='13') codeAddress();" placeholder="추가하실 지역을 검색해보세요" aria-describedby="sizing-addon2">
						<span class="input-group-addon btn" id="sizing-addon2" onClick="javascript:codeAddress();">위치찾기</span>
					</div>
	 			</div>
	 			
	 			<div class="col-sm-12 input-column">
					<div class="alert-area alert-siteName"></div>
	 				<input type="text" class="form-control" name="site_name" id="site_name" value="" onClick="this.value='';" onkeypress="if(event.keyCode=='13') siteDetailWrite();" placeholder="관광지의 이름을 입력하세요" aria-describedby="sizing-addon2">
	 			</div>
			</div>
			<div class="row" align="right">
				<div class="col-sm-12 input-column">
					<input id="nextButton" type="button" class="btn" value="다 음"  onkeypress="" onClick="javascript:siteDetailWrite()">
				</div>
			</div>
		</div>	
	</div>
	<div id="step2-area">
		<div class="container">
			<div class="row">
	 			
	 			<div class="col-sm-12 input-column">

	 				<input type="text" class="form-control" name="brief_info" id="brief_info" value="" onClick="this.value='';" placeholder="관광지에 대한 간략한 설명을 입력해주세요" aria-describedby="sizing-addon2">
	 			</div>
			</div>
			<div class="row">
				<div class="col-sm-12 input-column">
				<div id="textareaError">관광지에 대한 자세한 설명을 입력해주세요</div>
				<textarea id="content" name="content" style="width:100%; height:100%;"></textarea>
				
				</div>
			</div>
			
			<div class="row" align="right">
				<div class="col-sm-12 input-column">
				<input type="file" name="siteImageFile" id="siteImageFile" class="filestyle" data-buttonName="btn-primary" placeholder="관광지의 대표사진을 업로드해주세요">
				</div>
			</div>
			
			<div class="row" align="center">

				<input id="siteMakeButton" type="submit" class="btn" value="등 록"  /> 

			</div>
		</div>

	</div>
	</form>
</div>


