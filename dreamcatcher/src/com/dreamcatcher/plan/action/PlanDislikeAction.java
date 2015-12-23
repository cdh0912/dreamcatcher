package com.dreamcatcher.plan.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.plan.model.PlanDto;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;
import com.dreamcatcher.util.NumberCheck;

public class PlanDislikeAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int route_id=NumberCheck.nullToZero(request.getParameter("route_id"));
		MemberDto member = (MemberDto) request.getSession().getAttribute("memberInfo");

	      String userId = (member != null) ? member.getId() : "anonymous";
		PlanServiceImpl.getInstance().dislikeSchedule(route_id, userId);

		int likeCheck= PlanServiceImpl.getInstance().dislikeCheck(route_id, userId);
		request.setAttribute("likeCheck", likeCheck);
		
		List<PlanDto> pList= PlanServiceImpl.getInstance().getScheduleList(route_id);
		
		int likeCnt=pList.get(0).getRecommend();
		
		JSONObject jSONObject = new JSONObject();
		jSONObject.put("likeCnt", likeCnt);
		
		String jSONString = jSONObject.toJSONString();
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        response.getWriter().write(jSONString);

        System.out.println("list :" + jSONString);
		
		return null;
	}

}
