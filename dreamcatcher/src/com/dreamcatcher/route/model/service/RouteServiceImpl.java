package com.dreamcatcher.route.model.service;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.route.model.RouteDto;
import com.dreamcatcher.route.model.RouteReplyDto;
import com.dreamcatcher.route.model.dao.RouteDaoImpl;
import com.dreamcatcher.site.model.dao.SiteDaoImpl;
import com.dreamcatcher.util.ArticleListSizeConstant;
import com.dreamcatcher.util.PageNavigator;

public class RouteServiceImpl implements RouteService {

	private static RouteService routeService;
	
	private RouteServiceImpl() {}

	static{
		routeService = new RouteServiceImpl();
	}
	
	public static RouteService getInstance(){
		return routeService;
	}

	@Override
	public List<KeywordDto> autoComplete(String keyword) {
		return RouteDaoImpl.getInstance().autoComplete(keyword);
	}

	@Override
	public List<RouteDto> routeArticleList(Map searchMap) {
		int page = (int)searchMap.get("page");
		int start = 0;
		int end = 0;
		if("mainView".equals((String)searchMap.get("viewMode"))){
			end = ArticleListSizeConstant.ROUTE_MAIN_LIST_SIZE * page;
			start = end - ArticleListSizeConstant.ROUTE_MAIN_LIST_SIZE + 1;
		} else if("myView".equals((String)searchMap.get("viewMode"))){
			end = ArticleListSizeConstant.ROUTE_MY_LIST_SIZE * page;
			start = end - ArticleListSizeConstant.ROUTE_MY_LIST_SIZE + 1;
		} else {
			end = ArticleListSizeConstant.ROUTE_ARTICLE_LIST_SIZE * page;
			start = end - ArticleListSizeConstant.ROUTE_ARTICLE_LIST_SIZE + 1;
		}
		searchMap.put("start", start);
		searchMap.put("end", end);
		return RouteDaoImpl.getInstance().routeArticleList(searchMap);
	}


	@Override
	public boolean isLastPage(Map searchMap){
		int listSize = ArticleListSizeConstant.ROUTE_ARTICLE_LIST_SIZE;
		int totalArticleCount = RouteDaoImpl.getInstance().totalArticleCount(searchMap);
		int totalPageCount = (totalArticleCount-1) / listSize + 1;
		
		int page = (int)searchMap.get("page");
		boolean isLastPage = ( totalPageCount - 1)  < page;
		return isLastPage;
	}
	
	@Override
	public PageNavigator makePageNavigator(Map searchMap) {
		int listSize = (int)searchMap.get("listSize");
		int pageSize = (int)searchMap.get("pageSize");
		int page = (int)searchMap.get("page");
		String jsMethodName = (String)searchMap.get("jsMethodName");
		int totalArticleCount = totalArticleCount = RouteDaoImpl.getInstance().totalArticleCount(searchMap);

		int totalPageCount = (totalArticleCount-1) / listSize + 1; // (totalArticleCount + listSize - 1) /listSize
		
		boolean isFirstPage = (page <= pageSize);
		boolean isLastPage = (( totalPageCount - 1) / pageSize * pageSize < page);
		
		
		PageNavigator navigator = new PageNavigator();
		navigator.setJsMethodName(jsMethodName);
		navigator.setPageSize(pageSize);
		navigator.setTotalArticleCount(totalArticleCount);
		navigator.setCurrentPage(page);
		navigator.setTotalPageCount(totalPageCount);
		navigator.setFirstPage(isFirstPage);
		navigator.setLastPage(isLastPage);
		
		return navigator;
	}
	
	@Override
	public List<RouteReplyDto> replyList(Map<String,Integer> replyListMap) {
		int page = replyListMap.get("page");
		int isTotalList = replyListMap.get("isTotalList");
		
		int end = ArticleListSizeConstant.REPLY_LIST_SIZE * page;
		int start = 1;
		if(isTotalList != 1){
			start = end - ArticleListSizeConstant.REPLY_LIST_SIZE + 1;
		}
		replyListMap.put("start", start);
		replyListMap.put("end", end);
		return RouteDaoImpl.getInstance().replyList(replyListMap);
	}

	@Override
	public int replyRegister(RouteReplyDto routeReplyDto) {
		return RouteDaoImpl.getInstance().replyRegister(routeReplyDto);
	}


	@Override
	public int replyModify(RouteReplyDto routeReplyDto) {
		return RouteDaoImpl.getInstance().replyModify(routeReplyDto);
	}
	
	@Override
	public int replyDelete(Map<String,Integer> replyMap) {
		return RouteDaoImpl.getInstance().replyDelete(replyMap);
	}
	
	@Override
	public boolean isLastReplyPage(Map<String,Integer> replyListMap){
		int listSize = ArticleListSizeConstant.REPLY_LIST_SIZE;
		int totalArticleCount = RouteDaoImpl.getInstance().totalReplyCount(replyListMap.get("route_id"));
		int totalPageCount = (totalArticleCount-1) / listSize + 1;
		
		int page = replyListMap.get("page");
		boolean isLastPage = ( totalPageCount - 1)  < page;
		return isLastPage;
	}

	@Override
	public RouteReplyDto replyInfo(int rre_id) {
		return RouteDaoImpl.getInstance().replyInfo(rre_id);
	}


	
}
