package com.dreamcatcher.site.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.site.model.SiteReplyDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.NumberCheck;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class SiteReplyListAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int site_id = NumberCheck.nullToZero(request.getParameter("site_id"));
		int replyPage = NumberCheck.nullToOne(request.getParameter("replyPage"));		
		int isTotalList = NumberCheck.nullToZero(request.getParameter("isTotalList"));
		
		Map<String, Integer> replyListMap = new HashMap<String, Integer>();
		replyListMap.put("site_id", site_id);
		replyListMap.put("page", replyPage);
		replyListMap.put("isTotalList", isTotalList);


		List<SiteReplyDto> replyList = SiteServiceImpl.getInstance().replyList(replyListMap);

		JSONObject jSONObject = new JSONObject();
		
		Gson gson = new Gson();
		JsonElement replyJsonElement = gson.toJsonTree(replyList);	
		jSONObject.put("replyList", replyJsonElement);
		
		boolean isLastPage = SiteServiceImpl.getInstance().isLastReplyPage(replyListMap);
       
		jSONObject.put("isLastPage", isLastPage);

        response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
        response.getWriter().write(jSONObject.toJSONString());

        return null;
	}
}
