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
		
		
		String CONSUMER_KEY = CommonConstant.TWITTER_CONSUMER_KEY; //APP��� �� ���� consumer key
		String CONSUMER_SECRET = CommonConstant.TWITTER_CONSUMER_SECRET; //APP��� �� ���� consumer secret
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		String oauthToken = request.getParameter("oauth_token"); //��û��ū ��ġ���� Ȯ���� ���� token key
		String oauthVerifier = request.getParameter("oauth_verifier"); //���� ����Ű
		RequestToken requestToken = (RequestToken)request.getSession().getAttribute("requestToken"); //������ ���� ������ ��û ��ū ����
		
		String id = null;
		String name = null;
		//Ʈ���� ���� �õ�
		if (requestToken.getToken().equals(oauthToken)) { //��û ��ġ���� Ȯ��
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier); //����
				twitter.setOAuthAccessToken(accessToken); //�������� ����
				User user = twitter.showUser(twitter.getScreenName());
				name = StringCheck.nullToBlank(user.getName());
				//System.out.println("name : " + name);
				id = StringCheck.nullToBlank(user.getScreenName());
				//System.out.println("screenName : " + screenName);
				
			} catch (TwitterException e) {
				e.printStackTrace();
				//Ʈ������������
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
		out.write("alert('"+name+"("+id+") ��!\nȯ���մϴ�!\nTwitter �������� �α����ϼ̽��ϴ�.');");
		out.write("</script>");
		return "/main";
	}

}
