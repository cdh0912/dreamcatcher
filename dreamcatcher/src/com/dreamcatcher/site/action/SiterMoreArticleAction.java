package com.dreamcatcher.site.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.impl.CharConstant;
import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.common.model.service.CommonServiceImpl;
import com.dreamcatcher.site.model.*;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class SiterMoreArticleAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		int page = NumberCheck.nullToOne(request.getParameter("page"));
		String viewMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("viewMode")));
		String searchMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchMode")));
		String searchWord = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchWord")));
		String categoryMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("categoryMode")));
//		HttpSession session = request.getSession();
//		MemberDto memberDto = (MemberDto)session.getAttribute("memberInfo");	
//		int m_level = memberDto.getM_level();	// 0 : 일반회원 | 1 : 관리자	
//		boolean isAdmin = false;
//		if(m_level == 1)
//			isAdmin = true;
		
		Map searchMap = new HashMap();
		searchMap.put("page", page);
		searchMap.put("viewMode", viewMode);
		searchMap.put("searchMode", searchMode);
		searchMap.put("searchWord", searchWord);
		searchMap.put("categoryMode", categoryMode);
		//searchMap.put("isAdmin", isAdmin);
		
		JSONObject jsonObject = new JSONObject();
		
		
		List<NationDto> nationList = CommonServiceImpl.getInstance().getNationListWithPaging(searchMap);

		Gson gson = new Gson();
		JsonElement nationJsonElement = gson.toJsonTree(nationList);
		jsonObject.put("nationList", nationJsonElement);

			
		List<LocationDto> locationList = CommonServiceImpl.getInstance().getLocationListWithPaging(searchMap);

		JsonElement locationJsonElement = gson.toJsonTree(locationList);
		jsonObject.put("locationList", locationJsonElement);

		
		List<SiteDetailDto> siteArticleList = SiteServiceImpl.getInstance().siteArticleList(searchMap);

		JsonElement siteArticleJsonElement = gson.toJsonTree(siteArticleList);
		jsonObject.put("siteArticleList", siteArticleJsonElement);

		boolean isLastPage = SiteServiceImpl.getInstance().isLastPage(searchMap);
		jsonObject.put("isLastPage", isLastPage);
		
//		System.out.println(jsonObject.toJSONString());
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
		response.getWriter().write(jsonObject.toJSONString());
		
		return null;
	}

}
