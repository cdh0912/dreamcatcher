<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dreamcatcher.util.SessionCheck"%>
<%
	boolean flag = SessionCheck.loginCheck(request, "memberInfo");
	if(!flag){
%>

<script type="text/javascript">
//alert('Your session has expired. Please log-in again.');
alert('세션이 만료되었습니다. 다시 로그인 해주십시오.');
document.location.href="/dreamcatcher/";
document.body.innerHTML = '';
</script>
<%
	}
%>
<script type="text/javascript">
function returnMainPage(){
	document.location.href="/dreamcatcher/";
	document.body.innerHTML = '';
}
</script>