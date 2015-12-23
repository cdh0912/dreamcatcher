package com.dreamcatcher.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.util.CharacterConstant;

public class MemberLogoutAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.removeAttribute("memberInfo");
		session.invalidate();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result","logoutSuccess");
		String jSONStr = jsonObject.toJSONString();
		//System.out.println(jSONStr);
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONStr);
		return null;
	}

}
