package com.dreamcatcher.member.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.util.function.Consumer;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.service.MemberServiceImpl;
import com.dreamcatcher.util.*;
import com.google.gson.Gson;

public class MemberLoginWithTwitterCallbackAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		String CONSUMER_KEY = CommonConstant.TWITTER_CONSUMER_KEY; //APP등록 후 받은 consumer key
		String CONSUMER_SECRET = CommonConstant.TWITTER_CONSUMER_SECRET; //APP등록 후 받은 consumer secret
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		String oauthToken = request.getParameter("oauth_token"); //요청토큰 일치여부 확인을 위한 token key
		String oauthVerifier = request.getParameter("oauth_verifier"); //인증 검증키
		RequestToken requestToken = (RequestToken)request.getSession().getAttribute("requestToken"); //위에서 세션 저장한 요청 토큰 정보
		
		String id = null;
		String name = null;
		//트위터 인증 시도
		if (requestToken.getToken().equals(oauthToken)) { //요청 일치여부 확인
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier); //검증
				twitter.setOAuthAccessToken(accessToken); //인증정보 저장
				User user = twitter.showUser(twitter.getScreenName());
				name = StringCheck.nullToBlank(user.getName());
				//System.out.println("name : " + name);
				id = StringCheck.nullToBlank(user.getScreenName());
				//System.out.println("screenName : " + screenName);
				
			} catch (TwitterException e) {
				e.printStackTrace();
				//트위터인증실패
			}
		}
		String loginMode = "Twitter";
		int m_level = 0;
		int m_state = 1;
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		memberDto.setName(name);
		memberDto.setM_level(m_level);
		memberDto.setM_state(m_state);
		
		HttpSession session = request.getSession();
		session.setAttribute("memberInfo", memberDto);
		session.setAttribute("loginMode", loginMode);
			
		JSONObject jSONObject = new JSONObject();
		jSONObject.put("result", "loginWithOAuthSuccess");
		jSONObject.put("memberName", name);
		jSONObject.put("loginMode", loginMode);
		
		response.setContentType("text/html; charset="+CharacterConstant.DEFAULT_CHAR);  
		PrintWriter out = response.getWriter();
		out.write("<script type='text/javascript'>");
		out.write("alert('"+name+"("+id+") 님!\n환영합니다!\nTwitter 계정으로 로그인하셨습니다.');");
		out.write("</script>");
		return "/main";
	}

}
