package com.dreamcatcher.plan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.factory.PlanActionFactory;
import com.dreamcatcher.factory.SiteActionFactory;
import com.dreamcatcher.plan.action.PlanMakeAction;
import com.dreamcatcher.plan.action.PlanViewAction;
import com.dreamcatcher.util.*;


@WebServlet("/plan")
public class PlanController extends HttpServlet {
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
		String viewMode = Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("viewMode")));
		String categoryMode = StringCheck.nullToBlank(request.getParameter("searchMode"));
		String queryString = "&page=" + page + "&searchMod=" + searchMod + "&searchWord=" + UrlEncoder.encode(searchWord)+"&viewMode="+viewMode+"&categoryMode="+categoryMode;


		if("registerMap".equals(act)) {
			PageMove.redirect(response, root + "/plan/planMapView.jsp");
		} else if("getSitesForMap".equals(act)) {
			PlanActionFactory.getPlanMapViewAction().execute(request, response);
		} else if("moveToPlanPage".equals(act)) {
			String jsonString = request.getParameter("jsonString");
			request.setAttribute("jsonString", jsonString);
			PageMove.forward(request,response, "/plan/planRegister.tiles");
		} else if("autoCompleteInMap".equals(act)) {
			PlanActionFactory.getPlanAutoCompleteInMapAction().execute(request, response);
		} else if ("registerPlan".equals(act)) {
			path = PlanActionFactory.getPlanMakeAction().execute(request, response);
			PageMove.redirect(response, root + path);
		} else if("modifyMapStart".equals(act)) {
			path = PlanActionFactory.getRouteModifyStartAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if("modifyMapEnd".equals(act)) {
			PlanActionFactory.getRouteModifyEndAction().execute(request, response);
			path = PlanActionFactory.getPlanViewAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if ("viewModify".equals(act)) {
			path = PlanActionFactory.getPlanModifyViewAction().execute(request, response); 
			PageMove.forward(request, response, path);
		} else if ("modifyPlan".equals(act)) {
			PlanActionFactory.getPlanModifyAction().execute(request, response);
			path = PlanActionFactory.getPlanViewAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if ("viewPlan".equals(act)) {
			path = PlanActionFactory.getPlanViewAction().execute(request, response);
			PageMove.forward(request, response, path);
		} else if ("deletePlan".equals(act)) {
			PlanActionFactory.getPlanDeleteAction().execute(request, response);
			PageMove.redirect(response, root + "/route?act=routeArticleList"+queryString);
		} else if ("likePlan".equals(act)) {
			PlanActionFactory.getplanLikeAction().execute(request, response);
		} else if ("disLikePlan".equals(act)) {
			 PlanActionFactory.getplanDisLikeAction().execute(request, response);
		} else if ("getPlanLatlng".equals(act)) {
			PlanActionFactory.getPlanGetLatLngAction().execute(request, response);
		} else {
			PageMove.redirect(response, root + path);
		}
	}	

}
