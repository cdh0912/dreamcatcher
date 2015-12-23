<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@include file="/common/member.jsp" %> 

<script type="text/javascript">

var loadingImgStr = '<div><img src="${root }/assets/img/loading.gif"></div>';
jQuery(function() {

	// 유효성 검사
		jQuery("#loginForm").validate({
        
        submitHandler: memberLogin,

                rules: {
                    id: {
                        required: true,
                        email: true,
                        maxlength: 40
                    },
                    password: {
                        required: true,
                        maxlength: 16
                    }
                },
                messages: {
                	id: {
                        required: "이메일 주소를 입력해주세요.",
                        email: "올바른 형식의 이메일 주소를 입력해주세요.",
                        maxlength: "이메일 주소는 최대 40자를 초과할 수 없습니다."
                    },
                    password: {
                        required: "비밀번호를 입력해주세요.",
                        maxlength: "비밀번호는 최대 16자를 초과할 수 없습니다."
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
	
	$('#id').on('focus', function(e) {
	    $('#alertMsg').html('');
	});
	
	$('#password').on('focus', function(e) {
	    $('#alertMsg').html('');
	});
	

	
						
});

function memberLogin() {
	$('#alertMsg').html('');
	jQuery.ajax({
		url: root+'/member',
		type : 'post',
		data : jQuery('#loginForm').serialize(),
		dataType : 'json',
		success : loginSuccess,
		error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
	});
}	
var id = '';
var name = '';
function loginSuccess(data){
	var result = data.result;
	if(result == 'loginSuccess'){
		$('.content-body').hide();
		// memberState = 0 : 가입대기 상태 | 1 : 가입완료 상태 | 2: 비밀번호 리셋 상태
		var memberState = data.memberState;


		if( memberState == 0 ){
			id = data.memberInfo.id;
			name = data.memberInfo.name;
			$('.userName').html(name);
			$('.send-auth').show();
		}else if(memberState == 1){
			location.reload();
			//$(window.parent.document).find('#memberModal').modal('hide');
		}else if(memberState == 2){
			id = data.memberInfo.id;
			//alert($('#resetPassButton').data('src'));
			var src = $('#resetPassButton').data('src') + '&id=' + id + "&tempPassword="+$('#password').val();
			//alert(src);
			$('#resetPassButton').data('src', src);
			//alert($('#resetPassButton').data('src'));
			name = data.memberInfo.name;
			$('.userName').html(name);
			$('.reset-pass').show();
		}
		
	}else{
		$('#alertMsg').css({
			"color" : "red",
			"text-align" : "right"
		}).html('아이디 또는 비밀번호를 확인하신 후 다시 로그인해 주세요.');
	}
}

function sendAuthCode(){
	jQuery.ajax({
		url: root+'/admin',
		type : 'post',
		data : {'act':'sendAuthCode','id': id, 'name':name},
		dataType : 'json',
		success : sendAuthCodeSuccess,
		beforeSend : function(){
			$('#alertMsg').css({
				"text-align" : "center"
			}).html(loadingImgStr);
			$('.content-body').hide();
			$('.hidden-content').hide();

		},
		error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
	});
}

function sendAuthCodeSuccess(data){
	var result = data.result;
	$('.content-body').show();
	if(result == 'sendAuthCodeSuccess'){
		$('#alertMsg').css({
			"color" : "#9A9A9A"
		}).html('<h3>인증메일 발송이 완료되었습니다.</h3><br>');
	}else{
		$('#alertMsg').css({
			"color" : "#9A9A9A"
		}).html('<h3>인증메일 발송에 실패했습니다.</h3><br>');		
	}	
}

function loginWithOAuthSuccess(data){
	var result = data.result;
	if(result == 'loginWithOAuthSuccess'){
		alert(data.memberName+'('+data.memberId+') 님!\n환영합니다!\n'+data.loginMode + ' 계정으로 로그인하셨습니다.');
		location.reload();	
	}else{
		alert('로그인 도중 오류가 발생하였습니다.');
	}
}

function loginCallback(authResult) {
	  if (authResult['access_token']) {


		 // 승인 성공
	    // 사용자가 승인되었으므로 로그인 버튼 숨김. 예:
	    //document.getElementById('signinButton').setAttribute('style', 'display: none');
		  gapi.client.load('plus', 'v1', function() {
				var request = gapi.client.plus.people.get({
					'userId' : 'me'
				});
				request.execute(function(response){
					var displayName = response.displayName;
					var id = response.id;
					var emails = response.emails;
					var email = '';
					var m_level = 0;
					var m_state = 1;
					for(var i=0; i<emails.length; i++){
						if(emails[i].primary){
							email = emails[i].value;
						}
						if(i == emails.length - 1 && email == ''){
							email = emails[0].value;
						}
					}
					//alert("닉네임 : "+displayName+"\n아이디 : "+id+"\n이메일 : "+email);
					if( displayName != '' && displayName != 'undefined'
							&&  email != '' && email != 'undefined'){
						jQuery.ajax({
							url: root+'/member',
							type : 'post',
							data : {'act':'loginWithOAuth','id':email,'name':displayName,'loginMode':'Google'},
							dataType : 'json',
							success : loginWithOAuthSuccess,
							error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
						});
					}
				});
			});

	  } else if (authResult['error']) {
		//alert('오류 발생');
	    // 오류가 발생했습니다.
	    // 가능한 오류 코드:
	    //   "access_denied" - 사용자가 앱에 대한 액세스 거부
	    //   "immediate_failed" - 사용자가 자동으로 로그인할 수 없음
	    // console.log('오류 발생: ' + authResult['error']);
	  }
}

function facebookLogin(){
    //페이스북 로그인 버튼을 눌렀을 때의 루틴.  
	  FB.login(function(response) {  
	    var fbname;  
	    var accessToken = response.authResponse.accessToken;  
	    FB.api('/me', function(user) {  
	      fbname = user.name;
	      jQuery.ajax({
				url: root+'/member',
				type : 'post',
				data : {'act':'loginWithOAuth','id':user.email,'name':fbname,'loginMode':'Facebook',"accessToken":accessToken},
				dataType : 'json',
				success : loginWithOAuthSuccess,
				error : function(){alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
			});
	     
	    });   
	  }, {scope: "user_about_me,email"});  

}
window.fbAsyncInit = function() {  
    FB.init({appId: "466651296834694", status: true, cookie: true,xfbml: true});      
};  


	(function(d) {
		var js, id = "466651296834694", ref = d.getElementsByTagName("script")[0];
		if (d.getElementById(id)) {
			return;
		}
		js = d.createElement("script");
		js.id = id;
		js.async = true;
		js.src = "//connect.facebook.net/en_US/all.js";
		ref.parentNode.insertBefore(js, ref);
	}(document));

	(function() {
		var po = document.createElement('script');
		po.type = 'text/javascript';
		po.async = true;
		po.src = 'https://apis.google.com/js/client:plusone.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(po, s);
	})();
</script>

<c:forEach var="loginCookie" items="<%=request.getCookies()%>">
	<c:if test='${loginCookie.name=="loginId"}'>
        <c:set var="loginId" value="${loginCookie.value}"/>
        <c:set var="check" value="checked"/>
	</c:if>
</c:forEach>
	
<!--/start-login-->
<div class="member-style">
	<div class="login">
		<div class="login-content  hvr-float-shadow">
			<div class="login-content-head">
					<img src="${root }/assets/img/member/top-lock.png" alt=""/>
					<h1>LOGIN</h1>
					<span></span>
			</div>
			
			<form id="loginForm" name="loginForm" method="POST" action="" onsubmit="return false;">
			<div id="alertMsg"></div>
			<div class="content-body">
					<!-- Hidden Input Start-->
					<input type="hidden" id="act" name="act" value="login">
					<!-- Hidden Input End-->		
			<ul>	
				<li>
					<input type="text" class="text" id="id" name="id" value="${loginId}" placeholder="E-MAIL" ><a href="#" class=" icon user"></a>
				</li>
				<li>
					<input type="password" id="password" name="password" value="" placeholder="PASSWORD"><a href="#" class=" icon lock"></a>
				</li>
			</ul>	
				<div class="p-container">
						<label class="checkbox"><input type="checkbox" id="idSave" name="idSave" value="saveOk" ${check}><i></i>Remember Me</label>
						<h6>	
						<!-- <a class="modalButton" data-toggle="modal" data-src="${root }/member/forgotPass.jsp" data-target="#loginModal"> -->
						<a class="modalButton" data-src="${root }/member?act=moveForgotPass" data-target="#memberModal">
						비밀번호 찾기</a> </h6>
							<div class="clear"> </div>
				</div>
				<div class="submit">
						<input type="submit" value="SIGN IN" >
				</div>
				
					<h5>드림캐처와 함께 하길 원하세요 ?
					<a class="modalButton" data-src="${root }/member?act=moveRegister" data-target="#memberModal">
					<%-- <a href="${root }/member/register.jsp"> --%>
					지금 가입하세요!
					 </a></h5>
					 
				<div class="social-icons">
					<p>다음 계정으로도 로그인 하실 수 있습니다.</p>
						<ul class="soc_icons2">
			
							<li class="pic"><a href="javascript:facebookLogin();"><i class="icon_4"></i></a></li>
							<li class="pic"><a href="${root }/member?act=loginWithTwitter"><i class="icon_5"></i></a></li>
							<li class="pic"><span id="signinButton"><span class="g-signin"
								data-approvalprompt="force"
							    data-callback="loginCallback"
							    data-clientid="315114240422-cqvn8r99edk8vlg6p4c0j225q6vc7kvf.apps.googleusercontent.com"
							    data-cookiepolicy="single_host_origin"
							    data-requestvisibleactions="http://schemas.google.com/AddActivity"
							    data-scope="https://www.googleapis.com/auth/plus.login
							    		https://www.googleapis.com/auth/userinfo.email">
							    
							    <a><i class="icon_6"></i></a>
							 </span></span>   
							 </li>
								<div class="clear"> </div>
						</ul>
				</div>
			</div>
					<div class="hidden-content send-auth">
						<div class="fail">
							<h4><b><span class="userName"></span> </b>님<br></h4>
							<h5>아직 이메일 인증을 하지 않으셨군요.<br></h5>
							<h5>가입 시 사용하신 이메일 주소로 <b>가입 승인</b> 메일이 발송되었습니다.</h5>	
							<h5>이메일 인증 절차를 완료하신  후 다시 로그인해주세요.</h5><br>					
						</div>
						<h4>인증 메일 <a onClick="javascript:sendAuthCode();"><b>다시 받기</b></a></h4>
					</div>		
					<div class="hidden-content reset-pass">
						<div class="fail">
							<h4><b><span class="userName"></span> </b>님</h4>
							<h5>임시 비밀번호로 로그인하셨습니다.<br><br>
							비밀번호를 변경하시면 서비스를 이용하실 수 있습니다.</h5><br>	
						</div>
						<h4>비밀번호 <a id="resetPassButton" class="modalButton" data-src="${root}/member?act=moveResetPass" data-target="#memberModal"><b>변경</b></a></h4>				
					</div>
			
			</form>
				
		</div>
	</div>
</div>
	<!--//end-copyright-->

</html>