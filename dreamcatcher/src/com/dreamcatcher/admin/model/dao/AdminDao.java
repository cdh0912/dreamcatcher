package com.dreamcatcher.admin.model.dao;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.admin.model.StatisticsDto;

public interface AdminDao {

	public int memberStateChange(Map memberStateMap);
	public int siteStateChange(Map siteStateMap);

	public List<StatisticsDto>  locationRankByRecommend(int range);
	
	public List<StatisticsDto>  siteRankByRecommend(int range);
	
	public List<StatisticsDto>  siteRankByRoute(int range);
}
