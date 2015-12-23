package com.dreamcatcher.site.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.factory.*;
import com.dreamcatcher.util.*;

@WebServlet("/site")
public class SiteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(CharacterConstant.DEFAULT_CHAR);
		execute(request, response);
	}
	
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String root = request.getContextPath();
		String path = "/main";

		String act = request.getParameter("act");
		System.out.println("action = " + act);

		int page = NumberCheck.nullToOne(request.getParameter("page"));
		String searchMod = StringCheck.nullToBlank(request.getParameter("searchMode"));
		String searchWord = null;
		if(request.getMethod().equals("GET"))
			searchWord = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchWord")));
		else
			searchWord = StringCheck.nullToBlank(request.getParameter("searchWord"));
		String queryString = "page=" + page + "&searchMod=" + searchMod + "&searchWord=" + UrlEncoder.encode(searchWord);
		System.out.println("QS = " + queryString);
		
		
		if("siteList".equals(act)) {
			path = SiteActionFactory.getSiteListAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if("siteArticleList".equals(act)) {
			path = SiteActionFactory.getSiteArticleListAction().execute(request, response);
			PageMove.forward(request, response, path + "?" + queryString);
		} else if("siteMoreArticle".equals(act)) {
			SiteActionFactory.getSiteMoreArticleAction().execute(request, response);
		} else if ("moveSiteMake".equals(act)) {
			PageMove.redirect(response, root+"/site/siteMake.jsp");
		} else if ("siteView".equals(act)) {
			path = SiteActionFactory.getSiteViewAction().execute(request,	response);
			PageMove.forward(request, response, path);
			//PageMove.forward(request, response, path + "?" + queryString);
			
		} else if ("moveSiteModify".equals(act)){
			path = SiteActionFactory.getSiteModifyViewAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if ("replyRegister".equals(act)) {
			SiteActionFactory.getSiteReplyRegisterAction().execute(request, response);
		} else if ("replyList".equals(act)) {
			SiteActionFactory.getSiteReplyListAction().execute(request, response);
		} else if ("replyDelete".equals(act)) {
			SiteActionFactory.getSiteReplyDeleteAction().execute(request, response);
		} else if ("replyModify".equals(act)) {
			SiteActionFactory.getSiteReplyModifyAction().execute(request, response);
		} else if ("likeSite".equals(act)) {
			SiteActionFactory.getSiteLikeAction().execute(request, response);
		} else if ("disLikeSite".equals(act)) {
			SiteActionFactory.getSiteDisLikeAction().execute(request, response);
		}else {
			PageMove.redirect(response, root + path);
		}
	}

}
