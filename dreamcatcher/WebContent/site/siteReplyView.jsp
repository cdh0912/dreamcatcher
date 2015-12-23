<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<script src="${root}/assets/js/jquery.validate.min.js"></script>
<script src="${root}/assets/js/additional-methods.min.js"></script>
<script type="text/javascript">
var replyPage = 1;
var loginId = '${memberInfo.id}';
var memberLevel = '${memberInfo.m_level}';

	$(document).ready(function() {

		
		$("#replyRegisterForm").validate({	        
	        submitHandler: replyRegister,

	                rules: {
	                	replyContent: {
	                        required: true,
	                        maxlength: 300
	                	}
	                },
	                messages: {
	                	replyContent: {
	                        required: "덧글 내용을 입력해주세요",
	                        maxlength: "덧글 내용은 300자를 초과할 수 없습니다"
	                	}
	                },
	                errorElement: "div",
	                errorPlacement: function(error, element) {
	                    error.insertBefore(element);
	                    error.css({"margin": "0 0 5px 0", "color":"red", "text-align":"left"});
	                }

	     }); 
		$('#replyListArea').html('');
		replyList();
	});
	
	function replyList() {

		$('#replyRegisterForm #act').val('replyList');
		$.ajax({
		      type : 'POST',
		      dataType: 'json',
		      url : root+'/site',
		      data :  $('#replyRegisterForm').serialize(),
		      success : function(data) {
		    	  $('#moreButtonArea').html('');
				  if(data.replyList.length > 0){
					  	$('#reply_count').html('댓글수 [ '+ data.replyList.length +' ]'); 
					  }else{
		
						 if('${memberInfo.name}' == ''){
							 $('#replyBG').hide();
						 }
					  }
		    	  $(data.replyList).each(
		            	function(index, item) {
		            	   var sb = new StringBuffer();
		            	   sb.append('<div id="reply'+item.sre_id+'" class="col-sm-10 col-sm-offset-1 fadeInUp">');
		            	   sb.append('<div class="templatemo-content-widget white-bg">');
		            	   sb.append('<i class="fa fa-times" onClick="javascript:replyClose(this);"></i>');
		            	   
		            	   sb.append('<div class="square"></div>');
		            	   sb.append('<h2 class="templatemo-inline-block"><span style="position:inline-block; color:#C13383;">'+item.name+'</span>&lt;'+item.id+'&gt;</h2>');
		            	   sb.append('<hr>');
		            	   if(item.id == loginId || memberLevel=='1'){
		            		sb.append('<form class="logout-remove-element" id="replyModifyForm-'+item.sre_id+'" method="post" onsubmit="return false;">');
		            	   sb.append('<div id="modify-TextArea-'+item.sre_id+'" style="padding:10px 8px 10px 8px; min-height:100px;display:none;">');
		            	   sb.append('<textarea class="form-control modifyContent" id="modifyContent-'+item.sre_id+'" rows="5">'+item.content+'</textarea>');
		            	   sb.append('</div>');
		            	   sb.append('</form>');
		            	   }
		            	   sb.append('<div id="reply-TextArea-'+item.sre_id+'" style="padding:10px 8px 10px 8px; min-height:100px;">');
		            	   sb.append(encode4HTML(item.content));
		            	   //sb.append(item.content);
		            	   sb.append('</div>');
		            	   if(item.id == loginId || memberLevel=='1'){
		            	   sb.append('<hr class="logout-remove-element">');
		            	   sb.append('<div class="logout-remove-element" id="reply-ButtonArea-'+item.sre_id+'" style="padding-top:10px;text-align:right;">');
		            	   sb.append('<button type="button" class="btn btn-default" onClick="javascript:replyModifyView('+item.sre_id+');">수 정</button>&nbsp;&nbsp;&nbsp;&nbsp;');
		            	   sb.append('<button type="button" class="btn btn-default" onClick="javascript:replyDelete('+item.sre_id+');">삭 제</button>');
		            	   sb.append('</div>');
		            	   sb.append('<div class="logout-remove-element" id="modify-ButtonArea-'+item.sre_id+'" style="padding-top:10px;text-align:right;display:none;">');
		            	   sb.append('<button type="button" class="btn btn-default" onClick="javascript:replyModify('+item.sre_id+');">등 록</button>&nbsp;&nbsp;&nbsp;&nbsp;');
		            	   sb.append('<button type="button" class="btn btn-default" onClick="javascript:replyModifyCancel('+item.sre_id+');">취 소</button>');
		            	   sb.append('</div>');
		            	   }
		            	   sb.append('</div>');
		            	   sb.append('</div>');
	   
							$('#replyListArea').append(sb.toString()); 
							
/* 							 if(item.id == loginId || memberLevel=='1'){
								 var validationId = 'modifyContent-'+item.sre_id;
						 		$("#replyModifyForm-"+item.sre_id).validate({	        
						            rules: {
						            	validationId: {
						                    required: true,
						                    maxlength: 300
						            	}
						            },
						            messages: {
						            	validationId: {
						                    required: "덧글 내용을 입력해주세요",
						                    maxlength: "덧글 내용은 300자를 초과할 수 없습니다"
						            	}
						            },
						            errorElement: "div",
						            errorPlacement: function(error, element) {
						                error.insertBefore(element);
						                error.css({"margin": "0 0 0 0px", "color":"red", "text-align":"left"});
						            }
	
						 		}); 
							 } */
							 
							 if(item.id == loginId || memberLevel=='1'){

						 		$("#replyModifyForm-"+item.sre_id).validate({
						            errorElement: "div",
						            errorPlacement: function(error, element) {
						                error.insertBefore(element);
						                error.css({"padding": "0 0 5px 0", "color":"red", "text-align":"left"});
						            }
						 		}); 
						 		

							 }

		               });  


			        $(".modifyContent").each(function (item) {
			            $(this).rules("add", {
		                    required: true,
		                    maxlength: 300,
				            messages: {
			                    required: "덧글 내용을 입력해주세요",
			                    maxlength: "덧글 내용은 300자를 초과할 수 없습니다"

			           		 }
			            });
			        });
			        
		            if(!data.isLastPage){
		           
	 					var sb = new StringBuffer();
						sb.append('<div class="col-sm-10 col-sm-offset-1">');
						sb.append('<button type="button" class="btn btn-default" onClick="javascript:replyMoreList()">더 보기</button>');
						sb.append('</div>');
						
						$('#moreButtonArea').html(sb.toString()); 
		            } 

		      }
		   });
	}
	
	function replyMoreList(){
		$('#replyPage').val(++replyPage);
		$('#isTotalList').val('0');
		replyList();
	}
	
	function replyClose(element) {
		$(element).parent().slideUp(function() {
			$(element).hide();
		});
	}
	
	function replyRegister() {

		var contentStr = $('#replyContent').val();
		//$('#content').val(encode4HTML(contentStr));
		$('#content').val(contentStr);
		$('#replyRegisterForm #act').val('replyRegister');
		//alert($('#content').val());

	      $.ajax({
	         type : 'POST',
	         dataType: 'json',
	         url : root+'/site',
	         data : $('#replyRegisterForm').serialize(),
	         success : function(data) {

	        	  $('#replyListArea').html(''); 
		    	  $('#moreButtonArea').html('');
		    	  $('#content').val('');
		    	  $('#replyContent').val('');

		 		 $('#isTotalList').val('1');
				replyList();
	        
	            }
         });

	}
	
	function replyModifyView(sre_id){
		$('#reply-TextArea-'+sre_id).hide();
		$('#reply-ButtonArea-'+sre_id).hide();
		$('#modify-TextArea-'+sre_id).show();
		$('#modify-ButtonArea-'+sre_id).show();
			
	}
	
	function replyModifyCancel(sre_id){
		$('#modify-TextArea-'+sre_id).hide();
		$('#modify-ButtonArea-'+sre_id).hide();
		$('#reply-TextArea-'+sre_id).show();
		$('#reply-ButtonArea-'+sre_id).show();
			
	}
	
	function replyModify(sre_id){
		if($("#replyModifyForm-"+sre_id).valid()){
			var contentStr = $('#modify-TextArea-'+sre_id+' textarea').val();
			$('#replyRegisterForm #sre_id').val(sre_id);
			//$('#content').val(encode4HTML(contentStr));
			$('#content').val(contentStr);
			$('#replyRegisterForm #act').val('replyModify');
			//alert($('#content').val());
	
		      $.ajax({
		         type : 'POST',
		         dataType: 'json',
		         url : root+'/site',
		         data : $('#replyRegisterForm').serialize(),
		         success : function(data) {
	
		        	  $('#replyListArea').html(''); 
			    	  $('#moreButtonArea').html('');
			    	  $('#content').val('');
			    	  $('#replyContent').val('');
	
			 		 $('#isTotalList').val('1');
					replyList();
		        
		            }
	         });
		}	
	}
	
	function replyDelete(sre_id){
			$('#replyRegisterForm #act').val('replyDelete');
			$('#replyRegisterForm #sre_id').val(sre_id);

		      $.ajax({
		         type : 'POST',
		         dataType: 'json',
		         url : root+'/site',
		         data : $('#replyRegisterForm').serialize(),
		         success : function(data) {

		        	  $('#replyListArea').html(''); 
			    	  $('#moreButtonArea').html('');

					 $('#isTotalList').val('1');
					replyList();
		        
		            }
	         });
	}
	
	function encode4HTML(str) {
		if (str != '' && str != null){
		    return str
		        .replace(/\r\n?/g,'\n')
		        // normalize newlines - I'm not sure how these
		        // are parsed in PC's. In Mac's they're \n's
		        .replace(/(^((?!\n)\s)+|((?!\n)\s)+$)/gm,'')
		        // trim each line
		        .replace(/(?!\n)\s+/g,' ')
		        // reduce multiple spaces to 2 (like in "a    b")
		        .replace(/^\n+|\n+$/g,'')
		        // trim the whole string
		        .replace(/[<>&"']/g,function(a) {
		        // replace these signs with encoded versions
		            switch (a) {
		                case '<'    : return '&lt;';
		                case '>'    : return '&gt;';
		                case '&'    : return '&amp;';
		                case '"'    : return '&quot;';
		                case '\''   : return '&apos;';
		            }
		        })
		   //     .replace(/\n{2,}/g,'</p><p>')
		        // replace 2 or more consecutive empty lines with these
		        .replace(/\n/g,'<br/>')
		        // replace single newline symbols with the <br /> entity
		        .replace(/^(.+?)$/,'<p>$1</p>');
		        // wrap all the string into <p> tags
		        // if there's at least 1 non-empty character
		}else{
			return str;
		}
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

</script>

<div id="replyBG" class="container">
	<div class="portfolio-container container">
		<div class="row">
			<form id="replyRegisterForm" name="replyRegisterForm" method="post" action="" onSubmit="return false;">
			<input type="hidden" name="site_id" id="site_id" value="${siteInfo.site_id }">
			<input type="hidden" name="sre_id" id="sre_id" value="">
			<input type="hidden" name="act" id="act" value="">
			<input type="hidden" name="replyPage" id="replyPage" value="">
			<input type="hidden" name="isTotalList" id="isTotalList" value="">
			<input type="hidden" name="content" id="content" value="">
			<c:if test="${memberInfo != null }">
			<div class="col-sm-10 col-sm-offset-1 logout-remove-element">
				<div style="margin:30px 5px 5px 5px;">
				<textarea class="form-control" id="replyContent" name="replyContent" rows="5"></textarea>
				</div>
			</div>
			<div class="col-sm-10 col-sm-offset-1 logout-remove-element">
				<div style="margin:5px; text-align:right;">
				<button type="submit" class="btn btn-default">등 록</button>
				</div>
			</div>
			</c:if>
			</form>
		</div>
	</div>
	<c:if test="${memberInfo != null && siteInfo.reply_count > 0}">
	<hr id="replyLine" class="logout-remove-element" style="border-top: 1px solid #ccc;"> <!-- ////////////////////////////////////////////////////////////////////////////// -->
	</c:if>
		<strong id="reply_count" style="font-size:medium;" class="col-sm-2 col-sm-offset-1"></strong> <!-- ////////////////////////////////////////////////////// -->	

	<div id="replyListArea" class="row">	
	</div>

	<div class="portfolio-container container">
			<div id="moreButtonArea" class="row">
			</div>
	</div>
</div>