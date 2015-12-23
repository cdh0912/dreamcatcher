package com.dreamcatcher.main.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.site.model.SiteDetailDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;

public class MainSiteArticleSortAction implements Action {

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
		List<SiteDetailDto> siteArticleList = SiteServiceImpl.getInstance().siteArticleList(searchMap);
		//System.out.println("size="+siteArticleList.size());
		if(siteArticleList != null){
			Gson gson = new Gson();
			String siteArticleString = gson.toJson(siteArticleList);
			response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
			response.getWriter().write(siteArticleString);
		}
		//System.out.println("siteArticleList Size = "+siteArticleList.size());
		return null;
	}

}
