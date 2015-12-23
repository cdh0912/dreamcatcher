package com.dreamcatcher.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.factory.AdminActionFactory;
import com.dreamcatcher.factory.MemberActionFactory;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.PageMove;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
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
		System.out.println("action = " + act);
		if("sendPassword".equals(act)){
			AdminActionFactory.getAdminSendPasswordAction().execute(request, response);
		} else if("sendAuthCode".equals(act)) {
			AdminActionFactory.getAdminSendAuthCodeAction().execute(request, response);
		} else if("memberJoinConfirm".equals(act)) {
			AdminActionFactory.getAdminMemberJoinConfirmAction().execute(request, response);
			//PageMove.forward(request, response, path + "?" + queryString);
		} else if("moveStatistics".equals(act)) {
			PageMove.redirect(response, root + "/admin/statistics.tiles");
		} else if("statistics".equals(act)) {
			AdminActionFactory.getAdminStatisticsAction().execute(request, response);
		} else if("locationRank".equals(act)) {
			AdminActionFactory.getAdminLocationRankAction().execute(request, response);
		} else if("siteRank".equals(act)) {
			AdminActionFactory.getAdminSiteRankAction().execute(request, response);
		} else if("routeRank".equals(act)) {
			AdminActionFactory.getAdminSiteRankByRouteAction().execute(request, response);
		} else {
			PageMove.redirect(response, root + path);
		}
	}
}
