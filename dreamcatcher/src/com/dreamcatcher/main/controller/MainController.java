package com.dreamcatcher.main.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.factory.CommonActionFactory;
import com.dreamcatcher.factory.MainActionFactory;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.PageMove;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/main")
public class MainController extends HttpServlet {
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

		String path = "/main";
		if("siteArticleSort".equals(act)) {
			MainActionFactory.getMainSiteArticleSortAction().execute(request, response);
		} else if("routeArticleSort".equals(act)) {
			MainActionFactory.getMainRouteArticleSortAction().execute(request, response);
		} else {
			path = MainActionFactory.getMainViewAction().execute(request, response);
			PageMove.forward(request, response, path);
		}
	}

}
