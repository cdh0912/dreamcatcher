package com.dreamcatcher.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamcatcher.factory.MemberActionFactory;
import com.dreamcatcher.util.*;


@WebServlet("/member")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(CharacterConstant.DEFAULT_CHAR);
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String root = request.getContextPath();
		String act = request.getParameter("act");
		//int bcode = NumberCheck.nullToZero(request.getParameter("bcode"));
		//int pg = NumberCheck.nullToOne(request.getParameter("pg"));
		//String key = StringCheck.nullToBlank(request.getParameter("key"));
		//String word = Encoder.isoToEuc(StringCheck.nullToBlank(request.getParameter("word")));
		
		//String queryString = "bcode=" + bcode + "&pg=" + pg + "&key=" + key + "&word=" + UrlEncoder.encode(word);
		//System.out.println("QS = " + queryString);
		
		String path = "/main";
		System.out.println("action = " + act);
		if("moveLogin".equals(act)) {
			PageMove.redirect(response, root + "/member/login.jsp");
		} else if("moveRegister".equals(act)) {
			PageMove.redirect(response, root + "/member/register.jsp");
		} else if("moveForgotPass".equals(act)) {
			PageMove.redirect(response, root + "/member/forgotPass.jsp");
		} else if("moveResetPass".equals(act)) {
			PageMove.forward(request,response, "/member/resetPass.jsp");
		} else if("moveModify".equals(act)) {
			PageMove.forward(request,response, "/member/modifyInfo.jsp");
		} else if("moveContact".equals(act)) {
			PageMove.redirect(response, root + "/etc/contact.tiles");
		} else if("idCheck".equals(act)) {
			MemberActionFactory.getMemberIdCheckAction().execute(request, response);
		} else if("passwordCheck".equals(act)) {
			MemberActionFactory.getMemberPasswordCheckAction().execute(request, response);
		} else if("join".equals(act)) {
			MemberActionFactory.getMemberJoinAction().execute(request, response);
		} else if("login".equals(act)) {
			MemberActionFactory.getMemberLoginAction().execute(request, response);
		} else if("logout".equals(act)) {
			MemberActionFactory.getMemberLogoutAction().execute(request, response);
		} else if("resetPassword".equals(act)) {
			MemberActionFactory.getMemberResetPasswordAction().execute(request, response);
		} else if("modifyInfo".equals(act)) {
			MemberActionFactory.getMemberModifyAction().execute(request, response);
		} else if("moveMyInfo".equals(act)) {
			PageMove.redirect(response, root + "/myInfo.tiles");
		} else if("myInfo".equals(act)) {
			MemberActionFactory.getMemberMyInfoAction().execute(request, response);
		} else if("sendContactMail".equals(act)) {
			MemberActionFactory.getMemberContactAction().execute(request, response);
		} else if("loginWithOAuth".equals(act)) {
			MemberActionFactory.getMemberLoginWithOAuthAction().execute(request, response);
		} else if("loginWithTwitter".equals(act)) {
			path = MemberActionFactory.getMemberLoginWithTwitterAction().execute(request, response);
			PageMove.redirect(response, path);
		} else if("twitterCallback".equals(act)) {
			path = MemberActionFactory.getMemberLoginWithTwitterCallbackAction().execute(request, response);
			PageMove.redirect(response, root + path);
		} else {
			PageMove.redirect(response, root + path);
		}
	}

}
