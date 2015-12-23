package com.dreamcatcher.admin.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.model.service.AdminServiceImpl;
import com.dreamcatcher.factory.AdminActionFactory;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.util.*;

public class AdminSendAuthCodeAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String name = StringCheck.nullToBlank(request.getParameter("name"));
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		memberDto.setName(name);
		
		int cnt = AdminServiceImpl.getInstance().sendAuthCode(memberDto);
		
		JSONObject jsonObject = new JSONObject();
		if( cnt == 1 ){
			jsonObject.put("result", "sendAuthCodeSeccess");
		}else{
			jsonObject.put("result", "sendAuthFailed");
		}
		
		String jSONString = jsonObject.toJSONString();
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
		response.getWriter().write(jSONString);
		return null;
	}

}
