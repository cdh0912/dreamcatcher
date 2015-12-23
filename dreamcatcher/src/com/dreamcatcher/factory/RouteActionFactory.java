package com.dreamcatcher.factory;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.route.action.*;

public class RouteActionFactory {
	
	private static Action routeListAction;
	private static Action routeAutoCompleteAction;
	private static Action routeArticleListAction;
	
	private static Action routeReplyRegisterAction;
	private static Action routeReplyListAction;


	private static RouteReplyDeleteAction routeReplyDeleteAction;
	private static RouteReplyModifyAction routeReplyModifyAction;	

	
	static{
		routeListAction = new RouteListAction();
		routeAutoCompleteAction = new RouteAutoCompleteAction();
		routeArticleListAction = new RouteArticleListAction();
		
		routeReplyRegisterAction = new RouteReplyRegisterAction();

		routeReplyListAction = new RouteReplyListAction();
		routeReplyDeleteAction = new RouteReplyDeleteAction();
		routeReplyModifyAction = new RouteReplyModifyAction();
	}

	public static Action getRouteReplyRegisterAction() {
		return routeReplyRegisterAction;
	}

	public static Action getRouteArticleListAction() {
		return routeArticleListAction;
	}

	public static Action getRouteListAction() {
		return routeListAction;
	}

	public static Action getRouteAutoCompleteAction() {
		return routeAutoCompleteAction;
	}


	public static Action getRouteReplyListAction() {
		return routeReplyListAction;
	}

	public static RouteReplyDeleteAction getRouteReplyDeleteAction() {
		return routeReplyDeleteAction;
	}

	public static RouteReplyModifyAction getRouteReplyModifyAction() {
		return routeReplyModifyAction;
	}
	
	
}
