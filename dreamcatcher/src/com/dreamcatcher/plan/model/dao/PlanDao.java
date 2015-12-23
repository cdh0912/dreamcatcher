package com.dreamcatcher.plan.model.dao;

import java.util.HashMap;
import java.util.List;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.plan.model.PlanDto;
import com.dreamcatcher.site.model.SiteImageDto;


public interface PlanDao {
	
	public List<HashMap> getRouteDetailList(int route_id);
	public int getRouteModifyArticle(int route_id, String jsonStringFromMap);
	
	List<PlanDto> getScheduleList(int route_id);
	int registerSchedule(PlanDto pDto, List<PlanDto> list, String jsonStringFromMap);
	int updateSchedule(PlanDto pDto, List<PlanDto> list, int route_id);
	int deleteSchedule(int route_id);
	public String getRoute_url(int route_id);
	
	List<SiteImageDto> getSiteList();
	public List<KeywordDto> autoCompleteInMap(String keyword);
	
	int likeSchedule(int route_id, String id);
	int likeCheck(int route_id, String id);
	int dislikeSchedule(int route_id, String id);
	int dislikeCheck(int route_id, String id);



}