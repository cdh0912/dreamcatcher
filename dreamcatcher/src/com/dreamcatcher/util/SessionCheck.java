package com.dreamcatcher.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dreamcatcher.member.model.MemberDto;

public class SessionCheck {
	

	
	public static Object getAttribute(HttpServletRequest request, String attributeName){
		Object obj = null;
		HttpSession session = request.getSession();
		obj = session.getAttribute(attributeName);
		return obj;
	}

	
	public static MemberDto getMemberInfo(HttpServletRequest request, String attributeName){
		MemberDto memberDto = null;
		Object obj = getAttribute(request, attributeName);
		if(obj != null){
			memberDto = (MemberDto)obj;
		}
		return memberDto;
	}
	
	public static boolean loginCheck(HttpServletRequest request, String attributeName){
		boolean flag = false;
		Object obj = getAttribute(request, attributeName);
		if(obj != null){
			flag = true;
		}
		return flag;
	}
}
