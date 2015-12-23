package com.dreamcatcher.admin.model.service;

import java.security.GeneralSecurityException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dreamcatcher.admin.model.StatisticsDto;
import com.dreamcatcher.admin.model.dao.AdminDaoImpl;
import com.dreamcatcher.member.model.MemberDto;
import com.dreamcatcher.util.*;
import com.sun.mail.handlers.message_rfc822;
import com.sun.mail.util.MailSSLSocketFactory;


public class AdminServiceImpl implements AdminService {
	
	private static AdminService adminService;
	
	private AdminServiceImpl(){}
	
	static{
		adminService = new AdminServiceImpl();
	}
	
	public static AdminService getInstance(){
		return adminService;
	}

	@Override
	public int memberStateChange(Map memberStateMap) {
		return AdminDaoImpl.getInstance().memberStateChange(memberStateMap);
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
	public int sendTempPassword(MemberDto memberDto) {
		int cnt = 0;
		
		String admin_name = CommonConstant.ADMIN_NAME;
		String admin_email = CommonConstant.ADMIN_EMAIL;
		String siteUrl = CommonConstant.SITE_DOMAIN;
		
		String member_id = memberDto.getId();
		String member_password = memberDto.getPassword();
		
		String title = "DREAM CATCHER - ["+member_id+"]님의 임시 패스워드 발송";
		StringBuffer message = new StringBuffer();
		message.append("보낸 사람 : " + admin_name + "&lt;" + admin_email + "&gt;<br><br>");
		message.append("받는 사람 : &lt;" + member_id + "&gt;<br><br>");
		message.append("<strong>"+member_id+"</strong> 님의 임시 비밀번호는 <strong>"+member_password+"</strong> 입니다.<br><br>");
		message.append("임시 비밀번호로 로그인 후 비밀번호를 변경하시면 정상적으로 서비스를 이용하실 수 있습니다..<br><br>");				
		
		

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
			Address fromAddr = new InternetAddress(admin_name+"<"+admin_email+">"); // 보내는 사람의 메일주소
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(member_id); // 받는 사람의 메일주소
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


	@Override
	public int sendAuthCode(MemberDto memberDto) {
		int cnt = 0;
		String member_name = memberDto.getName();
		String member_id = memberDto.getId();
		
		String admin_name = CommonConstant.ADMIN_NAME;
		String admin_email = CommonConstant.ADMIN_EMAIL;
		String siteUrl = CommonConstant.SITE_DOMAIN;
		
		String authCode = RandomString.getUUID();
		
		String title = "Dream Catcher - "+member_name+"("+member_id+")님의 회원가입 인증";
		StringBuffer message = new StringBuffer();
		message.append("보낸 사람 : " + admin_name + "&lt;" + admin_email + "&gt;<br><br>");
		message.append("받는 사람 : "+ member_name + "&lt;" + member_id + "&gt;<br><br>");
		message.append("<strong>"+member_name+"</strong>님의 회원가입을 위한 인증 절차를 위해<br><br>");
		message.append("다음 링크를 클릭해주세요.<br><br>");
//		message += "<form name=\"authForm\" method=\"POST\" action=\"\" onsubmit=\"return false;\">";
//		message += "<input type=\"hidden\" id=\"id\" name=\"id\" value=\""+to_id+"\">";
//		message += "<input type=\"hidden\" id=\"email\" name=\"email\" value=\""+to_email+"\">";
//		message += "<input type=\"hidden\" id=\"authCode\" name=\"authCode\" value=\""+authCode+"\">";
//		message += "<input type=\"button\" id=\"sendButton\" value=\"회원인증\" onClick=\"javascript:function(){"
//				+ "window.open(\""+siteUrl+"\");};\">";
//		message += "</form>";
		message.append("<a href=\""+siteUrl+"/admin?act=memberJoinConfirm&id="+member_id +"&authCode="+authCode+"\"><font color=\"GREEN\">회원인증</font></a>");
				
		

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
			Address fromAddr = new InternetAddress(admin_name+"<"+admin_email+">"); // 보내는	사람의 메일주소
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(member_id); // 받는 사람의 메일주소
			msg.addRecipient(Message.RecipientType.TO, toAddr);
//			System.out.println("Message: " + msg.getContent());
			Transport.send(msg);
			System.out.println("Gmail SMTP서버를 이용한 메일보내기 성공");
			saveAuthCodeFile(member_id, authCode);
			cnt = 1;
		} catch (Exception mex) { 
//			System.out.println("이메일 전송 중 Error 발생");
			mex.printStackTrace();
			cnt = -1;
		}
		return cnt;
	}
	
	private void saveAuthCodeFile(String member_id, String authCode) {
		String path = this.getClass().getResource("").getPath(); 
		PropertyManager pManager = PropertyManager.getInstance(path+"/authCode.properties");
		pManager.setProperty(member_id, authCode);
	}
	
	public static void main(String[] args) {
		MemberDto memberDto = new MemberDto();
		memberDto.setId("shivan1@naver.com");
		memberDto.setName("정현태");
		memberDto.setPassword("asdgsag");
		AdminServiceImpl.getInstance().sendAuthCode(memberDto);
	}

	@Override
	public int verifyAuthCode(Map memberStateMap) {
		int cnt = 0;
		String id = (String)memberStateMap.get("id");
		String authCode = (String)memberStateMap.get("authCode");
		String path = this.getClass().getResource("").getPath(); 
		PropertyManager pManager = PropertyManager.getInstance(path+"/authCode.properties");
		String validAuthCode = pManager.getProperty(id);
		System.out.println(validAuthCode + "    " + authCode);	
		if(authCode.equals(validAuthCode)){
			System.out.println("회원 이메일 인증 성공");
			pManager.removeProperty(id);
			cnt = 1;
		}else{
			System.out.println("회원 이메일 인증 실패");
		}
		return cnt;
	}
	
	
	@Override
	public int siteStateChange(Map siteStateMap) {
		return AdminDaoImpl.getInstance().siteStateChange(siteStateMap);
	}

	@Override
	public List<StatisticsDto>  locationRankByRecommend(int range) {
		return AdminDaoImpl.getInstance().locationRankByRecommend(range);
	}

	@Override
	public List<StatisticsDto>  siteRankByRecommend(int range) {
		return AdminDaoImpl.getInstance().siteRankByRecommend(range);
	}

	@Override
	public List<StatisticsDto>  siteRankByRoute(int range) {
		return AdminDaoImpl.getInstance().siteRankByRoute(range);
	}


	
	

}
