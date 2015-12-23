package com.dreamcatcher.common.model.service;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.common.model.dao.CommonDaoImpl;
import com.dreamcatcher.route.model.dao.RouteDaoImpl;
import com.dreamcatcher.site.model.LocationDto;
import com.dreamcatcher.site.model.NationDto;
import com.dreamcatcher.site.model.dao.SiteDaoImpl;
import com.dreamcatcher.util.ArticleListSizeConstant;
import com.dreamcatcher.util.PageNavigator;

public class CommonServiceImpl implements CommonService {
	private static CommonService commonService;
	
	private CommonServiceImpl() {}

	static{
		commonService = new CommonServiceImpl();
	}
	
	public static CommonService getInstance(){
		return commonService;
	}

	@Override
	public List<KeywordDto> autoComplete(String keyword) {
		return CommonDaoImpl.getInstance().autoComplete(keyword);
	}

	@Override
	public List<NationDto> getNationList(Map searchMap) {
		
		return CommonDaoImpl.getInstance().getNationList(searchMap);
	}

	@Override
	public List<LocationDto> getLocationList(Map searchMap) {
		return CommonDaoImpl.getInstance().getLocationList(searchMap);
	}

	@Override
	public List<NationDto> getNationListWithPaging(Map searchMap) {
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
		return CommonDaoImpl.getInstance().getNationListWithPaging(searchMap);
	}

	@Override
	public List<LocationDto> getLocationListWithPaging(Map searchMap) {
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
		return CommonDaoImpl.getInstance().getLocationListWithPaging(searchMap);
	}
	
	


}
