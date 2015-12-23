package com.dreamcatcher.admin.model.service;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.admin.model.StatisticsDto;
import com.dreamcatcher.member.model.MemberDto;

public interface AdminService {

	public int memberStateChange(Map memberStateMap);
	
	public int sendTempPassword(MemberDto memberDto);
	
	public int sendAuthCode(MemberDto memberDto);
	
	public int verifyAuthCode(Map memberStateMap);
	
	public int siteStateChange(Map siteStateMap);

	public List<StatisticsDto>  locationRankByRecommend(int range);
	
	public List<StatisticsDto>  siteRankByRecommend(int range);
	
	public List<StatisticsDto>  siteRankByRoute(int range);
}
