package com.dreamcatcher.factory;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.action.*;

public class AdminActionFactory {
	
	private static Action adminMemberJoinConfirmAction;
	private static Action adminSendPasswordAction;
	private static Action adminSendAuthCodeAction;
	private static Action adminSiteStateChangeAction;
	private static Action adminStatisticsAction;
	private static Action adminLocationRankAction;
	private static Action adminSiteRankAction;
	private static Action adminSiteRankByRouteAction;
	static{
		adminMemberJoinConfirmAction = new AdminMemberJoinConfirmAction();
		adminSendPasswordAction = new AdminSendPasswordAction();
		adminSendAuthCodeAction = new AdminSendAuthCodeAction();
		adminSiteStateChangeAction = new AdminSiteStateChangeAction();
		adminStatisticsAction = new AdminStatisticsAction();
		adminLocationRankAction = new AdminLocationRankAction();
		adminSiteRankAction = new AdminSiteRankAction();
		adminSiteRankByRouteAction = new AdminSiteRankByRouteAction();
	}

	public static Action getAdminSiteStateChangeAction() {
		return adminSiteStateChangeAction;
	}

	public static Action getAdminSendAuthCodeAction() {
		return adminSendAuthCodeAction;
	}

	public static Action getAdminMemberJoinConfirmAction() {
		return adminMemberJoinConfirmAction;
	}

	public static Action getAdminSendPasswordAction() {
		return adminSendPasswordAction;
	}

	public static Action getAdminStatisticsAction() {
		return adminStatisticsAction;
	}

	public static Action getAdminLocationRankAction() {
		return adminLocationRankAction;
	}

	public static Action getAdminSiteRankAction() {
		return adminSiteRankAction;
	}

	public static Action getAdminSiteRankByRouteAction() {
		return adminSiteRankByRouteAction;
	}
	
	

}
