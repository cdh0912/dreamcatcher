package com.dreamcatcher.site.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.site.model.SiteReplyDto;
import com.dreamcatcher.site.model.service.SiteServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class SiteReplyModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		int sre_id = NumberCheck.nullToZero(request.getParameter("sre_id"));
		String content = StringCheck.nullToBlank(request.getParameter("content"));
		

		if (sre_id != 0) {
			SiteReplyDto siteReplyDto = new SiteReplyDto();
			siteReplyDto.setSre_id(sre_id);
			siteReplyDto.setContent(content);

			int cnt = SiteServiceImpl.getInstance().replyModify(siteReplyDto);

			if (cnt == 1) {
				siteReplyDto = SiteServiceImpl.getInstance().replyInfo(sre_id);
								
				Gson gson = new Gson();
				JsonElement jsonElement = gson.toJsonTree(siteReplyDto);
				
				response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);
				response.getWriter().write(jsonElement.toString());
			}
		}
		return null;
	}
}