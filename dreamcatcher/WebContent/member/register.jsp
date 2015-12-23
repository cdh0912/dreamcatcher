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

		// 회원가입폼 유효성 검사를 위한 jQuery 함수
		$("#registerForm").validate({

			submitHandler : memberJoin,

			rules : {
				id : {
					required : true,
					email : true,
					maxlength : 40
				},
				name : {
					required : true,
					maxlength : 20
				},
				password : {
					required : true,
					rangelength : [ 5, 16 ]
				},
				password2 : {
					required : true,
					equalTo : "#password"
				},
				agree : "required"
			},
			messages : {
				id : {
					required : "이메일 주소를 입력해주세요.",
					email : "올바른 형식의 이메일 주소를 입력해주세요.",
					maxlength : "이메일 주소는 최대 40자를 초과할 수 없습니다."
				},
				name : {
					required : "이름을 입력해주세요.",
					maxlength : "이름은 최대 20자를 초과할 수 없습니다."
				},
				password : {
					required : "비밀번호를 입력해주세요.",
					rangelength : "비밀번호는 5~16자까지 입력 가능합니다."
				},
				password2 : {
					required : "비밀번호를 다시 한번 입력해주세요.",
					equalTo : "입력하신 비밀번호가 일치하지 않습니다."
				},
				agree : "회원약관에 동의해주세요."
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
		
		$('#id').on('focus', function(e) {
		    $('#alertMsg').html('');
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
	function memberJoin() {
		jQuery.ajax({
			url: root+'/member',
			type : 'post',
			data : $('#registerForm').serialize(),
			dataType : 'json',
			success : memberJoinResult,
			beforeSend : function(){
				$('#alertMsg').css({
					"text-align" : "center"
				}).html(loadingImgStr);
				$('.content-body').hide();
			},
			error : function(){alert('회원 가입 중 오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
		});
	}

	function memberJoinResult(data) {
		$('#alertMsg').html('');
		var result = data.result;
		if( result == 'idExist'){
			$('.content-body').show();
			$('#alertMsg').css({
				"color" : "red",
				"text-align" : "right"
			}).html('이미 사용중인 이메일입니다.');
			return;
		}else if( result == 'joinSuccess'){
			jQuery('.userName').html(jQuery('#name').val());
			jQuery('.hidden-content').show();
		}else{
			$('.content-body').show();
			$('#alertMsg').css({
				"color" : "red",
			}).html('<br><b>회원 가입 중 오류가 발생했습니다.</b><br>');
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
					<h2>register</h2>
					<labele>
					</lable>
				</div>
				
				<form id="registerForm" name="registerForm" method="POST" action=""
					onSubmit="return false;">
				<div id="alertMsg"></div>	
				<div class="content-body">
					<!-- Hidden Input Start-->
					<input type="hidden" id="act" name="act" value="join">
					<!-- Hidden Input End-->
					<ul>
						<li>
						<input type="text" class="text" id="id" name="id"
							placeholder="E-MAIL"><a href="#" class=" icon2 mail"></a>
						</li>

						<li><input type="text" class="text" id="name" name="name"
							placeholder="NAME"><a href="#" class=" icon2 user2"></a>
						</li>
						<li><input type="password" id="password" name="password"
							placeholder="PASSWORD"><a href="#" class=" icon2 lock2"></a>
						</li>
						<li><input type="password" id="password2" name="password2"
							placeholder="CONFIRM PASSWORD"><a href="#"
							class=" icon2 lock2"></a>
						</li>
					</ul>
					<div class="p-container">
						<label class="checkbox two"><input type="checkbox"
							id="agree" name="agree" checked><i></i>I agree to the <a
							href="#">Terms of Servicee</a></label>
					</div>
					<div class="submit two">
						<input type="submit" value="SIGN UP">
					</div>
					<h5>
		<%-- 				이미 회원이신가요 ? <a class="modalButton" data-src="${root}/member?act=moveLogin" data-target="#memberModal"  onClick="javascript:modalPopup(this)">로그인</a> --%>
						
						이미 회원이신가요 ? <a class="modalButton" data-src="${root}/member?act=moveLogin" data-target="#memberModal">로그인</a>
					</h5>
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