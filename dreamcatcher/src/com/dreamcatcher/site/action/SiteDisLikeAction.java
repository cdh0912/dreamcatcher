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
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.NumberCheck;

public class SiteDisLikeAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int site_id=NumberCheck.nullToZero(request.getParameter("site_id"));
		MemberDto member = (MemberDto) request.getSession().getAttribute("memberInfo");

	    String userId = (member != null) ? member.getId() : "anonymous";
		SiteServiceImpl.getInstance().disLikeSite(site_id, userId);

		int likeCheck= SiteServiceImpl.getInstance().likeCheck(site_id, userId);
		request.setAttribute("likeCheck", likeCheck);
		
		int likeCnt = SiteServiceImpl.getInstance().siteView(site_id).getRecommend();
		JSONObject jSONObject = new JSONObject();
		jSONObject.put("likeCnt", likeCnt);
		
		String jSONString = jSONObject.toJSONString();
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        response.getWriter().write(jSONString);

//        System.out.println("list :" + jSONString);
		
		return null;
	}

}
