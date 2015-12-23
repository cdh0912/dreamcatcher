package com.dreamcatcher.member.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;

public class MemberPasswordCheckAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String password = StringCheck.nullToBlank(request.getParameter("oldPassword"));
		
		Map validMap = new HashMap();
		validMap.put("id",id);
		validMap.put("password",password);
		
		int cnt = MemberServiceImpl.getInstance().passwordCheck(validMap);
		//System.out.println(id + " " + password + " " + cnt);
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
		// 기존 패스워드와 일치
		if(cnt == 1){
			response.getWriter().write("exist");
		// 사용 가능한 패스워드
		}else if(cnt == 0){
			response.getWriter().write("notExist");
		}
		return null;
	}

}
