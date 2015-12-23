package com.dreamcatcher.member.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.factory.MemberActionFactory;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.CharacterConstant;
import com.dreamcatcher.util.StringCheck;
import com.google.gson.Gson;

public class MemberLoginAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String id = StringCheck.nullToBlank(request.getParameter("id"));
		String password = StringCheck.nullToBlank(request.getParameter("password"));

		Map validMap = new HashMap();
		validMap.put("id",id);
		validMap.put("password", password);
		MemberDto memberDto = MemberServiceImpl.getInstance().login(validMap);
		
		JSONObject jSONObject = new JSONObject();
		if(memberDto != null){
			String idSave = StringCheck.nullToBlank(request.getParameter("idSave"));
			if("saveOk".equals(idSave)){
				Cookie cookie = new Cookie("loginId", id);
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(60*60*24*365*10); // 10��
				response.addCookie(cookie);
			}else{
				Cookie cookies[] = request.getCookies();
				if( cookies != null ){
					int length = cookies.length;
					for(int i=0; i < length; i++){
						if(cookies[i].getName().equals("loginId")){
							cookies[i].setPath(request.getContextPath()); // ���� �� ��� ������ ����
							cookies[i].setMaxAge(0);
							response.addCookie(cookies[i]);
							break;	// return �ϸ� ����!!!
						}
					}
				}
			}
			int memberState = memberDto.getM_state();
			
			// memberState = 0 : ���Դ�� ���� | 1 : ���ԿϷ� ���� | 2: ��й�ȣ ���� ����
			if(memberState == 1){
				HttpSession session = request.getSession();
				session.setAttribute("memberInfo", memberDto);
			}else{
				Gson gson = new Gson();
				jSONObject.put("memberInfo",gson.toJsonTree(memberDto));
			}			
			jSONObject.put("result", "loginSuccess"); // �������� �α���
			jSONObject.put("memberState", memberState);		// ȸ�� ���� ���

		}else{
			jSONObject.put("result", "loginFailed");		// �α��� ����

		}
		
		String jSONStr = jSONObject.toJSONString();
		
		response.setContentType("text/plain; charset="+CharacterConstant.DEFAULT_CHAR);  
		response.getWriter().write(jSONStr);
		
		return null;
	}

}
