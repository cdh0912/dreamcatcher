package com.dreamcatcher.site.model.service;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.site.model.*;
import com.dreamcatcher.util.PageNavigator;

public interface SiteService {
	
	public List<SiteDetailDto> siteArticleList(Map searchMap);

	public boolean isLastPage(Map searchMap);
	
	public PageNavigator makePageNavigator(Map searchMap);
	
	public List<NationDto> getNationList();
	
	public int siteMake(SiteDetailDto siteDetailDto);
	
	public SiteDetailDto siteView(int site_id);
	public int deleteSite(int site_id);
	public int siteModify(SiteDetailDto siteDetailDto);
	
	public SiteImageDto siteImage(int site_id);
	
	public List<SiteReplyDto> replyList(Map<String,Integer> replyListMap);
	public boolean isLastReplyPage(Map<String,Integer> replyListMap);
	
	public SiteReplyDto replyInfo(int rre_id);
		
	public int replyRegister(SiteReplyDto siteReplyDto);
	public int replyModify(SiteReplyDto siteReplyDto);	
	public int replyDelete(Map<String,Integer> replyMap);
	
	public int likeCheck(int site_id, String userId);
	public void likeSite(int site_id, String userId);
	public void disLikeSite(int site_id, String userId);
}
