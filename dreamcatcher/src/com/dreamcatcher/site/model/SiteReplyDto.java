package com.dreamcatcher.site.model;

public class SiteReplyDto {

	private int sre_id;
	private int site_id;
	private String id;
	private String name;
	private String content;
	private String logtime;

	public int getSre_id() {
		return sre_id;
	}

	public void setSre_id(int sre_id) {
		this.sre_id = sre_id;
	}

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}

}
