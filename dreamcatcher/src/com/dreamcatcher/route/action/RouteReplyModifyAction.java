package com.dreamcatcher.route.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.route.model.RouteReplyDto;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class RouteReplyModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int rre_id = NumberCheck.nullToZero(request.getParameter("rre_id"));
		String content = StringCheck.nullToBlank(request.getParameter("content"));
		

		if (rre_id != 0) {
			RouteReplyDto routeReplyDto = new RouteReplyDto();
			routeReplyDto.setRre_id(rre_id);
			routeReplyDto.setContent(content);

			int cnt = RouteServiceImpl.getInstance().replyModify(routeReplyDto);

			if (cnt == 1) {
				routeReplyDto = RouteServiceImpl.getInstance().replyInfo(rre_id);
								
				Gson gson = new Gson();
				JsonElement jsonElement = gson.toJsonTree(routeReplyDto);
				
				response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
				response.getWriter().write(jsonElement.toString());
			}
		}
		return null;
	}
}