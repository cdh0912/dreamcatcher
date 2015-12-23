<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/member.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>:: Dream Catcher - Register ::</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Flat UI Web Form Widget and Kit,Login Forms,Sign up Forms,Registration Forms,News latter Forms,Elements" />
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>

<script type="text/javascript">
var loadingImgStr = '<div><img src="${root }/assets/img/loading.gif"></div>';

	$(function() {
		jQuery.validator.addMethod(
				'notEqualTo',
				function (value, element, param) {
				return this.optional(element) || value != param.val();
				},
				'Please specify a different value'
		);
		
		// 회원가입폼 유효성 검사를 위한 jQuery 함수
		$("#modifyInfoForm").validate({
			submitHandler : memberModify,
			rules : {
				name : {
					required : true,
					maxlength : 20
				},
				oldPassword : {
					required : true,
					rangelength : [ 5, 16 ]
				},
				password : {
					rangelength : [ 5, 16 ],
					notEqualTo : $('#oldPassword')
				},
				password2 : {
					equalTo : "#password"
				}
			},
			messages : {
				name : {
					required : "이름을 입력해주세요.",
					maxlength : "이름은 최대 20자를 초과할 수 없습니다."
				},
				oldPassword : {
					required : "기존 비밀번호를 입력해주세요.",
					rangelength : "비밀번호는 5~16자까지 입력 가능합니다."
				},
				password : {
					rangelength : "비밀번호는 5~16자까지 입력 가능합니다.",
					notEqualTo : "기존 비밀번호와 동일한 비밀번호는 사용하실 수 없습니다."
				},
				password2 : {
					equalTo : "입력하신 비밀번호가 일치하지 않습니다."
				}
			},
			errorElement : "div",
			errorPlacement : function(error, element) {
				error.insertBefore(element.parent());
				error.css({
					"margin" : "0 0 0 0px",
					"color" : "red",
					"text-align" : "right"
				});
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
		
		$('input').on('focus', function(e) {
		    $('#alertMsg').html('');
		});	
		
		$('#oldPassword').on('focus', function(e) {
		    $('#alertMsg2').html('');
		});	
	})

/* 	function modalPopup(element){

	    var url = $(element).data('src');
	    var target = $(element).data('target');
	    $(window.parent.document).find('.modal-backdrop').remove();
	    $(window.parent.document).find(target).removeData('bs.modal');
	    $(window.parent.document).find(target).modal({remote: url });		
	} */

	// http://blog.naver.com/rlarhdn66/220337059508
	function memberModify() {
		jQuery.ajax({
			url: root+'/member',
			type : 'post',
			data : jQuery('#modifyInfoForm').serialize(),
			dataType : 'json',
			success : memberModifyResult,
			beforeSend : function(){
				$('#alertMsg').css({
					"text-align" : "center"
				}).html(loadingImgStr);
				$('.content-body').hide();
			},
			error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
		});
	}

	function memberModifyResult(data) {
		$('#alertMsg').html('');
		var result = data.result;
		if( result == 'invalidPassword'){
			$('.content-body').show();
			$('#alertMsg2').css({
				"color" : "red",
				"text-align" : "right"
			}).html('잘못된 비밀번호입니다.');
			return;
		}else if( result == 'modifyInfoSuccess'){
			$('.content-body').show();
			jQuery('#name').val(data.memberInfo.name);
			jQuery('#oldPassword').val('');
			$('#alertMsg').html('<br><h4>회원 정보가 정상적으로 수정되었습니다.</h4><br>');
		}else{
			$('.content-body').show();
			$('#alertMsg').css({
				"color" : "red",
			}).html('<br><b>회원 정보 수정 중 오류가 발생했습니다.</b><br>');
		}

	}

	
	
</script>
</head>
<body>

	<!-- 회원가입 -->
	<div class="member-style">
		<div class="register">
			<div class="register-content  hvr-float-shadow">
				<div class="register-content-head">
					<img src="${root }/assets/img/member/top-note.png" alt="" />
					<h2>My Information</h2>
					<labele>
					</lable>
				</div>
				
				<form id="modifyInfoForm" name="modifyInfoForm" method="POST" action=""
					onSubmit="return false;">
				<div id="alertMsg"></div>	
				<div class="content-body">
					<!-- Hidden Input Start-->
					<input type="hidden" id="act" name="act" value="modifyInfo">
					<input type="hidden" id="id" name="id" value="${memberInfo.id }">
					<!-- Hidden Input End-->
					<ul>
						<li>
						<input type="text" class="text" id="blockedId" name="blockedId"
							placeholder="E-MAIL" value="${memberInfo.id }" disabled><div class=" icon2 mail"></div>
						</li>

						<li><input type="text" class="text" id="name" name="name"
							placeholder="NAME" value="${memberInfo.name }"><div class=" icon2 user2"></div>
						</li>
						<div id="alertMsg2"></div>
						<li><input type="password" id="oldPassword" name="oldPassword"
							placeholder="PASSWORD"><div class=" icon2 lock2"></div>
						</li>
						<li><input type="password" id="password" name="password"
							placeholder="PASSWORD"><div class=" icon2 lock2"></div>
						</li>
						<li><input type="password" id="password2" name="password2"
							placeholder="CONFIRM PASSWORD"><div 
							class=" icon2 lock2"></div>
						</li>
					</ul>
					<div class="submit two">
						<input type="submit" value="SIGN UP">
					</div>
				</div>	
					<div class="hidden-content">
						<div class="success">
							<h4><b><span class="userName"></span> </b>님<br></h4>
							<h5>가입 요청 절차가 정상적으로 완료되었습니다.<br></h5>
							<h5>가입 시 사용하신 이메일 주소로 <b>가입 승인</b> 메일이 발송되었습니다.</h5>	
							<h5>이메일 인증 절차를 완료하신  후 로그인해주세요.</h5><br>	
						</div>
						<h4>지금 <a class="modalButton" data-src="${root}/member?act=moveLogin" data-target="#memberModal">로그인</a></h4>
					</div>
				</form>
						
				
			</div>
		</div>
	</div>
</body>
</html>