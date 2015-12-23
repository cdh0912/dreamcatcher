package com.dreamcatcher.route.model.dao;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.route.model.RouteDto;
import com.dreamcatcher.route.model.RouteReplyDto;

public interface RouteDao {
	public List<KeywordDto> autoComplete(String keyword);
	public List<RouteDto> routeArticleList(Map searchMap); 
	public int totalArticleCount(Map searchMap);
	
	
	public List<RouteReplyDto> replyList(Map<String,Integer> replyListMap);
	public int totalReplyCount(int route_id);
	
	public RouteReplyDto replyInfo(int rre_id);

	
	public int replyRegister(RouteReplyDto routeReplyDto);
	public int replyModify(RouteReplyDto routeReplyDto);
	public int replyDelete(Map<String,Integer> replyMap);	
}
