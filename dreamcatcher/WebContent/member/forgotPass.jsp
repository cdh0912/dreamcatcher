<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/member.jsp" %>    
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<script type="text/javascript">
var loadingImgStr = '<div><img src="${root }/assets/img/loading.gif"></div>';

jQuery(function(){
	
	$("#forgotPassForm").validate({
        
        submitHandler: adminSendPassword,

                rules: {
                    id: {
                        required: true,
                        email: true,
                        maxlength: 40
                    }
                },
                messages: {
                	id: {
                        required: "이메일 주소를 입력해주세요.",
                        email: "올바른 형식의 이메일 주소를 입력해주세요.",
                        maxlength: "이메일 주소는 최대 40자를 초과할 수 없습니다."
                    }
                },
                errorElement: "div",
                errorPlacement: function(error, element) {
                    error.insertBefore(element.parent());
                    error.css({"margin": "0 0 0 0px", "color":"red", "text-align":"right"});
                }

            });
	
	// 버튼 클릭 이벤트
	$('.member-style a.modalButton').on('click', function(e) {
	    e.preventDefault();
	    var url = $(this).data('src');
	    var target = $(this).data('target');
	    $(window.parent.document).find('.modal-backdrop').remove();
	    $(window.parent.document).find(target).removeData('bs.modal');
	    $(window.parent.document).find(target).modal({remote: url });

	});	
	
	function adminSendPassword() {
		$.ajax({
			url: root+'/admin',
			type : 'post',
			data : jQuery('#forgotPassForm').serialize(),
			dataType : 'json',
			success : sendPasswordResult,
			beforeSend : function(){
				$('#alertMsg').css({
					"text-align" : "center"
				}).html(loadingImgStr);
				$('.content-body').hide();
			},
			error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
		});
	}
	
	function sendPasswordResult(data) {
		$('#alertMsg').html('');	
		var result = data.result;
		if(result == 'idNotExist'){
			$('.content-body').show();
			$('#alertMsg').css({
				"color" : "red",
				"text-align" : 'right'
			}).html('가입되지 않은 이메일입니다.');
			return;
		}else if(result == 'sendPasswordSuccess'){

			jQuery('#forgotPassForm .hidden-content').show();
		}
	}
	
	function sendPasswordSuccess(data){
		$('#alertMsg').css({
			"color" : "#9A9A9A"
		}).html('<h3>임시 비밀번호 발송이 완료되었습니다.</h3><br>');
		$('.content-body').show();
		var result = data.result;
		if(result == 'sendPasswordSuccess'){
			jQuery('.hidden-content').show();
		}
	}
	
	$('#password').on('focus', function(e) {
	    $('#alertMsg').html('');
	});	

})
</script>




<!--/start-login-three-->
<div class="member-style">
	<div class="forgot">
		<div class="forgot-content  hvr-float-shadow">
			<div class="forgot-content-head">
					<img src="${root }/assets/img/member/top-key.png" alt=""/>
					<h3>account reset</h3>
					<lable></lable>
			</div>
			
			<form id="forgotPassForm" name="forgotPassForm" method="POST" action="" onsubmit="return false;">
				
				<div class="content-body">
				<!-- Hidden Input Start-->
				<input type="hidden" id="act" name="act" value="sendPassword">
				<!-- Hidden Input End-->	
				<h3 style="text-align:left;color:#828282">비밀번호를 잊어버리셨나요?</h3>
				<h5>비밀 번호 변경 절차를 위해 이메일 주소를 입력해주세요.</h5>
				<h5>임시 비밀번호가 해당 이메일 주소로 발송됩니다.</h5>
				</div>
				<div id="alertMsg"></div>
				<div class="content-body">				
				<ul>

				<li>
					<input type="text" class="text" id="id" name="id" value="" placeholder="E-MAIL" ><a href="#" class=" icon3 mail2"></a>
				</li>
				</ul>
				<div class="submit three">
					<input type="submit" value="SIGN UP" >
				</div>
				<div><br/>
				<h5 style="text-align:center;"><a class="modalButton" data-src="${root }/member?act=moveLogin" data-target="#memberModal">로그인</a></h5>
				</div>
				</div>
				<div class="hidden-content">
					<div class="success">
						<h5><b>임시 비밀번호가 성공적으로 발송되었습니다.</b><br></h5>
						<h5>임시 비밀번호를 사용하여 로그인 하신 후</h5>	
						<h5>새로운 비밀번호로 변경해주세요.</h5><br>	
					</div>
					<h4>지금 <a class="modalButton" data-src="${root}/member?act=moveLogin" data-target="#memberModal">로그인</a></h4>
				</div>
			</form>
		
		</div>
	</div>
	<!--//End-login-form-->
</div>

