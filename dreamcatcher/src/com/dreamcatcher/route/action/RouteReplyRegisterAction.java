package com.dreamcatcher.route.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.route.model.RouteReplyDto;
import com.dreamcatcher.route.model.service.RouteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class RouteReplyRegisterAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int route_id = NumberCheck.nullToZero(request.getParameter("route_id"));
		String content = StringCheck.nullToBlank(request.getParameter("content"));

		if( route_id != 0 && !content.isEmpty()){
			RouteReplyDto routeReplyDto = new RouteReplyDto();
			routeReplyDto.setRoute_id(route_id);
			routeReplyDto.setContent(content);
			
			MemberDto memberDto = (MemberDto) request.getSession().getAttribute("memberInfo");
			
			if(memberDto != null){
				routeReplyDto.setId(memberDto.getId());
				routeReplyDto.setName(memberDto.getName());
				
				int cnt = RouteServiceImpl.getInstance().replyRegister(routeReplyDto);
				
				if( cnt == 1){
					
					//int replyPage = NumberCheck.nullToOne(request.getParameter("replyPage"));		
//					int replyPage = 1;
//					
//					Map<String, Integer> replyListMap = new HashMap<String, Integer>();
//					replyListMap.put("route_id", route_id);
//					replyListMap.put("page", replyPage);	
//					replyListMap.put("isTotaList", 1);
//
//					List<RouteReplyDto> replyList = RouteServiceImpl.getInstance().replyList(replyListMap);
//
//					JSONObject jSONObject = new JSONObject();
//					
//					Gson gson = new Gson();
//					JsonElement replyJsonElement = gson.toJsonTree(replyList);	
//					jSONObject.put("replyList", replyJsonElement);
//					
//					boolean isLastPage = RouteServiceImpl.getInstance().isLastReplyPage(replyListMap);
//			       
//					jSONObject.put("isLastPage", isLastPage);
	
					JSONObject jSONObject = new JSONObject();
					jSONObject.put("result", "success");

			        response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
			        response.getWriter().write(jSONObject.toJSONString());

					
				}
			}
		}
		
	      return null;
	}

}
