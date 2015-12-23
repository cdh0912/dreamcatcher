package com.dreamcatcher.plan.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;
import com.dreamcatcher.util.Encoder;
import com.dreamcatcher.util.NumberCheck;
import com.dreamcatcher.util.StringCheck;

public class RouteModifyStartAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		int route_id = NumberCheck.nullToZero(request.getParameter("route_id"));
		List<HashMap> routeDetailList = PlanServiceImpl.getInstance().getRouteDetailList(route_id);
		if(routeDetailList != null)
			request.setAttribute("routeDetailList", routeDetailList);

		return  "/plan/planMapModify.jsp";
	}

}
