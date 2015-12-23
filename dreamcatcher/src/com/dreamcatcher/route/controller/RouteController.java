package com.dreamcatcher.route.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.factory.RouteActionFactory;
import com.dreamcatcher.factory.SiteActionFactory;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.PageMove;

@WebServlet("/route")
public class RouteController extends HttpServlet {
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
		String act = request.getParameter("act");
		//int bcode = NumberCheck.nullToZero(request.getParameter("bcode"));
		//int pg = NumberCheck.nullToOne(request.getParameter("pg"));
		//String key = StringCheck.nullToBlank(request.getParameter("key"));
		//String word = Encoder.isoToEuc(StringCheck.nullToBlank(request.getParameter("word")));
		
		//String queryString = "bcode=" + bcode + "&pg=" + pg + "&key=" + key + "&word=" + UrlEncoder.encode(word);
		//System.out.println("QS = " + queryString);
		
		String path = "/main";
		System.out.println("action = " + act);
		if("routeList".equals(act)) {
			path = RouteActionFactory.getRouteListAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if("autoComplete".equals(act)) {
			RouteActionFactory.getRouteAutoCompleteAction().execute(request, response);
		} else if("routeArticleList".equals(act)) {
			path = RouteActionFactory.getRouteArticleListAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if ("replyRegister".equals(act)) {
			RouteActionFactory.getRouteReplyRegisterAction().execute(request, response);
		} else if ("replyList".equals(act)) {
			RouteActionFactory.getRouteReplyListAction().execute(request, response);
		} else if ("replyDelete".equals(act)) {
			RouteActionFactory.getRouteReplyDeleteAction().execute(request, response);
		} else if ("replyModify".equals(act)) {
			RouteActionFactory.getRouteReplyModifyAction().execute(request, response);
		} else if("".equals(act)) {
			
		} else {
			PageMove.redirect(response, root + path);
		}
	}
}
