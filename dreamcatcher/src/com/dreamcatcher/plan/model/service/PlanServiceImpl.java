package com.dreamcatcher.plan.model.service;

import java.util.HashMap;
import java.util.List;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.plan.model.PlanDto;
import com.dreamcatcher.plan.model.dao.PlanDaoImpl;
import com.dreamcatcher.site.model.SiteDto;
import com.dreamcatcher.site.model.SiteImageDto;
import com.dreamcatcher.site.model.dao.SiteDaoImpl;

public class PlanServiceImpl implements PlanService {

	private static PlanService PlanService;

	static {
		PlanService = new PlanServiceImpl();
	}

	private PlanServiceImpl() {
	}

	public static PlanService getInstance() {
		return PlanService;
	}
	
	@Override
	public List<HashMap> getRouteDetailList(int route_id) {
		return PlanDaoImpl.getInstance().getRouteDetailList(route_id);
	}

	@Override
	public int getRouteModifyArticle(int route_id, String jsonStringFromMap) {
		return PlanDaoImpl.getInstance().getRouteModifyArticle(route_id, jsonStringFromMap);
	}

	//===========================================================================================
	
	@Override
	public int registerSchedule(PlanDto pDto, List<PlanDto> list, String jsonStringFromMap) {
		return PlanDaoImpl.getInstance().registerSchedule(pDto, list, jsonStringFromMap);
	}

	@Override
	public List<PlanDto> getScheduleList(int route_id) {
		return PlanDaoImpl.getInstance().getScheduleList(route_id);
	}
	
	@Override
	public int updateSchedule(PlanDto pDto, List<PlanDto> list, int route_id) {
		return PlanDaoImpl.getInstance().updateSchedule(pDto, list, route_id);
	}

	@Override
	public int deleteSchedule(int route_id) {
		return PlanDaoImpl.getInstance().deleteSchedule(route_id);
	}

	@Override
	public String getRoute_url(int route_id) {
		return PlanDaoImpl.getInstance().getRoute_url(route_id);
	}
	
	@Override
	public int likeSchedule(int route_id, String id) {
		
		return PlanDaoImpl.getInstance().likeSchedule(route_id, id);
	}

	@Override
	public int likeCheck(int route_id, String id) {

		return PlanDaoImpl.getInstance().likeCheck(route_id, id);
	}

	@Override
	public int dislikeSchedule(int route_id, String id) {
		
		return PlanDaoImpl.getInstance().dislikeSchedule(route_id, id);
	}

	@Override
	public int dislikeCheck(int route_id, String id) {
		
		return PlanDaoImpl.getInstance().dislikeCheck(route_id, id);
	}
	
	
	//========================================================================================
	
	@Override
	public List<SiteImageDto> getSiteList() {
		return PlanDaoImpl.getInstance().getSiteList();
	}
	
	@Override
	public List<KeywordDto> autoCompleteInMap(String keyword) {
		return PlanDaoImpl.getInstance().autoCompleteInMap(keyword);
	}
}
