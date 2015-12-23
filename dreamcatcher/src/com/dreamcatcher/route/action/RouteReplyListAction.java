package com.dreamcatcher.route.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.route.model.RouteReplyDto;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.NumberCheck;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class RouteReplyListAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int route_id = NumberCheck.nullToZero(request.getParameter("route_id"));
		int replyPage = NumberCheck.nullToOne(request.getParameter("replyPage"));		
		int isTotalList = NumberCheck.nullToZero(request.getParameter("isTotalList"));
		
		Map<String, Integer> replyListMap = new HashMap<String, Integer>();
		replyListMap.put("route_id", route_id);
		replyListMap.put("page", replyPage);
		replyListMap.put("isTotalList", isTotalList);


		List<RouteReplyDto> replyList = RouteServiceImpl.getInstance().replyList(replyListMap);

		JSONObject jSONObject = new JSONObject();
		
		Gson gson = new Gson();
		JsonElement replyJsonElement = gson.toJsonTree(replyList);	
		jSONObject.put("replyList", replyJsonElement);
		
		boolean isLastPage = RouteServiceImpl.getInstance().isLastReplyPage(replyListMap);
       
		jSONObject.put("isLastPage", isLastPage);

        response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
        response.getWriter().write(jSONObject.toJSONString());

        return null;
	}
}
