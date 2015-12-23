package com.dreamcatcher.route.model;

public class RouteDto {
	private int route_id;
	private String title;
	private String id;
	private String name;
	private String logtime;
	private String route_url;
	private int recommend;
	private int rec_percent;
	private int reply_count;
	private int rep_percent;
	
	
	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}

	public int getRec_percent() {
		return rec_percent;
	}

	public void setRec_percent(int rec_percent) {
		this.rec_percent = rec_percent;
	}

	public int getRep_percent() {
		return rep_percent;
	}

	public void setRep_percent(int rep_percent) {
		this.rep_percent = rep_percent;
	}

	public String getRoute_url() {
		return route_url;
	}

	public void setRoute_url(String route_url) {
		this.route_url = route_url;
	}

	public int getReply_count() {
		return reply_count;
	}

	public void setReply_count(int reply_count) {
		this.reply_count = reply_count;
	}

	public int getRoute_id() {
		return route_id;
	}

	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

}
