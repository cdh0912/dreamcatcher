package com.dreamcatcher.member.model.service;

import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.member.model.dao.MemberDaoImpl;
import com.dreamcatcher.util.*;
import com.sun.mail.util.MailSSLSocketFactory;

public class MemberServiceImpl implements MemberService {

	private static MemberService memberService;
	
	private MemberServiceImpl() {}
	
	static{
		memberService = new MemberServiceImpl();
	}
	
	public static MemberService getInstance(){
		return memberService;
	}

	// 회원가입 및 비밀번호 리셋을 위한 아이디 중복 검사
	@Override
	public int idCheck(String id) {
		return MemberDaoImpl.getInstance().idCheck(id);
	}

	// 신규 회원가입
	@Override
	public int join(MemberDto memberDto) {
		return MemberDaoImpl.getInstance().join(memberDto);
	}

	// 로그인
	@Override
	public MemberDto login(Map<String,String> validMap) {
		return MemberDaoImpl.getInstance().login(validMap);
	}

	// 회원정보 수정
	@Override
	public int modifyInfo(MemberDto memberDto) {
		return MemberDaoImpl.getInstance().modifyInfo(memberDto);
	}

	// 비밀번호 리셋 및 회원상태 변경
	@Override
	public int resetPassword(MemberDto memberDto) {
		return MemberDaoImpl.getInstance().resetPassword(memberDto);
	}

	// 회원정보
	@Override
	public MemberDto getMemberInfo(String id) {
		// TODO Auto-generated method stub
		return MemberDaoImpl.getInstance().getMemberInfo(id);
	}

	// 패스워드 검사
	@Override
	public int passwordCheck(Map<String, String> validMap) {
		return MemberDaoImpl.getInstance().passwordCheck(validMap);
	}
	
	private Properties getSMTPProperties() throws GeneralSecurityException{
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", CommonConstant.MAIL_SMTP_HOST);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.ssl.enable", "false");
		props.put("mail.smtp.ssl.trust", CommonConstant.MAIL_SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		MailSSLSocketFactory sf=new MailSSLSocketFactory();

		  sf.setTrustAllHosts(true);

		  props.put("mail.smtp.ssl.socketFactory", sf);

		props.put("mail.smtp.user", CommonConstant.ADMIN_EMAIL_ID); // Google계정@gmail.com으로 설정
		
		// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		return props;
	}
	
	@Override
	public int sendContactMail(Map<String, String> mailMap) {
		int cnt = 0;
		
	
		String member_name = mailMap.get("name");
		String member_Id = mailMap.get("id");
		String content = mailMap.get("message");
		content = content.replaceAll("\n", "<br>");
		String admin_email = CommonConstant.ADMIN_EMAIL;
		String siteUrl = CommonConstant.SITE_DOMAIN;

		
		String title = "DREAM CATCHER - " + mailMap.get("subject");
		StringBuffer message = new StringBuffer();
		message.append("보낸 사람 : " + member_name + "&lt;" + member_Id + "&gt;<br><br>");
		message.append("받는 사람 : &lt;" + admin_email + "&gt;<br><br>");
		message.append(content);				
		
		

		try {
			Properties props = getSMTPProperties();
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.

			// session = Session.getDefaultInstance(p);
			MimeMessage msg = new MimeMessage(session);
			
			msg.setSubject(title);
			msg.setContent(message.toString(), "text/html; charset="+CharacterConstant.DEFAULT_CHAR);
			msg.setHeader("Content-type", "text/html; charset="+CharacterConstant.DEFAULT_CHAR);
			Address fromAddr = new InternetAddress(member_name+"<"+member_Id+">"); // 보내는 사람의 메일주소
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(admin_email); // 받는 사람의 메일주소
			msg.addRecipient(Message.RecipientType.TO, toAddr);
//			System.out.println("Message: " + msg.getContent());
			Transport.send(msg);
			System.out.println("Gmail SMTP서버를 이용한 메일보내기 성공");
			cnt = 1;
		} catch (Exception mex) {
			System.out.println("이메일 전송 중 Error 발생");
			mex.printStackTrace();
			cnt = -1;
		}
		return cnt;
	}


}
