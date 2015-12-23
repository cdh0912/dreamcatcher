package com.dreamcatcher.main.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.route.model.RouteDto;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;

public class MainRouteArticleSortAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int page = NumberCheck.nullToOne(request.getParameter("page"));
		String viewMode = StringCheck.nullToBlank(request.getParameter("viewMode"));
		String searchMode = StringCheck.nullToBlank(request.getParameter("searchMode"));
		String searchWord = StringCheck.nullToBlank(request.getParameter("searchWord"));;
		
		Map searchMap = new HashMap();
		searchMap.put("page", page);
		searchMap.put("viewMode", viewMode);
		searchMap.put("searchMode", searchMode);
		searchMap.put("searchWord", searchWord);
		
		//System.out.println(page+ " " + viewMode +" "+ searchMode +" " + searchWord);
		List<RouteDto> routeArticleList = RouteServiceImpl.getInstance().routeArticleList(searchMap);
		//System.out.println("size="+siteArticleList.size());
		if(routeArticleList != null){
			Gson gson = new Gson();
			String routeArticleString = gson.toJson(routeArticleList);
			response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
			response.getWriter().write(routeArticleString);
		}
		//System.out.println("siteArticleList Size = "+siteArticleList.size());
		return null;
	}

}
