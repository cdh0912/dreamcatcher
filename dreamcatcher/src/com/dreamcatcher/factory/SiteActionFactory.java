package com.dreamcatcher.factory;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.site.action.*;

public class SiteActionFactory {
	private static Action siteListAction;
	private static Action siteArticleListAction;
	private static Action siteMoreArticleAction;

	private static Action siteViewAction;
	private static Action siteModifyViewAction;

	
	private static Action siteReplyRegisterAction;
	private static Action siteReplyListAction;


	private static SiteReplyDeleteAction siteReplyDeleteAction;
	private static SiteReplyModifyAction siteReplyModifyAction;
	
	private static SiteLikeAction siteLikeAction;
	private static SiteDisLikeAction siteDisLikeAction;
	static{
		siteListAction = new SiteListAction();
		siteArticleListAction = new SiteArticleListAction();
		siteMoreArticleAction = new SiterMoreArticleAction();
		siteViewAction = new SiteViewAction();
		siteModifyViewAction = new SiteModifyViewAction();

		
		
		siteReplyRegisterAction = new SiteReplyRegisterAction();

		siteReplyListAction = new SiteReplyListAction();
		siteReplyDeleteAction = new SiteReplyDeleteAction();
		siteReplyModifyAction = new SiteReplyModifyAction();
		
		siteLikeAction = new SiteLikeAction();
		siteDisLikeAction = new SiteDisLikeAction();
	}


	public static SiteLikeAction getSiteLikeAction() {
		return siteLikeAction;
	}

	public static SiteDisLikeAction getSiteDisLikeAction() {
		return siteDisLikeAction;
	}

	public static Action getSiteReplyRegisterAction() {
		return siteReplyRegisterAction;
	}

	public static Action getSiteReplyListAction() {
		return siteReplyListAction;
	}

	public static SiteReplyDeleteAction getSiteReplyDeleteAction() {
		return siteReplyDeleteAction;
	}

	public static SiteReplyModifyAction getSiteReplyModifyAction() {
		return siteReplyModifyAction;
	}

	public static Action getSiteViewAction() {
		return siteViewAction;
	}

	public static Action getSiteModifyViewAction() {
		return siteModifyViewAction;
	}


	public static Action getSiteMoreArticleAction() {
		return siteMoreArticleAction;
	}

	public static Action getSiteArticleListAction() {
		return siteArticleListAction;
	}

	public static Action getSiteListAction() {
		return siteListAction;
	}

	

}
