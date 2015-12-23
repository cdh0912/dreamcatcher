package com.dreamcatcher.admin.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.model.StatisticsDto;
import com.dreamcatcher.admin.model.service.AdminServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.CommonConstant;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class AdminStatisticsAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		int range = CommonConstant.STATISTICS_RANGE;
		
		List<StatisticsDto> locationStatisticsList = AdminServiceImpl.getInstance().locationRankByRecommend(range);
		
		List<StatisticsDto> siteStatisticsList = AdminServiceImpl.getInstance().siteRankByRecommend(range);
		
		List<StatisticsDto> routeStatisticsList = AdminServiceImpl.getInstance().siteRankByRoute(range);
		
		Gson gson = new Gson();
		
		JsonElement locationElement = gson.toJsonTree(locationStatisticsList);
		JsonElement siteElement = gson.toJsonTree(siteStatisticsList);
		JsonElement routeElement = gson.toJsonTree(routeStatisticsList);
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("locationStatistics", locationElement);
		jsonObject.put("siteStatistics", siteElement);
		jsonObject.put("routeStatistics", routeElement);
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
		response.getWriter().write(jsonObject.toJSONString());
		
		return null;
	}

}
