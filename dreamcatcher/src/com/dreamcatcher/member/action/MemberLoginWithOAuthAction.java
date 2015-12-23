package com.dreamcatcher.member.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;
import com.google.gson.Gson;

public class MemberLoginWithOAuthAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String name = StringCheck.nullToBlank(request.getParameter("name"));
		String loginMode = StringCheck.nullToBlank(request.getParameter("loginMode"));
		int m_level = 0;
		int m_state = 1;
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		memberDto.setName(name);
		memberDto.setM_level(m_level);
		memberDto.setM_state(m_state);
		
		HttpSession session = request.getSession();
		session.setAttribute("memberInfo", memberDto);
		session.setAttribute("loginMode", loginMode);
			
		JSONObject jSONObject = new JSONObject();
		jSONObject.put("result", "loginWithOAuthSuccess");
		jSONObject.put("memberName", name);
		jSONObject.put("memberId", id);
		jSONObject.put("loginMode", loginMode);
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONObject.toJSONString());
		
		return null;
	}

}
