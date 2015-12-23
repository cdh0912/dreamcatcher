package com.dreamcatcher.plan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyleConstants.CharacterConstants;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.action.Action;
import com.dreamcatcher.plan.model.service.PlanServiceImpl;
import com.dreamcatcher.site.model.SiteDto;
import com.dreamcatcher.site.model.SiteImageDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.google.gson.Gson;


public class PlanMapViewAction implements Action {
	
		@Override
		public String execute(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {
			List<SiteImageDto> siteList = PlanServiceImpl.getInstance().getSiteList();
			
			Gson gson = new Gson();
			String jsonString = gson.toJson(siteList);
			
			response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR); 
			PrintWriter out = response.getWriter();
			
			out.write(jsonString);
			return null;
		}

	}
