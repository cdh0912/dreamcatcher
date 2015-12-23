package com.dreamcatcher.plan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.plan.model.PlanDto;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;

public class PlanDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
//		System.out.println("delete action");
		int route_id = Integer.parseInt(request.getParameter("route_id"));
		
		PlanServiceImpl.getInstance().deleteSchedule(route_id);
		
		return null;
	}

}
