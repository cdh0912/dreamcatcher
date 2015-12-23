package com.dreamcatcher.common.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.common.model.service.CommonServiceImpl;
import com.dreamcatcher.site.model.LocationDto;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;

public class CommonLocationCategoryAction implements Action{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int page = NumberCheck.nullToOne(request.getParameter("page"));
		String categoryMode = StringCheck.nullToBlank(request.getParameter("searchMode"));
		String nation_code = StringCheck.nullToBlank(request.getParameter("nation_code"));
		String searchWord = StringCheck.nullToBlank(request.getParameter("searchWord"));
		String searchMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchMode")));
		String viewMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("viewMode")));

		Map searchMap = new HashMap();
		searchMap.put("page", page);
		searchMap.put("viewMode", viewMode);
		searchMap.put("nation_code", nation_code);
		searchMap.put("searchMode", searchMode);
		searchMap.put("searchWord", searchWord);
		searchMap.put("categoryMode", categoryMode);

		
		//System.out.println("categoryMode=" +categoryMode+"nation_code="+nation_code+"searchWord  = "+searchWord);
		List<LocationDto> locationList = CommonServiceImpl.getInstance().getLocationListWithPaging(searchMap);
		String jsonString = "";
		if(locationList != null){
			Gson gson = new Gson();
			jsonString = gson.toJson(locationList);
		}
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR); 
		response.getWriter().write(jsonString);
		return null;
	}
	
}
