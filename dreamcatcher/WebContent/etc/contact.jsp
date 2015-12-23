<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<script type="text/javascript">
var loadingImgStr = '<div><img src="${root }/assets/img/loading.gif"></div>';
jQuery(function() {
	
	// 유효성 검사
		jQuery("#contactForm").validate({
        
        submitHandler: sendContactMail,

                rules: {
    				id : {
    					required : true,
    					email : true
    				},
    				name : {
    					required : true
    				},
    				subject : {
    					required : true
    				},
                    message: {
                        required: true,
                        maxlength: 500
                    }
                },
                messages: {
    				id : {
    					required : "이메일 주소를 입력해주세요.",
    					email : "올바른 형식의 이메일 주소를 입력해주세요."
    				},
    				name : {
    					required : "이름을 입력해주세요."
    				},
    				subject : {
    					required : "제목을 입력해주세요."
    				},
    				message: {
                        required: "내용을 입력해주세요.",
                        maxlength: "내용은 500자를 초과할 수 없습니다."
                    }
                },
                errorElement: "div",
                errorPlacement: function(error, element) {
                    error.insertBefore(element.parent());
                    error.css({"margin": "0 0 0 0px", "color":"red", "text-align":"left"});
                }

            });

	
						
});
function sendContactMail(){
	jQuery.ajax({
		url: root+'/member',
		type : 'post',
		data : $('#contactForm').serialize(),
		dataType : 'json',
		success : sendContactMailResult,
		beforeSend : function(){
			$('#alertMsg').css({
				"text-align" : "center"
			}).html(loadingImgStr);
			$('.content-body').hide();
		},
		error : function(){alert('회원 가입 중 오류가 발생했습니다.\n나중에 다시 시도해주세요.');}
	});
}

function sendContactMailResult(data){
	$('#alertMsg').html('');
	$('.content-body').show();
	var result = data.result;
	if( result == 'sendContactMailSuccess'){
		alert('메일이 성공적으로 발송되었습니다.');
		return;
	}else{
		alert('오류가 발생했습니다.\n나중에 다시 시도해주세요.');
	}
}

</script>
        <!-- Page Title -->
        <div class="page-title-container">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 wow fadeIn">
                        <i class="fa fa-envelope"></i>
                        <h1>문의사항 /</h1>
                        <p>저희에게 여러분의 이야기를 들려주세요!</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Contact Us -->
        <div class="contact-us-container">
        	<div class="container">
	            <div class="row">
	                <div class="col-sm-7 contact-form wow fadeInLeft">
	                    <p>
	                    	서비스 이용 중 문제가 발생했거나 건의할 사항이 있으신가요? 저희에게 이야기를 들려주세요.<br>
	                    	드림캐처는 늘 여러분에게 귀를 기울일 준비가 되어있습니다:)
	                    </p>
	                    <form id="contactForm" name="contactForm" role="form" action="" method="post">
	                    <input type="hidden" id="act" name="act" value="sendContactMail">
	                    <div id="alertMsg"></div>
	                    <div class="content-body">
	                    	<div class="form-group">
	                    		<label for="contact-name">Name</label>
	                        	<input type="text" id="name" name="name" placeholder="Enter your name..." class="contact-name" value="${memberInfo.name}">
	                        </div>
	                    	<div class="form-group">
	                    		<label for="contact-email">Email</label>
	                        	<input type="text" id="id" name="id" placeholder="Enter your email..." class="contact-email"  value="${memberInfo.id}">
	                        </div>
	                        <div class="form-group">
	                        	<label for="contact-subject">Subject</label>
	                        	<input type="text" id="subject" name="subject" placeholder="Your subject..." class="contact-subject">
	                        </div>
	                        <div class="form-group">
	                        	<label for="contact-message">Message</label>
	                        	<textarea id="message" name="message" placeholder="Your message..." class="contact-message"></textarea>
	                        </div>
	                        <button type="submit" class="btn">Send</button>
	 					</div>                       
	                    </form>
	                </div>
	                <div class="col-sm-5 contact-address wow fadeInUp">
	                    <h3>We Are Here</h3>
	                    <div class="map"></div>
	                    <h3>Address</h3>
	                    <p>서울시 구로구 디지털로 34길 43<br> 사이언스벨리 1차 4층</p>
	                    <p>Phone: 02 869 8301</p>
	                </div>
	            </div>
	        </div>
        </div>