package com.dreamcatcher.admin.action;

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

public class AdminSiteStateChangeAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		int site_id = NumberCheck.nullToZero(request.getParameter("site_id"));
		int approval = NumberCheck.nullToOne(request.getParameter("approval"));
		
		Map siteStateMap = new HashMap();
		siteStateMap.put("site_id",site_id);
		siteStateMap.put("approval", approval);
		
		int cnt = AdminServiceImpl.getInstance().siteStateChange(siteStateMap);
		
		JSONObject jSONObject = new JSONObject();
		
		
		if( cnt == 1){
			jSONObject.put("result", "siteStateChangeSuccess");
		}else{
			jSONObject.put("result", "siteStateChangeFailed");			
		}		

		
		String jSONStr = jSONObject.toJSONString();
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONStr);

		return null;
	}

}
