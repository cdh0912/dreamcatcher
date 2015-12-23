package com.dreamcatcher.route.model;

public class RouteDetailDto extends RouteDto{
	private int rdet_id;
	private int site_id;
	private int route_order;

	public int getRdet_id() {
		return rdet_id;
	}

	public void setRdet_id(int rdet_id) {
		this.rdet_id = rdet_id;
	}

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

	public int getRoute_order() {
		return route_order;
	}

	public void setRoute_order(int route_order) {
		this.route_order = route_order;
	}

}
