package com.dreamcatcher.factory;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.main.action.*;

public class MainActionFactory {
	private static Action mainViewAction;
	private static Action mainSiteArticleSortAction;
	private static Action mainRouteArticleSortAction;	
	static{
		mainViewAction = new MainViewAction();
		mainSiteArticleSortAction = new MainSiteArticleSortAction();
		mainRouteArticleSortAction = new MainRouteArticleSortAction();
	}

	public static Action getMainRouteArticleSortAction() {
		return mainRouteArticleSortAction;
	}

	public static Action getMainSiteArticleSortAction() {
		return mainSiteArticleSortAction;
	}

	public static Action getMainViewAction() {
		return mainViewAction;
	}
}
