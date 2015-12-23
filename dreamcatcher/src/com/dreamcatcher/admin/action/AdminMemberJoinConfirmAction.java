package com.dreamcatcher.admin.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.model.service.AdminServiceImpl;
import com.dreamcatcher.util.*;

public class AdminMemberJoinConfirmAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String authCode = StringCheck.nullToBlank(request.getParameter("authCode"));
		Map memberStateMap = new HashMap();
		memberStateMap.put("id", id);
		memberStateMap.put("m_state", 1);
		memberStateMap.put("authCode", authCode);
		response.setContentType("text/html; charset="+CharacterConstant.DEFAULT_CHAR);
		int cnt = AdminServiceImpl.getInstance().verifyAuthCode(memberStateMap);
		if( cnt == 1){
			int cnt2 = AdminServiceImpl.getInstance().memberStateChange(memberStateMap);
			if( cnt2 == 1){	
				
				response.getWriter().write("<script>alert('이메일 인증에 성공했습니다.\\n로그인 후 서비스를 이용해주세요.');");
			}else{
				response.getWriter().write("<script>alert('이메일 인증 도중 알 수 없는 오류가 발생했습니다.\\n로그인 후 인증 메일을 다시 요청해주세요.');");
			}
		}else{
			response.getWriter().write("<script>alert('유효하지 않은 인증번호입니다.\\n로그인 후 인증 메일을 다시 요청해주세요.');");
		}
		response.getWriter().write("document.location.href='"+CommonConstant.SITE_DOMAIN+"'</script>");
		
		return null;
	}

}
