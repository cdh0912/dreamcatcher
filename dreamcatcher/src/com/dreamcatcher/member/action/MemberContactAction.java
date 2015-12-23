package com.dreamcatcher.member.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;

public class MemberContactAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String name = StringCheck.nullToBlank(request.getParameter("name"));
		String subject = StringCheck.nullToBlank(request.getParameter("subject"));
		String message = StringCheck.nullToBlank(request.getParameter("message"));
		
		System.out.println(message);
		JSONObject jSONObject = new JSONObject();
	
		Map<String,String> mailMap = new HashMap<String, String>();
		
		mailMap.put("id", id);
		mailMap.put("name", name);
		mailMap.put("subject", subject);
		mailMap.put("message", message);
		
		
		int cnt = MemberServiceImpl.getInstance().sendContactMail(mailMap);
		if( cnt == 1 ){
			jSONObject.put("result", "sendContactMailSuccess");
		}else{
			jSONObject.put("result", "sendContactMailFailed");
		}

		String jSONStr = jSONObject.toJSONString();
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONStr);

		
		return null;
	}

}
