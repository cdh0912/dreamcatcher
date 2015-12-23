package com.dreamcatcher.site.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.common.model.service.CommonServiceImpl;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.site.model.*;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
public class SiteArticleListAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		int page = NumberCheck.nullToOne(request.getParameter("page"));
		String viewMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("viewMode")));
		String searchMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchMode")));
		String searchWord = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchWord")));
		String categoryMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("categoryMode")));

		

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

		if( "myView".equals(viewMode)){
			HttpSession session = request.getSession();
			MemberDto memberDto = (MemberDto)session.getAttribute("memberInfo");
			String id = memberDto.getId();
			searchMap.put("id", id);
		}
		
		
		List<NationDto> nationList = CommonServiceImpl.getInstance().getNationListWithPaging(searchMap);
		if(nationList != null)
			request.setAttribute("nationList", nationList);
			
		List<SiteDetailDto> siteArticleList = SiteServiceImpl.getInstance().siteArticleList(searchMap);
		if(siteArticleList != null)
			request.setAttribute("siteArticleList", siteArticleList);
		
		boolean isLastPage = SiteServiceImpl.getInstance().isLastPage(searchMap);
		
		request.setAttribute("isLastPage", isLastPage);
		
		return "/site/siteList.tiles";
	}

}
