package com.dreamcatcher.plan.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.plan.model.PlanDto;
import com.dreamcatcher.plan.model.dao.PlanDaoImpl;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;
import com.dreamcatcher.util.NumberCheck;

public class PlanViewAction implements Action {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int route_id = NumberCheck.nullToOne(request.getParameter("route_id"));
		MemberDto member = (MemberDto) request.getSession().getAttribute( "memberInfo");
		String userId = (member != null) ? member.getId() : "anonymous";

//		String route_url = PlanServiceImpl.getInstance().getRoute_url(route_id);
//		request.setAttribute("route_url", route_url);

		List<PlanDto> pList= PlanServiceImpl.getInstance().getScheduleList(route_id);
		request.setAttribute("pList", pList);
		
		int likeCheck = PlanServiceImpl.getInstance().likeCheck(route_id, userId);
		request.setAttribute("likeCheck", likeCheck);
		
		List<HashMap> routeDetailList = PlanServiceImpl.getInstance().getRouteDetailList(route_id);
		request.setAttribute("routeDetailList", routeDetailList);

		return "/plan/planView.tiles";
	}

}
