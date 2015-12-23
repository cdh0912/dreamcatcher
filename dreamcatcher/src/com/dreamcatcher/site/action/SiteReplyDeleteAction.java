package com.dreamcatcher.site.action;

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
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.NumberCheck;

public class SiteReplyDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		int site_id = NumberCheck.nullToZero(request.getParameter("site_id"));
		int sre_id = NumberCheck.nullToZero(request.getParameter("sre_id"));
		
		Map<String, Integer> replyMap = new HashMap<String, Integer>();
		replyMap.put("site_id", site_id);
		replyMap.put("sre_id", sre_id);
		
		int cnt = SiteServiceImpl.getInstance().replyDelete(replyMap);
		
		if( cnt == 1 ){
			JSONObject jSONObject = new JSONObject();
			jSONObject.put("result", "success");

	        response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
	        response.getWriter().write(jSONObject.toJSONString());
		}
		return null;
	}

}
