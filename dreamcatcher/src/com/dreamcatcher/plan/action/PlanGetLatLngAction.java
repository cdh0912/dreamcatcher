package com.dreamcatcher.plan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.NumberCheck;
import com.google.gson.Gson;

public class PlanGetLatLngAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int route_id = NumberCheck.nullToOne(request.getParameter("route_id"));

		List<HashMap> routeDetailList = PlanServiceImpl.getInstance() .getRouteDetailList(route_id);

		Gson gson = new Gson();
		String jsonString = gson.toJson(routeDetailList);
		
		response.setContentType("text/plain; charset="+ CharacterConstant.DEFAULT_CHAR);
		PrintWriter out = response.getWriter();
		out.write(jsonString);
		
		return null;
	}
}
