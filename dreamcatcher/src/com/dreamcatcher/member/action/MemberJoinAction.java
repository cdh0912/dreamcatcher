package com.dreamcatcher.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.runtime.JspSourceDependent;
import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.model.service.AdminServiceImpl;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;

public class MemberJoinAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String name = StringCheck.nullToBlank(request.getParameter("name"));
		String password = StringCheck.nullToBlank(request.getParameter("password"));
		
		int idExist = MemberServiceImpl.getInstance().idCheck(id);
		
		JSONObject jSONObject = new JSONObject();
		// 존재하는 아이디
		if( idExist == 1 ){
			jSONObject.put("result", "idExist");
			
		// 존재하지 않는 아이디
		}else if (idExist == 0){		
			MemberDto memberDto = new MemberDto();
			memberDto.setId(id);
			memberDto.setName(name);
			memberDto.setPassword(password);
			int cnt = MemberServiceImpl.getInstance().join(memberDto);
			if(cnt == 1){
				int cnt2 = AdminServiceImpl.getInstance().sendAuthCode(memberDto);
				if( cnt2 == 1){
					jSONObject.put("result", "joinSuccess");
					
				}else{
					jSONObject.put("result", "sendAuthCodeFailed");
				}
			}else{
				jSONObject.put("result", "joinFailed");
			}

		}else{
			jSONObject.put("result", "idCheckFailed");
		}
		String jSONStr = jSONObject.toJSONString();
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONStr);

		return null;
	}

}
