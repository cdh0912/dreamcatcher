package com.dreamcatcher.route.model.service;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.route.model.RouteDto;
import com.dreamcatcher.route.model.RouteReplyDto;
import com.dreamcatcher.site.model.SiteDetailDto;
import com.dreamcatcher.util.PageNavigator;

public interface RouteService {
	public List<KeywordDto> autoComplete(String keyword);
	
	public List<RouteDto> routeArticleList(Map searchMap); 
	public boolean isLastPage(Map searchMap);
	
	public PageNavigator makePageNavigator(Map searchMap);
	
	
	public List<RouteReplyDto> replyList(Map<String,Integer> replyListMap);
	public boolean isLastReplyPage(Map<String,Integer> replyListMap);
	
	public RouteReplyDto replyInfo(int rre_id);
		
	public int replyRegister(RouteReplyDto routeReplyDto);
	public int replyModify(RouteReplyDto routeReplyDto);	
	public int replyDelete(Map<String,Integer> replyMap);

}
