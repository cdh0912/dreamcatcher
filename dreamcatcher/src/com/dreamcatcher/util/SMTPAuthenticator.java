package com.dreamcatcher.util;

import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;


public class SMTPAuthenticator extends Authenticator {

   public SMTPAuthenticator() {

       super();

   }



   public PasswordAuthentication getPasswordAuthentication() {

       String username = CommonConstant.ADMIN_EMAIL_ID;

       String password = CommonConstant.ADMIN_EMAIL_PASSWORD;

       return new PasswordAuthentication(username, password);

   }

}

