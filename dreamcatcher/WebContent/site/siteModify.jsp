<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/loginCheck.jsp" %>

<script type="text/javascript" src="${root}/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="${root}/assets/plugin/bootstrap/js/bootstrap-filestyle.js" charset="utf-8"></script>
<script src="${root}/assets/js/jquery.validate.min.js"></script>
<script src="${root}/assets/js/additional-methods.min.js"></script>

<script type="text/javascript">
var tempOriginFileName="";
var oEditors = [];
$(function(){
	localStorage.siteId="";
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
              //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
             // oEditors.getById["content"].exec("PASTE_HTML", ["기존 DB에 저장된 내용을 에디터에 적용할 문구"]);
          },
          fCreator: "createSEditor2"
      });
      
      //저장버튼 클릭시 form 전송
     // $("#save").click(function(){
      //    oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
     //     $("#frm").submit();
    //  }); 
      	$.validator.addMethod('filesize', function(value, element, param) {
		    // param = size (en bytes) 
		    // element = element to validate (<input>)
		    // value = value of the element (file name)
		    return this.optional(element) || (element.files[0].size <= param) 
		});
      
		jQuery("#siteModifyForm").validate({	        
	        submitHandler: siteModify,

	                rules: {
	                	site_name:{
	                        required: true,
	                        maxlength: 100
	                	},
	                	brief_info: {
	                        required: true,
	                        maxlength: 300
 	                    },
	                    siteImageFile:{
	                    	extension: 'png|jpe?g|gif', 
	                    	filesize: (10*1024*1024) 
	                    }
	                },
	                messages: {
	                	site_name:{
	                        required: "관광지 이름을 입력해주세요",
	                        maxlength:"관광지 이름은 최대 100자를 초과할 수 없습니다"
	                	},
	                	brief_info: {
	                        required: "간략 설명을 입력해주세요",
	                        maxlength: "간략 설명은 최대 300자를 초과할 수 없습니다"
 	                    },
	                    siteImageFile:{
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
});


function siteModify() {
	oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	var htmlString = $('#content').val();
	//alert(htmlString);
	if($.trim(htmlString) == '' ){
		$('#textareaError').show();
		$('#textareaError').css({'color':'red'});
		oEditors.getById["content"].exec("PASTE_HTML", [""]);
		oEditors.getById["content"].exec("FOCUS"); 
	}else{
		$('#textareaError').hide();
		$('#siteModifyForm #page').val(page);
		$('#siteModifyForm #viewMode').val(viewMode);
		$('#siteModifyForm #searchMode').val(searchMode);
		$('#siteModifyForm #searchWord').val(searchWord);
		$('#siteModifyForm #categoryMode').val(categoryMode);
		document.siteModifyForm.action = root+'/siteModify';
		document.siteModifyForm.submit();
	}
	
}
function cancelModify(site_id) {
	$('#boardForm').append('<input type="hidden" name="site_id" id="site_id" value="'+site_id+'">');	
	$('#boardForm #act').val('siteView');
	$('#boardForm #page').val(page);
	$('#boardForm #viewMode').val(viewMode);
	$('#boardForm #searchWord').val(searchWord);
	$('#boardForm #searchMode').val(searchMode);
	$('#boardForm #categoryMode').val(categoryMode);
	document.boardForm.action = root+"/site";
	document.boardForm.submit();
}

function uploadImagePreview(element){
    if (element.files && element.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
        	//$('#siteImage').attr('src',e.target.result);
        	$('#siteImage').css('background-image', 'url("' + e.target.result + '")');

        }
        reader.readAsDataURL(element.files[0]);
    }

}



</script>


        <div class="page-title-container ">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 wow fadeIn">
                        <i class="fa fa-camera"></i>
                        <h1>여행지 정보 수정 /</h1>
                        <p>더욱 소중한 정보를 나눠주세요!</p>
                    </div>
                </div>
            </div>
        </div>

<div class="portfolio-container">
	<form name="siteModifyForm" id="siteModifyForm" method="post" action="" enctype="multipart/form-data" onsubmit="return false;">

		<input type="hidden" name="site_id" id="site_id" value="${siteInfo.site_id }">
		<input type="hidden" name="savefolder" id="savefolder" value="${siteInfo.savefolder }">
		<input type="hidden" name="saved_picture" id="saved_picture" value="${siteInfo.saved_picture }">
		<input type="hidden" name="type" id="type" value="${siteInfo.type }">
		<input type="hidden" name="origin_picture" id="origin_picture" value="${siteInfo.origin_picture }">
		<input type="hidden" id="page" name="page" value="">
		<input type="hidden" id="viewMode" name="viewMode" value="">
		<input type="hidden" id="searchMode" name="searchMode" value="">
		<input type="hidden" id="searchWord" name="searchWord" value="">
		<input type="hidden" id="categoryMode" name="categoryMode" value="">
		<div class="container">
			<div class="article-container">
				<div class="row">
				<div class="col-sm-12 input-column">
				<div class="input-group">
					<span class="input-group-addon btn" id="sizing-addon2">관광지 이름</span>
					<input type="text" class="form-control" name="site_name" id="site_name" value="${siteInfo.site_name }" placeholder="관광지의 이름을 입력하세요" aria-describedby="sizing-addon2">
				 </div>
				 </div>
				</div>
				<div class="row">
				<div class="col-sm-12 input-column">
				<div class="input-group">
					<span class="input-group-addon btn" id="sizing-addon2">관광지 정보</span>
					<input type="text" class="form-control" name="brief_info" id="brief_info" value="${siteInfo.brief_info }" placeholder="관광지에 대한 간략한 설명을 입력해주세요" aria-describedby="sizing-addon2">
				 </div>
				 </div>
				 </div>
				 
				 <div class="col-sm-12"><hr></div>
				 
				<div class="col-sm-12 roundBorder imageArea">
					<div id="siteImage" name="siteImage" class="image-container" style="background: url('${root }/upload/${siteInfo.savefolder }/${siteInfo.saved_picture }');"></div>
				</div>
	
				<div class="col-sm-12"><hr></div>
				
				<div class="col-sm-12 squareBorder contentArea">
					<div id="textareaError">관광지에 대한 자세한 설명을 입력해주세요</div>
				
					<textarea id="content" name="content" style="width:100%; height:100%; ">${siteInfo.detail_info }</textarea>		
				</div>
				
				<div class="col-sm-12 squareBorder contentArea">
					<div class="originFile">기존 이미지  : <span id="originPicture">${siteInfo.origin_picture }</span></div>
					<input type="file" name="siteImageFile" id="siteImageFile" class="filestyle" data-buttonName="btn-primary" onChange="javscript:uploadImagePreview(this);">	
				</div>


			<hr>
			<div class="buttonArea">
			<button type="submit" class="btn btn-default" id="btnSave">등 록</button>
			<button type="button" class="btn btn-default" id="btnSave" onclick="javascript:cancelModify('${siteInfo.site_id }');">취 소</button>
			</div>
			</div>
		</div>
	</form>
</div>


	

