<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/member.jsp" %>    
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<script type="text/javascript">
var loadingImgStr = '<div><img src="${root }/assets/img/loading.gif"></div>';

jQuery(function(){
	jQuery.validator.addMethod(
			'notEqualTo',
			function (value, element, param) {
			return this.optional(element) || value != param.val();
			},
			'Please specify a different value'
	);
	
	$("#resetPassForm").validate({
        
        submitHandler: memberResetPassword,
                rules: {
    				password : {
    					required : true,
    					rangelength : [ 5, 16 ],
    					notEqualTo : $('#oldPassword')
    				},
    				password2 : {
    					required : true,
    					equalTo : "#password"
    				},
                },
                messages: {
    				password : {
    					required : "새 비밀번호를 입력해주세요.",
    					rangelength : "비밀번호는 5~16자까지 입력 가능합니다.",
    					notEqualTo : "임시 비밀번호와 동일한 비밀번호는 사용하실 수 없습니다."
    				},
    				password2 : {
    					required : "새 비밀번호를 다시 한번 입력해주세요.",
    					equalTo : "입력하신 비밀번호가 일치하지 않습니다."
    				},
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
	

		
	$('#password').on('focus', function(e) {
	    $('#alertMsg').html('');
	});	
})

function memberResetPassword() {
	jQuery.ajax({
		url: root+'/member',
		type : 'post',
		data : jQuery('#resetPassForm').serialize(),
		dataType : 'json',
		success : resetPasswordResult,
		beforeSend : function(){
			$('#alertMsg').css({
				"text-align" : "center"
			}).html(loadingImgStr);
			$('.content-body').hide();
		},
		error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
	});
}

function resetPasswordResult(data) {
	$('#alertMsg').html('');
	$('.content-body').show();
	//admin@dreamcatcher.com
	var result = data.result;
	if( result == 'passwordExist'){
		$('#alertMsg').css({
			"color" : "red",
			"text-align" : "right"
		}).html('임시 비밀번호와 동일한 비밀번호는 사용하실 수 없습니다.');
		return;
	}else if(result=='resetPasswordSuccess') {
		$('#alertMsg').html('');
		$('.content-body').hide();
		$('.hidden-content').show();
	}
			
}
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
			
			<form id="resetPassForm" name="resetPassForm" method="POST" action="" onsubmit="return false;">
				<div class="content-body">
				<!-- Hidden Input Start-->
				<input type="hidden" id="id" name="id" value="${param.id}">
				<input type="hidden" id="oldPassword" name="oldPassword" value="${param.tempPassword}">
				<input type="hidden" id="act" name="act" value="resetPassword">
				<!-- Hidden Input End-->
				<h4 style="text-align:left;color:#828282">임시 비밀번호 발송에 따른 <b>계정 잠금 상태</b>입니다.</h4>
				<h5>새로운 비밀번호로 변경해주세요.</h5>
				</div>
				<div id="alertMsg"></div>
				<div class="content-body">
					<ul>
					<li>
						<input type="password" id="password" name="password" value="" placeholder="NEW PASSWORD" ><div class=" icon3 pass2"></div>
					</li>
					<li><input type="password" id="password2" name="password2"
								placeholder="CONFIRM NEW PASSWORD"><div class=" icon3 pass2"></div>
					</li>
					</ul>
					<div class="submit three">
					<input type="submit" value="SIGN UP" >
					</div>
				</div>
				<div class="hidden-content">
					<div class="success">
						<h5><b>비밀번호가 성공적으로 변경되었습니다.</b><br></h5>
						<h5>새로운 비밀번호를 사용하여 로그인 하시면</h5>	
						<h5>정상적으로 서비스를 이용하실 수 있습니다.</h5><br>	
					</div>
					<h4>지금 <a class="modalButton" data-src="${root}/member?act=moveLogin" data-target="#memberModal">로그인</a></h4>
				</div>
			</form>
		
		</div>
	</div>
	<!--//End-login-form-->
</div>

