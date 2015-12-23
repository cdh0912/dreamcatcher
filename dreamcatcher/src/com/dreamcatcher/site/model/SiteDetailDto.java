package com.dreamcatcher.site.model;

public class SiteDetailDto extends SiteImageDto{
	private String brief_info;
	private String detail_info;
	private String id;
	private String logtime;
	private int recommend;
	private int rec_percent;
	private int reply_count;
	private int rep_percent;

	public String getBrief_info() {
		return brief_info;
	}

	public void setBrief_info(String brief_info) {
		this.brief_info = brief_info;
	}

	public String getDetail_info() {
		return detail_info;
	}

	public void setDetail_info(String detail_info) {
		this.detail_info = detail_info;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getRec_percent() {
		return rec_percent;
	}

	public void setRec_percent(int rec_percent) {
		this.rec_percent = rec_percent;
	}

	public int getReply_count() {
		return reply_count;
	}

	public void setReply_count(int reply_count) {
		this.reply_count = reply_count;
	}

	public int getRep_percent() {
		return rep_percent;
	}

	public void setRep_percent(int rep_percent) {
		this.rep_percent = rep_percent;
	}

}
