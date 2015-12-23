package com.dreamcatcher.plan.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;

public class PlanAutoCompleteInMapAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String keyword = StringCheck.nullToBlank(request.getParameter("term"));
		
		List<KeywordDto> keywordList = PlanServiceImpl.getInstance().autoCompleteInMap(keyword);

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
