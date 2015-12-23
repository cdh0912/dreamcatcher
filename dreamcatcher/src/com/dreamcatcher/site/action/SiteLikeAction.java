package com.dreamcatcher.site.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.site.model.SiteDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.NumberCheck;

public class SiteLikeAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		int site_id = NumberCheck.nullToZero(request.getParameter("site_id"));
//		System.out.println("im in");
		MemberDto member = (MemberDto) request.getSession().getAttribute("memberInfo");

		String userId = (member != null) ? member.getId() : "anonymous";

		SiteServiceImpl.getInstance().likeSite(site_id, userId);

		int likeCheck = SiteServiceImpl.getInstance().likeCheck(site_id, userId);
		
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + likeCheck);
		request.setAttribute("likeCheck", likeCheck);

		int likeCnt = SiteServiceImpl.getInstance().siteView(site_id).getRecommend();
//		System.out.println(likeCnt);
		
		JSONObject jSONObject = new JSONObject();
		jSONObject.put("likeCnt", likeCnt);

		String jSONString = jSONObject.toJSONString();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.getWriter().write(jSONString);

		return null;
	}

}
