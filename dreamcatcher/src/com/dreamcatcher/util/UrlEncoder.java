package com.dreamcatcher.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncoder {
	public static String encode(String tempStr){
		String url = "";
		try {
			if(tempStr != null)
				url = URLEncoder.encode(tempStr, CharacterConstant.DEFAULT_CHAR);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
