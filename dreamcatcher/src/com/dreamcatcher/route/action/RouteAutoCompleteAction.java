package com.dreamcatcher.route.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;
import com.google.gson.Gson;

public class RouteAutoCompleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String keyword = StringCheck.nullToBlank(request.getParameter("term"));
		
		List<KeywordDto> keywordList = RouteServiceImpl.getInstance().autoComplete(keyword);

		String keywordString = "";
		
//		System.out.println("keyword = " + keyword);
		
		if(keywordList == null){
			
		}else{
			Gson gson = new Gson();		
			keywordString = gson.toJson(keywordList);	
		}
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
		response.getWriter().write(keywordString);
		return null;
	}

}
