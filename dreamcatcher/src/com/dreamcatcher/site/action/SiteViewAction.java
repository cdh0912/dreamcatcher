package com.dreamcatcher.site.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.site.model.SiteDetailDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.NumberCheck;

public class SiteViewAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int site_id = NumberCheck.nullToOne(request.getParameter("site_id"));
		
		SiteDetailDto siteDetailDto = SiteServiceImpl.getInstance().siteView(site_id);
		
		request.setAttribute("siteInfo", siteDetailDto);
		
		MemberDto mdto = (MemberDto) request.getSession().getAttribute("memberInfo");
		String userId = (mdto != null) ? mdto.getId() : "anonymous";
		int likeCheck = SiteServiceImpl.getInstance().likeCheck(site_id, userId);
		request.setAttribute("likeCheck", likeCheck);
		
		return "/site/siteView.tiles";
	}

}
