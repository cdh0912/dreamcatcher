package com.dreamcatcher.route.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.NumberCheck;

public class RouteReplyDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		int route_id = NumberCheck.nullToZero(request.getParameter("route_id"));
		int rre_id = NumberCheck.nullToZero(request.getParameter("rre_id"));
		
		Map<String, Integer> replyMap = new HashMap<String, Integer>();
		replyMap.put("route_id", route_id);
		replyMap.put("rre_id", rre_id);
		
		int cnt = RouteServiceImpl.getInstance().replyDelete(replyMap);
		
		if( cnt == 1 ){
			JSONObject jSONObject = new JSONObject();
			jSONObject.put("result", "success");

	        response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
	        response.getWriter().write(jSONObject.toJSONString());
		}
		return null;
	}

}
