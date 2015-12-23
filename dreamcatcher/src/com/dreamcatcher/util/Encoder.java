package com.dreamcatcher.util;

import java.io.UnsupportedEncodingException;

public class Encoder {
	public static String serverCharToDefaultChar(String tempStr){
		String euc = "";
		try {
			euc = new String(tempStr.getBytes(CharacterConstant.SERVER_CHAR),CharacterConstant.DEFAULT_CHAR);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return euc;
	}
}
