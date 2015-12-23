package com.dreamcatcher.main.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.common.model.service.CommonServiceImpl;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.route.model.RouteDto;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.site.model.NationDto;
import com.dreamcatcher.site.model.SiteDetailDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;

public class MainViewAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Map searchMap = new HashMap();
		searchMap.put("page", 1);
		searchMap.put("viewMode", "mainView");
		searchMap.put("searchMode", "date");
		searchMap.put("searchWord", "");
			
		List<SiteDetailDto> siteArticleList = SiteServiceImpl.getInstance().siteArticleList(searchMap);
		request.setAttribute("siteArticleList", siteArticleList);
		
		List<RouteDto> routeArticleList = RouteServiceImpl.getInstance().routeArticleList(searchMap);
		request.setAttribute("routeArticleList", routeArticleList);
		
		
		searchMap.put("viewMode", "myView");
		searchMap.put("searchMode", "date");
		searchMap.put("id", CommonConstant.ADMIN_EMAIL);
		List<SiteDetailDto> siteAdminArticleList = SiteServiceImpl.getInstance().siteArticleList(searchMap);
		if(siteArticleList != null)
			request.setAttribute("siteAdminArticleList", siteAdminArticleList);
		//System.out.println("siteArticleList Size = "+siteArticleList.size());
		return "/main.tiles";
	}

}
