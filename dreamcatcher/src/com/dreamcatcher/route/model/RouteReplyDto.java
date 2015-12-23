package com.dreamcatcher.route.model;

public class RouteReplyDto extends RouteDetailDto{
	private int rre_id;
	private String content;
	
	public int getRre_id() {
		return rre_id;
	}

	public void setRre_id(int rre_id) {
		this.rre_id = rre_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
