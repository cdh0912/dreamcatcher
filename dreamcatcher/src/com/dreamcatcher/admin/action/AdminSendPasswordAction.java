package com.dreamcatcher.admin.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.model.service.AdminServiceImpl;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.CommonConstant;
import com.dreamcatcher.util.RandomString;
import com.dreamcatcher.util.StringCheck;

public class AdminSendPasswordAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String newPassword = RandomString.getString(CommonConstant.PASSWORD_LENGTH);
		
		
		int idExist = MemberServiceImpl.getInstance().idCheck(id);
		JSONObject jSONObject = new JSONObject();
		
		// 존재하지 않는 아이디
		if( idExist == 0 ){
			jSONObject.put("result", "idNotExist");
		// 존재하는 아이디
		}else if (idExist == 1){
			MemberDto memberDto = new MemberDto();
			memberDto.setId(id);
			memberDto.setPassword(newPassword);
			memberDto.setM_state(2); // 2 : 비밀번호 변경 대기 상태
			int cnt = MemberServiceImpl.getInstance().resetPassword(memberDto);	

			if( cnt == 1 ){
				int cnt2 = AdminServiceImpl.getInstance().sendTempPassword(memberDto);
				if( cnt2 == 1 ){
					jSONObject.put("result", "sendPasswordSuccess");
				}else{
					jSONObject.put("result", "sendPasswordFailed");
				}
			}else{
				jSONObject.put("result", "resetPasswordFailed");
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
