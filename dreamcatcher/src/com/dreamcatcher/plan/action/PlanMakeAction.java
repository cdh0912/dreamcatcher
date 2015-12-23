package com.dreamcatcher.plan.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.plan.model.PlanDto;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;
import com.dreamcatcher.util.NumberCheck;
import com.dreamcatcher.util.StringCheck;

public class PlanMakeAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String root = request.getContextPath();
		
		String jsonStringFromMap = request.getParameter("routeJsonString");
		//System.out.println(jsonStringFromMap);

		HttpSession session = request.getSession(true);
		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
		String id=memberDto.getId();
		String name=memberDto.getName();
		String title=StringCheck.nullToBlank(request.getParameter("title"));

		String stay_date[]= request.getParameterValues("date");
		String site_name[]=request.getParameterValues("site_name");
		String content[]=request.getParameterValues("schedule");
		String str_budget[]=request.getParameterValues("cost");
		String currency[]=request.getParameterValues("won");
		
		PlanDto pDto= new PlanDto();
		pDto.setSize(stay_date.length);
		
		pDto.setTitle(title);
		pDto.setId(id);
		pDto.setName(name);
		
		List<PlanDto> list = new ArrayList<PlanDto>();
		if(stay_date != null){
			for(int i=0; i<stay_date.length; i++){
				PlanDto planDto = new PlanDto();
				
				planDto.setStay_date(StringCheck.nullToBlank(stay_date[i]));
				planDto.setSite_name(StringCheck.nullToBlank(site_name[i]));
				planDto.setContent(StringCheck.nullToBlank(content[i]));
				int budget=NumberCheck.nullToZero(str_budget[i]);
				planDto.setBudget(budget);
				planDto.setCurrency(StringCheck.nullToBlank(currency[i]));
				list.add(planDto);
			}
		}
		
		int route_id = PlanServiceImpl.getInstance().registerSchedule(pDto, list, jsonStringFromMap);
		
		return "/plan?act=viewPlan&route_id=" + route_id;
	}

}
