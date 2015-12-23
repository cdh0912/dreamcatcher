package com.dreamcatcher.member.action;

import java.io.*;
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

public class MemberLoginWithTwitterAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// 트위터 연동의 주체는 Twitter 객체이다.
		Twitter twitter = new TwitterFactory().getInstance(); 

		// 각각의 파라미터에 Key와 Secret을 입력해주어야한다.
		twitter.setOAuthConsumer(CommonConstant.TWITTER_CONSUMER_KEY, CommonConstant.TWITTER_CONSUMER_SECRET);
		
		RequestToken requestToken = null;
		try {
			requestToken = twitter.getOAuthRequestToken();
			//System.out.println(requestToken);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("requestToken",requestToken); //요청 토큰 정보를 세션에 구움
		return requestToken.getAuthorizationURL();
	}

}
