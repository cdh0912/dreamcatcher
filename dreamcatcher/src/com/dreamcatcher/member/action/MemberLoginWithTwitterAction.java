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

		// Ʈ���� ������ ��ü�� Twitter ��ü�̴�.
		Twitter twitter = new TwitterFactory().getInstance(); 

		// ������ �Ķ���Ϳ� Key�� Secret�� �Է����־���Ѵ�.
		twitter.setOAuthConsumer(CommonConstant.TWITTER_CONSUMER_KEY, CommonConstant.TWITTER_CONSUMER_SECRET);
		
		RequestToken requestToken = null;
		try {
			requestToken = twitter.getOAuthRequestToken();
			//System.out.println(requestToken);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("requestToken",requestToken); //��û ��ū ������ ���ǿ� ����
		return requestToken.getAuthorizationURL();
	}

}
