package com.dreamcatcher.member.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.model.service.AdminServiceImpl;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.*;

public class MemberResetPasswordAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String newPassword = StringCheck.nullToBlank(request.getParameter("password"));
		
		Map validMap = new HashMap();
		validMap.put("id",id);
		validMap.put("password",newPassword);
		
		int cnt = MemberServiceImpl.getInstance().passwordCheck(validMap);
		
		JSONObject jSONObject = new JSONObject();
		// 기존 패스워드와 일치
		if(cnt == 1){
			jSONObject.put("result", "passwordExist");
		// 사용 가능한 패스워드
		}else if(cnt == 0){
			MemberDto memberDto = new MemberDto();
			memberDto.setId(id);
			memberDto.setPassword(newPassword);
			memberDto.setM_state(1); // 1 : 정상 회원 상태
			int cnt2 = MemberServiceImpl.getInstance().resetPassword(memberDto);
					
			if( cnt2 == 1 ){
				jSONObject.put("result", "resetPasswordSuccess");
			}else{
				jSONObject.put("result", "resetPasswordFailed");
			}
		}else{
			jSONObject.put("result", "passwordCheckFailed");
		}
		String jSONStr = jSONObject.toJSONString();
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONStr);

		return null;
	}

}
