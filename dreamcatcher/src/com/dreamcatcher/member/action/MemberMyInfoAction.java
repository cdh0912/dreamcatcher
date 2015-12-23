package com.dreamcatcher.member.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.route.model.RouteDto;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.site.model.SiteDetailDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class MemberMyInfoAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		int sitePage = NumberCheck.nullToOne(request.getParameter("sitePage"));		
		int routePage = NumberCheck.nullToOne(request.getParameter("routePage"));

		System.out.println("sitePage = "+sitePage + " & routePage = "+routePage);
		Map siteSearchMap = new HashMap();
		siteSearchMap.put("page", sitePage);
		siteSearchMap.put("viewMode", "myView");
		siteSearchMap.put("searchMode", "date");
		
		
		Map routeSearchMap = new HashMap();
		routeSearchMap.put("page", routePage);
		routeSearchMap.put("viewMode", "myView");
		routeSearchMap.put("searchMode", "date");

		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto)session.getAttribute("memberInfo");

		if(memberDto == null){
			response.getWriter().write("<javascipt>alert('로그인 후 서비스를 이용해주세요.');\n");
			response.getWriter().write("document.location.href='/dreamcatcher/';</javascript>");
			return null;
		}
		
		String id = memberDto.getId();

		siteSearchMap.put("id", id);
		routeSearchMap.put("id", id);
		
		Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject();
		
		List<SiteDetailDto> siteArticleList = SiteServiceImpl.getInstance().siteArticleList(siteSearchMap);
		JsonElement jsonSiteArticleList = gson.toJsonTree(siteArticleList);
		jsonObject.put("siteArticleList", jsonSiteArticleList);
		
		siteSearchMap.put("jsMethodName", "mySiteArticle");
		siteSearchMap.put("listSize", ArticleListSizeConstant.SITE_MY_LIST_SIZE);
		siteSearchMap.put("pageSize", ArticleListSizeConstant.SITE_MY_PAGE_SIZE);
		PageNavigator siteNavigator = SiteServiceImpl.getInstance().makePageNavigator(siteSearchMap);
		siteNavigator.setRoot(request.getContextPath());
		siteNavigator.setNavigator();

		JsonElement jsonSiteArticleNavigator = gson.toJsonTree(siteNavigator);
		jsonObject.put("siteNavigator", jsonSiteArticleNavigator);
		
		jsonObject.put("sitePage", sitePage);
		
		List<RouteDto> routeArticleList = RouteServiceImpl.getInstance().routeArticleList(routeSearchMap);
		JsonElement jsonRouteArticleList = gson.toJsonTree(routeArticleList);
		jsonObject.put("routeArticleList", jsonRouteArticleList);
		
		routeSearchMap.put("jsMethodName", "myRouteArticle");
		routeSearchMap.put("listSize", ArticleListSizeConstant.ROUTE_MY_LIST_SIZE);
		routeSearchMap.put("pageSize", ArticleListSizeConstant.ROUTE_MY_PAGE_SIZE);
		PageNavigator routeNavigator = RouteServiceImpl.getInstance().makePageNavigator(routeSearchMap);
		routeNavigator.setRoot(request.getContextPath());
		routeNavigator.setNavigator();

		JsonElement jsonRouteArticleNavigator = gson.toJsonTree(routeNavigator);
		jsonObject.put("routeNavigator", jsonRouteArticleNavigator);
		
		jsonObject.put("routePage", routePage);
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
		response.getWriter().write(jsonObject.toString());;
		
		return null;
	}

}
