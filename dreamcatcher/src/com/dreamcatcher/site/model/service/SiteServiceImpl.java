package com.dreamcatcher.site.model.service;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.route.model.RouteReplyDto;
import com.dreamcatcher.route.model.dao.RouteDaoImpl;
import com.dreamcatcher.site.model.*;
import com.dreamcatcher.site.model.dao.SiteDaoImpl;
import com.dreamcatcher.util.ArticleListSizeConstant;
import com.dreamcatcher.util.PageNavigator;


public class SiteServiceImpl implements SiteService {
	
// single tone pattern
	private static SiteService siteService;

	static {
		siteService = new SiteServiceImpl();
	}

	private SiteServiceImpl() {
	}

	public static SiteService getInstance() {
		return siteService;
	}
// single tone pattern end	

	
	@Override
	public List<SiteDetailDto> siteArticleList(Map searchMap) {
		int page = (int)searchMap.get("page");
		int start = 0;
		int end = 0;
		if("mainView".equals((String)searchMap.get("viewMode"))){
			end = ArticleListSizeConstant.SITE_MAIN_LIST_SIZE * page;
			start = end - ArticleListSizeConstant.SITE_MAIN_LIST_SIZE + 1;
		} else if("myView".equals((String)searchMap.get("viewMode"))){
			end = ArticleListSizeConstant.SITE_MY_LIST_SIZE * page;
			start = end - ArticleListSizeConstant.SITE_MY_LIST_SIZE + 1;
		} else if("moreView".equals((String)searchMap.get("viewMode"))) {
			end = ArticleListSizeConstant.SITE_ARTICLE_LIST_SIZE * page;
			start = end - ArticleListSizeConstant.SITE_ARTICLE_LIST_SIZE + 1;
		} else {
			end = ArticleListSizeConstant.SITE_ARTICLE_LIST_SIZE * page;
			start = 1;
		}
		searchMap.put("start", start);
		searchMap.put("end", end);
		
		return SiteDaoImpl.getInstance().siteArticleList(searchMap);
	}
	
	@Override
	public boolean isLastPage(Map searchMap){
		int listSize = ArticleListSizeConstant.SITE_ARTICLE_LIST_SIZE;
		int totalArticleCount = SiteDaoImpl.getInstance().totalArticleCount(searchMap);
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
		int totalArticleCount = totalArticleCount = SiteDaoImpl.getInstance().totalArticleCount(searchMap);

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
	public List<NationDto> getNationList() {
		// TODO Auto-generated method stub
		return SiteDaoImpl.getInstance().getNationList();
	}


	@Override
	public int siteMake(SiteDetailDto siteDetailDto) {
		return SiteDaoImpl.getInstance().siteMake(siteDetailDto);
	}
	
	@Override
	public SiteDetailDto siteView(int site_id) {
		
		return SiteDaoImpl.getInstance().siteView(site_id);
	}

	@Override
	public int deleteSite(int site_id) {
		
		return SiteDaoImpl.getInstance().deleteSite(site_id);
		
	}

	@Override
	public int siteModify(SiteDetailDto siteDetailDto) {
		// TODO Auto-generated method stub
		return SiteDaoImpl.getInstance().siteModify(siteDetailDto);
	}

	@Override
	public SiteImageDto siteImage(int site_id) {
		return SiteDaoImpl.getInstance().siteImage(site_id);
	}
	
	@Override
	public List<SiteReplyDto> replyList(Map<String,Integer> replyListMap) {
		int page = replyListMap.get("page");
		int isTotalList = replyListMap.get("isTotalList");
		
		int end = ArticleListSizeConstant.REPLY_LIST_SIZE * page;
		int start = 1;
		if(isTotalList != 1){
			start = end - ArticleListSizeConstant.REPLY_LIST_SIZE + 1;
		}
		replyListMap.put("start", start);
		replyListMap.put("end", end);
		return SiteDaoImpl.getInstance().replyList(replyListMap);
	}

	@Override
	public int replyRegister(SiteReplyDto siteReplyDto) {
		return SiteDaoImpl.getInstance().replyRegister(siteReplyDto);
	}


	@Override
	public int replyModify(SiteReplyDto siteReplyDto) {
		return SiteDaoImpl.getInstance().replyModify(siteReplyDto);
	}
	
	@Override
	public int replyDelete(Map<String,Integer> replyMap) {
		return SiteDaoImpl.getInstance().replyDelete(replyMap);
	}
	
	@Override
	public boolean isLastReplyPage(Map<String,Integer> replyListMap){
		int listSize = ArticleListSizeConstant.REPLY_LIST_SIZE;
		int totalArticleCount = SiteDaoImpl.getInstance().totalReplyCount(replyListMap.get("site_id"));
		int totalPageCount = (totalArticleCount-1) / listSize + 1;
		
		int page = replyListMap.get("page");
		boolean isLastPage = ( totalPageCount - 1)  < page;
		return isLastPage;
	}

	@Override
	public SiteReplyDto replyInfo(int rre_id) {
		return SiteDaoImpl.getInstance().replyInfo(rre_id);
	}
	
	@Override
	public int likeCheck(int site_id, String userId) {
		return SiteDaoImpl.getInstance().likeCheck(site_id, userId);
	}

	@Override
	public void likeSite(int site_id, String userId) {
		SiteDaoImpl.getInstance().likeSite(site_id, userId);
	}

	@Override
	public void disLikeSite(int site_id, String userId) {
		SiteDaoImpl.getInstance().disLikeSite(site_id, userId);
	}
}
