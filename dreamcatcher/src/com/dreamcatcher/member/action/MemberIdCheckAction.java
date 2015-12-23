package com.dreamcatcher.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;

public class MemberIdCheckAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		int cnt = MemberServiceImpl.getInstance().idCheck(id);
		
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
		// 존재하지 않는 아이디
		if( cnt == 0 ){
			response.getWriter().write("notExist");
		// 존재하는 아이디
		}else if (cnt == 1){
			response.getWriter().write("exist");
		}
		
		return null;
	}

}
