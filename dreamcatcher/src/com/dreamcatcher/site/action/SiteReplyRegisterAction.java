package com.dreamcatcher.site.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.site.model.SiteReplyDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;

public class SiteReplyRegisterAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int site_id = NumberCheck.nullToZero(request.getParameter("site_id"));
		String content = StringCheck.nullToBlank(request.getParameter("content"));

		if( site_id != 0 && !content.isEmpty()){
			SiteReplyDto siteReplyDto = new SiteReplyDto();
			siteReplyDto.setSite_id(site_id);
			siteReplyDto.setContent(content);
			
			MemberDto memberDto = (MemberDto) request.getSession().getAttribute("memberInfo");
			
			if(memberDto != null){
				siteReplyDto.setId(memberDto.getId());
				siteReplyDto.setName(memberDto.getName());
				
				int cnt = SiteServiceImpl.getInstance().replyRegister(siteReplyDto);
				
				if( cnt == 1){
					
					//int replyPage = NumberCheck.nullToOne(request.getParameter("replyPage"));		
//					int replyPage = 1;
//					
//					Map<String, Integer> replyListMap = new HashMap<String, Integer>();
//					replyListMap.put("site_id", site_id);
//					replyListMap.put("page", replyPage);	
//					replyListMap.put("isTotaList", 1);
//
//					List<SiteReplyDto> replyList = SiteServiceImpl.getInstance().replyList(replyListMap);
//
//					JSONObject jSONObject = new JSONObject();
//					
//					Gson gson = new Gson();
//					JsonElement replyJsonElement = gson.toJsonTree(replyList);	
//					jSONObject.put("replyList", replyJsonElement);
//					
//					boolean isLastPage = SiteServiceImpl.getInstance().isLastReplyPage(replyListMap);
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
