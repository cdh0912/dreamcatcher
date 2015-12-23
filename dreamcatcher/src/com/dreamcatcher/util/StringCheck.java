package com.dreamcatcher.util;

public class StringCheck {

	public static String nullToBlank(String tmp) {
		return tmp == null ? "" : tmp;
	}
	
	public static String nullToNoName(String tmp) {
		return tmp == null ? "제목없음" : tmp;
	}
	
}
