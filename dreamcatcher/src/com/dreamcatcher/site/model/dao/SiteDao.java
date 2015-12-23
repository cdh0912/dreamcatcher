package com.dreamcatcher.site.model.dao;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.site.model.*;

public interface SiteDao {

	public int totalArticleCount(Map searchMap);
	public List<SiteDetailDto> siteArticleList(Map searchMap);

	public List<NationDto> getNationList();
	
	public int siteMake(SiteDetailDto siteDetailDto);
	
	public SiteDetailDto siteView(int site_id);
	public int deleteSite(int site_id);
	public int siteModify(SiteDetailDto siteDetailDto);
	
	public SiteImageDto siteImage(int site_id);
	
	public List<SiteReplyDto> replyList(Map<String,Integer> replyListMap);
	public int totalReplyCount(int site_id);
	
	public SiteReplyDto replyInfo(int rre_id);

	
	public int replyRegister(SiteReplyDto siteReplyDto);
	public int replyModify(SiteReplyDto siteReplyDto);
	public int replyDelete(Map<String,Integer> replyMap);	
	
	
	public int likeCheck(int site_id, String userId);
	public void likeSite(int site_id, String userId);
	public void disLikeSite(int site_id, String userId);
}
