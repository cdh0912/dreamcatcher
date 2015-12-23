package com.dreamcatcher.member.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.model.service.AdminServiceImpl;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;
import com.google.gson.Gson;

public class MemberModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String name = StringCheck.nullToBlank(request.getParameter("name"));
		String oldPassword = StringCheck.nullToBlank(request.getParameter("oldPassword"));
		String newPassword = StringCheck.nullToBlank(request.getParameter("password"));
		
		Map validMap = new HashMap();
		validMap.put("id",id);
		validMap.put("password",oldPassword);
		System.out.println(id + " " + oldPassword);
		int validPassword = MemberServiceImpl.getInstance().passwordCheck(validMap);

		JSONObject jSONObject = new JSONObject();
		System.out.println(id + " " + oldPassword+ " " +validPassword);
		// 틀린 비밀번호 
		if( validPassword == 0 ){
			jSONObject.put("result", "invalidPassword");
			
		// 비밀번호 체크 성공
		}else if (validPassword == 1){		
			MemberDto memberDto = new MemberDto();
			memberDto.setId(id);
			memberDto.setName(name);
			if( newPassword.isEmpty() || "".equals(newPassword) )
				memberDto.setPassword(oldPassword);
			else{
				memberDto.setPassword(newPassword);
			}
			int cnt = MemberServiceImpl.getInstance().modifyInfo(memberDto);
			if(cnt == 1){
				MemberDto modifiedMemberDto = MemberServiceImpl.getInstance().getMemberInfo(id);
				if(modifiedMemberDto != null){
					HttpSession session = request.getSession();
					session.setAttribute("memberInfo", memberDto);
					Gson gson = new Gson();
					jSONObject.put("result", "modifyInfoSuccess");
					jSONObject.put("memberInfo",gson.toJsonTree(memberDto));
				}else{
					jSONObject.put("result", "getMemberInfoFailed");	
				}
			}else{
				jSONObject.put("result", "modifyInfoFailed");
			}

		}else{
			jSONObject.put("result", "passwordCheckFailed");
		}
		String jSONStr = jSONObject.toJSONString();
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONStr);

		return null;
	}

}
